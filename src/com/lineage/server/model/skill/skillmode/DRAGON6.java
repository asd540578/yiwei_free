package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.L1Character;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.skill.L1SkillId;
import com.lineage.server.serverpackets.S_PacketBoxIcon1;

/**
 * 誕生:物理攻擊迴避率+10% 魔法傷害減免+50 暗黑耐性+3
 * @author dexc
 *
 */
public class DRAGON6 extends SkillMode {

	public DRAGON6() {
	}

	@Override
	public int start(final L1PcInstance srcpc, final L1Character cha, final L1Magic magic, final int integer) throws Exception {
		final int dmg = 0;
		if (!srcpc.hasSkillEffect(L1SkillId.DRAGON6)) {
			srcpc.addRegistBlind(+3);
			srcpc.setSkillEffect(L1SkillId.DRAGON6, integer * 1000);
			srcpc.add_dodge(1); // 閃避率 + 10%
			// 更新閃避率顯示
			srcpc.sendPackets(new S_PacketBoxIcon1(true, srcpc.get_dodge()));
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
		cha.addRegistBlind(-3);
		cha.add_dodge(-1); // 閃避率 - 10%
		if (cha instanceof L1PcInstance) {
			final L1PcInstance pc = (L1PcInstance) cha;
			// 更新閃避率顯示
			pc.sendPackets(new S_PacketBoxIcon1(true, pc.get_dodge()));
		}
	}
}
