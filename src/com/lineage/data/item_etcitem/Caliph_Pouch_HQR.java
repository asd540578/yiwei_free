package com.lineage.data.item_etcitem;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.data.executor.ItemExecutor;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;

/**
 * <font color=#00800>卡立普的高級袋子(品紅)49011</font><BR>
 * High Quality Pouch of Karif (HQR)
 *
 * @author dexc
 *
 */
public class Caliph_Pouch_HQR extends ItemExecutor {

	/**
	 *
	 */
	private Caliph_Pouch_HQR() {
		// TODO Auto-generated constructor stub
	}

	public static ItemExecutor get() {
		return new Caliph_Pouch_HQR();
	}

	/**
	 * 道具物件執行
	 * @param data 參數
	 * @param pc 執行者
	 * @param item 物件
	 */
	@Override
	public void execute(final int[] data, final L1PcInstance pc, final L1ItemInstance item) {
		int item_id = 0;

		int count = 1;// 預設給予數量1

		final int k = (int) (Math.random() * 100);// 隨機數字範圍0~99

		switch (k) {
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
		case 10:// 終極治癒藥水
			item_id = 40012;
			count = 2;
			break;

		case 11:
		case 12:
		case 13:
		case 14:
		case 15:
		case 16:
		case 17:
		case 18:
		case 19:
		case 20:// 強力治癒藥水
			item_id = 40011;
			count = 5;
			break;

		case 26:
		case 27:
		case 28:
		case 29:
		case 30:// 濃縮強力體力恢復劑
			item_id = 40020;
			count = 5;
			break;

		case 36:
		case 37:
		case 38:
		case 39:
		case 40:// 濃縮終極體力恢復劑
			item_id = 40021;
			count = 2;
			break;

		case 41:
		case 42:
		case 43:
		case 44:
		case 45:// 勇敢藥水
			item_id = 40014;
			count = 2;
			break;

		case 46:
		case 47:
		case 48:
		case 49:
		case 50:// 自我加速藥水
			item_id = 40013;
			count = 5;
			break;

		case 51:
		case 52:
		case 53:
		case 54:
		case 55:// 強化自我加速藥水
			item_id = 40018;
			count = 2;
			break;

		case 56:
		case 57:
		case 58:
		case 59:
		case 60:// 金屬塊
			item_id = 40408;
			count = 4;
			break;

		case 61:
		case 62:
		case 63:
		case 64:
		case 65:// 一級黑魔石
			item_id = 40320;
			count = 5;
			break;

		case 66:
		case 67:
		case 68:
		case 69:
		case 70:// 黃金原石
			item_id = 40489;
			break;

		case 71:
		case 72:
		case 73:
		case 74:
		case 75:// 白金原石
			item_id = 40441;
			break;

		case 76:
		case 77:
		case 78:
		case 79:
		case 80:// 光明的鱗片
			item_id = 40458;
			count = 2;
			break;

		case 81:
		case 82:
		case 83:
		case 84:
		case 85:// 粗糙的米索莉塊
			item_id = 40496;
			count = 3;
			break;

		case 86:
		case 87:
		case 88:
		case 89:
		case 90:// 銀原石
			item_id = 40468;
			count = 2;
			break;

		case 91:
		case 92:
		case 93:
		case 94:
		case 95:// 加速魔力恢復藥水
			item_id = 40015;
			count = 2;
			break;

		case 21:
		case 22:
		case 23:
		case 24:
		case 25:// 奇美拉之皮(獅子)
			item_id = 40399;
			break;

		case 31:
		case 32:
		case 33:
		case 34:
		case 35:// 帕格里奧之石
			item_id = 40305;
			break;

		case 96:
		case 97:
		case 98:// 食人巨魔的血
			item_id = 40513;
			break;

		default:// 火龍鱗
			item_id = 40393;
			break;
		}

		// 刪除道具
		pc.getInventory().removeItem(item, 1);

		// 取得道具
		CreateNewItem.createNewItem(pc, item_id, count);
	}
}
