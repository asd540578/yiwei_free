package com.lineage.server.datatables.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.DBLog;
import com.lineage.server.datatables.storage.ServerGmCommandStorage;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.utils.SQLUtil;

/**
 * GM指令使用紀錄
 */
public class ServerGmCommandTable implements ServerGmCommandStorage {

	private static final Log _log = LogFactory.getLog(ServerGmCommandTable.class);

	/**
	 * GM指令使用紀錄
	 * @param pc
	 * @param itemtmp
	 * @param count
	 */
	@Override
	public void create(final L1PcInstance pc, final String cmd) {
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			final Timestamp lastactive = new Timestamp(System.currentTimeMillis());

			cn = DBLog.get().getConnection();
			final String sqlstr =
				"INSERT INTO `other_gmcommand` SET `gmobjid`=?,`gmname`=?,`cmd`=?,`time`=?";
			
			ps = cn.prepareStatement(sqlstr);
			
			int i = 0;
			if (pc == null) {
				ps.setInt(++i, 0);
				ps.setString(++i, "--視窗命令--");
				ps.setString(++i, cmd);
				ps.setTimestamp(++i, lastactive);
				
			} else {
				ps.setInt(++i, pc.getId());
				ps.setString(++i, pc.getName());
				ps.setString(++i, cmd);
				ps.setTimestamp(++i, lastactive);
				_log.info("建立GM指令使用紀錄: " + pc.getName() + " " + cmd);
			}

			ps.execute();

		} catch (final SQLException e) {
			_log.error(e.getLocalizedMessage(), e);

		} finally {
			SQLUtil.close(ps);
			SQLUtil.close(cn);
		}
	}
}
