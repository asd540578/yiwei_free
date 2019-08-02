package com.lineage.server.serverpackets;

import com.lineage.config.Config;

/**
 * 伺服器版本
 * @author dexc
 *
 */
public class S_ServerVersion extends ServerBasePacket {

	private static final int CLIENT_LANGUAGE = Config.CLIENT_LANGUAGE;
	 
	private byte[] _byte = null;
	
	
	/**
	 * 伺服器版本
	 */
	public S_ServerVersion() {
		
		this.writeC(S_OPCODE_SERVERVERSION);
		this.writeC(0x00);// 允許登入
		this.writeC(0x0d);
		this.writeD(Config.SVer);// server verion:dc 87 01 00(保留參考用)	
		this.writeD(Config.CVer);// cache verion:d1 87 01 00(保留參考用)
		this.writeD(Config.AVer);// auth verion:01 ee 00 00(保留參考用)
		this.writeD(Config.NVer);// npc verion:7f 87 01 00(保留參考用)
		
		//this.writeD(UPTIME);// time:0e a9 32 4c(悵隱統蕉蚚)
		this.writeD(0x0);
		this.writeC(0x00);//TODO 未知封包
		this.writeC(0x00);//TODO 未知封包
		this.writeC(CLIENT_LANGUAGE);
		this.writeD(0x77d82);//TODO 未知封包
		this.writeD(Config.Time);
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
