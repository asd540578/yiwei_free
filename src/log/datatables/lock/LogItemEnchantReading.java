package log.datatables.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;

import log.datatables.sql.LogItemEnchantTable;
import log.datatables.storage.LogItemStorage;

/**
 * 暴裝紀錄
 * @author Eugene
 *
 */
public class LogItemEnchantReading {

	private final Lock _lock;

	private final LogItemStorage _storage;

	private static LogItemEnchantReading _instance;

	private LogItemEnchantReading() {
		this._lock = new ReentrantLock(true);
		this._storage = new LogItemEnchantTable();
	}

	public static LogItemEnchantReading get() {
		if (_instance == null) {
			_instance = new LogItemEnchantReading();
		}
		return _instance;
	}
	
	/**
	 * 暴裝紀錄
	 * @param pc
	 * @param item
	 * @param removeCount
	 */
	public void logArchive(final L1PcInstance pc, final L1ItemInstance item  , final long removeCount ) {
		this._lock.lock();
		try {
			this._storage.logArchive( pc ,  item ,   removeCount );
		} finally {
			this._lock.unlock();
		}
	}
}
