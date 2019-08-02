package com.lineage.server.serverpackets;

/**
 * 服務器封包編組設置 380C_TW
 * 
 * @author dexc
 * @version TW13081901
 *
 */
public class OpcodesServer {
	
	public OpcodesServer() {
	}

	// TODO 登入
	
	/**
	 * 初始化
	 */
	public static final int S_OPCODE_INITOPCODE = 150;
	
	/**
	 * 伺服器版本
	 */
	protected static final int S_OPCODE_SERVERVERSION = 139;
	
	/**
	 * 登入狀態
	 */
	protected static final int S_OPCODE_LOGINRESULT = 233;
	
	/**
	 * 公告視窗
	 */
	protected static final int S_OPCODE_COMMONNEWS = 48;
	
	/**
	 * 宣告進入遊戲
	 */
	protected static final int S_OPCODE_UNKNOWN1 = 223;
	
	/**
	 * 多封包盒子
	 */
	protected static final int S_OPCODE_PACKETBOX = 250;
	protected static final int S_OPCODE_ACTIVESPELLS = 250;
	protected static final int S_OPCODE_SKILLICONGFX = 250;
	protected static final int S_OPCODE_EVENT = 250; //血盟清單UI
	
	/**
	 * 立即中斷連線
	 */
	protected static final int S_OPCODE_DISCONNECT = 227;

	// TODO 頻道相關
	
	/**
	 * 廣播頻道
	 */
	public static final int S_OPCODE_GLOBALCHAT = 243;
	
	/**
	 * 一般頻道
	 */
	protected static final int S_OPCODE_NORMALCHAT = 81;
	
	/**
	 * 使用密語聊天頻道
	 */
	public static final int S_OPCODE_WHISPERCHAT = 73;
	
	/**
	 * NPC 對話(文字對話)
	 */
	protected static final int S_OPCODE_NPCSHOUT = 161;
	
	// TODO 角色
	
	/**
	 * 角色列表
	 */
	protected static final int S_OPCODE_CHARAMOUNT = 178;
	
	/**
	 * 角色列表資訊
	 */
	protected static final int S_OPCODE_CHARLIST = 93;
	
	/**
	 * 角色創造結果
	 */
	protected static final int S_OPCODE_NEWCHARWRONG = 98;
	
	/**
	 * 創造角色(新創)
	 */
	protected static final int S_OPCODE_NEWCHARPACK = 127;
	
	/**
	 * 角色移除(立即/非立即)
	 */
	protected static final int S_OPCODE_DETELECHAROK = 6;
	
	/**
	 * 角色資訊
	 */
	protected static final int S_OPCODE_OWNCHARSTATUS = 8;
	
	/**
	 * 角色能力狀態(力量,敏捷等)
	 */
	protected static final int S_OPCODE_OWNCHARSTATUS2 = 155;
	
	/**
	 * 角色盟徽
	 */
	protected static final int S_OPCODE_EMBLEM = 118;
	
	/**
	 * 角色封號
	 */
	protected static final int S_OPCODE_CHARTITLE = 183;
	
	/**
	 * 角色名稱變紫色
	 */
	protected static final int S_OPCODE_PINKNAME = 60;
	
	/**
	 * 角色皇冠
	 */
	protected static final int S_OPCODE_CASTLEMASTER = 69;
	
	/**
	 * 角色重置升級能力
	 */
	protected static final int S_OPCODE_CHARRESET = 64;

	//TODO 物件
	
	/**
	 * 物件封包
	 */
	protected static final int S_OPCODE_CHARPACK = 87;

	/**
	 * 物件刪除
	 */
	protected static final int S_OPCODE_REMOVE_OBJECT = 120;
	
	/**
	 * 物件血條
	 */
	protected static final int S_OPCODE_HPMETER = 237;

	/**
	 * 物件屬性(門)
	 */
	protected static final int S_OPCODE_ATTRIBUTE = 209;
	
	/**
	 * 物件復活
	 */
	protected static final int S_OPCODE_RESURRECTION = 85;
	
	// TODO 動畫控制
	
	/**
	 * 物件移動
	 */
	protected static final int S_OPCODE_MOVEOBJECT = 10;
	
	/**
	 * 物件攻擊(傷害力變更封包類型為 writeH(0x0000))
	 */
	protected static final int S_OPCODE_ATTACKPACKET = 30;
	
