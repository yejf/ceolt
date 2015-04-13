package com.ceolt.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.ceolt.util.DateUtil;

/******************************************
 * 
 * @description 记录了每一次测试的数据 
 * @author yejf
 * @date 2013-7-5 上午9:46:26
 * @version jdk1.6
 *
 */
public class TestRecord implements Serializable, Comparable<TestRecord> {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4595665985752346333L;

	//存放了本次测试的数据
	private List<BaseTerm> datas;
	
	private Date testDate; //测试开始时间
	
	private long seconds ; //测试时长, 以秒为单位
	
	private int correct; //答正的个数
	
	private int error; //答错的个数
	
	private int total; //本次试题的总数，也就是 datas.size();
	
	private int count; // 实际测试数量，　如果测试完，它的值与　total是一样的。
	
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public TestRecord() {
		// TODO Auto-generated constructor stub
	}

	public TestRecord(List<BaseTerm> datas, Date testDate) {
		super();
		this.datas = datas;
		this.testDate = testDate;
	}

	public List<BaseTerm> getDatas() {
		return datas;
	}

	public void setDatas(List<BaseTerm> datas) {
		this.datas = datas;
	}

	public Date getTestDate() {
		return testDate;
	}

	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}

	public long getSeconds() {
		return seconds;
	}

	public void setSeconds(long seconds) {
		this.seconds = seconds;
	}

	public int getCorrect() {
		return correct;
	}

	public void setCorrect(int correct) {
		this.correct = correct;
	}

	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("测试时间：").append(DateUtil.format(testDate)).append(" , 测试时长：").append(DateUtil.convertSeconds(seconds)).append("\n");
		builder.append("测数总题数：").append(total).append(" , 完成题数：").append(count).append("\n");
		builder.append("正确个数：").append(correct).append(" , 错误个数：").append(error).append("\n");
		
		builder.append("本次测试数据如下：\n");
		for(int i=0;i<datas.size();i++) {
			BaseTerm bt = datas.get(i);
			builder.append((i+1)).append(". ").append(bt.getEn());
			if(i != datas.size()-1) {
				builder.append("\t");
			}
			if((i + 1) % 5 == 0) {
				builder.append("\n");
			}
		}
		System.out.println("————————————————————————————————————");
		builder.append("\n");
		
		return builder.toString();
	}

	/*****************************
	 * 按测试时间降序排序
	 */
	public int compareTo(TestRecord o) {
		// TODO Auto-generated method stub
		return o.testDate.compareTo(testDate);
	}
}
