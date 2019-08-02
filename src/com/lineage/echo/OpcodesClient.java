package com.lineage.echo;

import com.lineage.server.serverpackets.OpcodesServer;

/**
 * 服務器封包編組設置 380C_TW
 * 
 * @author dexc
 * @version TW13081901
 */
public class OpcodesClient {
	
	public OpcodesClient() {
		
	}
	
	protected static final int _seed = 0x70254d0a;//Lin.ver TW13081901 3.8
	
	protected static final byte[] _firstPacket = { // 封包輸出不需要演算
		(byte) 0x12, // 全部封包長度
		(byte) 0x00,
		// 改版時不需要變動以上2個BYTE
		(byte) OpcodesServer.S_OPCODE_INITOPCODE, // 初始化封包
		
		// 3.80
		(byte) 0x0a,(byte) 0x4d,(byte) 0x25,(byte) 0x70,
		(byte) 0x9d, (byte) 0xd1, (byte) 0xd6, (byte) 0x7a, 
		(byte) 0xf4, (byte) 0x62, (byte) 0xe7, (byte) 0xa0, 
		(byte) 0x66, (byte) 0x02, (byte) 0xfa	};
	
	// 客戶端封包

	/** 3.80C Client Packet  */
	/**
	 * 要求接收伺服器版本
	 */
	public static final int C_OPCODE_CLIENTVERSION = 14;

	/**
	 * 要求自動登入伺服器
	 */
	public static final int C_OPCODE_AUTO = 210;


	/**
	 * 要求離開遊戲
	 */
	public static final int C_OPCODE_QUITGAME = 122;

	/**
	 * 視窗失焦 & 請求傳送位置
	 */
	public static final int C_OPCODE_WINDOWS = 254;
	
	// XXX 攻擊相關
	/**
	 * 要求角色攻擊
	 */
	public static final int C_OPCODE_ATTACK = 229;

	/**
	 * 要求使用遠距武器
	 */
	public static final int C_OPCODE_ARROWATTACK = 123;

	/**
	 * 要求使用技能
	 */
	public static final int C_OPCODE_USESKILL = 6;

	/**
	 * 要求決鬥
	 */
	public static final int C_OPCODE_FIGHT = 5;

	// XXX 動作相關

	/**
	 * 要求改變角色面向
	 */
	public static final int C_OPCODE_CHANGEHEADING = 225;

	/**
	 * 要求丟棄物品(丟棄置地面)
	 */
	public static final int C_OPCODE_DROPITEM = 25;

	/**
	 * 要求撿取物品
	 */
	public static final int C_OPCODE_PICKUPITEM = 112;

	/**
	 * 要求角色表情動作
	 */
	public static final int C_OPCODE_EXTCOMMAND = 120;

	/**
	 * 要求門的控制/寶箱的開啟
	 */
	public static final int C_OPCODE_DOOR = 41;

	// XXX 移動相關

	/**
	 * 要求角色移動
	 */
	public static final int C_OPCODE_MOVECHAR = 29;

	/**
	 * 要求座標傳送(洞穴口)
	 */
	public static final int C_OPCODE_ENTERPORTAL = 219;

	/**
	 * 要求更新周圍物件(傳送後)
	 */
	public static final int C_OPCODE_TELEPORT = 552;
	
	/**
	 * 要求更新周圍物件(座標點/洞穴點切換進出後)
	 */
	public static final int C_OPCODE_TELEPORT2 = 246;
	

	/**
	 * 要求取消釣魚
	 */
	public static final int C_OPCODE_FISHCLICK = 62;

	// XXX 其它
	
	/**
	 * 要求變更與使用倉庫密碼
	 */
	public static final int C_OPCODE_PWD = 13;

	/**
	 * 要求進入遊戲
	 */
	public static final int C_OPCODE_LOGINTOSERVER = 137;

	/**
	 * 要求創造角色
	 */
	public static final int C_OPCODE_NEWCHAR = 84;

	/**
	 * 要求切換角色
	 */
	public static final int C_OPCODE_CHANGECHAR = 7;

	/**
	 * 要求角色刪除
	 */
	public static final int C_OPCODE_DELETECHAR = 162;

	/**
	 * 要求人物重設
	 */
	public static final int C_OPCODE_CHARRESET = 98;

	/**
	 * 要求死亡後重新開始
	 */
	public static final int C_OPCODE_RESTART = 177;

	/**
	 * 要求執行線上人物列表命令(GM管理選單)
	 */
	public static final int C_OPCODE_CALL = 185;

	/**
	 * 要求紀錄快速鍵
	 */
	public static final int C_OPCODE_CHARACTERCONFIG = 244;

	/**
	 * 要求增加記憶座標
	 */
	public static final int C_OPCODE_BOOKMARK = 165;

	/**
	 * 要求刪除記憶座標
	 */
	public static final int C_OPCODE_BOOKMARKDELETE = 3;

	/**
	 * 要求變更領地稅率
	 */
	public static final int C_OPCODE_TAXRATE = 19;

