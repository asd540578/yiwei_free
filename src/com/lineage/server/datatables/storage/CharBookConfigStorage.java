package com.lineage.server.datatables.storage;

import com.lineage.server.templates.L1BookConfig;

/**
 * @author terry0412
 */
public interface CharBookConfigStorage {

	public void load();

	public L1BookConfig get(int objectId);

	public L1BookConfig storeCharBookConfig(int objectId, byte[] data);

	public void updateCharBookConfig(int objectId, byte[] data);

	public void updateCharBookMaxSize(int objectId, int maxSize);

}
