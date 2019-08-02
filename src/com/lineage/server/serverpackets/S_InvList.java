package com.lineage.server.serverpackets;

import java.util.List;

import com.lineage.server.model.Instance.L1ItemInstance;

/**
 * 物品名單(背包)
 * 
 * @author dexc
 *
 */
public class S_InvList extends ServerBasePacket {

	private byte[] _byte = null;

	/**
	 * 物品名單(背包)
	 */
	public S_InvList(final List<L1ItemInstance> items) {
		this.writeC(S_OPCODE_INVLIST);
		this.writeC(items.size()); // 道具數量

		for (final L1ItemInstance item : items) {
			this.writeD(item.getId());
			this.writeH(item.getItem().getMagicCatalystType());// TODO 3.53C
			// TODO 3.53C
			int type = item.getItem().getUseType();
			if (type < 0) {
				type = 0;
			}
			this.writeC(type);
			int count = item.getChargeCount();// TODO 可用次數
			if (count < 0) {
				count = 0;
			}
			this.writeC(count);
			// TODO 3.53C

			this.writeH(item.get_gfxid());// 圖示
			this.writeC(item.getBless());// 祝福狀態
			this.writeD((int) Math.min(item.getCount(), 2000000000));// 數量
			int statusX = 0;
			if (item.isIdentified())
				statusX |= 1;
			if (!item.getItem().isTradable())
				statusX |= 2;
			if (item.getItem().isCantDelete())
				statusX |= 4;
			if (item.getItem().get_safeenchant() < 0 || item.getItem().getUseType() == -3
					|| item.getItem().getUseType() == -2)
				statusX |= 8;
			
			
			switch (item.getBless()) {
			case 128:
			case 129:
			case 130:
			case 131:
				statusX = 32;
				if (item.isIdentified()) {
					statusX |= 1;
					statusX |= 2;
					statusX |= 4;
					statusX |= 8;
				} else {
					statusX |= 2;
					statusX |= 4;
					statusX |= 8;
				}
				break;
			case 192:
			case 193:
			case 194:
			case 195:
				if (item.isIdentified()) {
					statusX |= 1;
					statusX |= 2;
				} else
					statusX |= 2;
				break;
			}
			this.writeC(statusX);
			this.writeS(item.getViewName());// 名稱
			if (!item.isIdentified()) {
				// 未見定狀態 不需發送詳細資料
				this.writeC(0x00);

			} else {
				final byte[] status = item.getStatusBytes();
				this.writeC(status.length);
				for (final byte b : status) {
					this.writeC(b);
				}
			}
			this.writeC(0x17);
			this.writeC(0);
			this.writeH(0);
			this.writeH(0);
			if(item.getItem().getType() == 10){     // 如果是法書，傳出法術編號
				this.writeC(0);
			} else {
				this.writeC(item.getEnchantLevel());     // 物品武捲等級
			}
			this.writeD(item.getId());                              // 3.80 物品世界流水編號
			this.writeD(0);
			this.writeD(0);
			this.writeD(item.getBless() >= 128 ? 3 : item.getItem().isTradable() ? 7 : 2); // 7:可刪除, 2: 不可刪除, 3: 封印狀態
			this.writeC(0);
			//writeC(10);// TODO 3.53C新增封包
			//writeH(0);// TODO 3.53C新增封包
			//writeD(0);// TODO 3.53C新增封包
			//writeD(0);// TODO 3.53C新增封包
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
