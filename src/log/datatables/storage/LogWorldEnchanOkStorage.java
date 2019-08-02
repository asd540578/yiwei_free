package log.datatables.storage;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;

/**
 * 裝備升級 Log 紀錄
 * @author dexc
 *
 */
public interface LogWorldEnchanOkStorage {
	
	/**
	 * 紀錄日誌
	 */
	public void logArchive(final L1PcInstance pc,final L1ItemInstance item , final int oldEnchantLvl ,  final int newEnchantLvl );	
	
		
}
