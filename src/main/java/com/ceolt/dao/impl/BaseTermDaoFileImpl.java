package com.ceolt.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Set;

import com.ceolt.dao.IBaseTermDao;
import com.ceolt.entity.Vocabulary;
import com.ceolt.entity.Word;

/************************************
 * 文件实现的版本
 * @description 
 * @author yejf
 * @date 2013-7-3 下午3:19:34
 * @version jdk1.6
 *
 */
public class BaseTermDaoFileImpl implements IBaseTermDao {

	public Set<Word> loadWords() {
		File wordFile = new File(word_file);
		if(!wordFile.exists()){
			System.out.println("单词库文件找不到，请联系管理员提供单词库文件: "+wordFile);
			return null;
		}
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(wordFile));
			@SuppressWarnings("unchecked")
			Set<Word> wordSet = (Set<Word>)ois.readObject();
			return wordSet;
			
		} catch (IOException e) {
			System.out.println("单词文件损坏");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if(ois != null){
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}

	/*******************************
	 * 加载整个词汇库
	 */
	public Set<Vocabulary> loadVocabularies() {
		File vFile = new File(vocabulary_file);
		if(!vFile.exists()){
			System.out.println("词汇库文件找不到，请联系管理员提供词汇库文件: "+vFile);
			return null;
		}
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(vFile));
			@SuppressWarnings("unchecked")
			Set<Vocabulary> vSet = (Set<Vocabulary>)ois.readObject();
			return vSet;
		} catch (IOException e) {
			System.out.println("词汇文件损坏");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if(ois != null){
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/***********************
	 * 加载整个单词库
	 */
	public void saveWords(Set<Word> words) {
		ObjectOutputStream oos = null;
		File wordFile = new File(word_file);
		try {
			oos = new ObjectOutputStream(new FileOutputStream(wordFile));
			oos.writeObject(words);
			System.out.println("持久化到文件成功...");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(oos != null)
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
	}

	/************************
	 * 持久化词汇库到文件中, 文件的目录请查看 
	 * @param vocabularies　词汇库
	 */
	public void saveVocabularies(Set<Vocabulary> vocabularies) {
		ObjectOutputStream oos = null;
		File vocabularyFile = new File(vocabulary_file);
		try {
			oos = new ObjectOutputStream(new FileOutputStream(vocabularyFile));
			oos.writeObject(vocabularies);
			System.out.println("持久化到文件成功...");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(oos != null)
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

}
