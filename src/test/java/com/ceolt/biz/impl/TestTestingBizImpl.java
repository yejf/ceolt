package com.ceolt.biz.impl;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.ceolt.biz.ITestingBiz;
import com.ceolt.entity.BaseTerm;
import com.ceolt.entity.TestRecord;
import com.ceolt.exception.NoTestRecordException;
import com.ceolt.util.DateUtil;

public class TestTestingBizImpl {

	private ITestingBiz biz;
	
	@Before
	public void init() {
		this.biz = new TestingBizImpl();
	}
	
	@Test
	public void testGetTestingData() {
		List<BaseTerm> datas = biz.getTestingData(50);
		assertEquals(datas.size(), 50);
		System.out.println(datas.size());
		//打印出来
		for(BaseTerm bt : datas) {
			System.out.println(bt);
		}
	}

	@Test
	public void testSaveTestRecord() {
		List<BaseTerm> datas = biz.getTestingData(20);
		Date d = DateUtil.getCurrentDate();
		//创建 TestRecord
		TestRecord record = new TestRecord(datas, d);
		//创建集合
//		List<TestRecord> records = new ArrayList<TestRecord>();
//		records.add(record);
		biz.saveTestRecord(record);
	}

	@Test
	public void testReviewTestRecord() {
		try {
			List<TestRecord> records = biz.reviewTestRecord();
			System.out.println(records.size());
			for(TestRecord r : records) {
				System.out.println(r);
			}
		} catch (NoTestRecordException e) {
			System.out.println(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
