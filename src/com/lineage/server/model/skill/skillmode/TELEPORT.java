package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.L1Character;
import com.lineage.server.model.L1Location;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.serverpackets.S_ServerMessage;

/**
 * 指定傳送5
 * 
 * @author dexc
 *
 */
public class TELEPORT extends SkillMode {

	public TELEPORT() {
	}

	@Override
	public int start(final L1PcInstance srcpc, final L1Character cha, final L1Magic magic, final int integer)
			throws Exception {
		final int dmg = 0;// magic.calcMagicDamage(L1SkillId.CURE_POISON);
		final L1PcInstance pc = (L1PcInstance) cha;

		int newX = pc.getTeleportX();
		int newY = pc.getTeleportY();
		short mapId = pc.getTeleportMapId();

		if (newX > 0 && newY > 0) { // 記憶座標取出
			if (pc.getMap().isEscapable() || pc.isGm()) {

				L1Teleport.teleport(pc, newX, newY, mapId, 5, true);

			} else {
				// 276 \f1在此無法使用傳送。
				pc.sendPackets(new S_ServerMessage(276));
				pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_TELEPORT_UNLOCK, false));
			}
		} else { // 任意地點
			if (pc.getMap().isTeleportable() || pc.isGm()) {
				final L1Location newLocation = pc.getLocation().randomLocation(200, true);
				newX = newLocation.getX();
				newY = newLocation.getY();
				mapId = (short) newLocation.getMapId();

				L1Teleport.teleport(pc, newX, newY, mapId, 5, true);

			} else {
				// 276 \f1在此無法使用傳送。
				pc.sendPackets(new S_ServerMessage(276));
				pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_TELEPORT_UNLOCK, false));
			}
		}
		return dmg;
	}

	@Override
	public int start(final L1NpcInstance npc, final L1Character cha, final L1Magic magic, final int integer)
			throws Exception {
		final int dmg = 0;

		return dmg;
	}

	@Override
	public void start(final L1PcInstance srcpc, final Object obj) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop(final L1Character cha) throws Exception {
		// TODO Auto-generated method stub
	}
}
