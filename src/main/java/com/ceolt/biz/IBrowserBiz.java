package com.ceolt.biz;

import java.util.Map;
import java.util.Set;

import com.ceolt.entity.Vocabulary;
import com.ceolt.entity.Word;

/****************************
 * 
 * @description  此接口为 按字母顺序浏览基库提供业务操作实现
 * 							主要有浏览单词所需的数据和浏览词汇所需要的数据
 * @author yejf
 * @date 2013-7-1 下午3:41:33
 * @version jdk1.6
 *
 */
public interface IBrowserBiz {

	/********************************
	 * 获取所有 单词，此处的实现会选用 TreeSet, 以保证按首字母排序
	 * @return
	 */
	Set<Word> getAllWords();
	
	/*****************************
	 * 获取所有的词汇，此处会选用TreeSet,以保证按词汇缩写排序
	 * @return
	 */
	Set<Vocabulary> getAllVocabularies();
	
	/*************************
	 * 加载首字母与对应单词的映射
	 * @return
	 */
	Map<Character, Set<Word>> getFirstMap();
	
}
