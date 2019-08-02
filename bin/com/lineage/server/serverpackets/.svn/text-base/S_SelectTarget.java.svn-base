package com.lineage.server.serverpackets;

/**
 * 選擇一個目標
 * @author DaiEn
 *
 */
public class S_SelectTarget extends ServerBasePacket {

	private byte[] _byte = null;

	/**
	 * 選擇一個目標
	 * @param ObjectId
	 */
	public S_SelectTarget(final int ObjectId) {
		this.writeC(S_OPCODE_SELECTTARGET);
		this.writeD(ObjectId);
		this.writeC(0x00);// TYPE 未知用途
		this.writeC(0x00);
		this.writeC(0x02);
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
