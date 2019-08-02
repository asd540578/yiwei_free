package com.lineage.data.item_etcitem.extra;

import com.lineage.config.ConfigAlt;
import com.lineage.data.executor.ItemExecutor;
import com.lineage.server.datatables.lock.CharBookConfigReading;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.templates.L1BookConfig;

/**
 * @author terry0412
 */
public class MemoryExpansionGem extends ItemExecutor {

	/**
	 *
	 */
	private MemoryExpansionGem() {
		// TODO Auto-generated constructor stub
	}

	public static ItemExecutor get() {
		return new MemoryExpansionGem();
	}

	/**
	 * 道具物件執行
	 * @param data 參數
	 * @param pc 執行者
	 * @param item 物件
	 */
	@Override
	public void execute(final int[] data, final L1PcInstance pc, final L1ItemInstance item) {
		final L1BookConfig bookConfig = CharBookConfigReading.get().get(pc.getId());
		if (bookConfig != null) {
			if (bookConfig.getMaxSize() >= ConfigAlt.CHAR_BOOK_MAX_CHARGE * 10) {
				pc.sendPackets(new S_ServerMessage(2962));
				return;
			}
			pc.getInventory().removeItem(item, 1);
			final int expansion_count = 10;
			bookConfig.setMaxSize(bookConfig.getMaxSize() + expansion_count);
			CharBookConfigReading.get().updateCharBookMaxSize(
					pc.getId(), bookConfig.getMaxSize());
			//pc.sendPackets(new S_Bookmarks(
			//		ConfigAlt.CHAR_BOOK_INIT_COUNT + bookConfig.getMaxSize()));
		}
	}
}
