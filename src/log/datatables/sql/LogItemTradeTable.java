package log.datatables.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.DBLog;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.utils.SQLUtil;

import log.datatables.storage.LogItemTradeStorage;

/**
 * 交易紀錄
 * 
 * @author dexc
 *
 */
public class LogItemTradeTable implements LogItemTradeStorage {

	private static final Log _log = LogFactory.getLog(LogItemTradeTable.class);

	@Override
	public void logArchive(final L1PcInstance pc , final L1PcInstance target,final L1ItemInstance item,final long count) {
		Connection cn = null;
		PreparedStatement ps = null;

		try {
			cn = DBLog.get().getConnection();
			ps = cn.prepareStatement("INSERT INTO `other_item_trade` SET "
					+ "`char_ip`=? , `char_id`=? ,`charName`=? , `item_objid`=? ,`itemName`=? , "
					+ "`itemCount`=?  , `target_ip`=?, `targetid`=?, `targetName`=?, `datetime`=SYSDATE()");

			int i = 0;
			
			ps.setString(++i, pc.getNetConnection().getIp().toString());
			ps.setInt(++i, pc.getId());
			ps.setString(++i, pc.getName());
			
			ps.setInt(++i, item.getId());
			
			
			if(item.getEnchantLevel() > 0) { //強化超過 0
				ps.setString(++i, item.getNumberedName_to_String());
			}else {
				ps.setString(++i, item.getItem().getName());
			}
			ps.setLong(++i, count);
			
			ps.setString(++i, target.getNetConnection().getIp().toString());
			ps.setLong(++i, target.getId());
			ps.setString(++i, target.getName());
			
			ps.execute();

		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);

		} finally {
			SQLUtil.close(ps);
			SQLUtil.close(cn);

		}
	}

}
