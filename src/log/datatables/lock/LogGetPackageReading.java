package log.datatables.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import log.datatables.sql.LogGetPackageTable;
import log.datatables.storage.LogGetPackageStorage;

/**
 * 包裹LOG紀錄
 * @author dexc
 *
 */
public class LogGetPackageReading {

	private final Lock _lock;

	private final LogGetPackageStorage _storage;

	private static LogGetPackageReading _instance;

	private LogGetPackageReading() {
		this._lock = new ReentrantLock(true);
		this._storage = new LogGetPackageTable();
	}

	public static LogGetPackageReading get() {
		if (_instance == null) {
			_instance = new LogGetPackageReading();
		}
		return _instance;
	}

	/**
	 * 包裹紀錄
	 * 
	 * @param type      類型 0 = 贊助 ; 1.2 = 角色包裹 ; 3= 歸還
	 * @param objid     玩家流水號
	 * @param key       流水號
	 * @param itemid    道具
	 * @param itemName  道具名稱
	 * @param itemCount 道具數量
	 * @param ip        IP
	 */
	public void logArchive(final int type, final long objid, final int key, final int itemid, final String itemName,
			final long itemCount, final String ip) {
		this._lock.lock();
		try {
			this._storage.logArchive(type ,  objid, key ,  itemid , itemName , itemCount , ip);
		} finally {
			this._lock.unlock();
		}
	}
}
