package log.datatables.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;

import log.datatables.sql.LogItemDeleteTable;
import log.datatables.storage.LogItemStorage;

/**
 * 物品刪除
 * @author dexc
 */
public class LogItemDeleteReading {

	private final Lock _lock;

	private final LogItemStorage _storage;

	private static LogItemDeleteReading _instance;

	private LogItemDeleteReading() {
		this._lock = new ReentrantLock(true);
		this._storage = new LogItemDeleteTable();
	}

	public static LogItemDeleteReading get() {
		if (_instance == null) {
			_instance = new LogItemDeleteReading();
		}
		return _instance;
	}
	
	/**
	 * 物品刪除
	 * @param pc 玩家
	 * @param item 刪除道具
	 * 	@param remCount 刪除數量
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
