package log.datatables.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;

import log.datatables.sql.LogItemTradeTable;
import log.datatables.storage.LogItemTradeStorage;

/**
 *  交易紀錄
 * @author Eugene
 *
 */
public class LogItemTradeReading {

	private final Lock _lock;

	private final LogItemTradeStorage _storage;

	private static LogItemTradeReading _instance;

	private LogItemTradeReading() {
		this._lock = new ReentrantLock(true);
		this._storage = new LogItemTradeTable();
	}

	public static LogItemTradeReading get() {
		if (_instance == null) {
			_instance = new LogItemTradeReading();
		}
		return _instance;
	}

	/**
	 * 交易
	 * @param pc 玩家
	 * @param target 目標玩家
	 * @param item 道具
	 * @param count 數量
	 */
	public void logArchive(final L1PcInstance pc , final L1PcInstance target , final L1ItemInstance item  , final long count) {
		this._lock.lock();
		try {
			this._storage.logArchive( pc ,  target ,  item ,   count  );
		} finally {
			this._lock.unlock();
		}
	}
}
