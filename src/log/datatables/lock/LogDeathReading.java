package log.datatables.lock;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;

import log.datatables.sql.LogDeathTable;
import log.datatables.storage.LogDeathStorage;

/**
 * 死亡 log 紀錄 
 * @author Eugene
 */
public class LogDeathReading {

	private final Lock _lock;

	private final LogDeathStorage _storage;

	private static LogDeathReading _instance;

	private LogDeathReading() {
		this._lock = new ReentrantLock(true);
		this._storage = new LogDeathTable();
	}

	public static LogDeathReading get() {
		if (_instance == null) {
			_instance = new LogDeathReading();
		}
		return _instance;
	}

	/**
	 * 死亡 log 紀錄 
	 * @param pc
	 * @param items 噴出道具清單
	 * @param skills 噴出魔法清單
	 * @param exp 噴出經驗數量
	 */
	public void logDeathArchive(final L1PcInstance pc, final ArrayList<L1ItemInstance> items ,final ArrayList<Integer> skills  ,  final long exp)
	{
		this._lock.lock();
		try {
			this._storage.logDeathArchive(pc, items , skills , exp);
		} finally {
			this._lock.unlock();
		}
	}
	
}
