package com.ceolt.factory;

import com.ceolt.dao.IBaseTermDao;
import com.ceolt.dao.impl.BaseTermDaoFileImpl;

/*****************************
 * 
 * @description　DAO实例产生工厂 
 * @author yejf
 * @date 2013-7-3 下午4:12:00
 * @version jdk1.6
 *
 */
public class DaoFactory {

	/*****************************
	 * 返回某个指定的DAO实现工厂，单机版采用　文件实现方式
	 * 网络版采用　DB实现方式
	 * @return
	 */
	public static IBaseTermDao getDao(){
		
		IBaseTermDao dao = new BaseTermDaoFileImpl();
		return dao;
		
	}

}
