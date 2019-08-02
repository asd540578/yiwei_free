package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.L1Character;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.model.skill.L1SkillId;
import com.lineage.server.serverpackets.S_OwnCharStatus2;

/**
 * 恐慌
 * @author dexc
 *
 */
public class PANIC extends SkillMode {

	public PANIC() {
	}

	@Override
	public int start(final L1PcInstance srcpc, final L1Character cha, final L1Magic magic, final int integer) throws Exception {
		final int dmg = 0;
		if (!cha.hasSkillEffect(L1SkillId.PANIC)) {
			if (cha instanceof L1PcInstance) {
				final L1PcInstance pc = (L1PcInstance) cha;
				pc.addStr((byte) -5);
				pc.addCon((byte) -5);
				pc.addDex((byte) -5);
				pc.addWis((byte) -5);
				pc.addInt((byte) -5);

				pc.setSkillEffect(L1SkillId.PANIC, integer * 1000);
				//pc.sendPackets(new S_OwnCharStatus(pc));
				pc.sendPackets(new S_OwnCharStatus2(pc));
				
			} else if ((cha instanceof L1MonsterInstance)
					|| (cha instanceof L1SummonInstance)
					|| (cha instanceof L1PetInstance)) {
				final L1NpcInstance tgnpc = (L1NpcInstance) cha;
				tgnpc.addStr((byte) -5);
				tgnpc.addCon((byte) -5);
				tgnpc.addDex((byte) -5);
				tgnpc.addWis((byte) -5);
				tgnpc.addInt((byte) -5);
				
				tgnpc.setSkillEffect(L1SkillId.PANIC, integer * 1000);
			}
		}
		return dmg;
	}

	@Override
	public int start(final L1NpcInstance npc, final L1Character cha, final L1Magic magic,
			final int integer) throws Exception {
		final int dmg = 0;
		
		return dmg;
	}

	@Override
	public void start(final L1PcInstance srcpc, final Object obj) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void stop(final L1Character cha) throws Exception {
		if (cha instanceof L1PcInstance) {
			final L1PcInstance pc = (L1PcInstance) cha;
			pc.addStr(5);
			pc.addCon(5);
			pc.addDex(5);
			pc.addWis(5);
			pc.addInt(5);

		} else if ((cha instanceof L1MonsterInstance)
				|| (cha instanceof L1SummonInstance)
				|| (cha instanceof L1PetInstance)) {
			final L1NpcInstance tgnpc = (L1NpcInstance) cha;
			tgnpc.addStr(5);
			tgnpc.addCon(5);
			tgnpc.addDex(5);
			tgnpc.addWis(5);
			tgnpc.addInt(5);
		}
	}
}
