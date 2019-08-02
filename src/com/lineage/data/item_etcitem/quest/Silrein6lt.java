package com.lineage.data.item_etcitem.quest;

import com.lineage.data.executor.ItemExecutor;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_NPCTalkReturn;

/**
 * 希蓮恩的第六次信件 49177
 */
public class Silrein6lt extends ItemExecutor {

	/**
	 *
	 */
	private Silrein6lt() {
		// TODO Auto-generated constructor stub
	}

	public static ItemExecutor get() {
		return new Silrein6lt();
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
		pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "silrein6lt"));
	}
}
