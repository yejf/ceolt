package com.ceolt.dao.impl;

import java.util.Set;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.ceolt.dao.IBaseTermDao;
import com.ceolt.entity.Vocabulary;
import com.ceolt.entity.Word;
import com.ceolt.util.Loader;

@Ignore
public class TestBaseTermFileImple {

	private IBaseTermDao dao ;
	
	@Before
	public void init(){
		this.dao = new BaseTermDaoFileImpl();
	}
	
	@Test
	public void testLoadWords() {
		Set<Word> words = dao.loadWords();
		System.out.println("共计："+words.size());
		for(Word w : words){
			System.out.println(w);
		}
	}

	@Test
	public void testLoadVocabularies() {
		Set<Vocabulary> vSet = dao.loadVocabularies();
		System.out.println("共计："+vSet.size());
		for(Vocabulary v : vSet) {
			System.out.println(v);
		}
	}

	@Test
	@Ignore
	public void testSaveWords() {
		Set<Word> words = Loader.loadWords();
		dao.saveWords(words);
	}

	@Test
	@Ignore
	public void testSaveVocabularies() {
		Set<Vocabulary> vs = Loader.loadVocabularies();
		dao.saveVocabularies(vs);
	}

}
