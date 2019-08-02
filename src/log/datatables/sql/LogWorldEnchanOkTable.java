package log.datatables.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.DBLog;
import com.lineage.config.Config;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.utils.SQLUtil;

import log.datatables.storage.LogWorldEnchanOkStorage;


/**
 * 裝備強化成功  Log 紀錄
 * @author dexc
 */
public class LogWorldEnchanOkTable implements LogWorldEnchanOkStorage {

	private static final Log _log = LogFactory.getLog(LogWorldEnchanOkTable.class);
	
	@Override
	public void logArchive(final L1PcInstance pc,final L1ItemInstance item ,final int oldEnchantLvl ,  final int newEnchantLvl)
	{
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			cn = DBLog.get().getConnection();
			ps = cn.prepareStatement(
					"INSERT INTO `other_log_world_enchant_ok` SET " + " `server_no`=? , `char_id`=? , `char_name`=? , `item_objid`=? ,`item_id`=? ,`item_name`=? ,"
							+ "`oldEnchantLvl`=? ,`newEnchantLvl`=? , `datetime`=SYSDATE()");

			int i = 0;	
			
			ps.setInt(++i, Config.SERVERNO);
			ps.setLong(++i, pc.getId());
			ps.setString(++i, pc.getName());
			ps.setLong(++i, item.getId());
			ps.setInt(++i, item.getItemId());
			ps.setString(++i, item.getItem().getName());
			ps.setInt(++i, oldEnchantLvl);
			ps.setInt(++i, newEnchantLvl);		
			
			ps.execute();

		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);

		} finally {
			SQLUtil.close(ps);
			SQLUtil.close(cn);

		}		
	}
	

	
}
