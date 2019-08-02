package com.lineage.server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.config.Config;
import com.lineage.config.ConfigAlt;
import com.lineage.config.ConfigRate;
import com.lineage.echo.PacketHandler;
import com.lineage.list.BadNamesList;

import com.lineage.server.datatables.*;
import com.lineage.server.datatables.lock.*;
import com.lineage.server.datatables.sql.*;

import com.lineage.server.model.L1AttackList;
import com.lineage.server.model.L1CastleLocation;
import com.lineage.server.model.Instance.L1DoorInstance;
import com.lineage.server.model.Instance.L1ItemPower;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.gametime.L1GameTimeClock;
import com.lineage.server.model.map.L1WorldMap;
import com.lineage.server.model.skill.L1SkillMode;
import com.lineage.server.templates.L1PcOther;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.thread.NpcAiThreadPool;
import com.lineage.server.thread.PcOtherThreadPool;
import com.lineage.server.timecontroller.StartTimer_Event;
import com.lineage.server.timecontroller.StartTimer_Pc;
import com.lineage.server.timecontroller.StartTimer_Npc;
import com.lineage.server.timecontroller.StartTimer_Pet;
import com.lineage.server.timecontroller.StartTimer_Server;
import com.lineage.server.timecontroller.StartTimer_Skill;
import com.lineage.server.utils.PerformanceTimer;
import com.lineage.server.world.*;

/**
 * 加載服務器設置
 * @author dexc
 *
 */
public class GameServer {

	private static final Log _log = LogFactory.getLog(GameServer.class);

	private static GameServer _instance;

	public static GameServer getInstance() {
		if (_instance == null) {
			_instance = new GameServer();
		}
		return _instance;
	}