	/**
	 * 物件動作種類(長時間)
	 */
	protected static final int S_OPCODE_CHARVISUALUPDATE = 119;
	
	/**
	 * 物件動作種類(短時間)
	 */
	protected static final int S_OPCODE_DOACTIONGFX = 158;
	
	/**
	 * 產生動畫(物件)
	 */
	protected static final int S_OPCODE_SKILLSOUNDGFX = 55;
	
	/**
	 * 產生動畫(地點)
	 */
	protected static final int S_OPCODE_EFFECTLOCATION = 106;
	
	/**
	 * 範圍魔法
	 */
	protected static final int S_OPCODE_RANGESKILLS = 42;
	
	// TODO 訊息
	
	/**
	 * 郵件系統
	 */
	protected static final int S_OPCODE_MAIL = 186;
	
	/**
	 * 血盟戰爭訊息(編號,血盟名稱,目標血盟名稱)
	 */
	protected static final int S_OPCODE_WAR = 84;
	
	/**
	 * NPC對話視窗
	 */
	protected static final int S_OPCODE_SHOWHTML = 39;
	
	/**
	 * 選取物品數量
	 */
	protected static final int S_OPCODE_INPUTAMOUNT = 136;
	
	/**
	 * 伺服器訊息(行數/行數,附加字串)
	 */
	protected static final int S_OPCODE_SERVERMSG = 71;
	
	/**
	 * 選項(Yes/No)
	 */
	protected static final int S_OPCODE_YES_NO = 219;
	
	/**
	 * 物品鑑定資訊訊息
	 */
	protected static final int S_OPCODE_IDENTIFYDESC = 245;
	
	
	// TODO 屬性更新顯示
	
	/**
	 * 更新物件亮度
	 */
	protected static final int S_OPCODE_LIGHT = 40;
	
	/**
	 * 更新遊戲天氣
	 */
	protected static final int S_OPCODE_WEATHER = 115;
	
	/**
	 * 更新物件面向
	 */
	protected static final int S_OPCODE_CHANGEHEADING = 122;
	
	/**
	 * 更新物件名稱
	 */
	protected static final int S_OPCODE_CHANGENAME = 46;
	
	/**
	 * 更新HP顯示
	 */
	protected static final int S_OPCODE_HPUPDATE = 225;
	
	/**
	 * 更新MP顯示
	 */
	protected static final int S_OPCODE_MPUPDATE = 33;
	
	/**
	 * 更新角色所在的地圖
	 */
	protected static final int S_OPCODE_MAPID = 206;
	
	/**
	 * 更新遊戲時間
	 */
	protected static final int S_OPCODE_GAMETIME = 123;
	
	/**
	 * 更新經驗值
	 */
	protected static final int S_OPCODE_EXP = 113;
	
	/**
	 * 更新正義值
	 */
	protected static final int S_OPCODE_LAWFUL = 34;
	
	/**
	 * 更新魔攻與魔防
	 */
	protected static final int S_OPCODE_SPMR = 37;
	
	/**
	 * 更新角色防禦屬性
	 */
	protected static final int S_OPCODE_OWNCHARATTRDEF = 174;
	
	/**
	 * 更新血盟數據
	 */
	protected static final int S_OPCODE_UPDATECLANID = 72;
	
	// TODO 佈告欄
	
	/**
	 * 佈告欄列表
	 */
	protected static final int S_OPCODE_BOARD = 68;
	
	/**
	 * 佈告欄(訊息閱讀)
	 */
	protected static final int S_OPCODE_BOARDREAD = 148;
	
	/**
	 * 盟屋拍賣公告欄列表
	 */
	protected static final int S_OPCODE_HOUSELIST = 156;
	
	/**
	 * 血盟小屋地圖(地點)
	 */
	protected static final int S_OPCODE_HOUSEMAP = 187;
	
	// TODO 魔法效果
	
	/**
	 * 魔法效果:毒素
	 */
	protected static final int S_OPCODE_POISON = 165;
	
	/**
	 * 魔法效果:勇敢藥水纇
	 */
	protected static final int S_OPCODE_SKILLBRAVE = 67;
	
	/**
	 * 魔法效果:防禦
	 */
	protected static final int S_OPCODE_SKILLICONSHIELD = 216;
	
	/**
	 * 魔法效果:加速纇
	 */
	protected static final int S_OPCODE_SKILLHASTE = 255;
	