	/**
	 * 城堡寶庫(要求領出資金)
	 */
	public static final int C_OPCODE_DRAWAL = 44;

	/**
	 * 城堡寶庫(要求存入資金)
	 */
	public static final int C_OPCODE_DEPOSIT = 56;

	/**
	 * 要求維修物品清單
	 */
	public static final int C_OPCODE_FIX_WEAPON_LIST = 118;

	/**
	 * 要求物品維修
	 */
	public static final int C_OPCODE_SELECTLIST = 20;

	/**
	 * 要求使用物品
	 */
	public static final int C_OPCODE_USEITEM = 164;

	/**
	 * 要求給予物品
	 */
	public static final int C_OPCODE_GIVEITEM = 45;

	/**
	 * 要求刪除物品
	 */
	public static final int C_OPCODE_DELETEINVENTORYITEM = 138;

	/**
	 * 要求使用信件系統
	 */
	public static final int C_OPCODE_MAIL = 87;

	/**
	 * 要求寵物回報選單
	 */
	public static final int C_OPCODE_PETMENU = 103;
	
	/** 
	 * 要求攻擊指定物件(寵物&召喚) 
	 */
	public static final int C_OPCODE_SELECTTARGET = 223;

	/**
	 * 要求使用寵物道具
	 */
	public static final int C_OPCODE_USEPETITEM = 104;

	/**
	 * 要求查詢PK次數(checkpk)
	 */
	public static final int C_OPCODE_CHECKPK = 51;
	
	// XXX 佈告欄相關

	/**
	 * 要求讀取 公佈欄/ 拍賣公告 訊息列表
	 */
	public static final int C_OPCODE_BOARD = 10;

	/**
	 * 要求讀取 公佈欄內容
	 */
	public static final int C_OPCODE_BOARDREAD = 114;

	/**
	 * 要求刪除 公佈欄訊息
	 */
	public static final int C_OPCODE_BOARDDELETE = 153;

	/**
	 * 要求加入 公佈欄訊息
	 */
	public static final int C_OPCODE_BOARDWRITE = 141;

	/**
	 * 要求下一頁 公佈欄訊息
	 */
	public static final int C_OPCODE_BOARDBACK = 23;

	// XXX 人際關係相關

	/**
	 * 要求脫離隊伍
	 */
	public static final int C_OPCODE_LEAVEPARTY = 33;

	/**
	 * 要求踢出隊伍
	 */
	public static final int C_OPCODE_BANPARTY = 255;

	/**
	 * 要求隊伍名單
	 */
	public static final int C_OPCODE_PARTY = 43;

	/**
	 * 要求邀請加入隊伍(要求創立隊伍)
	 */
	public static final int C_OPCODE_CREATEPARTY = 230;

	/**
	 * 要求隊伍對話控制(命令/chatparty)
	 */
	public static final int C_OPCODE_CAHTPARTY = 199;

	/**
	 * 要求創立血盟
	 */
	public static final int C_OPCODE_CREATECLAN = 222;

	/**
	 * 要求加入血盟
	 */
	public static final int C_OPCODE_JOINCLAN = 194;

	/**
	 * 要求脫離血盟
	 */
	public static final int C_OPCODE_LEAVECLANE = 61;

	/**
	 * 要求查詢血盟成員
	 */
	public static final int C_OPCODE_PLEDGE = 68;

	/**
	 * 請求開起重新開始選單
	 */
	public static final int C_OPCODE_RESTARTMENU = 63;

	/**
	 * 要求驅逐人物離開血盟
	 */
	public static final int C_OPCODE_BANCLAN = 69;

	/**
	 * 要求上傳盟徽
	 */
	public static final int C_OPCODE_EMBLEM = 18;

	/**
	 * 要求宣戰/投降/休戰
	 */
	public static final int C_OPCODE_WAR = 227;

	/**
	 * 要求更新盟輝
	 */
	public static final int C_OPCODE_CLAN = 72;

	/**
	 * 要求角色建立封號(/title)
	 */
	public static final int C_OPCODE_TITLE = 90;

	/**
	 * 要求婚姻的執行(/propose)
	 */
	public static final int C_OPCODE_PROPOSE = 50;

	/**
	 * 要求查詢玩家(/who)
	 */
	public static final int C_OPCODE_WHO = 206;

	/**
	 * 要求新增朋友名單
	 */
	public static final int C_OPCODE_ADDBUDDY = 207;

	/**
	 * 要求查詢朋友名單
	 */
	public static final int C_OPCODE_BUDDYLIST = 4;

	/**
	 * 要求刪除朋友名單
	 */
	public static final int C_OPCODE_DELBUDDY = 202;

	// XXX 商店相關

	/**
	 * 要求交易(雙方交易)
	 */
	public static final int C_OPCODE_TRADE = 2;

	/**
	 * 要求增加交易物品(雙方交易)
	 */
	public static final int C_OPCODE_TRADEADDITEM = 37;

