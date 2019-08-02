package log.datatables.storage;

import java.util.ArrayList;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;

/**
 * 死亡 Log紀錄
 * @author dexc
 *
 */
public interface LogDeathStorage {
	

	/**
	 * 死亡 Log紀錄
	 * @param pc 使用者
	 * @param items 噴道具清單
	 * @param skills 噴魔法清單
	 * @param exp 噴經驗值
	 */
	public void logDeathArchive(final L1PcInstance pc, final ArrayList<L1ItemInstance> items ,final ArrayList<Integer> skills  ,  final long exp);	
	
		
}
