package com.ceolt.util;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @描述 本类负责读取 config/db.properties 属性文件
 *  并对外提供根据KEY来获取VALUE的方法
 *  本类是一个单例类
 * @日期 Jun 3, 2013 4:45:39 PM
 * @作者 JSD1304
 */
public class DBConfig {

	private static DBConfig instance = new DBConfig();
	
	private DBConfig(){
		init();
	}
	
	/**
	 * 初始化 Properties对象，并加载配置文件
	 */
	private void init() {
		props = new Properties();
		
		InputStream in = null;
		try {
			in = new FileInputStream(PATH);
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*******************
	 * 根据KEY 获取VALUE
	 * @param key
	 * @return
	 */
	public String getValue(String key){
		return props == null ? null : props.getProperty(key);
	}

	public static DBConfig getInstance(){
		return instance;
	}
	
	//添加一个 Properties 属性
	private static Properties props;
	private final static String PATH = "config/db.properties";
	
}
