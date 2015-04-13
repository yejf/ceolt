package com.ceolt.entity;

/*******************
 * 
 * @description 单词类 
 * @author yejf
 * @date 2013-6-28 下午9:29:10
 * @version jdk1.6
 *
 */
public class Word extends BaseTerm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2353275780690096682L;
	
	private char first; //首字母

	public Word() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Word(long id, String en, String[] cn) {
		super(id, en, cn);
		setFirst();
	}

	public Word(String en, String[] cn) {
		super(en, cn);
		setFirst();
	}

	public char getFirst() {
		return first;
	}

	/************
	 * 根据 en 属性来设置 首字母
	 */
	private void setFirst() {
		this.first = getEn().toUpperCase().charAt(0);
	}

}
