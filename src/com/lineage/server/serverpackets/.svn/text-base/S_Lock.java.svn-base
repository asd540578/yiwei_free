package com.lineage.server.serverpackets;

/**
 * 座標異常重整
 * @author dexc
 *
 */
public class S_Lock extends ServerBasePacket {

	private byte[] _byte = null;

	/**
	 * 座標異常重整
	 * @param type
	 * @param equipped
	 */
	public S_Lock() {
		this.buildPacket();
	}

	private void buildPacket() {
		this.writeC(S_OPCODE_CHARLOCK);
		this.writeC(0x00);
		/*this.writeC(0xf1);
		this.writeC(0x2d);
		this.writeC(0x7d);
		this.writeC(0x02);
		this.writeC(0xf9);*/
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
