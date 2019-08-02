package com.lineage.server.serverpackets;

/**
 * 魔法效果:毒素
 * @author dexc
 *
 */
public class S_Poison extends ServerBasePacket {

	private byte[] _byte = null;

	/**
	 * 魔法效果:毒素
	 *
	 * @param objId
	 *            外見を変えるキャラクターのID
	 * @param type
	 *            外見のタイプ 0 = 通常色, 1 = 緑色, 2 = 灰色
	 */
	public S_Poison(final int objId, final int type) {
		//0000: 2f 2c 80 a1 01 00 00 08                            /,......
		this.writeC(S_OPCODE_POISON);
		this.writeD(objId);
		switch (type) {
		case 0: // 通常
			this.writeC(0x00);
			this.writeC(0x00);
			break;
			
		case 1: // 緑色
			this.writeC(0x01);
			this.writeC(0x00);
			break;
			
		case 2: // 灰色
			this.writeC(0x00);
			this.writeC(0x01);
			break;
		}
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
