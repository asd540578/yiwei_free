package log.datatables.storage;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;

/**
 * 特殊購買 Log 紀錄
 * @author Eugene
 *
 */
public interface LogWorldSpecialBuyStorage {
	

	/**
	 * 特殊購買 Log 紀錄
	 * @param pc 使用者
	 * @param itemCn_id 特殊金幣
	 * @param cnCount 購買金額
	 * @param item 獲得道具
	 */
	public void logArchive(final L1PcInstance pc, final int cnItem_id , final long cnCount , final L1ItemInstance item);	
	
		
}
