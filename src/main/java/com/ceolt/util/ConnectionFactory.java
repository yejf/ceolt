package com.ceolt.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @描述 获取数据库连接的简单工厂 
 * @日期 May 29, 2013 3:49:42 PM
 * @作者 JSD1304
 */
public class ConnectionFactory {

	/****************
	 * 获取数据库连接的静态方法
	 * @return 数据库连接对象
	 * @throws SQLException SQL异常
	 */
	public static Connection getConnection()
								throws SQLException {
		
		String driver = DBConfig.getInstance().getValue("driver");
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
			//throw new SQLException("注册驱动失败",e);
			
		}
		
		String url = DBConfig.getInstance().getValue("url");
		String user = DBConfig.getInstance().getValue("user");
		String pwd = DBConfig.getInstance().getValue("password");
		
		return DriverManager.getConnection(url,user,pwd);
	}
}


