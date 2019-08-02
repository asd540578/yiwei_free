package log.datatables.storage;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;

/**
 * 撿起來 Log 紀錄
 * 
 * @author Eugene
 *
 */
public interface LogWorldPickItUpStorage {
	
	/**
	 * 紀錄日誌
	 */
	public void logArchive(final L1PcInstance pc , final L1ItemInstance item , final long pickupCount);	
	
		
}
