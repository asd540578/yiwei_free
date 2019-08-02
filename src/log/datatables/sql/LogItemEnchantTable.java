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
import log.datatables.storage.LogItemStorage;

/**
 * 暴裝紀錄
 * @author Eugene
 *
 */
public class LogItemEnchantTable implements LogItemStorage {

	private static final Log _log = LogFactory.getLog(LogItemEnchantTable.class);

	
	/**
	 * 暴裝紀錄
	 */
	@Override
	public void logArchive(final L1PcInstance pc , final L1ItemInstance item  ,  final long removeCount )
	{
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			cn = DBLog.get().getConnection();
			ps = cn.prepareStatement(
					"INSERT INTO `other_item_enchant` SET " + " `item_objid`=? ,`item_name`=? , `char_id`=? ,`character_items`=? , `datetime`=SYSDATE() , `ip`=?");

			int i = 0;
			StringBuilder ip = pc.getNetConnection().getIp();
			
			ps.setInt(++i, item.getId());	
			ps.setString(++i, item.getItem().getName());	
			ps.setInt(++i, pc.getId());
			ps.setString(++i,  JsonManagement.Bale_Character_Items(item).toString());
			ps.setString(++i,  ip.toString() );
			
			ps.execute();

		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);

		} finally {
			SQLUtil.close(ps);
			SQLUtil.close(cn);

		}		
	}
}
