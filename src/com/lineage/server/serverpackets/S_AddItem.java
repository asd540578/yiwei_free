package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1ItemInstance;

/**
 * 物品增加
 * @author dexc
 *
 */
public class S_AddItem extends ServerBasePacket {

	private byte[] _byte = null;

	/**
	 * 物品增加
	 */
	public S_AddItem(final L1ItemInstance item) {
		this.writeC(S_OPCODE_ADDITEM);
		this.writeD(item.getId());
		this.writeH(item.getItem().getMagicCatalystType());//TODO 3.53C魔法觸媒的類型判斷
		//TODO 3.53C
		int type = item.getItem().getUseType();
		if (type < 0) {
			type = 0;
		}
		this.writeC(type);
		int count = item.getChargeCount();//TODO 可用次數
		if (count < 0) {
			count = 0;
		}
		this.writeC(count);
		//TODO 3.53C
		this.writeH(item.get_gfxid());//TODO 圖示
		this.writeC(item.getBless());//TODO 祝福狀態(是否祝福 0=祝福 1=普通 2=詛咒)
		this.writeD((int) Math.min(item.getCount(), 2000000000));//TODO 數量
		int statusX = 0;
		if (item.isIdentified())
			statusX |= 1;
		if (!item.getItem().isTradable())
			statusX |= 2;
		if (item.getItem().isCantDelete())
			statusX |= 4;
		if (item.getItem().get_safeenchant() < 0 || item.getItem().getUseType() == -3 || item.getItem().getUseType() == -2)
			statusX |= 8;
		
		switch(item.getBless()) {
		case 128:
		case 129:
		case 130:
		case 131:
			statusX = 32;
			if (item.isIdentified())
			{
				statusX |= 1;
				statusX |= 2;
				statusX |= 4;
				statusX |= 8;
			} else
			{
				statusX |= 2;
				statusX |= 4;
				statusX |= 8;
			}
			break;
		case 192:
		case 193:
		case 194:
		case 195:
			if (item.isIdentified())
			{
				statusX |= 1;
				statusX |= 2;
			} else
				statusX |= 2;
			break;
		}
		this.writeC(statusX);
		this.writeS(item.getViewName());//TODO 名稱
		if (!item.isIdentified()) {
			// 未鑑定 不發送詳細資訊
			this.writeC(0x00);
			
		} else {
			final byte[] status = item.getStatusBytes();
			this.writeC(status.length);
			for (final byte b : status) {
				this.writeC(b);
			}
		}
		writeC(10);//TODO 3.53C新增封包
		writeH(0);//TODO 3.53C新增封包
		writeD(0);//TODO 3.53C新增封包
		writeD(0);//TODO 3.53C新增封包
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
