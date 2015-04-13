package com.ceolt.biz.impl;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.ceolt.biz.IBroswerBiz;
import com.ceolt.entity.Word;

public class TestBroswerBizImpl {
	
	private IBroswerBiz biz;
	
	@Before
	public void init(){
		this.biz = new BroswerBizImpl();
	}

	@Test
	@Ignore
	public void testGetAllWords() {
		fail("Not yet implemented");
	}

	@Test
	@Ignore
	public void testGetAllVocabularies() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFirstMap() {
		Map<Character, Set<Word>> firstMap = biz.getFirstMap();
		Set<Character> firstSet = firstMap.keySet();
		System.out.println("共有字母：　"+firstSet.size());
		for(Character c : firstSet){
			Set<Word> wordSet = firstMap.get(c);
			System.out.println("=============="+c+" ["+wordSet.size()+"]");
			for(Word w : wordSet){
				System.out.println(w);
			}
			System.out.println("\n");
		}
	}

}
