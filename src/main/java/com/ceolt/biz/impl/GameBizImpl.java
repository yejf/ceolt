package com.ceolt.biz.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ceolt.biz.IGameBiz;
import com.ceolt.dao.IBaseTermDao;
import com.ceolt.entity.Vocabulary;
import com.ceolt.entity.Word;
import com.ceolt.exception.NoHistoryException;
import com.ceolt.factory.DaoFactory;
import com.ceolt.util.StringUtil;

/**************************************
 * 
 * @description　针对中英文互答环节的业务操作提供数据支撑 
 * @author yejf
 * @date 2013-7-4 上午9:44:13
 * @version jdk1.6
 *
 */
public class GameBizImpl implements IGameBiz {
	
	/** 依赖于 dao　接口*/
	private IBaseTermDao dao;
	
	//构造方法
	public GameBizImpl() {
		//通过工厂来初始化dao 属性
		this.dao = DaoFactory.getDao();
	}
	
	/****************************************
	 * 提示英文，回答中文　
	 * 获取一个由所有单词和词汇组成的MAP,　其中key是英文，　value是中文
	 * @return 
	 */
	public Map<String, String> en2cn() {
		// TODO Auto-generated method stub
		//1.创建一个集合，来存放所有的单词和词汇组成的　键值对
		Map<String, String> en2cnMap = new HashMap<String, String>();
		//2.通过dao 获取所有的单词数据
		Set<Word> words = dao.loadWords();
		//2.1　迭代此Set集合，把所有的单词英文、中文取出来，存放到 en2cnMap中
		for(Word w : words) {
			en2cnMap.put(w.getEn(), StringUtil.toString(w.getCn()));
		}
		//3.通过dao获取所有的词汇数据
		Set<Vocabulary> vs = dao.loadVocabularies();
		//3.1 迭代此Set集合，把所有的词汇英文、中文取出来，存放到 en2cnMap中
		for(Vocabulary v : vs) {
			en2cnMap.put(v.getEn(), StringUtil.toString(v.getCn()));
		}
		//返回
		return en2cnMap;
	}

	/***********************************
	 * 提示中文，回答英文
	 * 获取一个由所有单词和词汇组成的MAP,　其中key是中文[把字符串数组，转换成字符串]，　value是英文
	 * 注：
	 * 此处合并集合时会存在一个问题，就是两个英文单词的中文意思是一样的，比如：iterate 和 iteration 都是　迭代　的意思
	 * 那么存放时，以中文为key,则会造成覆盖，也就是产此 map中,以  迭代 为key 时， value只有一个 iteration或 iterate
	 * 此处的代码实现，没有考虑这种情况。 [请了解]
	 * @return
	 */
	public Map<String, String> cn2en() {
		// TODO Auto-generated method stub
		//1. 创建一个Map来存放数据
		Map<String, String> cn2enMap = new HashMap<String, String>();
		//2. 通过Dao获取所有的单词数据
		Set<Word> words = dao.loadWords();
		//2.1 迭代整个words,取出每个单词的中、英文
		for(Word w : words) {
			cn2enMap.put(StringUtil.toString(w.getCn()), w.getEn());
		}
		//3.通过Dao获取所有的词汇数据
		Set<Vocabulary> vs = dao.loadVocabularies();
		//3.1 迭代整个 vs，取出每个词汇的数据
		for(Vocabulary v : vs) {
			cn2enMap.put(StringUtil.toString(v.getCn()), v.getEn());
		}
		
		//返回
		return cn2enMap;
	}

	/******************************
	 * 把暂时还没有回答的数据持久到文件中，以便下次来继续 Game
	 * @param map  待持化的数据
	 * @param path　持久化的文件路径
	 */
	public void writeToFile(Map<String, String> map, String path) {
		// TODO Auto-generated method stub
		File historyDir = new File(HISTROY_DIR);
		if(!historyDir.exists()) {
			//如果不存在此目录，则创建
			historyDir.mkdir(); 
		}
		//创建需要持久化的文件, 存放在　datas/history 目录中
		File f = new File(historyDir, path);
		ObjectOutputStream oos = null;
		
		try {
			oos = new ObjectOutputStream(new FileOutputStream(f));
			//写入到文件中
			oos.writeObject(map);
			System.out.println("保存历史记录成功.");
			
		} catch (IOException e) {
			System.out.println("持久化失败.");
			e.printStackTrace();
		} finally {
			if(oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

	/**************************************
	 * 读取历史记录数据
	 * @param path 文件所在的位置
	 */
	public Map<String, String> readFromFile(String path)  throws NoHistoryException {
		// TODO Auto-generated method stub
		File historyDir = new File(HISTROY_DIR);
		if(!historyDir.exists()) {
			throw new NoHistoryException("无历史记录", historyDir.getAbsolutePath());
		}
		File f = new File(historyDir, path);
		if(!f.exists()) {
			throw new NoHistoryException("无历史记录", historyDir.getAbsolutePath());
		}
		//读取数据
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(f));
			@SuppressWarnings("unchecked")
			Map<String, String> map = (Map<String,String>)ois.readObject();
			//返回
			return map;
			
		} catch (Exception e) {
			System.out.println("读取历史记录失败.");
			e.printStackTrace();
		}  finally {
			if(ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}

}
