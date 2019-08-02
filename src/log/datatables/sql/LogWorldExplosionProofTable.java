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

import log.datatables.storage.LogWorldExplosionProofStorage;

/**
 * 防爆  Log 紀錄
 * 
 * @author Eugene
 *
 */
public class LogWorldExplosionProofTable implements LogWorldExplosionProofStorage {

	private static final Log _log = LogFactory.getLog(LogWorldExplosionProofTable.class);
	
	@Override
	public void logArchive(final int type , final L1PcInstance pc , final L1ItemInstance item)
	{
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			cn = DBLog.get().getConnection();
			ps = cn.prepareStatement(
					"INSERT INTO `other_log_world_explosion_proof` SET " + "  `server_no`=? , `type`=? , `char_id`=? , `char_name`=? ,  `item_objid`=? , `item_name`=? ,"
							+ " `datetime`=SYSDATE() ");

			int i = 0;	
			
			ps.setInt(++i, Config.SERVERNO);
			ps.setInt(++i, type);
			ps.setLong(++i, pc.getId());
			ps.setString(++i, pc.getName());
			ps.setLong(++i, item.getId());
			
			//道具訊息
			String itemName =  item.getEnchantLevel() > 0 ? item.getNumberedName_to_String() : item.getItem().getName();
			
			ps.setString(++i, itemName);
			
			ps.execute();

		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);

		} finally {
			SQLUtil.close(ps);
			SQLUtil.close(cn);

		}		
	}
	

	
}
