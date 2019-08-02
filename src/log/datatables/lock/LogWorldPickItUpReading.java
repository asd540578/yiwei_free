package log.datatables.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;

import log.datatables.sql.LogWorldPickItUpTable;
import log.datatables.storage.LogWorldPickItUpStorage;


/**
 * 撿起來 Log紀錄
 * @author dexc
 *
 */
public class LogWorldPickItUpReading {

	private final Lock _lock;

	private final LogWorldPickItUpStorage _storage;

	private static LogWorldPickItUpReading _instance;

	private LogWorldPickItUpReading() {
		this._lock = new ReentrantLock(true);
		this._storage = new LogWorldPickItUpTable();
	}

	public static LogWorldPickItUpReading get() {
		if (_instance == null) {
			_instance = new LogWorldPickItUpReading();
		}
		return _instance;
	}

	/**
	 * 紀錄日誌 
	 * @param pc 玩家
	 * @param item 道具
	 * @param pickupCount 撿起的數量
	 */
	public void logArchive(final L1PcInstance pc , final L1ItemInstance item , final long pickupCount) {
		this._lock.lock();
		try {
			this._storage.logArchive( pc , item ,  pickupCount);
		} finally {
			this._lock.unlock();
		}
	}
}
