package com.ceolt.biz.impl;

import java.util.TreeMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.ceolt.biz.IBroswerBiz;
import com.ceolt.dao.IBaseTermDao;
import com.ceolt.entity.Vocabulary;
import com.ceolt.entity.Word;
import com.ceolt.factory.DaoFactory;

/*********************************
 * 浏览单词或词汇的业务
 * @description 
 * @author yejf
 * @date 2013-7-3 下午4:14:41
 * @version jdk1.6
 *
 */
public class BroswerBizImpl implements IBroswerBiz {

	private IBaseTermDao dao ;
	
	public BroswerBizImpl(){
		this.dao = DaoFactory.getDao();
	}
	
	/*************************
	 * 获取所有的单词
	 */
	public Set<Word> getAllWords() {
		// TODO Auto-generated method stub
		return dao.loadWords();
	}

	public Set<Vocabulary> getAllVocabularies() {
		// TODO Auto-generated method stub
		return dao.loadVocabularies();
	}

	public Map<Character, Set<Word>> getFirstMap() {
		//获取所有的单词
		Set<Word> words = dao.loadWords();
		//创建一个Map，把单词按首字母划分
		Map<Character, Set<Word>> firstMap = new TreeMap<Character, Set<Word>>();
		//准备一个 wordSet
		Set<Word> wordSet = null;
		//迭代 words
		for(Word w : words){
			//判断此单词的首字母，在firstMap中是否存在
			if(firstMap.containsKey(w.getFirst())){
				//说明存在此　首字母，则取出对应的 Set
				wordSet = firstMap.get(w.getFirst());
			} else {
				//说明是第一次有此首字母，则创建一个 Set
				wordSet = new TreeSet<Word>();
				//存放到map中
				firstMap.put(w.getFirst(), wordSet);
			}
			
			//把单词添加到wordSet中
			wordSet.add(w);
		}
		return firstMap;
	}

}
