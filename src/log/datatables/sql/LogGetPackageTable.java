package log.datatables.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.DBLog;
import com.lineage.server.utils.SQLUtil;

import log.datatables.storage.LogGetPackageStorage;

/**
 * 包裹領取紀錄
 * 
 * @author dexc
 *
 */
public class LogGetPackageTable implements LogGetPackageStorage {

	private static final Log _log = LogFactory.getLog(LogGetPackageTable.class);

	/**
	 * 包裹紀錄
	 * 
	 * @param type      類型 0 = 贊助 ; 1.2 = 角色包裹 ; 3= 歸還
	 * @param objid     玩家流水號
	 * @param key       流水號
	 * @param itemid    道具
	 * @param itemName  道具名稱
	 * @param itemCount 道具數量
	 * @param ip        IP
	 */
	@Override
	public void logArchive(final int type, final long objid, final int key, final int itemid, final String itemName,
			final long itemCount, final String ip) {
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			cn = DBLog.get().getConnection();
			ps = cn.prepareStatement("INSERT INTO `other_get_package` SET " + "`type`=?,`object`=?,"
					+ "`key`=?,`itemid`=?," + "`itemName`=?,`itemCount`=?, `datetime`=SYSDATE() , `ip`=? ");

			int i = 0;
			ps.setInt(++i, type);
			ps.setLong(++i, objid);
			ps.setLong(++i, key);
			ps.setInt(++i, itemid);
			ps.setString(++i, itemName);
			ps.setLong(++i, itemCount);

			ps.setString(++i, ip);

			ps.execute();

		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);

		} finally {
			SQLUtil.close(ps);
			SQLUtil.close(cn);

		}
	}
}
