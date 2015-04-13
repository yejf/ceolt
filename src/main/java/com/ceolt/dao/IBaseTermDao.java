package com.ceolt.dao;

import java.util.Set;

import com.ceolt.entity.Vocabulary;
import com.ceolt.entity.Word;

/***************************************
 * 
 * @description 获取整个词汇库数据接口 
 * @author yejf
 * @date 2013-6-28 下午10:31:57
 * @version jdk1.6
 *
 */
public interface IBaseTermDao {

	//存放单词的文件
	String word_file = "datas/dic/w.dic";
	//存放词汇的文件
	final String vocabulary_file = "datas/dic/v.dic";
		
	/*********************************
	 * 加载整个词汇库进内存，并使用map存储
	 * key为英文单词、value为整个词汇或单词
	 * @return 
	 */
	Set<Word> loadWords();

	/*********************************
	 * 加载整个词汇库进内存，并使用map存储
	 * key为英文单词、value为整个词汇或单词
	 * @return
	 */
	Set<Vocabulary> loadVocabularies();
	
	/***************************
	 * 持久化单词库:
	 * 要保证 原始单词库中不含有待加入的单词，否则，把原始单词库中有的单词从中删除
	 * @param bases
	 */
	void saveWords(Set<Word> words);
	
	/***************************
	 * 持久化词汇库:
	 * 要保证 原始词汇库中不含有待加入的词汇，否则，把原始词汇中有的词汇从中删除
	 * @param bases
	 */
	void saveVocabularies(Set<Vocabulary> vocabularies);
	
}

