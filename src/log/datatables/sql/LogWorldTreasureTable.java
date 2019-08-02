package log.datatables.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.DBLog;
import com.lineage.config.Config;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.utils.SQLUtil;

import log.datatables.storage.LogWorldTreasureStorage;

/**
 * 打寶 Log 紀錄
 * 
 * @author Eugene
 *
 */
public class LogWorldTreasureTable implements LogWorldTreasureStorage {

	private static final Log _log = LogFactory.getLog(LogWorldTreasureTable.class);

	@Override
	public void logArchive(final L1PcInstance pc, final L1ItemInstance item, final L1NpcInstance npc , final long getCount) {
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			cn = DBLog.get().getConnection();
			ps = cn.prepareStatement("INSERT INTO `other_log_world_treasure` SET "
					+ " `server_no`=? ,  `map_id`=? , `char_id`=? , `char_name`=? , `item_objid`=? ,`item_id`=? ,"
					+ "`itemName`=? ,`itemCount`=?, `npc_id`=?, `npc_name`=? , `datetime`=SYSDATE()");

			int i = 0;

			ps.setInt(++i, Config.SERVERNO);
			ps.setInt(++i, pc.getMapId());
			ps.setLong(++i, pc.getId());
			ps.setString(++i, pc.getName());
			ps.setLong(++i, item.getId());
			ps.setInt(++i, item.getItemId());
			ps.setString(++i, item.getItem().getName());
			ps.setLong(++i, getCount);
			ps.setInt(++i, npc.getNpcId());
			ps.setString(++i, npc.getName());


			ps.execute();

		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);

		} finally {
			SQLUtil.close(ps);
			SQLUtil.close(cn);
		}
	}
}
