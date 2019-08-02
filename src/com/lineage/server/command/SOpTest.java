package com.lineage.server.command;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.model.L1Location;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.*;
import com.lineage.server.templates.L1Skills;
import com.lineage.server.world.World;

/**
 * 測試已知封包正確性
 * @author DaiEn
 *
 */
public class SOpTest extends OpcodesServer {

	private static final Log _log = LogFactory.getLog(SOpTest.class);
	
	/**
	 * 皇冠測試
	 * @param pc
	 * @throws Exception
	 */
	public static void testOpA(final L1PcInstance pc, final int opid) throws Exception {
		try {
			World.get().broadcastPacketToAll(new S_CastleMaster(opid, pc.getId()));
			System.out.println(opid + "/ " +pc.getId());
			//final Map<Integer, L1Clan> map = L1CastleLocation.mapCastle();
			//System.out.println(map.size());
			/*for (int i = 1 ; i < 9 ; i++) {
				if (i != 2) {
					World.get().broadcastPacketToAll(new S_CastleMaster(i, pc.getId()));
					System.out.println(i + "/ " +pc.getId());
					Thread.sleep(5000);

					System.out.println(i + "/ 移除");
					pc.sendPackets(new S_CastleMaster(0, pc.getId()));
					Thread.sleep(5000);
				}
			}//*/
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	/**
	 * 測試已知封包正確性<BR>
	 * 封包不正確者 預先註解掉
	 * @param pc
	 * @param opid
	 * @throws Exception 
	 */
	public static void testOp(final L1PcInstance pc, final int opid) throws Exception {
		/*初始化*/
		if (opid == S_OPCODE_INITOPCODE) {
			// 免測試
			
		/*伺服器版本*/
		} else if (opid == S_OPCODE_SERVERVERSION) {
			// 免測試
		
		/*登入狀態*/
		} else if (opid == S_OPCODE_LOGINRESULT) {
			// 免測試
		
		/*宣告進入遊戲*/
		} else if (opid == S_OPCODE_UNKNOWN1) {
			// 免測試
		
		/*公告視窗*/
		} else if (opid == S_OPCODE_COMMONNEWS) {
			// 免測試
		
		/*封包盒子*/
		} else if (opid == S_OPCODE_PACKETBOX) {
			// GM管理選單  移動至指定人物
			//pc.sendPackets(new S_PacketBoxGm(pc, 1));
			pc.sendPackets(new S_PacketBox(S_PacketBox.DOLL, 1800));
			//pc.sendPackets(new S_SkillIconAura(3819, 600));
			
			/*for (int i = 0 ; i < 128 ; i++) {
				Thread.sleep(1000);
				pc.sendPackets(new S_PacketBox(i, 600));
			}*/
			
		/*立即中斷連線*/
		} else if (opid == S_OPCODE_DISCONNECT) {
			pc.sendPackets(new S_Disconnect());

		/*廣播頻道*/
		} else if (opid == S_OPCODE_GLOBALCHAT) {
			// 廣播頻道測試
			pc.sendPackets(new S_ChatGlobal(pc, "廣播頻道測試"));
		
		/*一般頻道*/
		} else if (opid == S_OPCODE_NORMALCHAT) {
			// 一般頻道測試
			pc.sendPackets(new S_Chat(pc, "一般頻道測試"));
			
		/*使用密語聊天頻道*/
		} else if (opid == S_OPCODE_WHISPERCHAT) {
			// 密語交談(接收)頻道測試
			pc.sendPackets(new S_ChatWhisperFrom(pc, "密語交談(接收)頻道測試"));
			
		/*NPC 對話(文字對話)*/
		} else if (opid == S_OPCODE_NPCSHOUT) {
			// 取回認識物件清單
			for (final L1Object obj : pc.getKnownObjects()) {
				if (obj instanceof L1NpcInstance) {
					L1NpcInstance npc = (L1NpcInstance) obj;
					npc.broadcastPacketX8(new S_NpcChat(npc, pc.getName() + " 打3小~~"));
				}
			}
			
		/*角色列表*/
		} else if (opid == S_OPCODE_CHARAMOUNT) {
			// 免測試
		
		/*角色列表資訊*/
		} else if (opid == S_OPCODE_CHARLIST) {
			// 免測試
		
		/*角色創造結果*/
		} else if (opid == S_OPCODE_NEWCHARWRONG) {
			// 免測試
		
		/*創造角色(新創)*/
		} else if (opid == S_OPCODE_NEWCHARPACK) {
			// 免測試
		
		/*角色移除(立即/非立即)*/
		} else if (opid == S_OPCODE_DETELECHAROK) {
			// 免測試
		
		/*角色資訊*/
		} else if (opid == S_OPCODE_OWNCHARSTATUS) {
			// 測試角色資訊 - 力量101
			pc.sendPackets(new S_OwnCharStatus(pc, 101));
			
		/*角色狀態*/
		} else if (opid == S_OPCODE_OWNCHARSTATUS2) {
			// 測試角色狀態 - 力量103
			pc.sendPackets(new S_OwnCharStatus2(pc, 103));
			
		/*角色盟徽*/
		} else if (opid == S_OPCODE_EMBLEM) {
			// 免測試
		
		/*角色封號*/
		} else if (opid == S_OPCODE_CHARTITLE) {
			final StringBuilder title = new StringBuilder();
			title.append("\\f=測試角色封號");
			pc.sendPackets(new S_CharTitle(pc.getId(), title));
			
		/*角色名稱變紫色*/
		} else if (opid == S_OPCODE_PINKNAME) {
			for (final L1Object obj : pc.getKnownObjects()) {
				if (obj instanceof L1NpcInstance) {
					L1NpcInstance npc = (L1NpcInstance) obj;
					npc.broadcastPacketX8(new S_PinkName(npc.getId(), 1000));
				}
			}
			pc.sendPacketsAll(new S_PinkName(pc.getId(), 1000));
			
		/*角色皇冠*/
		} else if (opid == S_OPCODE_CASTLEMASTER) {
			// 5 海音城
			pc.sendPackets(new S_CastleMaster(5, pc.getId()));
			
		/*角色重置升級能力*/
		} else if (opid == S_OPCODE_CHARRESET) {
			pc.sendPackets(new S_CharReset(pc));

		/*物件封包*/
		} else if (opid == S_OPCODE_CHARPACK) {
			// 免測試
	
		/*物件刪除*/
		} else if (opid == S_OPCODE_REMOVE_OBJECT) {
			for (final L1Object obj : pc.getKnownObjects()) {
				if (obj instanceof L1NpcInstance) {
					L1NpcInstance npc = (L1NpcInstance) obj;
					pc.sendPackets(new S_RemoveObject(npc));
				}
			}
			
		/*物件血條*/
		} else if (opid == S_OPCODE_HPMETER) {
			for (final L1Object obj : pc.getKnownObjects()) {
				if (obj instanceof L1NpcInstance) {
					L1NpcInstance npc = (L1NpcInstance) obj;
					pc.sendPackets(new S_HPMeter(npc.getId(), 100 * npc.getCurrentHp() / npc.getMaxHp()));
				}
			}
	
		/*物件屬性(門)*/
		} else if (opid == S_OPCODE_ATTRIBUTE) {
			// 免測試
		
		/*物品增加(背包)*/
		} else if (opid == S_OPCODE_ADDITEM) {
			// 免測試
		
		/*物品刪除(背包)*/
		} else if (opid == S_OPCODE_DELETEINVENTORYITEM) {
			// 免測試
		
		/*物品色彩狀態(背包)*/
		} else if (opid == S_OPCODE_ITEMCOLOR) {
			// 免測試
		
		/*物件復活*/
		} else if (opid == S_OPCODE_RESURRECTION) {
			// 預先死亡人物
			pc.sendPacketsX8(new S_SkillSound(pc.getId(), '\346'));
			pc.resurrect(pc.getMaxHp());
			pc.setCurrentHp(pc.getMaxHp());
			pc.startHpRegeneration();
			pc.startMpRegeneration();
			pc.stopPcDeleteTimer();
			pc.sendPacketsAll(new S_Resurrection(pc, pc, 0));
			
			pc.sendPacketsAll(new S_CharVisualUpdate(pc));
			if ((pc.getExpRes() == 1) && pc.isGres() && pc.isGresValid()) {
				pc.resExp();
				pc.setExpRes(0);
				pc.setGres(false);
			}

		/*物件移動*/
		} else if (opid == S_OPCODE_MOVEOBJECT) {
			// 免測試
		
		/*物件攻擊*/
		} else if (opid == S_OPCODE_ATTACKPACKET) {
			// 免測試
		
		/*物件動作種類(長時間)*/
		} else if (opid == S_OPCODE_CHARVISUALUPDATE) {
			// 免測試
		
		/*物件動作種類(短時間)*/
		} else if (opid == S_OPCODE_DOACTIONGFX) {
			pc.sendPackets(new S_DoActionShop(pc.getId(), "物件動作種類(短時間)"));
		
		/*產生動畫(物件)*/
		} else if (opid == S_OPCODE_SKILLSOUNDGFX) {
			// 閃光
			//pc.sendPackets(new S_SkillSound(pc.getId(), 198));
			pc.sendPackets(new S_SkillSound(pc.getId(), 3819));
			for (int i = 7013 ; i < 7100 ; i++) {
				System.out.println("i: " + i);
				pc.sendPackets(new S_SkillSound(pc.getId(), i, 150));
				Thread.sleep(500);
			}
			
		/*產生動畫(地點)*/
		} else if (opid == S_OPCODE_EFFECTLOCATION) {
			// (電)組合動畫效果
			int x = pc.getX();
			int y = pc.getY();
			int mapId = pc.getMapId();
			
			pc.sendPackets(new S_EffectLocation(new L1Location(
					x - 2, y - 2, mapId), 4842));
			pc.sendPackets(new S_EffectLocation(new L1Location(
					x + 2, y - 2, mapId), 4842));
			pc.sendPackets(new S_EffectLocation(new L1Location(
					x + 2, y + 2, mapId), 4842));
			pc.sendPackets(new S_EffectLocation(new L1Location(
					x - 2, y + 2, mapId), 4842));
			
		/*範圍魔法*/
		} else if (opid == S_OPCODE_RANGESKILLS) {
			// 免測試
		
		/*角色鎖定(座標異常重整)*/
		} else if (opid == S_OPCODE_CHARLOCK) {
			// 免測試

		/*郵件系統*/
		} else if (opid == S_OPCODE_MAIL) {
			// 免測試
		
		/*血盟戰爭訊息(編號,血盟名稱,目標血盟名稱)*/
		} else if (opid == S_OPCODE_WAR) {
			// 7 : 毀掉%0 血盟與 %1血盟之間的同盟。
			pc.sendPackets(new S_War(7, "測試0血盟", "測試1血盟"));
			
		/*NPC對話視窗*/
		} else if (opid == S_OPCODE_SHOWHTML) {
			// 能力質選取資料
			pc.sendPackets(new S_Bonusstats(pc.getId()));
			
		/*選取物品數量*/
		} else if (opid == S_OPCODE_INPUTAMOUNT) {
			pc.sendPackets(new S_ItemCount(pc.getId(), 1000, "XXXX"));
			
		/*伺服器訊息(行數/行數,附加字串)*/
		} else if (opid == S_OPCODE_SERVERMSG) {
			// 96 \f1%0%s 拒絕你的請求。
			pc.sendPackets(new S_ServerMessage(96, "測試"));
		
		/*選項(Yes/No)*/
		} else if (opid == S_OPCODE_YES_NO) {
			// 你的血盟成員想要傳送你。你答應嗎？(Y/N)
			pc.sendPackets(new S_Message_YN(748));
		
		/*物品鑑定資訊訊息*/
		} else if (opid == S_OPCODE_IDENTIFYDESC) {
			// 物品資訊訊息(使用String-c.tbl) 測試(骰子匕首)
			pc.sendPackets(new S_IdentifyDesc());
		
		/*畫面中藍色(紅色)訊息*/
		} else if (opid == S_OPCODE_BLUEMESSAGE) {
			// 552 因為你已經殺了 %0(100) 人所以被打入地獄。 你將在這裡停留 %1(50) 分鐘。
			pc.sendPackets(new S_BlueMessage(552, "100", "50"));

		/*更新物品顯示名稱(背包)*/
		} else if (opid == S_OPCODE_ITEMNAME) {
			// 免測試
		
		/*更新物品使用狀態(背包)*/
		} else if (opid == S_OPCODE_ITEMAMOUNT) {
			// 免測試
		
		/*更新物件亮度*/
		} else if (opid == S_OPCODE_LIGHT) {
			pc.sendPackets(new S_Light(pc.getId(), 0x14));
			
		/*更新遊戲天氣*/
		} else if (opid == S_OPCODE_WEATHER) {
			// 下大雨
			pc.sendPackets(new S_Weather(19));
			
		/*更新物件面向*/
		} else if (opid == S_OPCODE_CHANGEHEADING) {
			try {
				pc.setHeading(0);
				pc.sendPackets(new S_ChangeHeading(pc));
				Thread.sleep(500);
				pc.setHeading(1);
				pc.sendPackets(new S_ChangeHeading(pc));
				Thread.sleep(500);
				pc.setHeading(2);
				pc.sendPackets(new S_ChangeHeading(pc));
				Thread.sleep(500);
				pc.setHeading(3);
				pc.sendPackets(new S_ChangeHeading(pc));
				Thread.sleep(500);
				pc.setHeading(4);
				pc.sendPackets(new S_ChangeHeading(pc));
				Thread.sleep(500);
				pc.setHeading(5);
				pc.sendPackets(new S_ChangeHeading(pc));
				Thread.sleep(500);
				pc.setHeading(6);
				pc.sendPackets(new S_ChangeHeading(pc));
				Thread.sleep(500);
				pc.setHeading(7);
				pc.sendPackets(new S_ChangeHeading(pc));
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
		/*更新物件外型*/
		} else if (opid == S_OPCODE_POLY) {
			// 可用GM命令
		
		/*更新物件名稱*/
		} else if (opid == S_OPCODE_CHANGENAME) {
			pc.sendPackets(new S_ChangeName(pc.getId(), "更新物件名稱"));
			
		/*更新HP顯示*/
		} else if (opid == S_OPCODE_HPUPDATE) {
			pc.sendPackets(new S_HPUpdate(32767, 32767));
		
		/*更新MP顯示*/
		} else if (opid == S_OPCODE_MPUPDATE) {
			pc.sendPackets(new S_MPUpdate(32767, 32767));
		
		/*更新角色所在的地圖*/
		} else if (opid == S_OPCODE_MAPID) {
			// XXX ?????????
		
		/*更新遊戲時間*/
		} else if (opid == S_OPCODE_GAMETIME) {
		
		/*更新經驗值*/
		} else if (opid == S_OPCODE_EXP) {
			// LV 59 95.7553%
			pc.sendPackets(new S_Exp());
		
		/*更新正義值*/
		} else if (opid == S_OPCODE_LAWFUL) {
			// -12345
			pc.sendPackets(new S_Lawful(pc.getId()));
		
		/*更新魔攻與魔防*/
		} else if (opid == S_OPCODE_SPMR) {
			// +50 +100
			pc.sendPackets(new S_SPMR());
		
		/*更新角色防禦屬性*/
		} else if (opid == S_OPCODE_OWNCHARATTRDEF) {
			// -99 90 85 80 75
			pc.sendPackets(new S_OwnCharAttrDef());

		/*佈告欄列表*/
		} else if (opid == S_OPCODE_BOARD) {
			pc.sendPackets(new S_Board(pc.getId()));
		
		/*佈告欄(訊息閱讀)*/
		} else if (opid == S_OPCODE_BOARDREAD) {
			pc.sendPackets(new S_BoardRead());
		
		/*盟屋拍賣公告欄列表*/
		} else if (opid == S_OPCODE_HOUSELIST) {
		
		/*血盟小屋地圖(地點)*/
		} else if (opid == S_OPCODE_HOUSEMAP) {

		/*魔法效果 毒素*/
		} else if (opid == S_OPCODE_POISON) {
			// 緑色
			pc.sendPackets(new S_Poison(pc.getId(), 1));
			Thread.sleep(1000);
			// 灰色
			pc.sendPackets(new S_Poison(pc.getId(), 2));
			Thread.sleep(1000);
			// 通常色
			pc.sendPackets(new S_Poison(pc.getId(), 0));
		
		/*魔法效果 勇敢藥水纇*/
		} else if (opid == S_OPCODE_SKILLBRAVE) {
			// 3:身體內深刻的感覺到充滿了森林的活力。(精靈餅乾)
			pc.sendPackets(new S_SkillBrave(pc.getId(), 3));
			Thread.sleep(1000);
			// 5:從身體的深處感到熱血沸騰。(第二階段勇水)
			pc.sendPackets(new S_SkillBrave(pc.getId(), 5));
			Thread.sleep(1000);
			// 0:你的情緒回復到正常。(解除 )
			pc.sendPackets(new S_SkillBrave(pc.getId(), 0));
		
		/*魔法效果 防禦*/
		} else if (opid == S_OPCODE_SKILLICONSHIELD) {
			pc.sendPackets(new S_SkillIconShield(5, 3600));
		
		/*魔法效果 加速纇*/
		} else if (opid == S_OPCODE_SKILLHASTE) {
		
		/*魔法效果 精準目標*/
		} else if (opid == S_OPCODE_TRUETARGET) {
			for (final L1Object obj : pc.getKnownObjects()) {
				if (obj instanceof L1NpcInstance) {
					L1NpcInstance npc = (L1NpcInstance) obj;
					npc.broadcastPacketX8(new S_TrueTarget(npc.getId(), pc.getId(), "魔法效果 精準目標"));
				}
			}
		
		/*魔法效果 水底呼吸*/
		} else if (opid == S_OPCODE_BLESSOFEVA) {
			pc.sendPackets(new S_SkillIconBlessOfEva(pc.getId(), 300));
			
		/*魔法效果 物件隱形*/
		} else if (opid == S_OPCODE_INVIS) {
			pc.sendPackets(new S_Invis(pc.getId(), 1));
			Thread.sleep(1000);
			pc.sendPackets(new S_Invis(pc.getId(), 0));
		
		/*魔法效果 操作混亂(醉酒)*/
		} else if (opid == S_OPCODE_LIQUOR) {
			pc.sendPackets(new S_Liquor(pc.getId(), 1));
			Thread.sleep(1000);
			pc.sendPackets(new S_Liquor(pc.getId(), 2));
			Thread.sleep(1000);
			pc.sendPackets(new S_Liquor(pc.getId(), 3));
			Thread.sleep(1000);
			pc.sendPackets(new S_Liquor(pc.getId(), 0));
		
		/*魔法效果 詛咒*/
		} else if (opid == S_OPCODE_PARALYSIS) {
			// 你的身體完全麻痺了
			pc.sendPackets(new S_Paralysis(0x02, true));
		
		/*魔法效果 敏捷提升*/
		} else if (opid == S_OPCODE_DEXUP) {
			pc.sendPackets(new S_Dexup(pc, 5, 1800));
		
		/*魔法效果 力量提升*/
		} else if (opid == S_OPCODE_STRUP) {
			pc.sendPackets(new S_Strup(pc, 5, 1800));
		
		/*魔法效果 暗盲咒術*/
		} else if (opid == S_OPCODE_CURSEBLIND) {
			pc.sendPackets(new S_CurseBlind(1));
			Thread.sleep(1000);
			pc.sendPackets(new S_CurseBlind(2));
			Thread.sleep(1000);
			pc.sendPackets(new S_CurseBlind(0));

		/*物品名單(背包)*/
		} else if (opid == S_OPCODE_INVLIST) {
		
		/*魔法購買清單(金幣)*/
		} else if (opid == S_OPCODE_SKILLBUY) {
		
		/*魔法購買清單(材料)*/
		} else if (opid == S_OPCODE_SKILLBUYITEM) {
		
		/*魔法清單(增加)*/
		} else if (opid == S_OPCODE_ADDSKILL) {
			for (int i = 0 ; i < 300 ; i++) {
				// 取得魔法資料(要移除的)
				final L1Skills skill = SkillsTable.get().getTemplate(i);
				if (skill != null) {
					if (skill.getSkillLevel() > 0) {
						// 移除魔法
						pc.sendPackets(new S_DelSkill(pc, i));
					}
				}
			}
			
			Thread.sleep(2000);
			System.out.println("TEST START!!");
			
			int[] level = new int[28];
			int dx = 1;
			for (int mode = 0 ; mode < 28 ; mode++) {
				while (dx < 255) {
					level[mode] += dx;
					pc.sendPackets(new S_AddSkill(
							pc, mode, level[mode]
							));
					System.out.println("dx: " + dx + " " + level[mode]);
					dx = dx << 1;
					Thread.sleep(100);
				}
				if (dx >= 255) {
					dx = 1;
				}
			}

		/*魔法清單(移除)*/
		} else if (opid == S_OPCODE_DELSKILL) {
			for (int i = 0 ; i < 300 ; i++) {
				// 取得魔法資料(要移除的)
				final L1Skills skill = SkillsTable.get().getTemplate(i);
				if (skill != null) {
					if (skill.getSkillLevel() > 0) {
						// 移除魔法
						pc.sendPackets(new S_DelSkill(pc, i));
					}
				}
				Thread.sleep(10);
			}

		/*物品名單(倉庫)*/
		} else if (opid == S_OPCODE_SHOWRETRIEVELIST) {
		
		/*角色座標名單*/
		} else if (opid == S_OPCODE_BOOKMARKS) {
		
		/*損壞武器清單*/
		} else if (opid == S_OPCODE_SELECTLIST) {
			// 身上必須有損壞武器
			// 暫時清單
			final List<L1ItemInstance> weaponList = new ArrayList<L1ItemInstance>();
			// 背包物件
			final List<L1ItemInstance> itemList = pc.getInventory().getItems();
			for (final L1ItemInstance item : itemList) {
				switch (item.getItem().getType2()) {
				case 1:
					if (item.get_durability() > 0) {
						weaponList.add(item);
					}
					break;
				}
			}
			pc.sendPackets(new S_FixWeaponList(weaponList));

		/*NPC物品購買清單(人物賣出)*/
		} else if (opid == S_OPCODE_SHOWSHOPSELLLIST) {
		
		/*NPC物品販賣清單(人物買入)*/
		} else if (opid == S_OPCODE_SHOWSHOPBUYLIST) {
		
		/*交易封包*/
		} else if (opid == S_OPCODE_TRADE) {
			//  顯示:測試交易封包
			pc.sendPackets(new S_Trade("測試交易封包"));
		
		/*交易狀態*/
		} else if (opid == S_OPCODE_TRADESTATUS) {
			//  1:交易取消
			pc.sendPackets(new S_TradeStatus(1));
		
		/*交易增加物品*/
		} else if (opid == S_OPCODE_TRADEADDITEM) {
			// 須先開起交易視窗
			//  1:交易視窗下半部  惡魔頭盔  測試物品(55) 0:祝福 
			pc.sendPackets(new S_TradeAddItem());
		
		/*交易商店清單(購買/個人商店)*/
		} else if (opid == S_OPCODE_PRIVATESHOPLIST) {

		/*戒指*/
		} else if (opid == S_OPCODE_ABILITY) {
		
		/*撥放音效*/
		} else if (opid == S_OPCODE_SOUND) {
		
		/*傳送鎖定(瞬間移動)*/
		} else if (opid == S_OPCODE_TELEPORT) {
		
		/*傳送鎖定(座標點)*/
		} else if (opid == S_OPCODE_TELEPORTLOCK) {

		/*選擇一個目標*/
		} else if (opid == S_OPCODE_SELECTTARGET) {
			pc.sendPackets(new S_SelectTarget(pc.getId()));
		
		/*城堡寶庫(要求存入資金)*/
		} else if (opid == S_OPCODE_DEPOSIT) {
			// 須先開啟一個HTML視窗
			// 顯示身上金幣
			pc.sendPackets(new S_Deposit(pc.getId()));
		
		/*城堡寶庫(要求領出資金)*/
		} else if (opid == S_OPCODE_DRAWAL) {
			// 須先開啟一個HTML視窗
			// 顯示 1234567
			pc.sendPackets(new S_Drawal(pc.getId(), 1234567));
	
		/*傭兵數量名單*/
		} else if (opid == S_OPCODE_HIRESOLDIER) {
			// 須先開啟一個HTML視窗
			pc.sendPackets(new S_HireSoldier(pc));
			
		/*稅收設定*/
		} else if (opid == S_OPCODE_TAXRATE) {
			// 須先開啟一個HTML視窗
			pc.sendPackets(new S_TaxRate(pc.getId()));
			
		/*圍城時間設定*/
		} else if (opid == S_OPCODE_WARTIME) {
			// 須先開啟一個HTML視窗
			pc.sendPackets(new S_WarTime(S_OPCODE_WARTIME));

		/*畫面中紅色訊息(登入來源)*/
		} else if (opid == S_OPCODE_RED) {
	
		/*NPC改變外型*/
		} else if (opid == S_OPCODE_NPCPOLY) {
			for (final L1Object obj : pc.getKnownObjects()) {
				if (obj instanceof L1NpcInstance) {
					L1NpcInstance npc = (L1NpcInstance) obj;
					npc.broadcastPacketX8(new S_ChangeShape(pc, npc, 1080));
				}
			}
			
		/*Ping Time*/
		} else if (opid == S_OPCODE_PINGTIME) {
			
		/*強制登出人物*/
		} else if (opid == S_OPCODE_CHAROUT) {
			pc.sendPackets(new S_ChatOut(pc));
	
		/*物件新增主人*/
		} else if (opid == S_OPCODE_NEWMASTER) {

		/*未知購物清單*/
		} else if (opid == S_OPCODE_SHOPX1) {
				
		/*未知購物清單*/
		} else if (opid == S_OPCODE_SHOPX2) {
		
		// NEW ADD
		
		/*學習魔法材料不足*/
		} else if (opid == S_OPCODE_ITEMERROR) {
			for (int i = 0 ; i < 10 ; i++) {
				pc.sendPackets(new S_ItemError(i));
				Thread.sleep(250);
			}
			
		/*服務器登入訊息(使用string.tbl)*/
		} else if (opid == S_OPCODE_COMMONINFO) {
			// 免測試

		}
	}
}
