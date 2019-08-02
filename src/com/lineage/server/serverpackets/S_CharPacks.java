package com.lineage.server.serverpackets;

/**
 * 角色資訊
 * @author dexc
 *
 */
public class S_CharPacks extends ServerBasePacket {

	private byte[] _byte = null;

	/**
	 * 角色資訊
	 * @param name
	 * @param clanName
	 * @param type
	 * @param sex
	 * @param lawful
	 * @param hp
	 * @param mp
	 * @param ac
	 * @param lv
	 * @param str
	 * @param dex
	 * @param con
	 * @param wis
	 * @param cha
	 * @param intel
	 */
	public S_CharPacks(final String name, final String clanName, final int type, final int sex,
			final int lawful, final int hp, final int mp, final int ac, final int lv, final int str, final int dex,
			final int con, final int wis, final int cha, final int intel, final int time) {
		writeC(S_OPCODE_CHARLIST);
		writeS(name);
		writeS(clanName);
		writeC(type);
		writeC(sex);
		writeH(lawful);
		writeH(hp);
		writeH(mp);
		
		if (ac > 0x0a) {// 10
			writeC(0x0a);// 10
			
		} else {
			writeC(ac);
		}
		/** TODO 伊薇3.53使用客戶端3.7等級顯示問題↓*/
		writeC(lv);
        writeC(str);
        writeC(dex);
        writeC(con);
        writeC(wis);
        writeC(cha);
        writeC(intel);
        /** TODO 伊薇3.53使用客戶端3.7等級顯示問題↓*/
		/** TODO 伊薇3.53使用客戶端3.7等級顯示問題↓
		if (lv > 0x7f) {// 127
			writeC(0x7f);// 127
			
		} else {
			writeC(lv);
		}
		
		if (str > 0x7f) {// 127
			writeC(0x7f);// 127
		} else {
			writeC(str);
		}
		if (dex > 0x7f) {// 127
			writeC(0x7f);// 127
		} else {
			writeC(dex);
		}
		if (con > 0x7f) {// 127
			writeC(0x7f);// 127
		} else {
			writeC(con);
		}
		if (wis > 0x7f) {// 127
			writeC(0x7f);// 127
		} else {
			writeC(wis);
		}
		if (cha > 0x7f) {// 127
			writeC(0x7f);// 127
		} else {
			writeC(cha);
		}
		if (intel > 0x7f) {// 127
			writeC(0x7f);// 127
		} else {
			writeC(intel);
		}
		 TODO 伊薇3.53使用客戶端3.7等級顯示問題↑*/
		// 大於0為GM權限
		this.writeC(0x00);

		String times = Integer.toHexString(time);
		if (times.length() < 8) {
			times = "0" + times;
		}
		// cb a5 31 01  131a295
		writeC(Integer.decode("0x" + times.substring(6, 8)));
		writeC(Integer.decode("0x" + times.substring(4, 6)));
		writeC(Integer.decode("0x" + times.substring(2, 4)));
		writeC(Integer.decode("0x" + times.substring(0, 2)));

		//writeC(0x00);
		//this.writeD(time);
		//解決發現外掛中斷連線 by aplus  
		int checkcode = lv ^ str ^ dex ^ con ^ wis ^ cha ^ intel;
		this.writeC(checkcode & 0xff);
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
