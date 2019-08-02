package log.datatables.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.DBLog;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.utils.SQLUtil;

import log.datatables.storage.LogItemWarehouseStorage;

/**
 * 倉庫使用紀錄
 * 
 * @author Eugene
 *
 */
public class LogItemWarehouseTable implements LogItemWarehouseStorage {

	private static final Log _log = LogFactory.getLog(LogItemWarehouseTable.class);

	/**
	 * 倉庫使用紀錄
	 * 
	 * @param pc    使用者
	 * @param item  存取道具
	 * @param count 存取數量
	 * @param type  存取類型  false 存 : true 取
	 */
	@Override
	public void logWarehouseArchive(final L1PcInstance pc, final L1ItemInstance item, final long count,
			final boolean type) {
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			cn = DBLog.get().getConnection();
			ps = cn.prepareStatement("INSERT INTO `other_item_warehouse` SET `item_objid`=? , `char_id`=? ,`itemName`=? ,`itemCount`=? ,`type`=? , `datetime`=SYSDATE() , `ip`=?");

			int i = 0;

			ps.setInt(++i, item.getId());
			ps.setInt(++i, pc.getId());

			if (item.getEnchantLevel() > 0) { // 強化超過 0
				ps.setString(++i, item.getNumberedName_to_String());
			} else {
				ps.setString(++i, item.getItem().getName());
			}
			ps.setLong(++i, count);
			ps.setBoolean(++i, type);
			ps.setString(++i, pc.getNetConnection().getIp().toString());

			ps.execute();

		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);

		} finally {
			SQLUtil.close(ps);
			SQLUtil.close(cn);

		}
	}

	/**
	 * 血盟倉庫使用紀錄
	 * 
	 * @param pc    使用者
	 * @param item  存取道具
	 * @param count 存取數量
	 * @param type  存取類型 false 存 : true 取
	 */
	@Override
	public void logWarehouseClanArchive(final L1PcInstance pc,final L1ItemInstance item, final long count, final boolean type) {
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			cn = DBLog.get().getConnection();
			ps = cn.prepareStatement("INSERT INTO `other_item_warehouse_clan` SET `item_objid`=? , `char_id`=? ,`itemName`=? ,`itemCount`=? ,`type`=? , `clanname`=? , `datetime`=SYSDATE() , `ip`=?");

			int i = 0;

			ps.setInt(++i, item.getId());
			ps.setInt(++i, pc.getId());

			if (item.getEnchantLevel() > 0) { // 強化超過 0
				ps.setString(++i, item.getNumberedName_to_String());
			} else {
				ps.setString(++i, item.getItem().getName());
			}
			ps.setLong(++i, count);
			ps.setBoolean(++i, type);
			ps.setString(++i, pc.getClanname());
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