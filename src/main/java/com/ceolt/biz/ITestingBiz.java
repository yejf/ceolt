package com.ceolt.biz;

import java.util.List;

import com.ceolt.entity.BaseTerm;
import com.ceolt.entity.TestRecord;
import com.ceolt.exception.NoTestRecordException;

/***********************************
 * 
 * @description 在线测试小业务 
 * @author yejf
 * @date 2013-7-1 下午4:35:06
 * @version jdk1.6
 *
 */
public interface ITestingBiz {

	/** 存放测试记录的 历史数据目录*/
	String TESTING_DIR = "datas/testing";
	
	String TESTING_RECORD_FILE = "test_records.rec";
	
	/*****************************
	 * 获取测试数据
	 * @param capacity 数据量
	 * @return
	 */
	List<BaseTerm> getTestingData(int capacity);
	
	/*****************************
	 * 持久化测试记录, 每次都要先读取之前已经存在的历史记录，然后，把最近的测试记录，追加到此List中，再把这个List持久化。
	 * @param record
	 */
	void saveTestRecord(TestRecord records);
	
	/**************************************
	 * 加载所有的已保存测试记录，并返回整个 List 
	 * @return
	 * @throws NoTestRecordException 如果没有此编号，则抛出此异常
	 */
	List<TestRecord> reviewTestRecord() throws NoTestRecordException;
	
}
