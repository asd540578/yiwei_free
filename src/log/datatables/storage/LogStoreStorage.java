package log.datatables.storage;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;

/**
 * 個人商店 Log紀錄
 * @author dexc
 *
 */
public interface LogStoreStorage {
	
	/**
	 * 個人商店 log 紀錄 
	 * @param StoreType  商店類型  false : 賣出 ; true : 回收
	 * @param pc 玩家
	 * @param item 道具
	 * @param itemCount 道具數量
	 * @param goldCount 金幣數量
	 * @param targetPc 目標玩家
	 */
	public void logArchive(final boolean StoreType , final L1PcInstance pc, final L1ItemInstance item , final long itemCount , final long goldCount , final L1PcInstance target );	
	
		
}
