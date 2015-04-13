package com.ceolt.entity;

import java.io.Serializable;

/***************
 * 
 * @description 基本术语类， 为扩展 Word和Vocabulary而来 
 * @author yejf
 * @date 2013-6-28 下午9:49:51
 * @version jdk1.6
 *
 */
public class BaseTerm implements Serializable, Comparable<BaseTerm> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -355111225062287520L;

	private long id; //编号
	private String en; //英文单词
	private String[] cn; //中文,可能会有多个解释
	//词汇分类
	private String category = "计算机英语";
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public BaseTerm() {
	}

	public BaseTerm(long id, String en, String[] cn) {
		super();
		this.id = id;
		this.en = en;
		this.cn = cn;
	}

	public BaseTerm(String en, String[] cn) {
		super();
		this.en = en;
		this.cn = cn;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEn() {
		return en;
	}

	public void setEn(String en) {
		this.en = en;
	}

	public String[] getCn() {
		return cn;
	}

	public void setCn(String[] cn) {
		this.cn = cn;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(en).append("=>");
		if(cn != null){
			for (int i = 0; i < cn.length; i++) {
				builder.append(cn[i]);
				if(i != cn.length - 1){
					builder.append("|");
				}
			}
		}
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((en == null) ? 0 : en.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseTerm other = (BaseTerm) obj;
		if (en == null) {
			if (other.en != null)
				return false;
		} else if (!en.equals(other.en))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	/*************************
	 * 按字母顺序排序
	 */
	public int compareTo(BaseTerm o) {
		
		return en.compareTo(o.getEn());
		
	}
	
	
}
