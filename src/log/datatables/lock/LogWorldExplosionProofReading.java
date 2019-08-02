package log.datatables.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;

import log.datatables.sql.LogWorldExplosionProofTable;
import log.datatables.storage.LogWorldExplosionProofStorage;

/**
 * 防爆  Log 紀錄
 * 
 * @author Eugene
 *
 */
public class LogWorldExplosionProofReading {

	private final Lock _lock;

	private final LogWorldExplosionProofStorage _storage;

	private static LogWorldExplosionProofReading _instance;

	private LogWorldExplosionProofReading() {
		this._lock = new ReentrantLock(true);
		this._storage = new LogWorldExplosionProofTable();
	}

	public static LogWorldExplosionProofReading get() {
		if (_instance == null) {
			_instance = new LogWorldExplosionProofReading();
		}
		return _instance;
	}
	
	/**
	 * 紀錄日誌 
	 * @param type 0     裝備中 無法插入防暴
	 * @param type 1  (裝備)初級防爆卷
	 * @param type 2  (裝備)高級防爆卷
	 * @param type 3  (裝備)頂級防爆卷 
	 * @param type 4  (臂甲)防爆卡
	 * @param type 5  (監獄)防爆卡
	 * @param type 6  (釣竿)防爆卡
	 * @param pc 玩家
	 * @param tgItem 要受保護的裝備
	 */
	public void logArchive(final int type , final L1PcInstance pc , final L1ItemInstance tgItem) {
		this._lock.lock();
		try {
			this._storage.logArchive(type , pc ,  tgItem);
		} finally {
			this._lock.unlock();
		}
	}
}
