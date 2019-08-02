package com.lineage.server.clientpackets;

import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.echo.ClientExecutor;
import com.lineage.server.datatables.lock.CharBookConfigReading;
import com.lineage.server.datatables.lock.CharBookReading;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_PacketBoxLoc;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.templates.L1BookConfig;
import com.lineage.server.templates.L1BookMark;
import com.lineage.server.world.World;

/**
 * 視窗失焦
 * @author dexc
 *
 */
public class C_Windows extends ClientBasePacket {

	private static final Log _log = LogFactory.getLog(C_Windows.class);

	@Override
	public void start(final byte[] decrypt, final ClientExecutor client) {
		try {
			// 資料載入
			read(decrypt);

			L1PcInstance pc = client.getActiveChar();
			int type = readC();

			switch (type) {
			case 0x00:
				int objid = readD();
				L1Object obj = World.get().findObject(objid);
				if (obj instanceof L1PcInstance) {
					L1PcInstance tgpc = (L1PcInstance) obj;
					_log.warn("玩家:" + pc.getName() + " 申訴:(" + objid + ")" + tgpc.getName());
				} else {
					_log.warn("玩家:" + pc.getName() + " 申訴:NPC(" + objid + ")");
				}
				break;
				
			case 0x0b:
				String name = readS();
				int mapid = readH();
				int x = readH();
				int y = readH();
				int zone = readD();
				L1PcInstance target = World.get().getPlayer(name);
				if (target != null) {
					target.sendPackets(new S_PacketBoxLoc(pc.getName(), mapid, x, y, zone));
					pc.sendPackets(new S_ServerMessage(1783,name));//已發送座標位置給%0。
					
				} else {
					pc.sendPackets(new S_ServerMessage(1782));//無法找到該角色或角色不在線上。
				}
				break;
				
			case 0x22:
				byte data[] = readBytes();
				
				final L1BookConfig config = CharBookConfigReading.get().get(pc.getId());
				if (config == null) {
					CharBookConfigReading.get().storeCharBookConfig(pc.getId(), data);
				} else {
					CharBookConfigReading.get().updateCharBookConfig(pc.getId(), data);
				}
				break;
				
			case 0x27: //更新記憶座標
				final int changeCount = readD();
				for (int i = 0; i < changeCount; i++) {
					final int bookId = readD();
					final L1BookMark bookm = CharBookReading.get().getBookMark(pc, bookId);
					if (bookm != null) {
						final String changeName = readS();
						if (bookm.getName().equalsIgnoreCase(changeName)) {
							 //pc.sendPackets(new S_ServerMessage(327));
						} else {
							CharBookReading.get().updateBookmarkName(pc, bookId, changeName);
						}
					}
				}
				break;
				
			case 0x2c:
				pc.resetKillMonCount();
				break;
			}

			if (pc != null) {
				// 額外
				if (pc.get_mazu_time() != 0) {
					if (pc.is_mazu()) {
						final Calendar cal = Calendar.getInstance();
						long h_time = cal.getTimeInMillis() / 1000;// 換算為秒
						if (h_time - pc.get_mazu_time() >= 2400) {// 2400秒 = 40分鐘
							pc.set_mazu_time(0);
							pc.set_mazu(false);
						}
					}
				}
			}
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
			
		} finally {
			this.over();
		}
	}

	@Override
	public String getType() {
		return this.getClass().getSimpleName();
	}
}
