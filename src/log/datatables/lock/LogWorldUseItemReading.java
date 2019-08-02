package log.datatables.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;

import log.datatables.sql.LogWorldUseItemTable;
import log.datatables.storage.LogWorldUseItemStorage;

/**
 * 道具使用的紀錄
 * @author Eugene
 *
 */
public class LogWorldUseItemReading {

	private final Lock _lock;

	private final LogWorldUseItemStorage _storage;

	private static LogWorldUseItemReading _instance;

	private LogWorldUseItemReading() {
		this._lock = new ReentrantLock(true);
		this._storage = new LogWorldUseItemTable();
	}

	public static LogWorldUseItemReading get() {
		if (_instance == null) {
			_instance = new LogWorldUseItemReading();
		}
		return _instance;
	}
	
	/**
	 * 紀錄日誌 
	 * @param pc 玩家
	 * @param item 丟棄道具
	 * @param count 丟棄數量
	 */
	public void logArchive(final L1PcInstance pc,final L1ItemInstance item) {
		this._lock.lock();
		try {
			this._storage.logArchive(pc ,  item );
		} finally {
			this._lock.unlock();
		}
	}
}
