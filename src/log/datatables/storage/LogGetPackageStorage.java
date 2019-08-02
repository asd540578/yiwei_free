package log.datatables.storage;

/**
 * 包裹取得紀錄
 * 
 * @author dexc
 *
 */
public interface LogGetPackageStorage {

	/**
	 * 包裹紀錄
	 * 
	 * @param type      類型 0 = 贊助 ; 1.2 = 角色包裹 ; 3= 歸還
	 * @param objid     玩家流水號
	 * @param key       流水號
	 * @param itemid    道具
	 * @param itemName  道具名稱
	 * @param itemCount 道具數量
	 * @param ip        IP
	 */
	public void logArchive(final int type, final long objid, final int key, final int itemid, final String itemName,
			final long itemCount, final String ip);

}
