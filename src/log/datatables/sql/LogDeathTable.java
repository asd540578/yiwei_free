package log.datatables.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;

import com.lineage.DBLog;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.utils.SQLUtil;

import eugene.JsonManagement;
import log.datatables.storage.LogDeathStorage;

/**
 * 死亡  log 紀錄
 * @author dexc
 *
 */
public class LogDeathTable implements LogDeathStorage {

	private static final Log _log = LogFactory.getLog(LogDeathTable.class);
	
	@Override
	public void logDeathArchive(final L1PcInstance pc, final ArrayList<L1ItemInstance> items , final ArrayList<Integer> skills  , final long exp)
	{
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			cn = DBLog.get().getConnection();
			ps = cn.prepareStatement(
					"INSERT INTO `other_log_death` SET " + " `char_id`=? , `deathMapid`=? , `delExp`=? , `delItem`=?,  `delSkill`=?  ,   `datetime`=SYSDATE() ,`ip`=?");

			int i = 0;
			
			ps.setLong(++i, pc.getId());
			ps.setLong(++i, pc.getMapId());
			ps.setLong(++i, exp);
			
			JSONArray array = null;
			if(items != null && items.size() > 0) {
				array = new JSONArray();
				for(L1ItemInstance item : items) {
					array.put(JsonManagement.Bale_Character_Items(item));
				}
				ps.setString(++i,  array.toString());//遺失的道具
			} else {
				ps.setString(++i,  null);
			}
		
			if(skills != null && skills.size() > 0) {
				
				String remSkill = "";
				for(Integer skill : skills) {
					remSkill += skill + ",";
				}
				ps.setString(++i,  remSkill ); //遺失的魔法
			} else {
				ps.setString(++i,  null);
			}
			
			
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
