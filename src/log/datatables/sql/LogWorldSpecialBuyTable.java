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

import log.datatables.storage.LogWorldSpecialBuyStorage;

/**
 * 特殊購買 Log 紀錄
 * 
 * @author dexc
 *
 */
public class LogWorldSpecialBuyTable implements LogWorldSpecialBuyStorage {

	private static final Log _log = LogFactory.getLog(LogWorldSpecialBuyTable.class);

	/**
	 * 特殊購買 Log 紀錄
	 * @param pc 使用者
	 * @param itemCn_id 特殊金幣
	 * @param cnCount 購買金額
	 * @param item 獲得道具
	 */
	@Override
	public void logArchive(final L1PcInstance pc, final int cnItem_id, final long cnCount, final L1ItemInstance item) {
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			cn = DBLog.get().getConnection();
			ps = cn.prepareStatement("INSERT INTO `other_log_world_special_buy` SET "
					+ " `server_no`=? ,  `char_id`=? , `char_name`=? , `gold_id`=? ,`gold_count`=? ,"
					+ "`item_objid`=? ,`item_id`=?, `item_enchantlvl`=? ,`itemName`=? ,`itemCount`=? , `datetime`=SYSDATE() ,`ip`=?");

			int i = 0;

			ps.setInt(++i, Config.SERVERNO);
			ps.setLong(++i, pc.getId());
			ps.setString(++i, pc.getName());
			ps.setInt(++i, cnItem_id);
			ps.setLong(++i, cnCount);
			ps.setLong(++i, item.getId());
			ps.setInt(++i, item.getItemId());
			ps.setInt(++i, item.getEnchantLevel());
			ps.setString(++i, item.getItem().getName());
			ps.setLong(++i, item.getCount());
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
