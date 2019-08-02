package log.datatables.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;

import log.datatables.sql.LogWorldTreasureTable;
import log.datatables.storage.LogWorldTreasureStorage;

/**
 * 打寶 Log紀錄
 * @author dexc
 *
 */
public class LogWorldTreasureReading {

	private final Lock _lock;

	private final LogWorldTreasureStorage _storage;

	private static LogWorldTreasureReading _instance;

	private LogWorldTreasureReading() {
		this._lock = new ReentrantLock(true);
		this._storage = new LogWorldTreasureTable();
	}

	public static LogWorldTreasureReading get() {
		if (_instance == null) {
			_instance = new LogWorldTreasureReading();
		}
		return _instance;
	}

	/**
	 * 打寶 Log紀錄
	 * @param pc 玩家
	 * @param item 道具
	 * @param npcName NPC
	 * @param getCount 獲得數量
	 */
	public void logArchive(final L1PcInstance pc ,final L1ItemInstance item ,final L1NpcInstance npc , final long getCount)
	{
		this._lock.lock();
		try {
			this._storage.logArchive( pc , item ,   npc ,getCount );
		} finally {
			this._lock.unlock();
		}
	}
}
