package log.datatables.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.lineage.server.model.Instance.L1PcInstance;

import log.datatables.sql.LogWorldGmCmmandTable;
import log.datatables.storage.LogWorldGmCmmandStorage;


/**
 * GM指令 Log 紀錄
 * 
 * @author Eugene
 *
 */
public class LogWorldGmCmmandReading {

	private final Lock _lock;

	private final LogWorldGmCmmandStorage _storage;

	private static LogWorldGmCmmandReading _instance;

	private LogWorldGmCmmandReading() {
		this._lock = new ReentrantLock(true);
		this._storage = new LogWorldGmCmmandTable();
	}

	public static LogWorldGmCmmandReading get() {
		if (_instance == null) {
			_instance = new LogWorldGmCmmandReading();
		}
		return _instance;
	}

	/**
	 * 紀錄日誌 
	 * @param pc 玩家
	 * @param cmd 指令
	 */
	public void logArchive(final L1PcInstance pc , final String cmd) {
		this._lock.lock();
		try {
			this._storage.logArchive(pc ,  cmd);
		} finally {
			this._lock.unlock();
		}
	}
}
