package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1NpcInstance;

/**
 * 物件封包
 * 
 * @author dexc
 *
 */
public class S_NPCPack extends ServerBasePacket {

	private static final int STATUS_POISON = 1;
	// private static final int STATUS_INVISIBLE = 2;
	// private static final int STATUS_PC = 4;
	/*
	 * private static final int STATUS_FREEZE = 8; private static final int
	 * STATUS_BRAVE = 16; private static final int STATUS_ELFBRAVE = 32; private
	 * static final int STATUS_FASTMOVABLE = 64; private static final int
	 * STATUS_GHOST = 128;
	 */

	private byte[] _byte = null;

	/**
	 * 物件封包
	 * 
	 * @param npc
	 */
	public S_NPCPack(final L1NpcInstance npc) {
		this.writeC(S_OPCODE_CHARPACK);
		this.writeH(npc.getX());
		this.writeH(npc.getY());
		this.writeD(npc.getId());

		if (npc.getTempCharGfx() == 0) {
			this.writeH(npc.getGfxId());

		} else {
			this.writeH(npc.getTempCharGfx());
		}

		this.writeC(npc.getStatus());
		this.writeC(npc.getHeading());
		this.writeC(npc.getChaLightSize());
		this.writeC(npc.getMoveSpeed());
		this.writeD((int) npc.getExp());
		this.writeH(npc.getTempLawful());
		this.writeS(npc.getNameId());

		this.writeS(npc.getTitle());

		/**
		 * ｼｼﾆﾃ - 0:mob,item(atk pointer), 1:poisoned(), 2:invisable(), 4:pc,
		 * 8:cursed(), 16:brave(), 32:??, 64:??(??), 128:invisable but name
		 */
		int status = 0;
		if (npc.getPoison() != null) { // 毒状態
			if (npc.getPoison().getEffectId() == 1) {
				status |= STATUS_POISON;
			}
		}
		// TODO 變怪免強制攻擊 by 0919162173
		/*
		 * if (npc.getNpcTemplate().is_doppel()) { // PC属性だとエヴァの祝福を渡せないためWIZクエストのドッペルは例外
		 * if (npc.getNpcTemplate().get_npcId() != 81069) { status |= STATUS_PC; } } if
		 * (npc.getNpcTemplate().get_npcId() == 90024) { status |= STATUS_POISON; }
		 */
		this.writeC(status); // 狀態

		this.writeD(0x00000000); // 0以外にするとC_27が飛ぶ
		this.writeS(null);
		this.writeS(null); // マスター名？

		this.writeC(0x00); // 物件分類

		this.writeC(0xff); // HP
		this.writeC(0x00);
		// this.writeC(0x00);// LV
		// TODO 99nets
		this.writeC(npc.getLevel());
		this.writeC(0x00);
		this.writeC(0xff);
		this.writeC(0xff);
	}

	@Override
	public byte[] getContent() {
		if (this._byte == null) {
			this._byte = this.getBytes();
		}
		return this._byte;
	}

	@Override
	public String getType() {
		return this.getClass().getSimpleName();
	}
}
