package log.datatables.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;

import log.datatables.sql.LogStoreTable;
import log.datatables.storage.LogStoreStorage;

/**
 * 個人商店 log 紀錄 
 * @author dexc
 */
public class LogStoreReading {

	private final Lock _lock;

	private final LogStoreStorage _storage;

	private static LogStoreReading _instance;

	private LogStoreReading() {
		this._lock = new ReentrantLock(true);
		this._storage = new LogStoreTable();
	}

	public static LogStoreReading get() {
		if (_instance == null) {
			_instance = new LogStoreReading();
		}
		return _instance;
	}


	/**
	 * 個人商店 log 紀錄 
	 * @param StoreType 商店類型  false : 賣出 ; true : 回收
	 * @param pc 玩家
	 * @param item 道具
	 * @param itemCount 道具數量
	 * @param goldCount 金幣數量
	 * @param targetPc 目標玩家
	 */
	public void logArchive(final boolean StoreType , final L1PcInstance pc, final L1ItemInstance item , final long itemCount , final long goldCount , final L1PcInstance target  )
	{
		this._lock.lock();
		try {
			this._storage.logArchive(StoreType, pc , item , itemCount , goldCount , target);
		} finally {
			this._lock.unlock();
		}
	}
	
}
