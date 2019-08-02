package com.lineage.server.datatables.storage;

import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.templates.L1Item;

/**
 * 天寶購買紀錄
 */
public interface ServerCnInfoStorage {

	/**
	 * 天寶購買紀錄
	 * @param pc
	 * @param itemtmp
	 * @param count
	 */
	public void create(final L1PcInstance pc, final L1Item itemtmp, final int count);
}
