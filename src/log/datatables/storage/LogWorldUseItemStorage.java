package log.datatables.storage;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;

/**
 * 道具使用的紀錄
 * @author Eugene
 *
 */
public interface LogWorldUseItemStorage {
	
	/**
	 * 紀錄日誌
	 */
	public void logArchive(final L1PcInstance pc,final L1ItemInstance item);	
	
		
}
