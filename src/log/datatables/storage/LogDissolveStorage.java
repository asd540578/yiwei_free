package log.datatables.storage;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;

/**
 * 溶解 Log紀錄
 * @author dexc
 *
 */
public interface LogDissolveStorage {
	

	/**
	 * 溶解 Log紀錄
	 * @param pc 使用者
	 * @param item 道具
	 * @param getcount 獲得魔法結晶體數量
	 */
	public void logArchive(final L1PcInstance pc, final L1ItemInstance item ,  final long getcount  );	
	
		
}
