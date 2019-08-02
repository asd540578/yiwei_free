package log.datatables.storage;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;


/**
 * 打寶 Log 紀錄
 * @author dexc
 *
 */
public interface LogWorldTreasureStorage {
	
	/**
	 * 打寶 Log 紀錄
	 * @param pc 使用者
	 * @param item  獲得道具
	 * @param npc 怪物
	 * @param getCount 獲得數量
	 */
	public void logArchive(final L1PcInstance pc ,final L1ItemInstance item , final L1NpcInstance npc ,final long getCount);	
	
		
}
