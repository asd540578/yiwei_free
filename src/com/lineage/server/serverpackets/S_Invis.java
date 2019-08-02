package com.lineage.server.serverpackets;

/**
 * 物件隱形
 * @author dexc
 *
 */
public class S_Invis extends ServerBasePacket {

	private byte[] _byte = null;

	/**
	 * 物件隱形
	 * @param objid
	 * @param type 0:無 1:隱形
	 */
	public S_Invis(final int objid, final int type) {
		this.buildPacket(objid, type);
	}

	private void buildPacket(final int objid, final int type) {
		//0000: 2a c5 8c b7 01 01 c1 99                            *.......
		this.writeC(S_OPCODE_INVIS);
		this.writeD(objid);
		this.writeC(type);
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