	public void initialize() throws Exception {
		final PerformanceTimer timer = new PerformanceTimer();
		try {
			_log.info(
					"\n\r--------------------------------------------------" +
					"\n\r       外部設置：經驗倍率: " + ConfigRate.RATE_XP +
					"\n\r       外部設置：正義質倍率: " + ConfigRate.RATE_LA +
					"\n\r       外部設置：有好度倍率: " + ConfigRate.RATE_KARMA +
					"\n\r       外部設置：物品掉落倍率: " + ConfigRate.RATE_DROP_ITEMS +
					"\n\r       外部設置：金幣掉落倍率: " + ConfigRate.RATE_DROP_ADENA +
					"\n\r       外部設置：廣播等級限制: " + ConfigAlt.GLOBAL_CHAT_LEVEL +
					"\n\r       外部設置：PK設置: " + (ConfigAlt.ALT_NONPVP ? "允許":"不允許") +
					"\n\r       外部設置：最大連線設置: " + Config.MAX_ONLINE_USERS +
					"\n\r--------------------------------------------------"
					);

			// 客戶端封包資料加載
			PacketHandler.load();
			
			ServerReading.get().load();// 載入保留的服務器資料

			IdFactory.get().load();// OBJID

			CharObjidTable.get().load();// 人物已用OBJID預先加載/血盟已用OBJID預先加載

			AccountReading.get().load();// 帳戶名稱資料

			GeneralThreadPool.get();// 線程工廠設置
			
			PcOtherThreadPool.get();// 線程工廠設置
			
			NpcAiThreadPool.get();// 線程工廠設置

			ExpTable.get().load();// 經驗資料庫
			
			SprTable.get().load();// 圖形影格資料

			MapsTable.get().load();// 地圖設置

			MapExpTable.get().load();// 地圖經驗設置
			
			MapLevelTable.get().load();// 地圖等級限制
			
			ItemTimeTable.get().load();// 物品可用時間指定
			
			L1WorldMap.get().load();// MAP

			L1GameTimeClock.init();// 遊戲時間時計

			NpcTable.get().load();// NPC資料
			
			NpcScoreTable.get().load();// NPC積分資料

			CharacterTable.loadAllCharName();// 載入已用名稱

			CharacterTable.clearOnlineStatus();// 全部狀態離線

			// 世界儲存中心資料建立
			World.get();
			
			WorldCrown.get();
			
			WorldKnight.get();
			
			WorldElf.get();
			
			WorldWizard.get();
			
			WorldDarkelf.get();
			
			WorldDragonKnight.get();
			
			WorldIllusionist.get();
			
			WorldPet.get();
			
			WorldSummons.get();

			TrapTable.get().load();// 陷阱資料
			
			TrapsSpawn.get().load();// 陷阱召喚資料

			ItemTable.get().load();// 道具物品資料

			DropTable.get().load();// 掉落物品資料

			DropMapTable.get().load();// 掉落物品資料

			DropItemTable.get().load();// 掉落物品機率資料

			SkillsTable.get().load();// 技能設置資料
			
			SkillsItemTable.get().load();// 購買技能 金幣/材料 設置資料

			MobGroupTable.get().load();// MOB隊伍資料

			SpawnTable.get().load();// 召喚清單

			PolyTable.get().load();// 人物變身資料

			ShopTable.get().load();// 商店販賣資料
			
			ShopCnTable.get().load();// 特殊商店販賣資料

			DungeonTable.get().load();// 地圖切換點設置

			DungeonRTable.get().load();// 地圖切換點設置(多點)

			NPCTalkDataTable.get().load();// NPC對話資料

			NpcSpawnTable.get().load();// 召喚NPC資料

			DwarfForClanReading.get().load();// 血盟倉庫資料建立
			
			ClanReading.get().load();// 血盟資料

			ClanEmblemReading.get().load();// 血盟盟輝資料

			CastleReading.get().load();// 城堡資料

			L1CastleLocation.setCastleTaxRate(); // 城堡稅收數據

			GetBackRestartTable.get().load();// 回城座標資料
			
			DoorSpawnTable.get().load();// 門資料

			WeaponSkillTable.get().load();// 技能武器資料

			NpcActionTable.load();// NPC XML對話結果資料

			GetbackTable.loadGetBack();// 回村座標設置

			PetTypeTable.load();// 寵物種族資料

			PetItemTable.get().load();// 寵物道具資料
			
			ItemBoxTable.get().load();// 箱子開出物設置

			ResolventTable.get().load();// 溶解物品設置

			NpcTeleportTable.get().load();// NPC傳送點設置

			NpcChatTable.get().load();// NPC會話資料

			ArmorSetTable.get().load();// 套裝設置

			ItemTeleportTable.get().load();// 傳送捲軸傳送點定義
			
			ItemPowerUpdateTable.get().load();// 特殊物品升級資料

			CommandsTable.get().load();// GM命令

			BeginnerTable.get().load();// 新手物品資料
			
			ItemRestrictionsTable.get().load();// 物品交易限制清單

			// TODO 預先加載SQL資料
			
			// 各項特殊活動設置啟動
			EventTable.get().load();
			
			// 特殊活動設置召喚啟動
			if (EventTable.get().size() > 0) {
				EventSpawnTable.get().load();
			}
			
			// 召喚BOSS資料
			SpawnBossReading.get().load();

			// 血盟小屋
			HouseReading.get().load();

			// 禁止位置
			IpReading.get().load();

			// 村莊資料
			TownReading.get().load();

			// 信件資料
			MailReading.get().load();

			// 拍賣公告欄資料
			AuctionBoardReading.get().load();

			// 佈告欄資料
			BoardReading.get().load();

			// 保留技能紀錄
			CharBuffReading.get().load();

			// 人物技能紀錄
			CharSkillReading.get().load();

			// 人物快速鍵紀錄
			CharacterConfigReading.get().load();

			// 人物好友紀錄
			BuddyReading.get().load();

			// 人物記憶座標紀錄資料
			CharBookReading.get().load();

			// 人物額外紀錄資料
			CharOtherReading.get().load();
			
			// 任務紀錄
			CharacterQuestReading.get().load();

			// 建立角色名稱時禁止使用的字元
			BadNamesList.get().load();

			// 景觀設置資料
			SceneryTable.get().load();

			// 各項技能設置啟用
			L1SkillMode.get().load();
			
			// 物理攻擊/魔法攻擊判定
			L1AttackList.load();

			// 物品能力值
			L1ItemPower.load();
			
			// 加載連續魔法減低損傷資料
			L1PcInstance.load();

			// 背包資料建立
			CharItemsReading.get().load();
			
			// 倉庫資料建立
			DwarfReading.get().load();
			
			// 精靈倉庫資料建立
			DwarfForElfReading.get().load();
			
			// 娃娃能力資料
			DollPowerTable.get().load();

			// 寵物資料
			PetReading.get().load();
			
			// 人物背包物品使用期限資料
			CharItemsTimeReading.get().load();
			
			// 人物其他相關設置表
			L1PcOther.load();
			
			// 任務(副本)地圖設置加載
			QuestMapTable.get().load();

			// 家具召喚資料
			FurnitureSpawnReading.get().load();

			// 打寶公告
			ItemMsgTable.get().load();
			
			// 武器額外傷害資料
			WeaponPowerTable.get().load();
			
			// 漁獲資料
			FishingTable.get().load();
			
			// 攻城獎勵
			CastleWarGiftTable.get().load();
			
			CharBookConfigReading.get().load();
			
			// TODO TIMER
			
			// 服務器專用時間軸
			final StartTimer_Server startTimer = new StartTimer_Server();
			startTimer.start();

			// PC專用時間軸
			final StartTimer_Pc pcTimer = new StartTimer_Pc();
			pcTimer.start();

			// NPC專用時間軸
			final StartTimer_Npc npcTimer = new StartTimer_Npc();
			npcTimer.start();

			// PET專用時間軸
			final StartTimer_Pet petTimer = new StartTimer_Pet();
			petTimer.start();

			// SKILL專用時間軸
			final StartTimer_Skill skillTimer = new StartTimer_Skill();
			skillTimer.start();
			
			// EVENT專用時間軸
			final StartTimer_Event eventTimer = new StartTimer_Event();
			eventTimer.start();

			// 設置關機導向
			Runtime.getRuntime().addShutdownHook(Shutdown.getInstance());

			// TODO 它向設置

			// 監聽端口啟動重置作業
			EchoServerTimer.get().start();
			
			// 打開關閉的門
			L1DoorInstance.openDoor();
			
			//_log.info("遊戲伺服器啟動完成!!");

		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);

		} finally {
			final String osname = System.getProperties().getProperty("os.name");
			final String username = System.getProperties().getProperty("user.name");

			final String ver = "\n\r--------------------------------------------------"
				+ "\n\r       遊戲伺服器核心版本: " + Config.VER + " " + Config.SRCVER
				+ "\n\r\n\r  Server Verion: " + Config.SVer
				+ "\n\r       Cache Verion: " + Config.CVer
				+ "\n\r       Auth Verion: " + Config.AVer
				+ "\n\r       Npc Verion: " + Config.NVer
				+ "\n\r\n\r       主機位置: " + Config.GAME_SERVER_HOST_NAME
				+ "\n\r       監聽端口: " + Config.GAME_SERVER_PORT
				+ "\n\r\n\r       伺服器作業系統: " + osname
				+ "\n\r       伺服器使用者: " + username
				//+ "\n\r       使用者名稱資料庫: " + ConfigSQL.DB_URL2_LOGIN
				//+ "\n\r       伺服器檔案資料庫: " + ConfigSQL.DB_URL2
				+ "\n\r       綁定登入器設置: " + Config.LOGINS_TO_AUTOENTICATION
				+ "\n\r--------------------------------------------------"
				+ "\n\r       伺服器由[伊薇JAVA技術團隊]學術研究修改"
				+ "\n\r       遊戲天堂技術 https://tgame.org/"
				+ "\n\r--------------------------------------------------";
			_log.info(ver);

			// 啟動視窗命令接收器
			CmdEcho cmdEcho = new CmdEcho(timer.get());
			cmdEcho.runCmd();
		}
	}
}
