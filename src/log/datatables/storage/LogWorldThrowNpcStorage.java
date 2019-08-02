package log.datatables.storage;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;

/**
 * 物品拉給寵物或NPC Log 紀錄
 * 
 * @author Eugene
 *
 */
public interface LogWorldThrowNpcStorage {
	
	/**
	 * 紀錄日誌
	 */
	public void logArchive(final L1PcInstance pc,final L1ItemInstance item,final long count,final L1NpcInstance target);	
	
		
}
