package com.lineage.server.serverpackets;

import java.util.List;

import com.lineage.server.templates.L1BookMark;

/**
 * 角色座標名單
 * 
 * @author dexc
 *
 */
public class S_Bookmarks extends ServerBasePacket {

	private byte[] _byte = null;

	/**
	 * 添加記憶座標-建構式
	 * 
	 * @param name 記憶座標的名稱
	 * @param map  記憶座標的地圖
	 * @param x    記憶座標的X軸
	 * @param y    記憶座標的Y軸
	 * @param id   記憶座標的識別碼
	 */
	public S_Bookmarks(final String name, final int map, final int x, final int y, final int id) {
		// [S] [id:115] [GroupId:16] [Length:16] [Millis:1355454570352]
		// 0000 73 35 39 39 30 00 00 00 96 7f ba 80 7f 32 01 00 s5990......2..
		this.writeC(S_OPCODE_BOOKMARKS);
		this.writeS(name); // 記憶座標的名稱
		this.writeH(map); // 記憶座標的地圖
		this.writeH(x); // 記憶座標的X軸
		this.writeH(y); // 記憶座標的Y軸
		this.writeD(id); // 記憶座標的識別碼
	}

	/**
	 * 角色座標名單
	 * 
	 * @param name
	 * @param map
	 * @param id
	 */
	/*
	 * public S_Bookmarks(final String name, final int map, final int id, final int
	 * x, final int y) { this.buildPacket(name, map, id, x, y); }
	 * 
	 * private void buildPacket(final String name, final int map, final int id,
	 * final int x, final int y) { //0000: 0d 30 30 30 31 00 04 00 3e 82 3f 80 d8 26
	 * 4c b8 .0001...>.?..&L. this.writeC(S_OPCODE_BOOKMARKS); this.writeS(name);
	 * this.writeH(map); this.writeD(id); this.writeH(x);//TODO 3.53新增記憶座標封包
	 * this.writeH(y);//TODO 3.53新增記憶座標封包 }
	 */

	/**
	 * 記憶座標 3.63
	 * 
	 * @param bookmarks 記憶座標列隊
	 */
	public S_Bookmarks(List<L1BookMark> bookmarks) {
		writeC(S_OPCODE_CHARRESET);
		writeC(0x2a);
		writeH(0x0080);
		writeC(0x02);

		for (int i = 1; i < 128; ++i) {
			writeC(0xff);
		}

		writeH(60);

		if (bookmarks.size() > 0) {
			writeH(bookmarks.size());

			for (L1BookMark book : bookmarks) {
				writeD(book.getId());
				writeS(book.getName());
				writeH(book.getMapId());
				writeH(book.getLocX());
				writeH(book.getLocY());
			}
		} else {
			writeH(0);
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