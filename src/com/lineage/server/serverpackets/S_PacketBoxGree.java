package com.lineage.server.serverpackets;

/**
 * 畫面中央顏色對話訊息
 * @author dexc
 *
 */
public class S_PacketBoxGree extends ServerBasePacket {

	private byte[] _byte = null;

	/**畫面中央顏色對話訊息*/
	private static final int GREEN_MESSAGE = 0x54; // 84

	/**畫面特效*/
	private static final int SECRETSTORY_GFX = 0x53; // 83

	/**
	 * \\fW=莓紅紫
	 * \\fT=橄欖綠
	 * \\fR=大便色
	 * \\fU=土耳其藍
	 * \\fY=暗粉紅 
	 * \\fI=鵝黃色 
	 * \\fS=綠藍色
	 * \\fX=紫紅色 
	 * \\fV=紫色
	 * 
	 * @param time
	 */
	public S_PacketBoxGree(final String msg) {
		this.writeC(S_OPCODE_PACKETBOX);
		this.writeC(GREEN_MESSAGE);// 57
		this.writeC(0x02);// 57
		this.writeS(msg);
	}
	
	int i = 0;
	/**
	 * 秘坛副本特殊特效(哈汀-欧林)
	 * @param type 01 画面粉红特效
	 * @param type 02 画面震动
	 * @param type 03 烟火特效 
	 */
	public S_PacketBoxGree() {
//		writeC(S_OPCODE_PACKETBOX);
//		//i = 80;
//		writeC(84);// 57
//		writeC(2);// 44
//		
//		writeS("測試 1");
//		writeD(100);//01画面震动 02 画面粉红特效 03 烟火特效 // 05 06 07 08
//		//writeS("測試");
//		//writeS("測試");
		
	}
	
	/**
	 * 分數 
	 * @param isOpen 3 = 關   4 = 開
	 * @param count
	 */
	public S_PacketBoxGree(final int isOpen ,final int count) {
		this.writeC(S_OPCODE_PACKETBOX);
		this.writeC(GREEN_MESSAGE);
		this.writeC(isOpen);
		
		this.writeC(1);//個位
		this.writeC(10);//十位
		this.writeC(8);//百位
		this.writeC(5);//千位
	}
	
	/**
	 * 秘坛副本特殊特效(哈汀-欧林)
	 * @param type 01 画面粉红特效
	 * @param type 02 画面震动
	 * @param type 03 烟火特效 
	 */
	public S_PacketBoxGree(int type) {
		this.writeC(S_OPCODE_PACKETBOX);
		this.writeC(SECRETSTORY_GFX);//0x53
		this.writeD(type);//01画面震动 02 画面粉红特效 03 烟火特效 // 05 06 07 08
		this.writeC(0x00);
		this.writeC(0x00);
		this.writeC(0x00); 
	}

	@Override
	public byte[] getContent() {
		if (_byte == null) {
			_byte = getBytes();
		}
		return _byte;
	}

	@Override
	public String getType() {
		return getClass().getSimpleName();
	}
}