	/**
	 * 魔法效果:精準目標
	 */
	protected static final int S_OPCODE_TRUETARGET = 11;
	
	/**
	 * 魔法效果:水底呼吸
	 */
	protected static final int S_OPCODE_BLESSOFEVA = 126;
	
	/**
	 * 魔法效果:物件隱形
	 */
	protected static final int S_OPCODE_INVIS = 171;
	
	/**
	 * 魔法效果:操作混亂(醉酒)
	 */
	protected static final int S_OPCODE_LIQUOR = 103;
	
	/**
	 * 魔法效果:詛咒
	 */
	protected static final int S_OPCODE_PARALYSIS = 202;
	
	/**
	 * 魔法效果:敏捷提升
	 */
	protected static final int S_OPCODE_DEXUP = 188;
	
	/**
	 * 魔法效果:力量提升
	 */
	protected static final int S_OPCODE_STRUP = 166;
	
	/**
	 * 魔法效果:暗盲咒術
	 */
	protected static final int S_OPCODE_CURSEBLIND = 47;
	
	// TODO 清單
	
	/**
	 * 物品名單(背包)
	 */
	protected static final int S_OPCODE_INVLIST = 5;

	/**
	 * 更新物品顯示名稱(背包)
	 */
	protected static final int S_OPCODE_ITEMNAME = 100;
	
	/**
	 * 更新物品可使用數量(背包)
	 */
	protected static final int S_OPCODE_ITEMAMOUNT = 24;
	
	/**
	 * 物品增加(背包)
	 */
	protected static final int S_OPCODE_ADDITEM = 15;
	
	/**
	 * 物品刪除(背包)
	 */
	protected static final int S_OPCODE_DELETEINVENTORYITEM = 57;
	
	/**
	 * 物品色彩狀態(背包)
	 */
	protected static final int S_OPCODE_ITEMCOLOR = 240;
	
	/**
	 * 物品名單(倉庫)
	 */
	protected static final int S_OPCODE_SHOWRETRIEVELIST = 176;
	
	/**
	 * 損壞武器清單
	 */
	protected static final int S_OPCODE_SELECTLIST = 83;
	
	/**
	 * 角色座標名單
	 */
	protected static final int S_OPCODE_BOOKMARKS = 92;
	
	// TODO 交易
	
	/**
	 * NPC物品購買清單(人物賣出)
	 */
	protected static final int S_OPCODE_SHOWSHOPSELLLIST = 65;
	
	/**
	 * NPC物品販賣清單(人物買入)
	 */
	protected static final int S_OPCODE_SHOWSHOPBUYLIST = 70;
	
	/**
	 * 交易封包(雙方交易)
	 */
	protected static final int S_OPCODE_TRADE = 52;
	
	/**
	 * 交易狀態(雙方交易)
	 */
	protected static final int S_OPCODE_TRADESTATUS = 112;
	
	/**
	 * 交易增加物品(雙方交易)
	 */
	protected static final int S_OPCODE_TRADEADDITEM = 35;
	
	/**
	 * 交易商店清單(購買/賣出-個人商店)
	 */
	protected static final int S_OPCODE_PRIVATESHOPLIST = 140;
	
	// TODO 其它
	
	/**
	 * 傳送控制戒指
	 */
	protected static final int S_OPCODE_ABILITY = 36;
	
	/**
	 * 撥放音效
	 */
	protected static final int S_OPCODE_SOUND = 22;
	
	/**
	 * 傳送鎖定(NPC瞬間移動)
	 */
	protected static final int S_OPCODE_TELEPORT = 56;
	
	/**
	 * 傳送鎖定(洞穴點座標點)
	 */
	protected static final int S_OPCODE_TELEPORTLOCK = 241;
	
	/**
	 * 角色鎖定(座標異常重整)
	 */
	protected static final int S_OPCODE_CHARLOCK = 149;
	
	/**
	 * 選擇一個目標
	 */
	protected static final int S_OPCODE_SELECTTARGET = 236;
	
	/**
	 * 城堡寶庫(要求存入資金)
	 */
	protected static final int S_OPCODE_DEPOSIT = 4;
	
	/**
	 * 城堡寶庫(要求領出資金)
	 */
	protected static final int S_OPCODE_DRAWAL = 141;

	
	/**
	 * 稅收設定
	 */
	protected static final int S_OPCODE_TAXRATE = 185;
	
	
	/**
	 * 血盟推薦
	 */
	public static final int S_OPCODE_CLANMATCHING = 0; // 推薦血盟資訊更新
	
