package com.lineage.server.serverpackets;

/**
 * 訊息通知
 * @author dexc
 *
 */
public class S_HelpMessage extends ServerBasePacket {

	private byte[] _byte = null;

	/**
	 * 強化成功成功訊息
	 * @param mode
	 */
	public S_HelpMessage(final String name, final String info) {
		this.writeC(S_OPCODE_NPCSHOUT);
		this.writeC(0x00);// 一般頻道
		this.writeD(0x00000000);
		this.writeS(name + " --> \\f4" + info);
	}

	/**
	 * 訊息通知(使用NPC對話一般頻道)
	 * @param mode
	 * @param color<br>
	 * <font color="#bdaaa5">\\fR <b>顏色範例</b></font><br>
	 * <font color="#739e84">\\fS <b>顏色範例</b></font><br>
	 * <font color="#7b9e7b">\\fT <b>顏色範例</b></font><br>
	 * <font color="#7b9aad">\\fU <b>顏色範例</b></font><br>
	 * <font color="#a59ac6">\\fV <b>顏色範例</b></font><br>
	 * <font color="#ad92b5">\\fW <b>顏色範例</b></font><br>
	 * <font color="#b592ad">\\fX <b>顏色範例</b></font><br>
	 * <font color="#bd9a94">\\fY <b>顏色範例</b></font><br>
	 */
	/*public S_HelpMessage(final String string) {
		this.writeC(S_OPCODE_NORMALCHAT);
		this.writeC(0x00);// 一般頻道
		this.writeD(0x00000000);
		this.writeS(string);
	}*/
	
	/**
	 * 訊息通知
	 * @param type
	 * <font color="#bdaaa5">\\fR <b>顏色範例</b></font><br>
	 * <font color="#739e84">\\fS <b>顏色範例</b></font><br>
	 * <font color="#7b9e7b">\\fT <b>顏色範例</b></font><br>
	 * <font color="#7b9aad">\\fU <b>顏色範例</b></font><br>
	 * <font color="#a59ac6">\\fV <b>顏色範例</b></font><br>
	 * <font color="#ad92b5">\\fW <b>顏色範例</b></font><br>
	 * <font color="#b592ad">\\fX <b>顏色範例</b></font><br>
	 * <font color="#bd9a94">\\fY <b>顏色範例</b></font><br>
	 */
	public S_HelpMessage(final int type) {
		this.writeC(S_OPCODE_SERVERMSG);
		this.writeH(type);
		this.writeC(0x00);
	}

	/**
	 * 訊息通知
	 * @param winName
	 * @param color<br>
	 * <font color="#bdaaa5">\\fR <b>顏色範例</b></font><br>
	 * <font color="#739e84">\\fS <b>顏色範例</b></font><br>
	 * <font color="#7b9e7b">\\fT <b>顏色範例</b></font><br>
	 * <font color="#7b9aad">\\fU <b>顏色範例</b></font><br>
	 * <font color="#a59ac6">\\fV <b>顏色範例</b></font><br>
	 * <font color="#ad92b5">\\fW <b>顏色範例</b></font><br>
	 * <font color="#b592ad">\\fX <b>顏色範例</b></font><br>
	 * <font color="#bd9a94">\\fY <b>顏色範例</b></font><br>
	 */
	public S_HelpMessage(final String msg) {
		this.writeC(S_OPCODE_GLOBALCHAT);
		this.writeC(0x09);
		this.writeS(msg);
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
