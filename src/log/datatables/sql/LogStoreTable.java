package log.datatables.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.DBLog;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.utils.SQLUtil;

import eugene.JsonManagement;
import log.datatables.storage.LogStoreStorage;

/**
 * 個人商店  log 紀錄
 * @author dexc
 *
 */
public class LogStoreTable implements LogStoreStorage {

	private static final Log _log = LogFactory.getLog(LogStoreTable.class);
	
	/**
	 * 個人商店 log 紀錄 
	 * @param StoreType  商店類型  false : 賣出 ; true : 回收
	 * @param pc 玩家
	 * @param item 道具
	 * @param itemCount 道具數量
	 * @param goldCount 金幣數量
	 * @param target 目標玩家
	 */
	@Override
	public void logArchive(final boolean StoreType , final L1PcInstance pc, final L1ItemInstance item , final long itemCount , final long goldCount , final L1PcInstance target  )
	{
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			cn = DBLog.get().getConnection();
			ps = cn.prepareStatement(
					"INSERT INTO `other_log_store` SET " + "`storeType`=? , `char_id`=? , `item_objid`=? , `item_name`=? , "
							+ " `character_items`=? ,`itemCount`=?  , `goldCount`=?,  `target_id`=? , `target`=? , `datetime`=SYSDATE() ,`ip`=?");

			int i = 0;
			
			ps.setBoolean(++i, StoreType);//買? 賣? 類型
			ps.setLong(++i, pc.getId());//玩家流水號
			ps.setLong(++i, item.getId());//道具流水號
			ps.setString(++i, item.getItem().getName());	 //道具
			
			ps.setString(++i,  JsonManagement.Bale_Character_Items(item).toString());
			
			ps.setLong(++i, itemCount);//道具數量
			ps.setLong(++i, goldCount);//消費
			
			ps.setLong(++i, target.getId());//目標流水號
			ps.setString(++i, target.getName());//目標流水號
			
			ps.setString(++i,  pc.getNetConnection().getIp().toString());
			ps.execute();

		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);

		} finally {
			SQLUtil.close(ps);
			SQLUtil.close(cn);

		}		
	}	
}
