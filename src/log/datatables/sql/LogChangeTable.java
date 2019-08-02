package log.datatables.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.DBLog;
import com.lineage.server.utils.SQLUtil;

import log.datatables.storage.LogChangeStorage;

/**
 * 玩家變更 Log紀錄
 * 
 * @author dexc
 *
 */
public class LogChangeTable implements LogChangeStorage {

	private static final Log _log = LogFactory.getLog(LogChangeTable.class);

	/**
	 * 玩家變更 Log紀錄
	 * 
	 * @param type    1 = 改名稱 ; 2 = 改密碼 ; 3 = 變性 ; 4 = 改帳號
	 * @param objid   使用者的編號
	 * @param olddata 舊的資料
	 * @param newdata 新的資料
	 * @param Ip      使用者的IP
	 */
	@Override
	public void logArchive(final byte type, final int objid, final String olddata, final String newdata,
			final String Ip) {
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			cn = DBLog.get().getConnection();
			ps = cn.prepareStatement("INSERT INTO `other_log_change` SET "
					+ " `type`=? , `char_id`=? ,`olddata`=? ,`newdata`=? ,`datetime`=SYSDATE() ,`ipmac`=?");

			int i = 0;
			ps.setInt(++i, type);
			ps.setLong(++i, objid);
			ps.setString(++i, olddata);
			ps.setString(++i, newdata);
			ps.setString(++i, Ip);

			ps.execute();

		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);

		} finally {
			SQLUtil.close(ps);
			SQLUtil.close(cn);

		}
	}
}
