package log.datatables.storage;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;

/**
 *  賣萬物回收商
 * 
 * @author Eugene
 *
 */
public interface LogItemSellNpcStorage {

	/**
	 * 賣萬物回收商
	 * @param pc 玩家
	 * @param item 賣的物品
	 * @param count 賣的數量
	 * @param price 賣的金額
	 */
	public void logArchive(final L1PcInstance pc, final L1ItemInstance item  , final long count , final long price  );	
	
	
}
