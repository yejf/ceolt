package com.ceolt.biz.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.ceolt.biz.IGameBiz;
import com.ceolt.exception.NoHistoryException;

public class TestGameBizImpl {

	private IGameBiz biz;
	
	@Before
	public void init() {
		this.biz = new GameBizImpl();
	}
	
	@Test
	public void testEn2cn() {
		Map<String, String> en2cnMap = biz.en2cn();
		//迭代输出查看
		iterateMap(en2cnMap);
	}

	private void iterateMap(Map<String, String> map) {
		// TODO Auto-generated method stub
		System.out.println("总量：　"+map.size());
		Set<Entry<String, String>> entrySet = map.entrySet();
		for(Entry<String, String> entry : entrySet) {
			String key = entry.getKey();
			String value = entry.getValue();
			//输出
			System.out.printf("%s => %s\n", key, value);
		}
	}

	@Test
	public void testCn2en() {
		Map<String, String> cn2enMap = biz.cn2en();
		//输出查看
		iterateMap(cn2enMap);
	}

	@Test
	public void testWriteToFile() {
		Map<String, String> en2cnMap = biz.en2cn();
		Map<String, String> backupMap = new HashMap<String, String>(en2cnMap);
		Set<String> enSet = en2cnMap.keySet();
		//
		int count = 0;
		for(String en : enSet) {
			System.out.println(en+" => "+en2cnMap.get(en));
			//从 backupMap　中称除
			backupMap.remove(en);
			count ++;
			if(count == 100) {
				break;
			}
		}
		//把剩下的持久化到文件中
		biz.writeToFile(backupMap, "en2cn_history.dic");
		
	}

	@Test
	public void testReadFromFile() {
		
		try {
			Map<String, String> history = biz.readFromFile("en2cn_history1.dic");
			iterateMap(history);
		} catch (NoHistoryException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println(e.getMessage()+"  => "+e.getHistoryFile());
		}
		
	}

}
