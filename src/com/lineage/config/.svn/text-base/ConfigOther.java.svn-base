package com.lineage.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * 服務器活動設置
 *
 * @author dexc
 *
 */
public final class ConfigOther {

	public static boolean SPEED = false;// 啟用加速檢測

	public static double SPEED_TIME = 1.2D;// 允許速率範圍質

	public static boolean KILLRED = true;// 怪物是否主動攻擊紅人
	
	public static int RATE_XP_WHO = 1;

	public static boolean CLANDEL;// 允許盟組解散血盟

	public static boolean CLANTITLE;// 允許盟員自行建立封號

	public static int CLANCOUNT;// 自行建立血盟人數上限

	public static boolean LIGHT;// 啟用人物全時光照(true啟用 false關閉)

	public static boolean HPBAR;// 顯示怪物血條

	public static boolean SHOPINFO;// 一般商店是否顯示詳細資訊

	public static int HOMEHPR;// 血盟小屋HP恢復增加

	public static int HOMEMPR;// 血盟小屋MP恢復增加

	public static boolean WAR_DOLL;// 攻城旗幟內是否允許攜帶娃娃 true:允許 false:禁止

	public static int SET_GLOBAL;// 廣播扣除金幣或是飽食度(0:飽食度    1:金幣)

	public static int SET_GLOBAL_COUNT;// 廣播扣除質(set_global設置0:扣除飽食度量    set_global設置1:扣除金幣量)

	public static int SET_GLOBAL_TIME;// 廣播/買賣頻道間隔秒數

	// 戰鬥特化遭遇的守護等級
	// 設置等級以下角色 被超過10級以上的玩家攻擊而死亡時，不會失去經驗值，也不會掉落物品
	public static int ENCOUNTER_LV;

	private static final String LIANG = "./config/other.properties";

	public static void load() throws ConfigErrorException {
		final Properties set = new Properties();
		try {
			final InputStream is = new FileInputStream(new File(LIANG));
			set.load(is);
			is.close();

			SPEED = Boolean.parseBoolean(set.getProperty("speed", "false"));
			
			SPEED_TIME = Double.parseDouble(set.getProperty("speed_time", "1.2"));

			ENCOUNTER_LV = Integer.parseInt(set.getProperty("encounter_lv", "20"));

			KILLRED = Boolean.parseBoolean(set.getProperty("kill_red", "false"));

			RATE_XP_WHO = Integer.parseInt(set.getProperty("rate_xp_who", "1"));

			CLANDEL = Boolean.parseBoolean(set.getProperty("clanadel", "false"));

			CLANTITLE = Boolean.parseBoolean(set.getProperty("clanatitle", "false"));

			CLANCOUNT = Integer.parseInt(set.getProperty("clancount", "100"));

			// 啟用人物全時光照(true啟用 false關閉)
			LIGHT = Boolean.parseBoolean(set.getProperty("light", "false"));

			// 顯示怪物血條(true啟用 false關閉)
			HPBAR = Boolean.parseBoolean(set.getProperty("hpbar", "false"));
			
			SHOPINFO = Boolean.parseBoolean(set.getProperty("shopinfo", "false"));

			HOMEHPR = Integer.parseInt(set.getProperty("homehpr", "100"));

			HOMEMPR = Integer.parseInt(set.getProperty("homempr", "100"));

			SET_GLOBAL = Integer.parseInt(set.getProperty("set_global", "100"));

			SET_GLOBAL_COUNT = Integer.parseInt(set.getProperty("set_global_count", "100"));

			SET_GLOBAL_TIME = Integer.parseInt(set.getProperty("set_global_time", "5"));
			
			WAR_DOLL = Boolean.parseBoolean(set.getProperty("war_doll", "true"));

		} catch (final Exception e) {
			throw new ConfigErrorException("設置檔案遺失: " + LIANG);

		} finally {
			set.clear();
		}
	}
}