package log.datatables.storage;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;

/**
 * 交易紀錄
 * @author Eugene
 *
 */
public interface LogItemTradeStorage {


	/**
	 * 交易紀錄
	 * @param pc 玩家
	 * @param target 目標
	 * @param item 交易道具
	 * @param count 交易名稱
	 */
	public void logArchive(final L1PcInstance pc , final L1PcInstance target , final L1ItemInstance item  , final long count);	
	
	
}
