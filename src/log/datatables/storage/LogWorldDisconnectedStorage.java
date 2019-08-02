package log.datatables.storage;

import com.lineage.server.model.Instance.L1PcInstance;

/**
 * 斷線紀錄  Log 紀錄
 * @author dexc
 *
 */
public interface LogWorldDisconnectedStorage {
	

	/**
	 * 斷線紀錄
	 * @param pc 使用者
	 * @param type <br>
	 * 1 = 速度異常
	 * <br>
	 * 2 = 使用倉庫封包異常 
	 * <br>
	 * 3 = 特殊購買封包異常
	 *  <br>
	 */
	public void logArchive(final L1PcInstance pc , final int type);	
	
		
}
