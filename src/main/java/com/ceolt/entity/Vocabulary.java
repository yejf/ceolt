package com.ceolt.entity;

/*************************************************
 * 
 * @description 词汇 类
 * @author yejf
 * @date 2013-6-28 下午9:28:32
 * @version jdk1.6
 *
 */
public class Vocabulary extends BaseTerm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1947376381172066858L;
	
	private String acronym; //词汇缩写

	public Vocabulary(String en, String[] cn, String acronym) {
		super(en, cn);
		this.acronym = acronym;
	}

	public Vocabulary() {
		super();
	}


	public Vocabulary(long id, String en, String[] cn, String acronym) {
		super(id, en, cn);
		this.acronym = acronym;
	}

	public String getAcronym() {
		return acronym;
	}

	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		if(acronym != null){
			builder.append("\t[简称：").append(acronym).append("]");
		}
		return builder.toString();
	}
}
