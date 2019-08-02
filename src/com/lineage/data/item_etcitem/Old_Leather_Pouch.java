package com.lineage.data.item_etcitem;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.data.executor.ItemExecutor;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;

/**
 * <font color=#00800>古老皮袋40167v</font><BR>
 * Old Leather Pouch<BR>
 *
 * @author dexc
 *
 */
public class Old_Leather_Pouch extends ItemExecutor {

	/**
	 *
	 */
	private Old_Leather_Pouch() {
		// TODO Auto-generated constructor stub
	}

	public static ItemExecutor get() {
		return new Old_Leather_Pouch();
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

		final int k = (int) (Math.random() * 10);// 隨機數字範圍0~9

		switch (k) {
		case 1:// 煙熏的麵包屑
			item_id = 40058;
			count = 2;
			break;

		case 2:// 烤焦的麵包屑
			item_id = 40071;
			break;

		case 3:// 紅酒
			item_id = 40039;
			count = 2;
			break;

		case 4:// 威士忌
			item_id = 40040;
			break;

		case 5:// 肯特戰士斧頭
			item_id = 40335;
			break;

		case 6:// 肯特射手之弓
			item_id = 40332;
			break;

		case 7:// 肯特勇士之劍
			item_id = 40331;
			break;

		case 8:// 肯特徽章長靴
			item_id = 40336;
			break;

		case 9:// 肯特徽章手套
			item_id = 40338;
			break;

		default:// 肯特刺客雙刀
			item_id = 40334;
		break;
		}

		// 刪除道具
		pc.getInventory().removeItem(item, 1);

		// 取得道具
		CreateNewItem.createNewItem(pc, item_id, count);
	}
}
