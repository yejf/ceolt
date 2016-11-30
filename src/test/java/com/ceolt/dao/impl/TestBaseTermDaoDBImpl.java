package com.ceolt.dao.impl;

import java.util.Set;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.ceolt.util.Loader;
import com.ceolt.dao.IBaseTermDao;
import com.ceolt.entity.Vocabulary;
import com.ceolt.entity.Word;


/*************************
 * 测试类
 * @description 
 * @author yejf
 * @date 2013-7-3 下午1:30:48
 * @version jdk1.6
 *
 */
public class TestBaseTermDaoDBImpl {

	private IBaseTermDao dao;
	
	@Before
	public void init(){
		this.dao = new BaseTermDaoFileImpl();
	}

	@Test
	@Ignore
	public void testSaveWords() {
		Set<Word> words = Loader.loadWords();
		dao.saveWords(words);
		System.out.println("-- 持久化Word成功");
	}

	@Test
	@Ignore
	public void testSaveVocabularies() {
		Set<Vocabulary> vocabularies = Loader.loadVocabularies();
		dao.saveVocabularies(vocabularies);
		System.out.println("-- 持久化Vocabulary成功.");
	}

	@Test
	public void testLoadWords(){
		Set<Word> words = dao.loadWords();
		System.out.println("共计："+words.size());
		for(Word w : words){
			System.out.println(w);
		}
	}
	
	@Test
	public void testLoadVocabularies(){
		Set<Vocabulary> vSet = dao.loadVocabularies();
		System.out.println("共计："+vSet.size());
		for(Vocabulary v : vSet) {
			System.out.println(v);
		}
	}
}
