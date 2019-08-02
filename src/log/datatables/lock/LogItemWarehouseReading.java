package log.datatables.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;

import log.datatables.sql.LogItemWarehouseTable;
import log.datatables.storage.LogItemWarehouseStorage;

/**
 * 倉庫使用紀錄
 * @author Eugene
 *
 */
public class LogItemWarehouseReading {

	private final Lock _lock;

	private final LogItemWarehouseStorage _storage;

	private static LogItemWarehouseReading _instance;

	private LogItemWarehouseReading() {
		this._lock = new ReentrantLock(true);
		this._storage = new LogItemWarehouseTable();
	}

	public static LogItemWarehouseReading get() {
		if (_instance == null) {
			_instance = new LogItemWarehouseReading();
		}
		return _instance;
	}

	/**
	 * 倉庫使用紀錄
	 * @param pc 使用者
	 * @param item 存取道具
	 * @param count 存取數量
	 * @param type 存取類型 false 存  : true 取
	 */
	public void logWarehouseArchive(final L1PcInstance pc, final L1ItemInstance item  , final long count , final boolean type  ) {
		this._lock.lock();
		try {
			this._storage.logWarehouseArchive( pc ,  item ,   count ,  type );
		} finally {
			this._lock.unlock();
		}
	}
	
	/**
	 * 血盟倉庫使用紀錄
	 * @param pc 使用者
	 * @param item 存取道具
	 * @param count 存取數量
	 * @param type 存取類型 false 存  : true 取
	 */
	public void logWarehouseClanArchive(final L1PcInstance pc, final L1ItemInstance item ,final long count , final boolean type)
	{
		this._lock.lock();
		try {
			this._storage.logWarehouseClanArchive(pc,   item , count , type);
		} finally {
			this._lock.unlock();
		}
	}
	
}
