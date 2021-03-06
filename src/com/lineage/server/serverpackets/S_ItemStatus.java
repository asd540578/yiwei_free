package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1ItemInstance;

/**
 * 更新物品使用狀態(背包)-數量/狀態
 * @author dexc
 *
 */
public class S_ItemStatus extends ServerBasePacket {

	private byte[] _byte = null;

	/**
	 * 更新物品使用狀態(背包)-數量/狀態
	 */
	public S_ItemStatus(final L1ItemInstance item) {
		if (item == null) {
			return;
		}
		this.buildPacket(item);
	}

	private void buildPacket(final L1ItemInstance item) {
	 this.writeC(S_OPCODE_ITEMAMOUNT);
		this.writeD(item.getId());
		this.writeS(item.getViewName());

		// 定義數量顯示
		int count = (int) Math.min(item.getCount(), 2000000000);
		// 數量
		this.writeD(count);
		
		//this.writeC(0x00);
		if (!item.isIdentified()) {
			// 未鑑定 不發送詳細資料
			this.writeC(0x00);
			
		} else {
			final byte[] status = item.getStatusBytes();
			this.writeC(status.length);
			for (final byte b : status) {
				this.writeC(b);
			}
		}
	}

	/**
	 * 更新物品使用狀態(背包)-數量(交易專用)
	 */
	public S_ItemStatus(final L1ItemInstance item, final long count) {
		/**
		 * 原依薇改為99nets團隊修正
		 */
		this.writeC(S_OPCODE_ITEMAMOUNT);
		//this.writeC(S_OPCODE_ITEMAMOUNT);
		this.writeD(item.getId());
		this.writeS(item.getNumberedViewName(count));
		
		// 定義數量顯示
		int out_count = (int) Math.min(count, 2000000000);
		// 數量
		this.writeD(out_count);
		
		if (!item.isIdentified()) {
			// 未鑑定 不發送詳細資料
			this.writeC(0x00);
			
		} else {
			final byte[] status = item.getStatusBytes();
			this.writeC(status.length);
			for (final byte b : status) {
				this.writeC(b);
			}
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
