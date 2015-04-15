package com.ceolt.biz.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.ceolt.biz.ITestingBiz;
import com.ceolt.dao.IBaseTermDao;
import com.ceolt.entity.BaseTerm;
import com.ceolt.entity.TestRecord;
import com.ceolt.entity.Vocabulary;
import com.ceolt.entity.Word;
import com.ceolt.exception.NoTestRecordException;
import com.ceolt.factory.DaoFactory;

/***********************************
 * 
 * @description 测试业务所需要擞 据操作
 * @author yejf
 * @date 2013-7-5 上午11:08:49
 * @version jdk1.6
 *
 */
public class TestingBizImpl implements ITestingBiz {

	private IBaseTermDao dao; 
	
	public TestingBizImpl() {
		//初始化dao对象
		this.dao = DaoFactory.getDao();
	}
	
	/**************************************
	 * 从所有总数据中随机挑选出需要的数据量
	 * @param capacity 数据量
	 */
	public List<BaseTerm> getTestingData(int capacity) {
		List<BaseTerm> allDatas = new ArrayList<BaseTerm>();
		//1.取出所有的 Word
		Set<Word> words = dao.loadWords();
		//1.1 把所有的words添加到 allDatas 中
		for(Word w : words) {
			allDatas.add(w);
		}
		//2.取出所有的 Vocabulary 
		Set<Vocabulary> vs = dao.loadVocabularies();
		//2.1 把所有的 Vocabulary 添加到 allDatas中
		for(Vocabulary v : vs) {
			allDatas.add(v);
		}
		//3. 打乱整个 List， 并从中取出随机取出 capacity 的数据
		Collections.shuffle(allDatas);
		//4. 随机获取起始位置
		int fromIndex = capacity / 2;
		int toIndex = fromIndex + capacity;
		/*List<BaseTerm> returnData =allDatas.subList(fromIndex, toIndex);
		return returnData;*/
		//由于 RandomAccessSubList 不支持序列化，所以，本处不能调用 subList方法
		List<BaseTerm> returnData  = new ArrayList<BaseTerm>();
		for(int i=fromIndex; i<toIndex;i++) {
			returnData.add(allDatas.get(i));
		}
		return returnData;
	}

	/**************************
	 * 持久化测试记录
	 * 1. 固定存放目录：datas/testing 目录中, 使用常量 TESTING_DIR 代表
	 * 2. 文件名使用常量  TESTING_RECORD_FILE 代表
	 * 操作步骤：
	 * 1. 首先，判断此文件是否存在，如果不存在，则说明第一次保写，则直接写入即可
	 * 2. 如果存在，则说明之前保存过，则：
	 *      2.1 首先要读出来，再把此集合中的TestRecord，添加到原来的集合中。
	 *      2.2 写到文件中
	 * @param record
	 */
	public void saveTestRecord(TestRecord record) {
		File dir = new File(TESTING_DIR);
		if(!dir.exists()) {
			dir.mkdir(); //创建此目录
		}
		//创建一个集合
		List<TestRecord> newRecords = null; 
		//创建记录文件
		File recordFile = new File(dir, TESTING_RECORD_FILE);
		//判断是否存在
		if(recordFile.exists()) {
			//说明之前已经有过历史记录,则直接读取出来
			try {
				newRecords = read(recordFile);
				//然后，把现在的集合中的数据合并到此集合中
				newRecords.add(record);
				//排一下序
				Collections.sort(newRecords);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			//说明不存在
			newRecords = new ArrayList<TestRecord>();
			newRecords.add(record);
		}
		
		//不存在或存在，都是要写文件
		try {
			write(recordFile, newRecords);
		} catch (Exception e) {
			System.out.println("测试记录写入失败.");
			e.printStackTrace();
		}
	}

	/**************************************
	 * 
	 * @param no 测试记录的文件编号，根据此编号就能定位到持久化文件，并读取文件中的内容
	 * @return
	 * @throws NoTestRecordException 如果没有此编号，则抛出此异常
	 */
	public List<TestRecord> reviewTestRecord() throws NoTestRecordException {
		File dir = new File(TESTING_DIR);
		if(!dir.exists()) {
			dir.mkdir(); //创建此目录
		}
		//创建记录文件
		File recordFile = new File(dir, TESTING_RECORD_FILE);
		//判断是否存在
		if(!recordFile.exists()) {
			throw new NoTestRecordException("你还没有过测试的历史记录.");
		}
		//读到对象
		try {
			return read(recordFile);
		} catch (Exception e) {
			System.out.println("读取数据失败...");
			e.printStackTrace();
		}
		return null;
	}

	/**************
	 * 读历史测试数据
	 * @param recordFile
	 * @return
	 * @throws Exception
	 */
	private List<TestRecord> read(File recordFile) throws Exception {
		//读到对象
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(recordFile));
			@SuppressWarnings("unchecked")
			List<TestRecord> records = (List<TestRecord>)ois.readObject();
			System.out.println("读取成功...");
			return records;
		} finally {
			if(ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/***********************************
	 * 
	 * @param recordFile
	 * @param records
	 * @throws Exception
	 */
	private void write(File recordFile, List<TestRecord> records) throws Exception {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(recordFile));
			//写对象
			oos.writeObject(records);
			System.out.println("保存成功.");
		} finally {
			if(oos != null) {
				oos.close();
			}
		}
	}
	
}