	/**
	 * 要求完成交易(雙方交易)
	 */
	public static final int C_OPCODE_TRADEADDOK = 71;

	/**
	 * 要求取消交易(雙方交易)
	 */
	public static final int C_OPCODE_TRADEADDCANCEL = 86;

	/**
	 * 要求角色商店清單(個人商店)
	 */
	public static final int C_OPCODE_PRIVATESHOPLIST = 47;

	/**
	 * 要求開設個人商店(個人商店)
	 */
	public static final int C_OPCODE_SHOP = 38;

	/**
	 * 要求完成學習魔法(金幣)
	 */
	public static final int C_OPCODE_SKILLBUYOK = 39;

	/**
	 * 要求學習魔法清單(金幣)
	 */
	public static final int C_OPCODE_SKILLBUY = 145;

	/**
	 * 要求完成學習魔法(材料)
	 */
	public static final int C_OPCODE_SKILLBUYOKITEM = -19;

	/**
	 * 要求學習魔法清單(材料)
	 */
	public static final int C_OPCODE_SKILLBUYITEM = 245;

	// XXX 對話相關

	/**
	 * 要求選取觀看頻道
	 */
	public static final int C_OPCODE_LOGINTOSERVEROK = 26;

	/**
	 * 要求使用一般聊天頻道
	 */
	public static final int C_OPCODE_CHAT = 136;

	/**
	 * 要求使用密語聊天頻道
	 */
	public static final int C_OPCODE_CHATWHISPER = 184;

	/**
	 * 要求使用廣播聊天頻道
	 */
	public static final int C_OPCODE_CHATGLOBAL = 40;

	/**
	 * 要求使用拒絕名單(開啟指定人物訊息)
	 */
	public static final int C_OPCODE_EXCLUDE = 171;

	/**
	 * 要求物件對話視窗
	 */
	public static final int C_OPCODE_NPCTALK = 34;

	/**
	 * 要求物件對話視窗結果
	 */
	public static final int C_OPCODE_NPCACTION = 125;

	/**
	 * 要求物件對話視窗數量選取結果
	 */
	public static final int C_OPCODE_AMOUNT = 11;

	/**
	 * 要求列表物品取得
	 */
	public static final int C_OPCODE_RESULT = 161;

	/**
	 * 要求點選項目的結果(Y/N)
	 */
	public static final int C_OPCODE_ATTR = 121;

	// XXX 固定時間封包
	
	/**
	 * 要求更新時間
	 */
	public static final int C_OPCODE_KEEPALIVE = 95;

	// XXX 未處理之部分(不具有操作碼)

	
	/**
	 * 要求退出鬼魂(觀看模式)
	 */
	public static final int C_OPCODE_EXIT_GHOST = 173;
	
	/**
	 * 要求下船
	 */
	public static final int C_OPCODE_SHIP = 231;

	/**
	 * 要求管理城堡治安
	 */
	public static final int C_OPCODE_CASTLESECURITY = 128;
	
	/**
	 * 血盟推薦
	 */
	public static final int C_OPCODE_CLAN_RECOMMEND = 76;	

	/**
	 * 要求座標異常重整 
	 */
	public static final int C_OPCODE_MOVELOCK = 52;
	

	public static final int C_OPCODE_PLEDGECONTENT = 78; // 請求寫入血盟查詢名單內容
	public static final int C_OPCODE_CLANATTENTION = 129; // 請求使用血盟注視
	public static final int C_OPCODE_MSG = 253; //要求簡訊服務 
	
	//TODO 已經不使用的封包
	public static final int C_OPCODE_CNITEM = -13; //要求提取天寶
	public static final int C_OPCODE_SHOPX2 = -14; //要求確認未知購物清單2
	public static final int C_OPCODE_NEWACC = -15;//要求新增帳號
	public static final int C_OPCODE_LOGINPACKET = -1; //要求登入伺服器 (已不使用)
	public static final int C_OPCODE_RETURNTOLOGIN = -2; //要求回到登入畫面 (已不使用)
	public static final int C_OPCODE_COMMONCLICK = -3; //要求顯示人物列表(公告視窗後) (已不使用)

	public static final int C_OPCODE_SETCASTLESECURITY = -5; //要求設置城內治安管理OK(已不使用)
	public static final int C_OPCODE_CHANGEWARTIME = -6; //要求決定下次圍城時間(官方已取消使用)
	public static final int C_OPCODE_SELECTWARTIME = -7; //要求決定圍城時間OK
	public static final int C_OPCODE_HIRESOLDIER = -8; // 僱請傭兵(購買傭兵完成)
	public static final int C_OPCODE_PUTSOLDIER = -9; //要求配置已僱用的士兵
	public static final int C_OPCODE_PUTHIRESOLDIER = -10; //要求配置已僱用的士兵OK
	public static final int C_OPCODE_PUTBOWSOLDIER = -11; //要求配置城牆上的弓箭手OK
	public static final int C_OPCODE_COMMONINFO = 16; //要求進入遊戲(確定服務器登入訊息)
	
}
