package log.datatables.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.DBLog;
import com.lineage.config.Config;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.utils.SQLUtil;

import log.datatables.storage.LogWorldDisconnectedStorage;

/**
 * 斷線紀錄
 * 
 * @author Eugene
 *
 */
public class LogWorldDisconnectedTable implements LogWorldDisconnectedStorage {

	private static final Log _log = LogFactory.getLog(LogWorldDisconnectedTable.class);

	/**
	 * 斷線紀錄
	 * @param pc 使用者
	 * @param type <br>
	 * 1 = 速度異常
	 * <br>
	 * 2 = 使用倉庫封包異常 
	 * <br>
	 * 3 = 特殊購買封包異常
	 *  <br>
	 */
	@Override
	public void logArchive(final L1PcInstance pc, final int type) {
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			cn = DBLog.get().getConnection();
			ps = cn.prepareStatement("INSERT INTO `other_log_world_disconnected` SET "
					+ " `server_no`=? , `char_id`=? , `char_name`=? , `type`=? ," + " `datetime`=SYSDATE() ");

			int i = 0;

			ps.setInt(++i, Config.SERVERNO);
			ps.setLong(++i, pc.getId());
			ps.setString(++i, pc.getName());
			ps.setInt(++i, type);

			ps.execute();

		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);

		} finally {
			SQLUtil.close(ps);
			SQLUtil.close(cn);

		}
	}

}
