package com.lineage.server.serverpackets;

import static com.lineage.server.model.skill.L1SkillId.SHAPE_CHANGE;

import java.util.concurrent.atomic.AtomicInteger;

import com.lineage.server.ActionCodes;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;

/**
 * 物件攻擊(技能使用)
 * @author dexc
 *
 */
public class S_UseAttackSkill extends ServerBasePacket {

	private static AtomicInteger _sequentialNumber = new AtomicInteger(4500000); 

	private byte[] _byte = null;

	/**
	 * 物件攻擊(武器 技能使用-不需動作代號-不送出傷害)
	 * @param cha 執行者
	 * @param targetobj 目標OBJID
	 * @param spellgfx 遠程動畫編號
	 * @param x X點
	 * @param y Y點
	 * @param actionId 動作代號
	 * @param motion 具有執行者
	 */
	public S_UseAttackSkill(final L1Character cha, final int targetobj, final int spellgfx,
			final int x, final int y, final int actionId, final boolean motion) {
		this.buildPacket(cha, targetobj, spellgfx, x, y, actionId, 0, motion);
	}

	/**
	 * 物件攻擊(NPC / PC 技能使用)
	 * @param cha 執行者
	 * @param targetobj 目標OBJID
	 * @param spellgfx 遠程動畫編號
	 * @param x X點
	 * @param y Y點
	 * @param actionId 動作代號
	 * @param dmg 傷害力
	 */
	public S_UseAttackSkill(final L1Character cha, final int targetobj, final int spellgfx,
			final int x, final int y, final int actionId, final int dmg) {
		this.buildPacket(cha, targetobj, spellgfx, x, y, 18, dmg, true);
	}

	/**
	 * 物件攻擊(技能使用 - PC/NPC共用)
	 * @param cha 執行者
	 * @param targetobj 目標OBJID
	 * @param spellgfx 遠程動畫編號
	 * @param x X點
	 * @param y Y點
	 * @param actionId 動作代號
	 * @param dmg 傷害力
	 * @param withCastMotion 具有執行者
	 */
	private void buildPacket(final L1Character cha, final int targetobj, 
			final int spellgfx,
			final int x, final int y, 
			int actionId, 
			final int dmg, 
			final boolean withCastMotion) {
		if (cha instanceof L1PcInstance) {
			// 變身中變動作代號異動
			if (cha.hasSkillEffect(SHAPE_CHANGE)
					&& (actionId == ActionCodes.ACTION_SkillAttack)) {
				
				final int tempchargfx = cha.getTempCharGfx();
				if ((tempchargfx == 5727) || (tempchargfx == 5730)) {
					// 物件具有變身 改變動作代號
					actionId = ActionCodes.ACTION_SkillBuff;
					
				} else if ((tempchargfx == 5733) || (tempchargfx == 5736)) {
					// 物件具有變身 改變動作代號
					actionId = ActionCodes.ACTION_Attack;
				}
			}
		}
		
		//System.out.println("bug" + cha.getTempCharGfx());
		switch(cha.getTempCharGfx()) {
		case 4013:// 火靈之主動作代號強制變更
			actionId = ActionCodes.ACTION_Attack;
			break;
		case 3049:// 黑豹動作代號強制變更
		case 9842:// 黑豹動作代號強制變更
		case 7260: //火靈王強制班更
			actionId = ActionCodes.ACTION_AltAttack;
			break;
		case 2747: //火靈王強制班更
			actionId = ActionCodes.ACTION_SkillAttack;
			break;
//		case 6396:// 黃金龍
//			actionId = ActionCodes.ACTION_SkillAttack;
//			break;
		}
		
		/*if (cha.getTempCharGfx() == 4013) {
			actionId = ActionCodes.ACTION_Attack;
		}**/

		// 設置新面向
		final int newheading = calcheading(cha.getX(), cha.getY(), x, y);
		cha.setHeading(newheading);
		/*
		0000: 5e 12 1a cc bd 01 a4 6c 00 00 04 00 05 a3 d2 bd    ^......l........
		0010: 01 a7 00 06 c3 83 e1 7e c1 83 e5 7e 00 00 00 af    .......~...~....

		0000: 5e 12 1a cc bd 01 a4 6c 00 00 07 00 05 ff d6 bd    ^......l........
		0010: 01 a7 00 06 c3 83 e1 7e c1 83 e5 7e 00 00 00 1a    .......~...~....

		0000: 5e 12 1a cc bd 01 3c 20 00 00 07 00 05 f2 da bd    ^.....< ........
		0010: 01 a7 00 06 c3 83 e1 7e c0 83 e5 7e 00 00 00 a9    .......~...~....
		// 吸吻
		0000: 5e 12 e1 b1 63 00 a9 1f 00 00 23 00 06 93 c4 65    ^...c.....#....e
		0010: 00 ec 00 00 68 7f 96 81 67 7f 96 81 00 00 00 19    ....h..g......
		*/
		this.writeC(S_OPCODE_ATTACKPACKET);
		this.writeC(actionId);// 動作代號
		this.writeD(withCastMotion ? cha.getId() : 0x00000000);// 執行者OBJID
		this.writeD(targetobj);// 目標OBJID
		
		if (dmg > 0) {
			this.writeH(0x000a); // 傷害值
			
		} else {
			this.writeH(0x0000); // 傷害值
		}
		
		this.writeC(newheading);// 新面向
		
		// 以原子方式将当前值加 1。
		this.writeD(_sequentialNumber.incrementAndGet());
		
		this.writeH(spellgfx);// 遠程動畫編號
		this.writeC(0x00); // 具備飛行動畫:6, 不具備飛行動畫:0
		this.writeH(cha.getX());// 執行者X點
		this.writeH(cha.getY());// 執行者Y點
		this.writeH(x);// 目標X點
		this.writeH(y);// 目標Y點

		this.writeD(0x00000000);
		this.writeC(0x00);
		//this.writeC(0x00);
		//this.writeC(0x00);
		//this.writeC(0x00);
	}

	private static int calcheading(final int myx, final int myy, final int tx, final int ty) {
		int newheading = 0;
		if ((tx > myx) && (ty > myy)) {
			newheading = 3;
		}
		if ((tx < myx) && (ty < myy)) {
			newheading = 7;
		}
		if ((tx > myx) && (ty == myy)) {
			newheading = 2;
		}
		if ((tx < myx) && (ty == myy)) {
			newheading = 6;
		}
		if ((tx == myx) && (ty < myy)) {
			newheading = 0;
		}
		if ((tx == myx) && (ty > myy)) {
			newheading = 4;
		}
		if ((tx < myx) && (ty > myy)) {
			newheading = 5;
		}
		if ((tx > myx) && (ty < myy)) {
			newheading = 1;
		}
		return newheading;
	}

	@Override
	public byte[] getContent() {
		if (this._byte == null) {
			this._byte = this.getBytes();
		} else {
			//TODO 修正無預警斷線問題byl1j-tw-99nets開發團隊
			int seq = _sequentialNumber.incrementAndGet();
			_byte[13] = (byte) (seq & 0xff);
			_byte[14] = (byte) (seq >> 8 & 0xff);
			_byte[15] = (byte) (seq >> 16 & 0xff);
			_byte[16] = (byte) (seq >> 24 & 0xff);
			//TODO 修正無預警斷線問題byl1j-tw-99nets開發團隊W
		}
		return this._byte;
	}

	@Override
	public String getType() {
		return this.getClass().getSimpleName();
	}
}