	// XXX 魔法
	
	/**
	 * 魔法購買清單(金幣)
	 */
	protected static final int S_OPCODE_SKILLBUY = 41;
	
	/**
	 * 魔法購買清單(材料)
	 */
	protected static final int S_OPCODE_SKILLBUYITEM = 41;
	
	/**
	 * 學習魔法材料不足
	 */
	protected static final int S_OPCODE_ITEMERROR = 197;
	
	/**
	 * 魔法清單(增加)
	 */
	protected static final int S_OPCODE_ADDSKILL = 164;
	
	/**
	 * 魔法清單(移除)
	 */
	protected static final int S_OPCODE_DELSKILL = 160;
	
	// TODO 其他
	
	/**
	 * NPC改變外型
	 */
	protected static final int S_OPCODE_NPCPOLY = 76;
	
	/**
	 * 更新物件外型
	 */
	protected static final int S_OPCODE_POLY = 76;
	
	
	/**
	 * 血盟注視
	 */
	public static final int S_OPCODE_CLANATTENTION = 200; 
	
	// XXX 未完成
	

	
	/**
	 * 配置城牆上的弓箭手列表(傭兵購買視窗)
	 */
	protected static final int S_OPCODE_PUTBOWSOLDIERLIST = 11;
	
	/**
	 * 傭兵配置清單
	 */
	protected static final int S_OPCODE_PUTSOLDIER = 117;
	
	/**
	 * 可配置排列傭兵數(HTML)(EX:雇用的總傭兵數:XX 可排列的傭兵數:XX )
	 */
	protected static final int S_OPCODE_PUTHIRESOLDIER = 13;

	/**
	 * Ping Time
	 */
	protected static final int S_OPCODE_PINGTIME = 88;
	
	/**
	 * 強制登出人物
	 */
	protected static final int S_OPCODE_CHAROUT = 17;
	
	/**
	 * 服務器登入訊息(使用string.tbl)
	 */
	protected static final int S_OPCODE_COMMONINFO = -88;
	
	/**
	 * 閱讀郵件(舊)
	 */
	protected static final int S_OPCODE_LETTER = -33;
	
	//XXX unknown
    //XXX 封包未新增
	
    /** 使用地圖道具. */
    public static final int S_OPCODE_USEMAP = 100;

    /** 
     * 更新現在的地圖 （水中）. 
     * */
    public static final int S_OPCODE_UNDERWATER = 206;

    /** 
     * 物品狀態更新
     *  */
    public static final int S_OPCODE_ITEMSTATUS = 24;
  
    /**
	 *	物件道具  地面物件
     * */
    public static final int S_OPCODE_DROPITEM = 87;
    
    /**
	 * 未知購物清單1
	 */
	protected static final int S_OPCODE_SHOPX1 = -0;
	
	/**
	 * 未知購物清單2
	 */
	protected static final int S_OPCODE_SHOPX2 = -71;
		
	/**
	 * 物理範圍攻擊
	 * Server op: 0000
	 */
	protected static final int S_OPCODE_ATTACKRANGE = -1;

	/**
	 * 圍城時間設定 (以不使用)
	 */
	protected static final int S_OPCODE_WARTIME = 231;
	
	/**
	 * 畫面中紅色訊息(登入來源)  (以不使用)
	 */
	protected static final int S_OPCODE_RED = 90;
	
	/**
	 * 畫面中紅色訊息 (以不使用)
	 */
	protected static final int S_OPCODE_BLUEMESSAGE = 105;
	
	/**
	 * 物件新增主人 (以不使用)
	 */
	protected static final int S_OPCODE_NEWMASTER = 88;
	
    /**
     *  寵物控制條  (以不使用)
     * */
    public static final int S_OPCODE_PETCTRL = 33;
    
	/**
	 * 僱請傭兵(傭兵購買視窗)
	 */
	protected static final int S_OPCODE_HIRESOLDIER = -1;
    
	/** 3.80 찾아야할 옵코드 **/
	public static final int S_OPCODE_DRAGONPERL = 31;
	public static final int S_OPCODE_SPOLY = 230; // 特別變身封包
	public static final int S_OPCODE_SYSMSG = 1000007; // 伺服器訊息

}
