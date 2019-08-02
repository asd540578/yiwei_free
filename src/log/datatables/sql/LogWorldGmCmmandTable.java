package log.datatables.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.DBLog;
import com.lineage.config.Config;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.utils.SQLUtil;

import log.datatables.storage.LogWorldGmCmmandStorage;

/**
 * GM指令 Log 紀錄
 * 
 * @author Eugene
 *
 */
public class LogWorldGmCmmandTable implements LogWorldGmCmmandStorage {

	private static final Log _log = LogFactory.getLog(LogWorldGmCmmandTable.class);
	
	@Override
	public void logArchive(final L1PcInstance pc,final String cmd)
	{
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			cn = DBLog.get().getConnection();
			ps = cn.prepareStatement(
					"INSERT INTO `other_log_world_gm_command` SET " + "  `server_no`=? ,  `Ip`=?,`char_acc`=? ,`char_id`=? , `char_name`=? , `command`=? ,"
							+ " `datetime`=SYSDATE() ");

			int i = 0;	
			
			ps.setInt(++i, Config.SERVERNO);
			
			if(pc == null) {
				ps.setString(++i, 	"serverip");
				ps.setString(++i,  "無");
				ps.setLong(++i, 0);
				ps.setString(++i, " 系統命令執行");
			} else {
				ps.setString(++i, 	pc.getNetConnection().getIp().toString());
				ps.setString(++i, pc.getAccountName());
				ps.setLong(++i, pc.getId());
				ps.setString(++i, pc.getName());
			}

			ps.setString(++i, cmd);
			
			ps.execute();

		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);

		} finally {
			SQLUtil.close(ps);
			SQLUtil.close(cn);

		}		
	}
	
}
