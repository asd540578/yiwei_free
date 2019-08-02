package com.lineage.server.serverpackets;

import java.util.List;

import com.lineage.server.model.Instance.L1ItemInstance;

public class S_EquipmentWindow extends ServerBasePacket {

	// private byte[] _byte = null;
	/// ** 頭盔 */
	// public static final byte EQUIPMENT_INDEX_HEML = 1;
	/// ** 盔甲 */
	// public static final byte EQUIPMENT_INDEX_ARMOR = 2;
	/// ** T恤 */
	// public static final byte EQUIPMENT_INDEX_T = 3;
	/// ** 斗篷 */
	// public static final byte EQUIPMENT_INDEX_CLOAK = 4;
	/// ** 靴子 */
	// public static final byte EQUIPMENT_INDEX_BOOTS = 5;
	/// ** 手套 */
	// public static final byte EQUIPMENT_INDEX_GLOVE = 6;
	/// ** 盾 */
	// public static final byte EQUIPMENT_INDEX_SHIELD = 7;
	/// ** 武器 */
	// public static final byte EQUIPMENT_INDEX_WEAPON = 8;
	/// ** 項鏈 */
	// public static final byte EQUIPMENT_INDEX_NECKLACE = 10;
	/// ** 腰帶 */
	// public static final byte EQUIPMENT_INDEX_BELT = 11;
	/// ** 耳環 */
	// public static final byte EQUIPMENT_INDEX_EARRING = 12;
	/// ** 戒指1 */
	// public static final byte EQUIPMENT_INDEX_RING1 = 18;
	/// ** 戒指2 */
	// public static final byte EQUIPMENT_INDEX_RING2 = 19;
	/// ** 戒指3 */
	// public static final byte EQUIPMENT_INDEX_RING3 = 20;
	/// ** 戒指4 */
	// public static final byte EQUIPMENT_INDEX_RING4 = 21;
	/// ** 符紋 */
	// public static final byte EQUIPMENT_INDEX_RUNE1 = 22;
	// public static final byte EQUIPMENT_INDEX_RUNE2 = 23;
	// public static final byte EQUIPMENT_INDEX_RUNE3 = 24;
	// public static final byte EQUIPMENT_INDEX_RUNE4 = 25;
	// public static final byte EQUIPMENT_INDEX_RUNE5 = 26;

	private byte[] _byte = null;

	public static final int TYPE_1 = 65;
	public static final int TYPE_2 = 66;
	public static final int TYPE_3 = 67;
	public static final int TYPE_4 = 68;

	
	/**
	 * 顯示指定物品到裝備窗口
	 * 
	 * @param itemObjId
	 *            對象ID
	 * @param index
	 *            序號
	 * @param equipped
	 *            0:脫下 1:使用
	 */
	public S_EquipmentWindow(final int itemObjId, final int index,
			final boolean equipped) {
		this.writeC(S_OPCODE_CHARRESET);
		this.writeC(TYPE_2);
		this.writeD(itemObjId);
		this.writeC(index);
		this.writeBoolean(equipped);
	}

	/**
	 * 顯示裝備中道具 (登入遊戲才發送)
	 * 
	 * @param items
	 */
	public S_EquipmentWindow(final List<L1ItemInstance> items) {
		this.writeC(S_OPCODE_CHARRESET);
		this.writeC(TYPE_1);
		this.writeC(items.size());

		for (final L1ItemInstance item : items) {
			this.writeD(item.getId());
			this.writeH(item.getEquipWindow());
			this.writeH(0x00);
		}
	}

	/**
	 * 
	 * @param type
	 * @param count
	 * @param type2
	 */
	public S_EquipmentWindow(final int type, final int count, final int type2) {
		this.writeC(S_OPCODE_CHARRESET);
		this.writeC(type);
		this.writeD(count);

		// unknown
		this.writeD(type2);
		this.writeD(type2);
		this.writeD(type2);
		this.writeD(type2);
	}

	/**
	 * 
	 * @param size
	 */
	public S_EquipmentWindow(final int size) {
		this.writeC(S_OPCODE_CHARRESET);
		this.writeC(TYPE_4);
		this.writeD(size);
	}
	
	
	/**
	 * 顯示指定物品到裝備窗口
	 * 
	 * @param itemObjId
	 *            對象ID
	 * @param index
	 *            序號
	 * @param isEq
	 *            0:脫下 1:使用
	 */
//	public S_EquipmentWindow(final L1PcInstance pc, final int itemObjId, final int index, final boolean isEq) {
//		this.writeC(S_OPCODE_CHARRESET);
//		this.writeC(0x42);
//		this.writeD(itemObjId);
//		this.writeC(index);
//		if (isEq)
//			this.writeC(1); // 使用
//		else
//			this.writeC(0); // 解除
//	}

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