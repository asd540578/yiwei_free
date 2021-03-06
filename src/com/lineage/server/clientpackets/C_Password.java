package com.lineage.server.clientpackets;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.echo.ClientExecutor;
import com.lineage.server.datatables.lock.AccountReading;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_RetrieveElfList;
import com.lineage.server.serverpackets.S_RetrieveList;
import com.lineage.server.serverpackets.S_RetrievePledgeList;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.templates.L1Account;
import com.lineage.server.world.World;

/**
 * 要求變更與使用倉庫密碼
 *
 * @author dexc
 *
 */
public class C_Password extends ClientBasePacket {

	private static final Log _log = LogFactory.getLog(C_Password.class);

	/*public C_Password() {
	}

	public C_Password(final byte[] abyte0, final ClientExecutor client) {
		super(abyte0);
		try {
			this.start(abyte0, client);
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}*/

	@Override
	public void start(final byte[] decrypt, final ClientExecutor client) {
		try {
			// 資料載入
			this.read(decrypt);
			
			final int mode = this.readC();// 模式
			int olepwd = this.readD();// 密碼
			final int newpwd = this.readD();// 新密碼

			if (olepwd < 0) {
				olepwd = -256;
			}
			//_log.error("mode: " + mode);
			//_log.error("olepwd: " + olepwd);
			//_log.error("newpwd RO npcid: " + newpwd);//*/
			
			final L1PcInstance pc = client.getActiveChar();

			// 原始密碼
			int srcpwd = client.getAccount().get_warehouse();

			int tmpId = pc.getTempID();
			
			if (olepwd != srcpwd) {
				// 835 密碼錯誤。
				pc.sendPackets(new S_ServerMessage(835));

				final int error = client.get_error();
				client.set_error(error + 1);
				_log.error(pc.getName() + " 倉庫密碼輸入錯誤!!( " + client.get_error() + " 次)");
				return;
			}
			
			boolean isRepas = false;
			boolean isNpc = false;
			L1Object obj = World.get().findObject(newpwd);
			if (obj == null) {
				obj = World.get().findObject(tmpId);
				if (obj != null) {
					isNpc = true;
					
				} else {
					isRepas = true;
				}
				
			} else {
				isNpc = true;
			}
			
			if (isNpc) {
				if (olepwd != -256) {
					if (obj instanceof L1NpcInstance) {
						L1NpcInstance npc = (L1NpcInstance) obj;
						final int difflocx = Math.abs(pc.getX() - npc.getX());
						final int difflocy = Math.abs(pc.getY() - npc.getY());
						// 距離3格以上無效
						if ((difflocx > 3) || (difflocy > 3)) {
							if (tmpId != 0) {
								obj = World.get().findObject(tmpId);
								npc = (L1NpcInstance) obj;
								
							} else {
								// 消除
								stopAction(client, pc);
								return;
							}
						}
						switch (npc.getNpcId()) {
						case 60028:// 艾爾
							if (pc.getLevel() >= 5) {
								// 精靈倉庫取回
								pc.sendPackets(new S_RetrieveElfList(newpwd, pc));
							}
							break;
							
						default:// 一般倉庫取回
							if (pc.getLevel() >= 5) {
								switch (mode) {
								case 1:// 個人
									pc.sendPackets(new S_RetrieveList(newpwd, pc));
									break;
									
								case 2:// 血盟
									pc.sendPackets(new S_RetrievePledgeList(newpwd, pc));
									break;
								}
							}
							break;
						}
					}
					
				} else {
					isRepas = true;
				}
			}

			// 變更密碼
			if (isRepas) {
				// 新舊密碼相同
				if (newpwd == srcpwd) {
					// 342：你不能使用舊的密碼當作新的密碼。請再次輸入密碼。  
					pc.sendPackets(new S_ServerMessage(342));
					// 消除
					stopAction(client, pc);
					return;
				}

				if (obj == null && newpwd < 1000000) {
					// 新密碼小於7位數
					// 變更密碼
					L1Account account = client.getAccount();
					account.set_warehouse(newpwd);
					AccountReading.get().updateWarehouse(account.get_login(), newpwd);
				}
			}

			// 消除
			stopAction(client, pc);
			
		} catch (final Exception e) {
			//_log.error(e.getLocalizedMessage(), e);
			
		} finally {
			this.over();
		}
	}

	private void stopAction(ClientExecutor client, L1PcInstance pc) {
		// 消除
		pc.setTempID(0);
		// 解除錯誤次數
		client.set_error(0);
	}

	@Override
	public String getType() {
		return this.getClass().getSimpleName();
	}
}