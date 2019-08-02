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

import log.datatables.storage.LogWorldPickItUpStorage;

/**
 * 撿起來 Log紀錄
 * @author dexc
 */
public class LogWorldPickItUpTable implements LogWorldPickItUpStorage {

	private static final Log _log = LogFactory.getLog(LogWorldPickItUpTable.class);
	
	@Override
	public void logArchive(final L1PcInstance pc , final L1ItemInstance item , final long pickupCount)
	{
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			cn = DBLog.get().getConnection();
			ps = cn.prepareStatement(
					"INSERT INTO `other_log_world_pick_up` SET " + " `server_no`=? ,`char_id`=? , `char_name`=? , `mapid`=? , `item_objid`=? ,"
							+ "`item_name`=?  ,`pickup_count`=?  , `datetime`=SYSDATE() ");

			int i = 0;	
			
			ps.setInt(++i, Config.SERVERNO);
			ps.setLong(++i, pc.getId());
			ps.setString(++i, pc.getName());
			ps.setInt(++i, pc.getMapId());
	
			ps.setLong(++i, item.getId());
			
			//道具訊息
			String itemName =  item.getEnchantLevel() > 0 ? item.getNumberedName_to_String() : item.getItem().getName();
			
			ps.setString(++i, itemName);

			ps.setLong(++i, pickupCount); 
			
			ps.execute();

		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);

		} finally {
			SQLUtil.close(ps);
			SQLUtil.close(cn);

		}		
	}
	

	
}
