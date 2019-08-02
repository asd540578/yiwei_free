package com.lineage;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBLogin implements Database {

	private static final Log _log = LogFactory.getLog(DBLogin.class);
	private static DBLogin _instance;
	private ComboPooledDataSource _source;
	private String _database;

	public static DBLogin get() {
		return _instance;
	}

	@Override
	public String get_db_name() {
		return this._database;
	}

	@Override
	public void setDatabase(final String driver, final String confition, final String username, final String password,
			final String  url ,final String  database) {
		_instance = this;
		
		try {
			
			this._database = database;

			this._source = new ComboPooledDataSource();
			this._source.setDriverClass(driver);
			this._source.setJdbcUrl(url + database + confition);
			this._source.setUser(username);
			this._source.setPassword(password);

			this._source.getConnection().close();
		} catch (SQLException e) {
			_log.fatal("資料庫讀取錯誤!", e);
		} catch (Exception e) {
			_log.fatal("資料庫讀取錯誤!", e);
		}
	}

	/**
	 * 關閉連線
	 */
	@Override
	public void shutdown() {
		try {
			this._source.close();
		} catch (Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
		try {
			this._source = null;
		} catch (Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	/**
	 * 請求連線
	 */
	@Override
	public Connection getConnection() {
		Connection con = null;
		while (con == null) {
			try {
				con = this._source.getConnection();
			} catch (SQLException e) {
				_log.error(e.getLocalizedMessage(), e);
			}
		}
		return con;
	}

}
