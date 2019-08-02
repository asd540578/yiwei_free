package log.datatables.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;

import log.datatables.sql.LogOtherItemOfflineTradeTable;
import log.datatables.storage.LogOtherItemOfflineTradeStorage;

/**
 * 離線商店的紀錄
 * 
 * @author Eugene
 *
 */
public class LogOtherItemOfflineTradeReading {

	private final Lock _lock;

	private final LogOtherItemOfflineTradeStorage _storage;

	private static LogOtherItemOfflineTradeReading _instance;

	private LogOtherItemOfflineTradeReading() {
		this._lock = new ReentrantLock(true);
		this._storage = new LogOtherItemOfflineTradeTable();
	}

	public static LogOtherItemOfflineTradeReading get() {
		if (_instance == null) {
			_instance = new LogOtherItemOfflineTradeReading();
		}
		return _instance;
	}

	public void logArchive(final L1PcInstance pc, final L1ItemInstance item, final long count, final int adena,
			final int status) {
		this._lock.lock();
		try {
			this._storage.logArchive(pc, item, count, adena, status);
		} finally {
			this._lock.unlock();
		}
	}
}
