package com.lineage.server.clientpackets;

import static com.lineage.server.model.skill.L1SkillId.AREA_OF_SILENCE;
import static com.lineage.server.model.skill.L1SkillId.SILENCE;
import static com.lineage.server.model.skill.L1SkillId.STATUS_CHAT_PROHIBITED;
import static com.lineage.server.model.skill.L1SkillId.STATUS_POISON_SILENCE;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.config.Config;
import com.lineage.config.ConfigRecord;
import com.lineage.echo.ClientExecutor;
import com.lineage.server.command.GMCommands;
import com.lineage.server.datatables.lock.AccountReading;
import com.lineage.server.datatables.lock.LogChatReading;
import com.lineage.server.model.L1Clan;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.item.L1ItemId;
import com.lineage.server.serverpackets.S_CharTitle;
import com.lineage.server.serverpackets.S_Chat;
import com.lineage.server.serverpackets.S_ChatClan;
import com.lineage.server.serverpackets.S_ChatClanUnion;
import com.lineage.server.serverpackets.S_ChatParty;
import com.lineage.server.serverpackets.S_ChatParty2;
import com.lineage.server.serverpackets.S_ChatShouting;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_NpcChat;
import com.lineage.server.serverpackets.S_NpcChatShouting;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.world.World;
import com.lineage.server.world.WorldClan;

import log.datatables.lock.LogChangeReading;

/**
 * 要求使用一般聊天頻道
 *
 * @author daien
 *
 */
public class C_Chat extends ClientBasePacket {

	private static final Log _log = LogFactory.getLog(C_Chat.class);

