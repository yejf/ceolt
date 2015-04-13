package com.ceolt.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import com.ceolt.entity.BaseTerm;
import com.ceolt.entity.Vocabulary;
import com.ceolt.entity.Word;

/***************************
 * 
 * @description 本工具类负责加载单词文件 
 * @author yejf
 * @date 2013-7-2 下午4:29:35
 * @version jdk1.6
 *
 */
public class Loader {
	
	public static void main(String[] args) {
		Set<Word> words = loadWords();
		for(BaseTerm  w : words){
			System.out.println(w);
		}
		System.out.println("共计单词："+words.size());
		System.out.println("========================");
		Set<Vocabulary> vocabularies = loadVocabularies();
		for(BaseTerm v : vocabularies){
			System.out.println(v);
		}
		System.out.println("共计词汇："+vocabularies.size());
	}

	private static final String WORD_FILE = "datas/ce-word.dat";
	private static final String VOCABULARY_FILE = "datas/ce-vocabulary.dat";
	
	/**********************************
	 * 本方法负责加载 datas/ce-word.dat 文件中的数据，并封装成 Word对象
	 * @return
	 */
	public static Set<Word> loadWords(){
		Set<Word> words = new TreeSet<Word>();
		File wordFile = new File(WORD_FILE);
		if(!wordFile.exists()){
			System.out.println("文件不存在: "+wordFile);
			return null;
		}
		//处理
		BufferedReader br = null;
		StringBuilder builder = new StringBuilder();
		try {
			br = new BufferedReader(new FileReader(wordFile));
			String line = null;
			while((line = br.readLine()) != null){
				//处理读到的每一行
				String[] temp = line.split("\\s+");
				String en = temp[0];
				builder.append(en.toLowerCase());
				builder.setCharAt(0, (char)(builder.charAt(0)-32));
				String cnTemp = temp[1].trim();
				String[] cn = cnTemp.split(":");
				//创建 Word 对象
				Word w = new Word(builder.toString(),cn);
				w.setCategory("计算机英语");
				builder.delete(0, builder.capacity());
				//添加到集合中
				words.add(w);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(br != null)
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		return words;
	}
	
	/******************************
	 * 本方法负责加载词汇
	 * @return
	 */
	public static Set<Vocabulary> loadVocabularies(){
		Set<Vocabulary> vocabularies = new TreeSet<Vocabulary>();
		BufferedReader br = null;
		File vocaFile = new File(VOCABULARY_FILE);
		if(!vocaFile.exists()){
			System.out.println("文件不存在: "+vocaFile);
			return null;
		}
		try {
			br = new BufferedReader(new FileReader(vocaFile));
			String line = null;
			while((line = br.readLine())  != null){
				//读到每一行
				String[] datas = line.split("#");
				//拿出每一部份
				String en = datas[0];
				String[] cn = datas[1].trim().split(":");
				//创建对象
				Vocabulary v = new Vocabulary();
				v.setEn(en);
				v.setCn(cn);
				v.setCategory("计算机英语");
				//判断是否存在简称
				if(datas.length == 3){
					String acronym = datas[2];
					v.setAcronym(acronym);
				}
				//添加到集合中
				vocabularies.add(v);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(br != null)
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		return vocabularies;
	}
	
}
