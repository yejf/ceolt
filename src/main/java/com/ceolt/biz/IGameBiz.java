package com.ceolt.biz;

import java.util.Map;

import com.ceolt.exception.NoHistoryException;

/******************************
 * 
 * @description  做游戏学习单词相关的业务数据
 * @author yejf
 * @date 2013-7-1 下午4:22:44
 * @version jdk1.6
 *
 */
public interface IGameBiz {
	
	//定义存放历史文件名的常量
	String EN_TO_CN_BACKUP = "en2cn_history.dic";
	
	String CN_TO_EN_BACKUP = "cn2en_history.dic";
	
	//定义存放历史文件的路径
	String HISTROY_DIR = "datas/histroy";
	
	/*******************************
	 * 提示英文，回答中文
	 * @return
	 */
	Map<String, String> en2cn();
	
	/**************************
	 * 提示中文，回答英文 
	 * @return
	 */
	Map<String, String> cn2en();
	
	/*******************
	 * 为了参加趣味性，要对中英文互答时，可以把没有答过的项持久化到本地文件中
	 * 下次来使用时，自动从上次没有答过的开始，而不会从头再来。
	 * 当然，也可以选择从头再来模式。
	 * @param map
	 * @param path
	 */
	void writeToFile(Map<String, String> map, String path);
	
	/************************
	 * 从指定文件中读出之前持久化的Map对象,以便接着上次没有答完的开始。
	 * @param path
	 * @return
	 */
	Map<String, String> readFromFile(String path) throws NoHistoryException;
	
}
