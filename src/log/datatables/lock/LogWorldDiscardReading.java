package log.datatables.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;

import log.datatables.sql.LogWorldDiscardTable;
import log.datatables.storage.LogWorldDiscardStorage;

/**
 * 丟地上紀錄
 * 
 * @author Eugene
 *
 */
public class LogWorldDiscardReading {

	private final Lock _lock;

	private final LogWorldDiscardStorage _storage;

	private static LogWorldDiscardReading _instance;

	private LogWorldDiscardReading() {
		this._lock = new ReentrantLock(true);
		this._storage = new LogWorldDiscardTable();
	}

	public static LogWorldDiscardReading get() {
		if (_instance == null) {
			_instance = new LogWorldDiscardReading();
		}
		return _instance;
	}
	
	/**
	 * 紀錄日誌 
	 * @param pc 玩家
	 * @param item 丟棄道具
	 * @param count 丟棄數量
	 */
	public void logArchive(final L1PcInstance pc,final L1ItemInstance item,final int count) {
		this._lock.lock();
		try {
			this._storage.logArchive(pc ,  item , count);
		} finally {
			this._lock.unlock();
		}
	}
}
