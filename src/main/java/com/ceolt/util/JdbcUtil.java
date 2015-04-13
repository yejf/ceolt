package com.ceolt.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @描述 JDBC的工具类，提供一些JDBC通用的操作
 * @日期 May 29, 2013 3:58:30 PM
 * @作者 JSD1304
 */
public class JdbcUtil {

	/*************
	 * 释放JDBC相关的资源
	 * @param conn 连接对象
	 * @param stmt 语句对象
	 * @param rs   结果集对象
	 */
	public static void release(
						Connection conn, 
						Statement stmt, 
						ResultSet rs){
		try {
			if(rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(stmt != null)
				stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}


