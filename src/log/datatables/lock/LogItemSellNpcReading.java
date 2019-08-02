package log.datatables.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;

import log.datatables.sql.LogItemSellNpcTable;
import log.datatables.storage.LogItemSellNpcStorage;

/**
 *  賣萬物回收商
 *  
 * @author Eugene
 *
 */
public class LogItemSellNpcReading {

	private final Lock _lock;

	private final LogItemSellNpcStorage _storage;

	private static LogItemSellNpcReading _instance;

	private LogItemSellNpcReading() {
		this._lock = new ReentrantLock(true);
		this._storage = new LogItemSellNpcTable();
	}

	public static LogItemSellNpcReading get() {
		if (_instance == null) {
			_instance = new LogItemSellNpcReading();
		}
		return _instance;
	}
	


	/**
	 * 賣萬物回收商
	 * @param pc 玩家
	 * @param item 賣的物品
	 * @param count 賣的數量
	 * @param price 賣的金額
	 */
	public void logArchive(final L1PcInstance pc, final L1ItemInstance item  , final long count , final long price  ) {
		this._lock.lock();
		try {
			this._storage.logArchive( pc ,  item ,   count ,  price );
		} finally {
			this._lock.unlock();
		}
	}
}
