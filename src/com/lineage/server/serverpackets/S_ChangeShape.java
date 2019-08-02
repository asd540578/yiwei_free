package com.lineage.server.serverpackets;

import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;

/**
 * 物件外型改變
 * @author dexc
 *
 */
public class S_ChangeShape extends ServerBasePacket {

	private byte[] _byte = null;

	
	/**
	 * 掛賣外型改變
	 * @param objId
	 * @param polyId
	 * @param currentWeapon
	 */
	public S_ChangeShape(int objId, int polyId, int currentWeapon) {
		this.writeC(S_OPCODE_POLY);
		this.writeD(objId);
		this.writeH(polyId);
		this.writeC(currentWeapon);
		this.writeC(0xff);
		this.writeC(0xff);
	}
	
	/**
	 * 物件外型改變
	 * @param obj
	 * @param polyId
	 */
	public S_ChangeShape(final L1Character obj, final int polyId) {
		this.buildPacket(obj, polyId, false);
	}

	/**
	 * 物件外型改變
	 * @param obj
	 * @param polyId
	 * @param weaponTakeoff
	 */
	public S_ChangeShape(final L1Character obj, final int polyId, final boolean weaponTakeoff) {
		this.buildPacket(obj, polyId, weaponTakeoff);
	}

	private void buildPacket(final L1Character obj, final int polyId, final boolean weaponTakeoff) {
		this.writeC(S_OPCODE_POLY);
		this.writeD(obj.getId());
		this.writeH(polyId);
		// 何故29なのか不明
		this.writeH(weaponTakeoff ? 0 : 29);
	}

	/**
	 * NPC改變外型(寵物 迷魅使用)
	 * @param pc 執行命令PC
	 * @param npc 執行命令NPC
	 * @param polyId 代號
	 */
	public S_ChangeShape(final L1PcInstance pc, final L1NpcInstance npc, final int polyId) {
		this.writeC(S_OPCODE_NPCPOLY);
		this.writeD(npc.getId());
		this.writeD(pc.getId());
		this.writeH(polyId);
		this.writeS(pc.getName());
	}

	@Override
	public byte[] getContent() {
		if (this._byte == null) {
			this._byte = this.getBytes();
		}
		return this._byte;
	}
}
