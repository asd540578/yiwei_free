package com.lineage.server.serverpackets;

/**
 * 傳送鎖定(座標點)
 * @author dexc
 *
 */
public class S_Teleport2 extends ServerBasePacket {

	private byte[] _byte = null;

	/**
	 * 傳送鎖定(座標點)
	 * @param mapid
	 * @param id
	 */
	public S_Teleport2(final int mapid, final int id) {
		//0000: 4a 04 00 3a 05 15 00 cc                            J..:....
		this.writeC(S_OPCODE_TELEPORTLOCK);
		this.writeH(mapid);
		this.writeD(id);// 傳送點編號
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
