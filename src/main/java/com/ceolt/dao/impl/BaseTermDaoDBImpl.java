package com.ceolt.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;
import java.util.TreeSet;

import com.ceolt.dao.IBaseTermDao;
import com.ceolt.entity.Vocabulary;
import com.ceolt.entity.Word;
import com.ceolt.util.ConnectionFactory;
import com.ceolt.util.JdbcUtil;
import com.ceolt.util.StringUtil;

/*************************************
 * 
 * @description 实现类 
 * @author yejf
 * @date 2013-6-28 下午10:37:26
 * @version jdk1.6
 *
 */
public class BaseTermDaoDBImpl implements IBaseTermDao {

	/*******************************
	 * 加载整个词汇库
	 * @return 
	 */
	public Set<Vocabulary> loadVocabularies() {
		 Set<Vocabulary> vocabularies = new TreeSet<Vocabulary>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = ConnectionFactory.getConnection();
			String sql = "select id,en,cn,category,acronym from tbl_vocabulary";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			//处理结果集
			while(rs.next()){
				//取值
				long id = rs.getLong(1);
				String en = rs.getString(2);
				String[] cn = StringUtil.toStringArray(rs.getString(3));
				String category = rs.getString(4);
				String acronym = rs.getString(5);
				
				Vocabulary v = new Vocabulary(id, en, cn, acronym);
				v.setCategory(category);
				
				vocabularies.add(v);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.release(conn, stmt, rs);
		}
		return vocabularies;
	}
	
	/*********************************
	 * 加载整个词汇库
	 * key 为英文，　Value为 BaseTerm
	 */
	public Set<Word> loadWords() {
		Set<Word> words = new TreeSet<Word>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = ConnectionFactory.getConnection();
			String sql = "select id,en,cn,category from tbl_word";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			//处理结果集
			while(rs.next()){
				//取值
				long id = rs.getLong(1);
				String en = rs.getString(2);
				String[] cn = StringUtil.toStringArray(rs.getString(3));
				String category = rs.getString(4);
				
				Word w = new Word(id,en,cn);
				w.setCategory(category);
				
				words.add(w);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.release(conn, stmt, rs);
		}
		return words;
	}

	/************************************
	 * 持久化单词库
	 * @param words
	 */
	public void saveWords(Set<Word> words) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int count = 0;
		try {
			conn = ConnectionFactory.getConnection();
			conn.setAutoCommit(false);
			String sql = "insert into tbl_word(id,en,cn,first,category) values(word_id.nextval,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			for(Word w: words){
					//绑定参数
				pstmt.setString(1, w.getEn());
				pstmt.setString(2, StringUtil.toString(w.getCn()));
				pstmt.setString(3, String.valueOf(w.getFirst()));
				pstmt.setString(4, w.getCategory());
					
				//添加到批处理
				pstmt.addBatch();
				count ++;
				if(count == 20){
					pstmt.executeBatch();
					count = 0;
				}
			}
			//执行最后的批处理
			pstmt.executeBatch();
			
			//提交事务
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			JdbcUtil.release(conn, pstmt, null);
		}
	}

	/*************************
	 * 持久化词汇库
	 * @param vocabularies
	 */
	public void saveVocabularies(Set<Vocabulary> vocabularies) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int count = 0;
		try {
			conn = ConnectionFactory.getConnection();
			conn.setAutoCommit(false);
			String sql = "insert into tbl_vocabulary(id,en,cn,acronym,category) values(vocabulary_id.nextval,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			for(Vocabulary v: vocabularies){
					//绑定参数
				pstmt.setString(1, v.getEn());
				pstmt.setString(2, StringUtil.toString(v.getCn()));
				pstmt.setString(3, v.getAcronym());
				pstmt.setString(4, v.getCategory());
					
				//添加到批处理
				pstmt.addBatch();
				count ++;
				if(count == 20){
					pstmt.executeBatch();
					count = 0;
				}
			}
			
			//执行最后的批处理
			pstmt.executeBatch();
			
			//提交事务
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			JdbcUtil.release(conn, pstmt, null);
		}
	}

}
