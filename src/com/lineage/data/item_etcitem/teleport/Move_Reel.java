package com.lineage.data.item_etcitem.teleport;

import static com.lineage.server.model.skill.L1SkillId.ABSOLUTE_BARRIER;

import com.lineage.data.executor.ItemExecutor;
import com.lineage.server.model.L1ItemDelay;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.serverpackets.S_ServerMessage;

/**
 * 魔法卷軸(指定傳送)40863<br>
 * 瞬间移动卷轴 40100 <br>
 * 瞬间移动卷轴（祝福）140100<br>
 */
public class Move_Reel extends ItemExecutor {

	/**
	 *
	 */
	private Move_Reel() {
		// TODO Auto-generated constructor stub
	}

	public static ItemExecutor get() {
		return new Move_Reel();
	}

	/**
	 * 道具物件執行
	 * 
	 * @param data 參數
	 * @param pc   執行者
	 * @param item 物件
	 */
	@Override
	public void execute(final int[] data, final L1PcInstance pc, final L1ItemInstance item) {
		// 記憶座標點排序
		final int map = data[0];
		final int x = data[1];
		final int y = data[2];

		// 所在位置 是否允許傳送
		final boolean isTeleport = pc.getMap().isTeleportable();
		if (!isTeleport) {
			// 647 這附近的能量影響到瞬間移動。在此地無法使用瞬間移動。
			pc.sendPackets(new S_ServerMessage(647));
			pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_TELEPORT_UNLOCK, false));

		} else {
			// 取出記憶座標
			if ((x > 0) && (y > 0)) {
				// 刪除道具
				pc.getInventory().removeItem(item, 1);
				L1Teleport.teleport(pc, x, y, (short)map, 5, true);

			} else {
				// 刪除道具
				pc.getInventory().removeItem(item, 1);
				L1ItemDelay.teleportUnlock(pc, item);// 99nets團隊解除傳送卡點問題 R299
				L1Teleport.randomTeleport(pc, true);
			}
			// 絕對屏障解除
			if (pc.hasSkillEffect(ABSOLUTE_BARRIER)) {
				pc.killSkillEffectTimer(ABSOLUTE_BARRIER);
				pc.startHpRegeneration();
				pc.startMpRegeneration();
			}
		}
	}
}
