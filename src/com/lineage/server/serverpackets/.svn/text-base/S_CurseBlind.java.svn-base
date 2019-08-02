package com.lineage.server.serverpackets;

/**
 * 魔法效果:暗盲咒術
 * @author dexc
 *
 */
public class S_CurseBlind extends ServerBasePacket {

	private byte[] _byte = null;

	/**
	 * 魔法效果:暗盲咒術
	 * @param type 0:OFF 1:自己 2:週邊物件可見
	 */
	public S_CurseBlind(final int type) {
		this.buildPacket(type);
	}

	private void buildPacket(final int type) {
		this.writeC(S_OPCODE_CURSEBLIND);
		this.writeH(type);
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
