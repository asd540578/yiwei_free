package log.datatables.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;

import log.datatables.sql.LogWorldSpecialBuyTable;
import log.datatables.storage.LogWorldSpecialBuyStorage;

/**
 * 特殊購買 Log紀錄
 * @author dexc
 *
 */
public class LogWorldSpecialBuyReading {

	private final Lock _lock;

	private final LogWorldSpecialBuyStorage _storage;

	private static LogWorldSpecialBuyReading _instance;

	private LogWorldSpecialBuyReading() {
		this._lock = new ReentrantLock(true);
		this._storage = new LogWorldSpecialBuyTable();
	}

	public static LogWorldSpecialBuyReading get() {
		if (_instance == null) {
			_instance = new LogWorldSpecialBuyReading();
		}
		return _instance;
	}

	/**
	 * 特殊購買 Log 紀錄
	 * @param pc 使用者
	 * @param itemCn_id 特殊金幣
	 * @param cnCount 購買金額
	 * @param item 獲得道具
	 */
	public void logArchive(final L1PcInstance pc, final int cnItem_id, final long cnCount, final L1ItemInstance item) 
	{
		this._lock.lock();
		try {
			this._storage.logArchive( pc , cnItem_id , cnCount , item);
		} finally {
			this._lock.unlock();
		}
	}
}
