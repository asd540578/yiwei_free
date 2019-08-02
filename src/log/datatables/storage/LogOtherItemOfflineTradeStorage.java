package log.datatables.storage;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;

/**
 * 離線商店的紀錄
 * @author Eugene
 *
 */
public interface LogOtherItemOfflineTradeStorage {

	public void logArchive(final L1PcInstance pc, final L1ItemInstance item, final long count, final int adena, final int status);
	
}
