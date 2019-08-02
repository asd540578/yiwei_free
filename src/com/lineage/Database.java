package com.lineage;

import java.sql.Connection;

/**
 * 資料連線 介面
 * @author Eugene
 *
 */
public abstract interface Database {

	public abstract String get_db_name();

	public abstract void setDatabase(final String driver ,final String  confition ,final String  username ,final String  password ,final String  url ,final String  database);

	public abstract void shutdown();

	public abstract Connection getConnection();
}