	/*public C_Chat() {
	}

	public C_Chat(final byte[] abyte0, final ClientExecutor client) {
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
			
			final L1PcInstance pc = client.getActiveChar();

			if (decrypt.length > 108) {
				_log.warn("人物:" + pc.getName() + "對話長度超過限制:" + client.getIp().toString());
				client.set_error(client.get_error() + 1);
				return;
			}

			boolean isStop = false;// 停止輸出
			
			boolean errMessage = false;// 異常訊息
			
			// 中毒狀態
			if (pc.hasSkillEffect(SILENCE)) {
				if (!pc.isGm()) {
					isStop = true;
				}
			}

			// 中毒狀態
			if (pc.hasSkillEffect(AREA_OF_SILENCE)) {
				if (!pc.isGm()) {
					isStop = true;
				}
			}

			// 中毒狀態
			if (pc.hasSkillEffect(STATUS_POISON_SILENCE)) {
				if (!pc.isGm()) {
					isStop = true;
				}
			}

			// 你從現在被禁止閒談。
			if (pc.hasSkillEffect(STATUS_CHAT_PROHIBITED)) {
				isStop = true;
				errMessage = true;
			}

			if (isStop) {
				if (errMessage) {
					pc.sendPackets(new S_ServerMessage(242));
				}
				return;
			}

			// 取回對話內容
			final int chatType = this.readC();
			final String chatText = this.readS();
			
			switch (chatType) {
			case 0:// 一般頻道
				if (pc.is_retitle()) {
					re_title(pc, chatText.trim());
					return;
				}
				if (pc.is_repass() != 0) {
					re_repass(pc, chatText.trim());
					return;
				}
				chatType_0(pc, chatText);
				break;

			case 2: // 大叫頻道(!)
				chatType_2(pc, chatText);
				break;

			case 4: // 血盟頻道(@)
				chatType_4(pc, chatText);
				break;

			case 11: // 隊伍頻道(#)
				chatType_11(pc, chatText);
				break;

			case 13: // 連盟頻道(%)
				chatType_13(pc, chatText);
				break;

			case 14: // 隊伍頻道(聊天)
				chatType_14(pc, chatText);
				break;
			}

			if (!pc.isGm()) {
				pc.checkChatInterval();
			}
			
		} catch (final Exception e) {
			//_log.error(e.getLocalizedMessage(), e);
			
		} finally {
			this.over();
		}
	}

	private static final String _check_pwd = "abcdefghijklmnopqrstuvwxyz0123456789!_=+-?.#";
	
	private void re_repass(L1PcInstance pc, String password) {
		try {
			switch (pc.is_repass()) {
			case 1:// 輸入舊密碼
				if (!pc.getNetConnection().getAccount().get_password().equals(password)) {
					// 1,744：密碼錯誤  
					pc.sendPackets(new S_ServerMessage(1744));
					return;
				}
				pc.repass(2);
				pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "y_pass_01", new String[]{"請輸入您的新密碼"}));
				break;
				
			case 2:// 輸入新密碼
				boolean iserr = false;
				for (int i = 0 ; i < password.length() ; i++) {
					final String ch = password.substring(i, i + 1);
					if (!_check_pwd.contains(ch.toLowerCase())) {
						// 1,742：帳號或密碼中有無效的字元  
						pc.sendPackets(new S_ServerMessage(1742));
						iserr = true;
						break;
					}
				}
				if (password.length() > 13) {
					// 1,742：帳號或密碼中有無效的字元  
					pc.sendPackets(new S_ServerMessage(166, "密碼長度過長"));
					iserr = true;
				}
				if (password.length() < 3) {
					// 1,742：帳號或密碼中有無效的字元  
					pc.sendPackets(new S_ServerMessage(166, "密碼長度過長"));
					iserr = true;
				}
				if (iserr) {
					return;
				}
				pc.setText(password);
				pc.repass(3);
				pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "y_pass_01", new String[]{"請確認您的新密碼"}));
				break;
				
			case 3:// 確認新密碼
				if (!pc.getText().equals(password)) {
					// 1,982：所輸入的密碼不一致.請重新輸入.
					pc.sendPackets(new S_ServerMessage(1982));
					return;
				}
				if(pc.getInventory().consumeItem(49538 , 1)) { //刪除 密碼修改卷
					
					final byte type = 2; // 改密碼紀錄
					final String oldpassword = pc.getNetConnection().getAccount().get_password();
					
					LogChangeReading.get().logArchive( type , pc.getId() , oldpassword , password ,  pc.getNetConnection().getIp().toString()); 
					
					pc.sendPackets(new S_CloseList(pc.getId()));
					// 1,985：角色密碼成功地變更.(忘記密碼時請至天堂網站詢問)  
					pc.sendPackets(new S_ServerMessage(1985));
					AccountReading.get().updatePwd(pc.getAccountName(), password);
					pc.setText(null);
					pc.repass(0);
				}
				break;
			}
			
		} catch (final Exception e) {
			pc.sendPackets(new S_CloseList(pc.getId()));
			// 45：未知的錯誤%d   
			pc.sendPackets(new S_ServerMessage(45));
			pc.setText(null);
			pc.repass(0);
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	/**
	 * 變更封號
	 * @param pc
	 * @param chatText
	 */
	private void re_title(final L1PcInstance pc, final String chatText) {
		try {
			final String newchatText = chatText.trim();
			if (newchatText.isEmpty() || newchatText.length() <= 0) {
				pc.sendPackets(new S_ServerMessage("\\fU請輸入封號內容"));
				return;
			}
			final int length = Config.LOGINS_TO_AUTOENTICATION ? 18 : 13;
			if (newchatText.getBytes().length > length) {
				pc.sendPackets(new S_ServerMessage("\\fU封號長度過長"));
				return;
			}
			final StringBuilder title = new StringBuilder();
			title.append(newchatText);
			
			pc.setTitle(title.toString());
			pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
			pc.save();
			pc.retitle(false);
			pc.sendPackets(new S_ServerMessage("\\fU封號變更完成"));
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	/**
	 * 隊伍頻道(聊天)
	 * @param pc
	 * @param chatText
	 */
	private void chatType_14(final L1PcInstance pc, final String chatText) {
		if (pc.isInChatParty()) {
			S_ChatParty2 chatpacket = new S_ChatParty2(pc, chatText);
			final L1PcInstance[] partyMembers = pc.getChatParty().getMembers();
			for (final L1PcInstance listner : partyMembers) {
				if (!listner.getExcludingList().contains(pc.getName())) {
					listner.sendPackets(chatpacket);
				}
			}

			if (ConfigRecord.LOGGING_CHAT_CHAT_PARTY) {
				LogChatReading.get().noTarget(pc, chatText, 14);
			}
		}
	}

	/**
	 * 連盟頻道(%)
	 * @param pc
	 * @param chatText
	 */
	private void chatType_13(final L1PcInstance pc, final String chatText) {
		if (pc.getClanid() != 0) {
			final L1Clan clan = WorldClan.get().getClan(pc.getClanname());
			if (clan == null) {
				return;
			}
			switch (pc.getClanRank()) {
			case L1Clan.ALLIANCE_CLAN_RANK_GUARDIAN:// 6:守護騎士 
			case L1Clan.NORMAL_CLAN_RANK_GUARDIAN:// 9:守護騎士
			case L1Clan.CLAN_RANK_GUARDIAN:// 3:副君主 
			case L1Clan.CLAN_RANK_PRINCE:// 4:聯盟君主 
			case L1Clan.NORMAL_CLAN_RANK_PRINCE:// 10:聯盟君主
				final S_ChatClanUnion chatpacket = new S_ChatClanUnion(pc, chatText);
				final L1PcInstance[] clanMembers = clan.getOnlineClanMember();
				for (final L1PcInstance listner : clanMembers) {
					if (!listner.getExcludingList().contains(pc.getName())) {
						switch (listner.getClanRank()) {
						case L1Clan.ALLIANCE_CLAN_RANK_GUARDIAN:// 6:守護騎士 
						case L1Clan.NORMAL_CLAN_RANK_GUARDIAN:// 9:守護騎士
						case L1Clan.CLAN_RANK_GUARDIAN:// 3:副君主 
						case L1Clan.CLAN_RANK_PRINCE:// 4:聯盟君主 
						case L1Clan.NORMAL_CLAN_RANK_PRINCE:// 10:聯盟君主
							listner.sendPackets(chatpacket);
							break;
						}
					}
				}

				if (ConfigRecord.LOGGING_CHAT_COMBINED) {
					LogChatReading.get().noTarget(pc, chatText, 13);
				}
				break;
			}
		}
	}

	/**
	 * 隊伍頻道(#)
	 * @param pc
	 * @param chatText
	 */
	private void chatType_11(final L1PcInstance pc, final String chatText) {
		if (pc.isInParty()) {
			S_ChatParty chatpacket = new S_ChatParty(pc, chatText);

			final ConcurrentHashMap<Integer, L1PcInstance> pcs = pc.getParty().partyUsers();
			
			if (pcs.isEmpty()) {
				return;
			}
			if (pcs.size() <= 0) {
				return;
			}
			
			for (final Iterator<L1PcInstance> iter = pcs.values().iterator(); iter.hasNext();) {
				final L1PcInstance listner = iter.next();
				if (!listner.getExcludingList().contains(pc.getName())) {
					listner.sendPackets(chatpacket);
				}
			}

			if (ConfigRecord.LOGGING_CHAT_PARTY) {
				LogChatReading.get().noTarget(pc, chatText, 11);
			}
		}
	}

	/**
	 * 血盟頻道(@)
	 * @param pc
	 * @param chatText
	 */
	private void chatType_4(final L1PcInstance pc, final String chatText) {
		if (pc.getClanid() != 0) {
			final L1Clan clan = WorldClan.get().getClan(pc.getClanname());
			if (clan != null) {
				final S_ChatClan chatpacket = new S_ChatClan(pc, chatText);
				final L1PcInstance[] clanMembers = clan.getOnlineClanMember();
				for (final L1PcInstance listner : clanMembers) {
					if (!listner.getExcludingList().contains(pc.getName())) {
						listner.sendPackets(chatpacket);
					}
				}

				if (ConfigRecord.LOGGING_CHAT_CLAN) {
					LogChatReading.get().noTarget(pc, chatText, 4);
				}
			}
		}
	}

	/**
	 * 大叫頻道(!)
	 * @param pc
	 * @param chatText
	 */
	private void chatType_2(final L1PcInstance pc, final String chatText) {
		if (pc.isGhost()) {
			return;
		}
		S_ChatShouting chatpacket = new S_ChatShouting(pc, chatText);
		pc.sendPackets(chatpacket);
		for (final L1PcInstance listner : World.get().getVisiblePlayer(pc, 50)) {
			if (!listner.getExcludingList().contains(pc.getName())) {
				// 副本ID相等
				if (pc.get_showId() == listner.get_showId()) {
					listner.sendPackets(chatpacket);
				}
			}
		}

		if (ConfigRecord.LOGGING_CHAT_SHOUT) {
			LogChatReading.get().noTarget(pc, chatText, 2);
		}
		// 變形怪重複對話
		this.doppelShouting(pc, chatText);
	}

	/**
	 * 一般頻道
	 * @param pc
	 * @param chatText
	 */
	private void chatType_0(final L1PcInstance pc, final String chatText) {
		if (pc.isGhost() && !(pc.isGm() || pc.isMonitor())) {
			return;
		}
		if (pc.getAccessLevel() > 0) {
			// GM命令
			if (chatText.startsWith(".")) {
				final String cmd = chatText.substring(1);
				GMCommands.getInstance().handleCommands(pc, cmd);
				return;
			}
		}

		// 產生封包
		S_Chat chatpacket = new S_Chat(pc, chatText);
		pc.sendPackets(chatpacket);
		
		for (final L1PcInstance listner : World.get().getRecognizePlayer(pc)) {
			if (!listner.getExcludingList().contains(pc.getName())) {
				// 副本ID相等
				if (pc.get_showId() == listner.get_showId()) {
					listner.sendPackets(chatpacket);
				}
			}
		}

		// 對話紀錄
		if (ConfigRecord.LOGGING_CHAT_NORMAL) {
			LogChatReading.get().noTarget(pc, chatText, 0);
		}
		// 變形怪重複對話
		doppelGenerally(pc, chatText);
	}

	/**
	 * 變形怪重複對話(一般頻道)
	 * @param pc
	 * @param chatType
	 * @param chatText
	 */
	private void doppelGenerally(final L1PcInstance pc, final String chatText) {
		// 變形怪重複對話
		for (final L1Object obj : pc.getKnownObjects()) {
			if (obj instanceof L1MonsterInstance) {
				final L1MonsterInstance mob = (L1MonsterInstance) obj;
				if (mob.getNpcTemplate().is_doppel() && mob.getName().equals(pc.getName())) {
					mob.broadcastPacketX8(new S_NpcChat(mob, chatText));
				}
			}
		}
	}

	/**
	 * 變形怪重複對話(大喊頻道)
	 * @param pc
	 * @param chatType
	 * @param chatText
	 */
	private void doppelShouting(final L1PcInstance pc, final String chatText) {
		// 變形怪重複對話
		for (final L1Object obj : pc.getKnownObjects()) {
			if (obj instanceof L1MonsterInstance) {
				final L1MonsterInstance mob = (L1MonsterInstance) obj;
				if (mob.getNpcTemplate().is_doppel() && mob.getName().equals(pc.getName())) {
					mob.broadcastPacketX8(new S_NpcChatShouting(mob, chatText));
				}
			}
		}
	}

	@Override
	public String getType() {
		return this.getClass().getSimpleName();
	}
}
