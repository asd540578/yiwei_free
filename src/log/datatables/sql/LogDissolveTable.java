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
import log.datatables.storage.LogDissolveStorage;

/**
 * 溶解  log 紀錄
 * @author dexc
 *
 */
public class LogDissolveTable implements LogDissolveStorage {

	private static final Log _log = LogFactory.getLog(LogDissolveTable.class);
	
	@Override
	public void logArchive(final L1PcInstance pc, final L1ItemInstance item ,  final long count  )
	{
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			cn = DBLog.get().getConnection();
			ps = cn.prepareStatement(
					"INSERT INTO `other_log_dissolve` SET " + " `char_id`=? , `item_objid`=? , `item_name`=? , `character_items`=? , `getcount`=?,  `datetime`=SYSDATE() ,`ip`=?");

			int i = 0;
			
			ps.setLong(++i, pc.getId());
			
			ps.setLong(++i, item.getId());
			ps.setString(++i, item.getItem().getName());	
			
			ps.setString(++i,  JsonManagement.Bale_Character_Items(item).toString());
			
			ps.setLong(++i, count);
			
			ps.setString(++i, pc.getNetConnection().getIp().toString());

			ps.execute();

		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);

		} finally {
			SQLUtil.close(ps);
			SQLUtil.close(cn);

		}		
	}
}
