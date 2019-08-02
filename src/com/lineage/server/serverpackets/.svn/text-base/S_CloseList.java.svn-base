package com.lineage.server.serverpackets;

/**
 * 關閉對話窗
 * @author dexc
 *
 */
public class S_CloseList extends ServerBasePacket {
	
	private byte[] _byte = null;
	
	/**
	 * 關閉對話窗
	 * @param objid
	 */
	public S_CloseList(final int objid) {
		this.writeC(S_OPCODE_SHOWHTML);
		this.writeD(objid);
		this.writeS("");
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
