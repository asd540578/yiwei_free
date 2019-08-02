package com.lineage.server.datatables.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.DBCharacter;
import com.lineage.server.datatables.CharObjidTable;
import com.lineage.server.datatables.storage.CharBookConfigStorage;
import com.lineage.server.templates.L1BookConfig;
import com.lineage.server.utils.PerformanceTimer;
import com.lineage.server.utils.SQLUtil;

/**
 * @author terry0412
 */
public class CharBookConfigTable implements CharBookConfigStorage {

	private static final Log _log = LogFactory.getLog(CharBookConfigTable.class);

	private static final Map<Integer, L1BookConfig> _configList =
			new ConcurrentHashMap<Integer, L1BookConfig>();

	@Override
	public void load() {
		final PerformanceTimer timer = new PerformanceTimer();
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = DBCharacter.get().getConnection();
			pstm = con.prepareStatement("SELECT * FROM `character_teleport_config`");
			rs = pstm.executeQuery();

			L1BookConfig bookConfig;
			while (rs.next()) {
				final int objid = rs.getInt("object_id");
				
				if (CharObjidTable.get().isChar(objid) != null) {
					bookConfig = new L1BookConfig();
					bookConfig.setObjid(objid);
					bookConfig.setMaxSize(rs.getInt("max_size"));
					bookConfig.setData(rs.getBytes("data"));
					
					_configList.put(objid, bookConfig);
					
				} else {
					delete(objid);
				}
			}
			
		} catch (final SQLException e) {
			_log.error(e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		_log.info("載入人物記憶座標清單資料數量: " + _configList.size() + "(" + timer.get() + "ms)");
	}

	private static void delete(final int objid) {
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			cn = DBCharacter.get().getConnection();
			ps = cn.prepareStatement(
					"DELETE FROM `character_teleport_config` WHERE `object_id`=?");
			ps.setInt(1, objid);
			ps.execute();
		} catch (final SQLException e) {
			_log.error(e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(ps);
			SQLUtil.close(cn);
		}
	}

	@Override
	public L1BookConfig get(final int objectId) {
		return _configList.get(objectId);
	}

	@Override
	public L1BookConfig storeCharBookConfig(final int objectId, final byte[] data) {
		final L1BookConfig bookConfig = new L1BookConfig();
		bookConfig.setObjid(objectId);
		bookConfig.setData(data);
		
		_configList.put(objectId, bookConfig);
		
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = DBCharacter.get().getConnection();
			pstm = con.prepareStatement(
					"INSERT INTO `character_teleport_config` SET `object_id`=?,`data`=?");
			int i = 0;
			pstm.setInt(++i, bookConfig.getObjid());
			pstm.setBytes(++i, bookConfig.getData());
			pstm.execute();
		} catch (final SQLException e) {
			_log.error(e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		return bookConfig;
	}

	@Override
	public void updateCharBookConfig(final int objectId, final byte[] data) {
		final L1BookConfig configl = _configList.get(objectId);
		configl.setObjid(objectId);
		configl.setData(data);
		
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = DBCharacter.get().getConnection();
			pstm = con.prepareStatement(
					"UPDATE `character_teleport_config` SET `data`=? WHERE `object_id`=?");
			int i = 0;
			pstm.setBytes(++i, configl.getData());
			pstm.setInt(++i, configl.getObjid());
			pstm.execute();
		} catch (final SQLException e) {
			_log.error(e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	@Override
	public void updateCharBookMaxSize(final int objectId, final int maxSize) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = DBCharacter.get().getConnection();
			pstm = con.prepareStatement(
					"UPDATE `character_teleport_config` SET `max_size`=? WHERE `object_id`=?");
			int i = 0;
			pstm.setInt(++i, maxSize);
			pstm.setInt(++i, objectId);
			pstm.execute();
		} catch (final SQLException e) {
			_log.error(e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}
}
