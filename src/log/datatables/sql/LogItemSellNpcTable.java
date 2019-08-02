package log.datatables.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.DBLog;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.utils.SQLUtil;

import eugene.JsonManagement;
import log.datatables.storage.LogItemSellNpcStorage;

/**
 * 
 * 賣 NPC 紀錄
 * 
 * @author Eugene
 *
 */
public class LogItemSellNpcTable implements LogItemSellNpcStorage {

	private static final Log _log = LogFactory.getLog(LogItemSellNpcTable.class);

	
	/**
	 * 賣 NPC 紀錄
	 */
	@Override
	public void logArchive(final L1PcInstance pc, final L1ItemInstance item  , final long count , final long price  )
	{
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			cn = DBLog.get().getConnection();
			ps = cn.prepareStatement(
					"INSERT INTO `other_item_sellnpc` SET " + " `item_objid`=? , `item_name`=? , `char_id`=? ,`character_items`=? ,`sellcount`=? ,`sellprice`=? , `datetime`=SYSDATE() , `ip`=?");

			int i = 0;
			StringBuilder ip = pc.getNetConnection().getIp();
			
			ps.setInt(++i, item.getId());				
			ps.setString(++i, item.getItem().getName());	
			ps.setInt(++i, pc.getId());
			ps.setString(++i,  JsonManagement.Bale_Character_Items(item).toString());
			
			ps.setLong(++i, count);
			ps.setLong(++i, price);
			
			ps.setString(++i,  ip.toString() );
			
			ps.execute();

		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);

		} finally {
			SQLUtil.close(ps);
			SQLUtil.close(cn);

		}		
	}
}
