package log.datatables.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;

import log.datatables.sql.LogWorldEnchanOkTable;
import log.datatables.storage.LogWorldEnchanOkStorage;

/**
 * 裝備升級 Log紀錄
 * @author Eugene
 *
 */
public class LogWorldEnchanOkReading {

	private final Lock _lock;

	private final LogWorldEnchanOkStorage _storage;

	private static LogWorldEnchanOkReading _instance;

	private LogWorldEnchanOkReading() {
		this._lock = new ReentrantLock(true);
		this._storage = new LogWorldEnchanOkTable();
	}

	public static LogWorldEnchanOkReading get() {
		if (_instance == null) {
			_instance = new LogWorldEnchanOkReading();
		}
		return _instance;
	}

	/**
	 * 裝備升級 Log紀錄
	 * @param pc 使用者
	 * @param item 道具
	 * @param oldEnchantLvl 舊強化
	 * @param newEnchantLvl 新強化
	 */
	public void logArchive(final L1PcInstance pc,final L1ItemInstance item,final int oldEnchantLvl , final int newEnchantLvl) {
		this._lock.lock();
		try {
			this._storage.logArchive( pc , item ,oldEnchantLvl , newEnchantLvl  );
		} finally {
			this._lock.unlock();
		}
	}
}
