package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1PcInstance;

/**
 * 更新角色防禦屬性
 * @author dexc
 *
 */
public class S_OwnCharAttrDef extends ServerBasePacket {

	private byte[] _byte = null;

	/**
	 * 更新角色防禦屬性
	 * @param pc
	 */
	public S_OwnCharAttrDef(final L1PcInstance pc) {
		this.buildPacket(pc);
	}

	private void buildPacket(final L1PcInstance pc) {
		this.writeC(S_OPCODE_OWNCHARATTRDEF);
		int ac = pc.getAc();

		this.writeC(ac);
		this.writeH(pc.getFire());
		this.writeH(pc.getWater());
		this.writeH(pc.getWind());
		this.writeH(pc.getEarth());
	}

	/**
	 * 更新角色防禦屬性-測試
	 * @param pc
	 */
	public S_OwnCharAttrDef() {
		this.writeC(S_OPCODE_OWNCHARATTRDEF);
		this.writeC(-20);
		this.writeH(30);
		this.writeH(30);
		this.writeH(30);
		this.writeH(30);
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
