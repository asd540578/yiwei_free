package com.lineage.server.serverpackets;

/**
 * 魔法效果:精準目標
 * @author DaiEn
 *
 */
public class S_TrueTarget extends ServerBasePacket {

	private byte[] _byte = null;

	/**
	 * 魔法效果:精準目標
	 * @param targetId 目標OBJID
	 * @param objectId 施展者OBJID
	 * @param message 附加訊息
	 */
	public S_TrueTarget(final int targetId, final int objectId, final String message) {
		this.buildPacket(targetId, objectId, message);
	}

	private void buildPacket(final int targetId, final int objectId, final String message) {
		this.writeC(S_OPCODE_TRUETARGET);
		this.writeD(targetId);
		this.writeD(objectId);
		this.writeS(message);
	}
	
	public S_TrueTarget(final int targetId, final int gfxid) {
		this.writeC(S_OPCODE_TRUETARGET);
		this.writeD(targetId);
		this.writeD(targetId);
		this.writeS(null);
		this.writeH(gfxid);
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
