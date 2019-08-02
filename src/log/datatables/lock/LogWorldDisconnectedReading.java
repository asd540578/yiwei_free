package log.datatables.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.lineage.server.model.Instance.L1PcInstance;

import log.datatables.sql.LogWorldDisconnectedTable;
import log.datatables.storage.LogWorldDisconnectedStorage;

/**
 * 斷線紀錄 Log紀錄
 * @author Eugene
 *
 */
public class LogWorldDisconnectedReading {

	private final Lock _lock;

	private final LogWorldDisconnectedStorage _storage;

	private static LogWorldDisconnectedReading _instance;

	private LogWorldDisconnectedReading() {
		this._lock = new ReentrantLock(true);
		this._storage = new LogWorldDisconnectedTable();
	}

	public static LogWorldDisconnectedReading get() {
		if (_instance == null) {
			_instance = new LogWorldDisconnectedReading();
		}
		return _instance;
	}
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
	public void logArchive(final L1PcInstance pc , final int type) 
	{
		this._lock.lock();
		try {
			this._storage.logArchive( pc , type );
		} finally {
			this._lock.unlock();
		}
	}
}
