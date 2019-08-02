package log.datatables.storage;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;

/**
 * 丟地上紀錄
 * 
 * @author Eugene
 *
 */
public interface LogWorldDiscardStorage {
	
	/**
	 * 紀錄日誌
	 */
	public void logArchive(final L1PcInstance pc,final L1ItemInstance item,final int count);	
	
		
}
