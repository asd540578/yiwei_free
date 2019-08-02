package com.lineage.data.item_etcitem.brave;

import com.lineage.data.executor.ItemExecutor;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.skill.L1BuffUtil;
import com.lineage.server.model.skill.L1SkillId;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SkillBrave;
import com.lineage.server.serverpackets.S_SkillSound;

/**
 * 惡魔之血40031<br>
 */
public class Potion_3 extends ItemExecutor {

	/**
	 *
	 */
	private Potion_3() {
		// TODO Auto-generated constructor stub
	}

	public static ItemExecutor get() {
		return new Potion_3();
	}

	/**
	 * 道具物件執行
	 * @param data 參數
	 * @param pc 執行者
	 * @param item 物件
	 */
	@Override
	public void execute(final int[] data, final L1PcInstance pc, final L1ItemInstance item) {
		// 例外狀況:物件為空
		if (item == null) {
			return;
		}
		// 例外狀況:人物為空
		if (pc == null) {
			return;
		}

		if (L1BuffUtil.stopPotion(pc)) {
			if (pc.isCrown()) {
				this.useBravePotion(pc);
				
			} else { // \f1没有任何事情发生。
				pc.sendPackets(new S_ServerMessage(79));
			}
			pc.getInventory().removeItem(item, 1);
		}
	}

	private void useBravePotion(final L1PcInstance pc) {
		// // 解除魔法技能绝对屏障
		L1BuffUtil.cancelAbsoluteBarrier(pc);

		// 勇敢效果 抵銷對應技能
		L1BuffUtil.braveStart(pc);
		
		final int time = 700;

		pc.sendPackets(new S_SkillBrave(pc.getId(), 1, time));
		pc.broadcastPacketAll(new S_SkillBrave(pc.getId(), 1, 0));
		
		pc.sendPacketsX8(new S_SkillSound(pc.getId(), 751));
		pc.setSkillEffect(L1SkillId.STATUS_BRAVE, time * 1000);

		pc.setBraveSpeed(1);
	}
}