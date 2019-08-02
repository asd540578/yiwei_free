package log.datatables.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;

import log.datatables.sql.LogWorldThrowNpcTable;
import log.datatables.storage.LogWorldThrowNpcStorage;

/**
 * 物品拉給寵物或NPC Log 紀錄
 * 
 * @author Eugene
 *
 */
public class LogWorldThrowNpcReading {

	private final Lock _lock;

	private final LogWorldThrowNpcStorage _storage;

	private static LogWorldThrowNpcReading _instance;

	private LogWorldThrowNpcReading() {
		this._lock = new ReentrantLock(true);
		this._storage = new LogWorldThrowNpcTable();
	}

	public static LogWorldThrowNpcReading get() {
		if (_instance == null) {
			_instance = new LogWorldThrowNpcReading();
		}
		return _instance;
	}
	

	/**
	 * 紀錄日誌 
	 * @param pc
	 * @param item
	 * @param count
	 * @param target
	 */
	public void logArchive(final L1PcInstance pc,final L1ItemInstance item,final long count,final L1NpcInstance target) {
		this._lock.lock();
		try {
			this._storage.logArchive(pc ,  item , count , target);
		} finally {
			this._lock.unlock();
		}
	}
}
