package log.datatables.storage;

/**
 * 玩家變更 Log紀錄
 * @author dexc
 *
 */
public interface LogChangeStorage {
	

	/**
	 * 玩家變更 Log紀錄
	 * @param type 1 = 改名稱  ; 2 = 改密碼 ; 3 = 變性 ; 4 = 改帳號
	 * @param objid 使用者的編號
	 * @param olddata 舊的資料
	 * @param newdata 新的資料
	 * @param Ip 使用者的IP
	 */
	public void logArchive(final byte type , final int objid , final String olddata , final String newdata , final String Ip);	
	
		
}
