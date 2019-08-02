package log.datatables.storage;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;

/**
 *  物品刪除
 * 	 爆裝
 * @author Eugene
 *
 */
public interface LogItemStorage {

	/**
	 * 物品刪除
	 * @param pc 玩家
	 * @param item 刪除道具
	 * 	@param remCount 刪除數量
	 */
	public void logArchive(final L1PcInstance pc, final L1ItemInstance item  , final long removeCount  );	
	
	
}
