package com.lineage.server.datatables.lock;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.lineage.server.datatables.sql.DwarfForElfTable;
import com.lineage.server.datatables.storage.DwarfForElfStorage;
import com.lineage.server.model.Instance.L1ItemInstance;

/**
 * 精靈倉庫數據
 * @author dexc
 *
 */
public class DwarfForElfReading {

	private final Lock _lock;

	private final DwarfForElfStorage _storage;

	private static DwarfForElfReading _instance;

	private DwarfForElfReading() {
		this._lock = new ReentrantLock(true);
		this._storage = new DwarfForElfTable();
	}

	public static DwarfForElfReading get() {
		if (_instance == null) {
			_instance = new DwarfForElfReading();
		}
		return _instance;
	}

	/**
	 * 資料預先載入
	 */
	public void load() {
		this._lock.lock();
		try {
			this._storage.load();
			
		} finally {
			this._lock.unlock();
		}
	}

	/**
	 * 傳回精靈倉庫數據
	 * @return 
	 */
	public CopyOnWriteArrayList<L1ItemInstance> loadItems(final String account_name) {
		this._lock.lock();
		CopyOnWriteArrayList<L1ItemInstance> tmp = null;
		try {
			tmp = this._storage.loadItems(account_name);
			
		} finally {
			this._lock.unlock();
		}
		return tmp;
	}
	
	/**
	 * 刪除精靈倉庫資料(完整)
	 * @param account_name
	 */
	public void delUserItems(final String account_name) {
		this._lock.lock();
		try {
			this._storage.delUserItems(account_name);
			
		} finally {
			this._lock.unlock();
		}
	}
	
	/**
	 * 加入精靈倉庫數據
	 */
	public void insertItem(final String account_name, final L1ItemInstance item) {
		this._lock.lock();
		try {
			this._storage.insertItem(account_name, item);
			
		} finally {
			this._lock.unlock();
		}
	}

	/**
	 * 精靈倉庫資料更新(物品數量)
	 * @param item
	 */
	public void updateItem(final L1ItemInstance item) {
		this._lock.lock();
		try {
			this._storage.updateItem(item);
			
		} finally {
			this._lock.unlock();
		}
	}

	/**
	 * 精靈倉庫物品資料刪除
	 * @param account_name
	 * @param item
	 */
	public void deleteItem(final String account_name, final L1ItemInstance item) {
		this._lock.lock();
		try {
			this._storage.deleteItem(account_name, item);
			
		} finally {
			this._lock.unlock();
		}
	}
	
	/**
	 * 該精靈倉庫是否有指定數據
	 * @param account_name
	 * @param objid
	 * @param count 
	 * @return 
	 */
	public boolean getUserItems(final String account_name, final int objid, int count) {
		this._lock.lock();
		boolean tmp = false;
		try {
			tmp = this._storage.getUserItems(account_name, objid, count);
			
		} finally {
			this._lock.unlock();
		}
		return tmp;
	}
}
