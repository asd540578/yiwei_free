package com.lineage.server.datatables.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.DBCharacter;
import com.lineage.server.datatables.storage.CharItemsPowerStorage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.templates.L1ItemPower_name;
import com.lineage.server.utils.PerformanceTimer;
import com.lineage.server.utils.SQLUtil;
import com.lineage.server.world.WorldItem;

/**
 * 人物物品凹槽資料
 * @author dexc
 */
public class CharItemsPowerTable implements CharItemsPowerStorage {

	private static final Log _log = LogFactory.getLog(BuddyTable.class);

	private static final Map<Integer, L1ItemPower_name> _powerMap = new HashMap<Integer, L1ItemPower_name>();

	/**
	 * 資料預先載入
	 */
	@Override
	public void load() {
		final PerformanceTimer timer = new PerformanceTimer();
		int i = 0;
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			cn = DBCharacter.get().getConnection();
			ps = cn.prepareStatement( "SELECT * FROM `character_item_power`");
			rs = ps.executeQuery();
			
			while (rs.next()) {
				final int item_obj_id = rs.getInt("item_obj_id");
				final int hole_count = rs.getInt("hole_count");
				final int hole_1 = rs.getInt("hole_1");
				final int hole_2 = rs.getInt("hole_2");
				final int hole_3 = rs.getInt("hole_3");
				final int hole_4 = rs.getInt("hole_4");
				final int hole_5 = rs.getInt("hole_5");

				final L1ItemPower_name power = new L1ItemPower_name();
				power.set_item_obj_id(item_obj_id);
				power.set_hole_count(hole_count);
				power.set_hole_1(hole_1);
				power.set_hole_2(hole_2);
				power.set_hole_3(hole_3);
				power.set_hole_4(hole_4);
				power.set_hole_5(hole_5);
				
				addValue(item_obj_id, power);
				i++;
			}
			
		} catch (final SQLException e) {
			_log.error(e.getLocalizedMessage(), e);

		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(ps);
			SQLUtil.close(cn);
		}
		_log.info("載入物件凹槽清單資料數量: " + i + "(" + timer.get() + "ms)");
	}

	/**
	 * 初始化建立資料
	 * @param item_obj_id
	 * @param value
	 */
	private static void addValue(final int item_obj_id, final L1ItemPower_name power) {
		final L1ItemInstance item = WorldItem.get().getItem(item_obj_id);
		boolean isError = true;
		if (item != null) {
			if (item.get_power_name() == null) {
				item.set_power_name(power);
			}
			isError = false;
		}

		if (isError) {
			errorItem(item_obj_id);
		}
	}

	/**
	 * 刪除 錯誤/遺失 物品資料
	 * @param objid
	 */
	private static void errorItem(int item_obj_id) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = DBCharacter.get().getConnection();
			pstm = con.prepareStatement(
					"DELETE FROM `character_item_power` WHERE `item_obj_id`=?");
			pstm.setInt(1, item_obj_id);
			pstm.execute();

		} catch (final SQLException e) {
			_log.error(e.getLocalizedMessage(), e);

		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	/**
	 * 增加物品凹槽資料
	 * @param item_obj_id
	 * @param power
	 * @throws Exception
	 */
	@Override
	public void storeItem(final int item_obj_id, final L1ItemPower_name power) throws Exception {
		if (_powerMap.get(item_obj_id) != null) {
			return;
		}
		_powerMap.put(item_obj_id, power);
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = DBCharacter.get().getConnection();
			pstm = con.prepareStatement(
					"INSERT INTO `character_item_power` SET `item_obj_id`=?,`hole_count`=?,`hole_1`=?,`hole_2`=?,`hole_3`=?,`hole_4`=?,`hole_5`=?");

			int i = 0;
			pstm.setInt(++i, item_obj_id);
			pstm.setInt(++i, power.get_hole_count());
			pstm.setInt(++i, power.get_hole_1());
			pstm.setInt(++i, power.get_hole_2());
			pstm.setInt(++i, power.get_hole_3());
			pstm.setInt(++i, power.get_hole_4());
			pstm.setInt(++i, power.get_hole_5());
			pstm.execute();

		} catch (final SQLException e) {
			_log.error(e.getLocalizedMessage(), e);

		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}
	
	/**
	 * 更新凹槽資料
	 * @param item_obj_id
	 * @param power
	 */
	@Override
	public void updateItem(final int item_obj_id, final L1ItemPower_name power) {
		Connection co = null;
		PreparedStatement pm = null;
		try {
			co = DBCharacter.get().getConnection();
			pm = co.prepareStatement(
					"UPDATE `character_item_power` SET `hole_count`=?,`hole_1`=?,`hole_2`=?,`hole_3`=?,`hole_4`=?,`hole_5`=? WHERE `item_obj_id`=?");
			
			int i = 0;
			pm.setInt(++i, power.get_hole_count());
			pm.setInt(++i, power.get_hole_1());
			pm.setInt(++i, power.get_hole_2());
			pm.setInt(++i, power.get_hole_3());
			pm.setInt(++i, power.get_hole_4());
			pm.setInt(++i, power.get_hole_5());
			
			pm.setInt(++i, item_obj_id);
			
			pm.execute();

		} catch (final SQLException e) {
			_log.error(e.getLocalizedMessage(), e);

		} finally {
			SQLUtil.close(pm);
			SQLUtil.close(co);
		}
	}
}
