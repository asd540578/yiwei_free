package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1ItemInstance;

/**
 * 交易增加物品
 * @author dexc
 *
 */
public class S_TradeAddItem extends ServerBasePacket {

	/**
	 * 交易增加物品
	 * @param item
	 * @param count
	 * @param type
	 */
	public S_TradeAddItem(final L1ItemInstance item, final long count, final int type) {
		//0000: 21 00 3e 01 24 37 36 37 00 03 00 b7 32 b3 9c 2f    !.>.$767....2../
		this.writeC(S_OPCODE_TRADEADDITEM);
		this.writeC(type); // 0:交易視窗上半部 1:交易視窗下半部 
		this.writeH(item.getItem().getGfxId());

		String name = item.getNumberedViewName(count);

		this.writeS(name);
		if (!item.isIdentified()) {
			this.writeC(3);
			this.writeC(0);
		} else {
			this.writeC(item.getBless());
			byte[] status = item.getStatusBytes();
			this.writeC(status.length);
		      for (byte b : status) {
		    	  this.writeC(b);
		      }
		}
	}

	/**
	 * 交易增加物品 - 測試
	 */
	public S_TradeAddItem() {
		this.writeC(S_OPCODE_TRADEADDITEM);
		this.writeC(0x01); // 0:交易視窗上半部 1:交易視窗下半部 
		this.writeH(714);// 惡魔頭盔
		this.writeS("測試物品(55)");
		// 0:祝福 
		this.writeC(0x00);
	}

	@Override
	public byte[] getContent() {
		return this.getBytes();
	}

	@Override
	public String getType() {
		return this.getClass().getSimpleName();
	}
}
