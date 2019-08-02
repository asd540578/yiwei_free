package log.datatables.storage;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;

/**
 * 防爆 Log 紀錄
 * 
 * @author Eugene
 *
 */
public interface LogWorldExplosionProofStorage {
	
	/**
	 * 紀錄日誌
	 */
	public void logArchive(final int type , final L1PcInstance pc , final L1ItemInstance tgItem);	
	
		
}
