package log.datatables.storage;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;

/**
 * 倉庫使用紀錄
 * @author Eugene
 *
 */
public interface LogItemWarehouseStorage {



	/**
	 * 倉庫使用紀錄
	 * @param pc 使用者
	 * @param item 存取道具
	 * @param count 存取數量
	 * @param type 存取類型 false 存  : true 取
	 */
	public void logWarehouseArchive(final L1PcInstance pc, final L1ItemInstance item  , final long count , final boolean type  );	
	
	
	/**
	 * 倉庫使用紀錄
	 * @param pc 使用者
	 * @param item 存取道具
	 * @param count 存取數量
	 * @param type 存取類型  false 存  : true 取
	 */
	public void logWarehouseClanArchive(final L1PcInstance pc, final L1ItemInstance item  , final long count , final boolean type  );


}
