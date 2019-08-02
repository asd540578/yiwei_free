package log.datatables.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;

import log.datatables.sql.LogDissolveTable;
import log.datatables.storage.LogDissolveStorage;

/**
 * 溶解 log 紀錄 
 * @author Eugene
 */
public class LogDissolveReading {

	private final Lock _lock;

	private final LogDissolveStorage _storage;

	private static LogDissolveReading _instance;

	private LogDissolveReading() {
		this._lock = new ReentrantLock(true);
		this._storage = new LogDissolveTable();
	}

	public static LogDissolveReading get() {
		if (_instance == null) {
			_instance = new LogDissolveReading();
		}
		return _instance;
	}

	/**
	 * 溶解 Log紀錄
	 * @param pc 使用者
	 * @param item 道具
	 * @param getcount 獲得魔法結晶體數量
	 */
	public void logArchive(final L1PcInstance pc, final L1ItemInstance item , final long getcount  )
	{
		this._lock.lock();
		try {
			this._storage.logArchive(pc, item ,  getcount);
		} finally {
			this._lock.unlock();
		}
	}
	
}
