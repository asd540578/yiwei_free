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

import log.datatables.storage.LogWorldUseItemStorage;

/**
 * 道具使用的紀錄
 * @author Eugene
 *
 */
public class LogWorldUseItemTable implements LogWorldUseItemStorage {

	private static final Log _log = LogFactory.getLog(LogWorldUseItemTable.class);
	
	@Override
	public void logArchive(final L1PcInstance pc,final L1ItemInstance item)
	{
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			cn = DBLog.get().getConnection();
			ps = cn.prepareStatement(
					"INSERT INTO `other_log_world_useitem` SET " + "  `server_no`=? , `char_id`=? , `char_name`=? , `item_id`=? , `item_name`=? , "
							+ " `datetime`=SYSDATE() , `ip`=?");

			int i = 0;	
			
			ps.setInt(++i, Config.SERVERNO);
			ps.setLong(++i, pc.getId());
			ps.setString(++i, pc.getName());
			ps.setInt(++i, item.getItemId());
			
			
			//道具訊息
			ps.setString(++i,  item.getItem().getName());
			
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
