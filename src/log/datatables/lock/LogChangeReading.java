package log.datatables.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import log.datatables.sql.LogChangeTable;
import log.datatables.storage.LogChangeStorage;

/**
 * 玩家變更 Log紀錄
 * @author dexc
 *
 */
public class LogChangeReading {

	private final Lock _lock;

	private final LogChangeStorage _storage;

	private static LogChangeReading _instance;

	private LogChangeReading() {
		this._lock = new ReentrantLock(true);
		this._storage = new LogChangeTable();
	}

	public static LogChangeReading get() {
		if (_instance == null) {
			_instance = new LogChangeReading();
		}
		return _instance;
	}
	
	/**
	 * 玩家變更 Log紀錄
	 * 
	 * @param type    1 = 改名稱 ; 2 = 改密碼 ; 3 = 變性 ; 4 = 改帳號
	 * @param objid   使用者的編號
	 * @param olddata 舊的資料
	 * @param newdata 新的資料
	 * @param Ip      使用者的IP
	 */
	public void logArchive(final byte type , final int objid , final String olddata , final String newdata , final String ip)
	{
		this._lock.lock();
		try {
			this._storage.logArchive(type,   objid , olddata , newdata ,  ip);
		} finally {
			this._lock.unlock();
		}
	}
}
