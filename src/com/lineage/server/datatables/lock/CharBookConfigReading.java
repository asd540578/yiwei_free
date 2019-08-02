package com.lineage.server.datatables.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.lineage.server.datatables.sql.CharBookConfigTable;
import com.lineage.server.datatables.storage.CharBookConfigStorage;
import com.lineage.server.templates.L1BookConfig;

/**
 * @author terry0412
 */
public class CharBookConfigReading {

	private final Lock _lock;

	private final CharBookConfigStorage _storage;

	private static CharBookConfigReading _instance;

	private CharBookConfigReading() {
		this._lock = new ReentrantLock(true);
		this._storage = new CharBookConfigTable();
	}

	public static CharBookConfigReading get() {
		if (_instance == null) {
			_instance = new CharBookConfigReading();
		}
		return _instance;
	}

	public void load() {
		this._lock.lock();
		try {
			this._storage.load();
		} finally {
			this._lock.unlock();
		}
	}

	public L1BookConfig get(final int objectId) {
		this._lock.lock();
		L1BookConfig tmp;
		try {
			tmp = this._storage.get(objectId);
		} finally {
			this._lock.unlock();
		}
		return tmp;
	}

	public L1BookConfig storeCharBookConfig(final int objectId, final byte[] data) {
		this._lock.lock();
		L1BookConfig tmp;
		try {
			tmp = this._storage.storeCharBookConfig(objectId, data);
		} finally {
			this._lock.unlock();
		}
		return tmp;
	}

	public void updateCharBookConfig(final int objectId, final byte[] data) {
		this._lock.lock();
		try {
			this._storage.updateCharBookConfig(objectId, data);
		} finally {
			this._lock.unlock();
		}
	}

	public void updateCharBookMaxSize(final int objectId, final int maxSize) {
		this._lock.lock();
		try {
			this._storage.updateCharBookMaxSize(objectId, maxSize);
		} finally {
			this._lock.unlock();
		}
	}
}
