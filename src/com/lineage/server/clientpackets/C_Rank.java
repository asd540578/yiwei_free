package com.lineage.server.clientpackets;

import java.util.Calendar;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.echo.ClientExecutor;
import com.lineage.server.datatables.sql.CharacterTable;
import com.lineage.server.model.L1Clan;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_PacketBox;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.world.World;
import com.lineage.server.world.WorldClan;

/**
 * 要求給予角色血盟階級
 *
 * @author daien
 *
 */
public class C_Rank extends ClientBasePacket {

	private static final Log _log = LogFactory.getLog(C_Rank.class);

	/*public C_Rank() {
	}

	public C_Rank(final byte[] abyte0, final ClientExecutor client) {
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
			int data = 0;
			int rank = 0;
			String name = "";

			try {
				data = this.readC();
				rank = this.readC();
				name = this.readS();

			} catch (final Exception e) {
				return;
			}

			final L1PcInstance pc = client.getActiveChar();
			if (pc == null) {
				return;
			}
			
			switch (data) {
			case 1:// 階級
				this.rank(pc, rank, name);
				break;
				
			case 2:// 同盟目錄
			case 3:// 加入同盟
			case 4:// 退出同盟
				break;
				
			case 5:// 生存吶喊(CTRL+E)
				if (pc.get_food() >= 225) {
					if (pc.getWeapon() != null) {
						final Random random = new Random();
						long time = pc.get_h_time();
						final Calendar cal = Calendar.getInstance();
						long h_time = cal.getTimeInMillis() / 1000;// 換算為秒
						int n = (int) ((h_time - time) / 60);// 換算為分

						int addhp = 0;
						
						if (n <= 0) {
							// 1974：還無法使用生存的吶喊。 
							pc.sendPackets(new S_ServerMessage(1974));
							
						} else if (n >= 1 && n <= 29) {
							addhp = (int) (pc.getMaxHp() * (n / 100.0D));
							
						} else if (n >= 30) {
							int lv = pc.getWeapon().getEnchantLevel();
							switch (lv) {
							case 0:
							case 1:
							case 2:
							case 3:
							case 4:
							case 5:
							case 6:
								pc.sendPacketsX8(new S_SkillSound(pc.getId(), 8907));
								pc.sendPacketsX8(new S_SkillSound(pc.getId(), 8684));
								addhp = (int) (pc.getMaxHp() * ((random.nextInt(20) + 20) / 100.0D));
								break;
								
							case 7:
							case 8:
								pc.sendPacketsX8(new S_SkillSound(pc.getId(), 8909));
								pc.sendPacketsX8(new S_SkillSound(pc.getId(), 8685));
								addhp = (int) (pc.getMaxHp() * ((random.nextInt(20) + 30) / 100.0D));
								break;
								
							case 9:
							case 10:
								pc.sendPacketsX8(new S_SkillSound(pc.getId(), 8910));
								pc.sendPacketsX8(new S_SkillSound(pc.getId(), 8773));
								addhp = (int) (pc.getMaxHp() * ((random.nextInt(10) + 50) / 100.0D));
								break;
								
							case 11:
							default:
								pc.sendPacketsX8(new S_SkillSound(pc.getId(), 8908));
								pc.sendPacketsX8(new S_SkillSound(pc.getId(), 8686));
								addhp = (int) (pc.getMaxHp() * (0.7));
								break;
							}
						}
						
						if (addhp != 0) {
							pc.set_food((short) 0);
							pc.sendPackets(new S_PacketBox(S_PacketBox.FOOD, (short) 0));
							pc.setCurrentHp(pc.getCurrentHp() + addhp);
						}
						
					} else {
						// 1973：必須裝備上武器才可使用。  
						pc.sendPackets(new S_ServerMessage(1973));
					}
				}
				break;
				
			case 6:// 生存吶喊(Alt+0)
				if (pc.getWeapon() != null) {
					int lv = pc.getWeapon().getEnchantLevel();
					int gfx = 8684;
					switch (lv) {
					case 0:
					case 1:
					case 2:
					case 3:
					case 4:
					case 5:
					case 6:
						gfx = 8684;
						break;
						
					case 7:
					case 8:
						gfx = 8685;
						break;
						
					case 9:
					case 10:
						gfx = 8773;
						break;
						
					case 11:
					default:
						gfx = 8686;
						break;
					}

					pc.sendPacketsX8(new S_SkillSound(pc.getId(), gfx));
					
				} else {
					// 1973：必須裝備上武器才可使用。  
					pc.sendPackets(new S_ServerMessage(1973));
				}
				break;
				
			case 8:
				// 奇岩監獄 - 每日允許時間: 180分
				pc.sendPackets(new S_ServerMessage(2535, "$12125",
						String.valueOf(180)));
				// 象牙塔 - 每日允許時間: 60分
				pc.sendPackets(new S_ServerMessage(2535, "$6081",
						String.valueOf(60)));
				// 拉斯塔巴德地監 - 每星期允許時間: 720分
				pc.sendPackets(new S_ServerMessage(2535, "$12126",
						String.valueOf(720)));
				break;
				
			case 9:
				
				final int time1 = 60;
				final int time2 = 60;
				final int time3 = 60;
				final int time4 = 0;
				pc.sendPackets(new S_PacketBox(S_PacketBox.DISPLAY_MAP_TIME, (time1), // 奇岩地監
						(time2), // 象牙塔地監
						(time3), // 拉斯塔巴德地監
						(time4) 
				));
				break;
			}
			
		} catch (final Exception e) {
			//_log.error(e.getLocalizedMessage(), e);
			
		} finally {
			this.over();
		}
	}

	private void rank(final L1PcInstance pc, final int rank, final String name) {
		final L1PcInstance targetPc = World.get().getPlayer(name);
		final L1Clan clan = WorldClan.get().getClan(pc.getClanname());
		if (clan == null) {
			return;
		}
		boolean isOK = false;
		// rank 2:一般 3:副君主 4:聯盟君主 5:修習騎士 6:守護騎士 7:一般 8:修習騎士 9:守護騎士 10:聯盟君主
		if (rank >= 2 && rank <= 10) {
			isOK = true;
		}

		if (!isOK) {
			// 2,149：\f1請輸入以下內容: "/階級 \f0角色名稱 階級[守護騎士, 修習騎士, 一般]\f1"  
			pc.sendPackets(new S_ServerMessage(2149));
			return;
		}
		if (pc.isCrown()) { // 君主
			if (pc.getId() != clan.getLeaderId()) { // 血盟主
				// 785 你不再是君主了
				pc.sendPackets(new S_ServerMessage(785));
				return;
			}
			
		} else {
			// 518 血盟君主才可使用此命令。
			pc.sendPackets(new S_ServerMessage(518));
			return;
		}

		if (targetPc != null) {
			try {
				if (pc.getClanid() == targetPc.getClanid()) {
					targetPc.setClanRank(rank);
					targetPc.save();
					targetPc.sendPackets(new S_PacketBox(S_PacketBox.MSG_RANK_CHANGED, rank));

				} else {
					// 201：\f1%0%d不是你的血盟成員。
					pc.sendPackets(new S_ServerMessage(201, name));
					return;
				}

			} catch (final Exception e) {
				_log.error(e.getLocalizedMessage(), e);
			}

		} else { // 線上無此人物
			try {
				final L1PcInstance restorePc = CharacterTable.get().restoreCharacter(name);
				if ((restorePc != null) && (restorePc.getClanid() == pc.getClanid())) { // 相同血盟
					restorePc.setClanRank(rank);
					restorePc.save();

				} else {
					// 109 沒有叫%0的人。
					pc.sendPackets(new S_ServerMessage(109, name));
					return;
				}
				
			} catch (final Exception e) {
				_log.error(e.getLocalizedMessage(), e);
			}
		}
	}

	@Override
	public String getType() {
		return this.getClass().getSimpleName();
	}
}
