package log.datatables.storage;

import com.lineage.server.model.Instance.L1PcInstance;

/**
 * GM指令 Log 紀錄
 * 
 * @author Eugene
 *
 */
public interface LogWorldGmCmmandStorage {
	
	/**
	 * 紀錄日誌
	 */
	public void logArchive(final L1PcInstance pc,final String cmd);	
	
		
}
