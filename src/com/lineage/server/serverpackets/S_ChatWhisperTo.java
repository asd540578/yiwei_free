package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1PcInstance;

/**
 * 密語交談(發送)頻道
 * @author dexc
 *
 */
public class S_ChatWhisperTo extends ServerBasePacket {

	private byte[] _byte = null;

	public S_ChatWhisperTo(final L1PcInstance pc, final String chat) {
		this.buildPacket(pc, chat);
	}

	public S_ChatWhisperTo(String name, String chat) {
		writeC(S_OPCODE_GLOBALCHAT);
		writeC(0x09);
		writeS("-> (" + name + ") " + chat);
	}

	private void buildPacket(final L1PcInstance pc, final String chat) {
		this.writeC(S_OPCODE_GLOBALCHAT);
		this.writeC(0x09);
		this.writeS("-> (" + pc.getName() + ") " + chat);
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