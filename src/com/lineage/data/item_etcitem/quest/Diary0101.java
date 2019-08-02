package com.lineage.data.item_etcitem.quest;

import com.lineage.data.executor.ItemExecutor;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_NPCTalkReturn;

/**
 * 49308 哈汀日記：1月1日
 */
public class Diary0101 extends ItemExecutor {

	/**
	 *
	 */
	private Diary0101() {
		// TODO Auto-generated constructor stub
	}

	public static ItemExecutor get() {
		return new Diary0101();
	}

	/**
	 * 道具物件執行
	 * @param data 參數
	 * @param pc 執行者
	 * @param item 物件
	 */
	@Override
	public void execute(final int[] data, final L1PcInstance pc, final L1ItemInstance item) {
		// 內容顯示
		pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "j_ep0s07"));
	}
}
