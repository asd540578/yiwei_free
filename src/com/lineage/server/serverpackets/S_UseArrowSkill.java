package com.lineage.server.serverpackets;

import java.util.concurrent.atomic.AtomicInteger;

import com.lineage.server.model.L1Character;

/**
 * 物件攻擊(遠程-物理攻擊 PC/NPC共用)
 * @author dexc
 *
 */
public class S_UseArrowSkill extends ServerBasePacket {

	private static AtomicInteger _sequentialNumber = new AtomicInteger(0);
	
	private byte[] _byte = null;

	/**
	 * 物件攻擊 - <font color="#ff0000">命中</font>(遠程-物理攻擊 PC/NPC共用)
	 * @param cha 執行者
	 * @param targetobj 目標OBJID
	 * @param spellgfx 遠程動畫編號
	 * @param x 目標X
	 * @param y 目標Y
	 * @param dmg 傷害力
	 */
	public S_UseArrowSkill(final L1Character cha, final int targetobj, 
			final int spellgfx, 
			final int x,
			final int y, 
			final int dmg) {

		int aid = 1;
		// 外型編號改變動作
		switch (cha.getTempCharGfx()) {
		case 3860:// 妖魔弓箭手
		case 6269:
		case 13346:
			aid = 21;
			break;
//		case 7220:// 小猴子
//			aid = 3;	
//			break;
		case 2716:// 古代亡靈
			aid = 19;
			break;
		}

		/*
		0000: 5e 01 8e 24 bb 01 a4 6c 00 00 0a 00 05 52 01 00    ^..$...l.....R..
		0010: 00 42 00 00 c3 83 e1 7e c1 83 e5 7e 00 00 00 85    .B.....~...~....

		0000: 5e 01 8e 24 bb 01 a4 6c 00 00 0d 00 05 52 01 00    ^..$...l.....R..
		0010: 00 42 00 00 c3 83 e1 7e c1 83 e5 7e 00 00 00 ee    .B.....~...~....

		0000: 5e 01 8e 24 bb 01 3c 20 00 00 0b 00 05 52 01 00    ^..$..< .....R..
		0010: 00 42 00 00 c3 83 e1 7e c0 83 e5 7e 00 00 00 58    .B.....~...~...X
		*/
		this.writeC(S_OPCODE_ATTACKPACKET);
		this.writeC(aid);// 動作代號
		this.writeD(cha.getId());// 執行者OBJID
		this.writeD(targetobj);// 目標OBJID
		
		if (dmg > 0) {
			this.writeH(0x0a); // 傷害值
			
		} else {
			this.writeH(0x00); // 傷害值
		}
		
		this.writeC(cha.getHeading());// 新面向
		
		// 以原子方式将当前值加 1。
		//this.writeD(0x00000152);
		this.writeD(_sequentialNumber.incrementAndGet());//TODO 修正無預警斷線問題byl1j-tw-99nets開發團隊
		this.writeH(spellgfx);// 遠程動畫編號
		this.writeC(0x7f);// ??
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

	/**
	 * 物件攻擊 - <font color="#ff0000">未命中</font>(遠程-物理攻擊 PC/NPC共用)
	 * 空攻擊使用
	 * @param cha 執行者
	 * @param spellgfx 遠程動畫編號
	 * @param x 目標X
	 * @param y 目標Y
	 */
	public S_UseArrowSkill(final L1Character cha,
			final int spellgfx, 
			final int x,
			final int y
			) {

		int aid = 1;
		// 外型編號改變動作
		if (cha.getTempCharGfx() == 3860) {
			aid = 21;
		}
		this.writeC(S_OPCODE_ATTACKPACKET);
		this.writeC(aid);// 動作代號
		this.writeD(cha.getId());// 執行者OBJID
		this.writeD(0x00);// 目標OBJID
		this.writeH(0x00);// 傷害力
		this.writeC(cha.getHeading());// 新面向
		
		// 以原子方式将当前值加 1。
		//this.writeD(0x00000152);
		this.writeD(_sequentialNumber.incrementAndGet());//TODO 修正無預警斷線問題byl1j-tw-99nets開發團隊
		
		this.writeH(spellgfx);// 遠程動畫編號
		this.writeC(0x7f);// ??
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
			//TODO 修正無預警斷線問題byl1j-tw-99nets開發團隊

		}
		return this._byte;
	}

	@Override
	public String getType() {
		return this.getClass().getSimpleName();
	}
}
