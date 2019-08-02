package com.lineage.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import com.lineage.DBLogin;
import com.lineage.DBCharacter;
import com.lineage.DBData;
import com.lineage.DBLog;

/**
 * 服務器資料庫設置
 *
 * @author dexc
 *
 */
public final class ConfigSQL {

	private static final String SQL_CONFIG = "./config/sql.properties";

	public static void load() throws ConfigErrorException {
		// _log.info("載入服務器資料庫設置!");
		final Properties set = new Properties();
		try {
			final InputStream is = new FileInputStream(new File(SQL_CONFIG));
			set.load(is);
			is.close();

			final String driver = set.getProperty("DATABASE_DRIVER", "com.mysql.jdbc.Driver");
			final String confition = set.getProperty("DATABASE_CONFITION", "?useUnicode=true&characterEncoding=utf8");
			final String username = set.getProperty("DATABASE_USERNAME", "root");
			final String password = set.getProperty("DATABASE_PASSWORD", "123456");

			DBLogin(set, driver, confition, username, password); // 登入資料表
			DBCharacter(set, driver, confition, username, password); // 角色資料表
			DBData(set, driver, confition, username, password); // 伺服器資料表
			DBLog(set, driver, confition, username, password); // 紀錄檔案
			
			
		} catch (final Exception e) {
			throw new ConfigErrorException("設置檔案遺失: " + SQL_CONFIG);

		} finally {
			set.clear();
		}
	}

	/**
	 * 登入資料
	 * 
	 * @param set       撈資料
	 * @param driver    資料庫連接驅動程式
	 * @param confition 連接附加條件
	 * @param username  資料庫使用者
	 * @param password  資料庫使用者密碼
	 */
	private static void DBLogin(final Properties set, final String driver, final String confition,
			final String username, final String password) {
		String url = set.getProperty("URL_LOGIN", "jdbc:mysql://localhost/");
		String database = set.getProperty("DATABASE_LOGIN", "l1jsrc");
		new DBLogin().setDatabase(driver, confition, username, password, url, database);
	}

	/**
	 * 角色資料
	 * 
	 * @param set       撈資料
	 * @param driver    資料庫連接驅動程式
	 * @param confition 連接附加條件
	 * @param username  資料庫使用者
	 * @param password  資料庫使用者密碼
	 */
	private static void DBCharacter(final Properties set, final String driver, final String confition,
			final String username, final String password) {
		String url = set.getProperty("URL_CHARACTER", "jdbc:mysql://localhost/");
		String database = set.getProperty("DATABASE_CHARACTER", "l1jsrc");
		new DBCharacter().setDatabase(driver, confition, username, password, url, database);
	}

	/**
	 * 遊戲資料
	 * 
	 * @param set       撈資料
	 * @param driver    資料庫連接驅動程式
	 * @param confition 連接附加條件
	 * @param username  資料庫使用者
	 * @param password  資料庫使用者密碼
	 */
	private static void DBData(final Properties set, final String driver, final String confition, final String username,
			final String password) {
		String url = set.getProperty("URL_DATA", "jdbc:mysql://localhost/");
		String database = set.getProperty("DATABASE_DATA", "l1jsrc");
		new DBData().setDatabase(driver, confition, username, password, url, database);
	}

	/**
	 * 遊戲紀錄檔
	 * 
	 * @param set       撈資料
	 * @param driver    資料庫連接驅動程式
	 * @param confition 連接附加條件
	 * @param username  資料庫使用者
	 * @param password  資料庫使用者密碼
	 */
	private static void DBLog(final Properties set, final String driver, final String confition, final String username,
			final String password) {

		String url = set.getProperty("URL_LOG", "jdbc:mysql://localhost/");
		String database = set.getProperty("DATABASE_LOG", "l1jsrc");
		new DBLog().setDatabase(driver, confition, username, password, url, database);
	}
}