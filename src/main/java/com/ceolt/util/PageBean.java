package com.ceolt.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.ceolt.entity.Vocabulary;

/*****************************
 * 
 * @description　分页数据Bean 
 * @author yejf
 * @date 2013-7-3 下午7:36:03
 * @version jdk1.6
 *
 */
public class PageBean {

	private int size; //每页显示记录数
	private int total; //总记录数
	
	private int pages; //总页数
	private int first = 1; //第一页
	private int last; //最后一页
	
	private int next; //下一页
	private int previous; //上一页
	
	private int current; //当前页
	//数据
	private List<Vocabulary> datas;
	
	//展现的样式
	private String showStyle;
	
	//构造方法
	public PageBean(int size,Set<Vocabulary> datas) {
		//初始化属性
		this.size = size;
		//把Set中的所有数据缓存在 List中，这样方便后面根据页码来取出对应的　数据
		this.datas = new ArrayList<Vocabulary>();
		for(Vocabulary v : datas){
			this.datas.add(v);
		}
		
		//总记录数
		this.total = datas.size();
		//计算总页数
		this.pages = this.total % this.size == 0 ? this.total / this.size : this.total / this.size + 1;
		//初始化第１页和最后一页
		this.first = 1;
		this.last = this.pages;
		
		//设定 初始showStyle
		this.showStyle = "| 1 | 2 | 3 | 4 | ... | "+pages+" |";
	}

	/*******************************
	 * 根据页码来选择对应的数据,　注：
	 * 所有的数据都已经缓存在 datas中,　我们只需要根据页码计算出对应的记录位置，就可以获取相应的数据了
	 * @param page 页码
	 * @return
	 */
	public List<Vocabulary> getShowData(int page){
		//判断参数的有效性
		if(page < 1) {
			page = 1;
		}
		if(page > this.pages) {
			page = this.pages;
		}
		
		//改变属性值
		this.current = page;
		//如果当前页已经是最后一页了，则无下一页，所以，把下一页置为　－１
		this.next = this.current == this.pages ?  -1 : this.current + 1;
		//前一页等于当前页－１，　如果当前页是1，则无前一面，所以，把前一页置为　－１
		this.previous = this.current == 1 ? -1 : this.current - 1;
		
		//改变 showStyle的值
		if(page >= this.pages - 3){
			this.showStyle =  "| 1 | 2 | 3 | 4 | ... | "+pages+" |"; // 回到初始值
		} else {
			this.showStyle = "| "+page+" | "+(page+1)+" | "+(page+2)+" |  ... | "+pages+" | ";
		}
		
		//计算当前页要取到的数据范围　
		int startPos = (this.current - 1) * this.size;
		//如果是最后一页的话，则结束位置是总记录数减去起始位置
		int endPos = page == last ?  total : startPos + this.size;
		//取值
		List<Vocabulary> showData = new ArrayList<Vocabulary>(datas);
		//返回
		return showData.subList(startPos, endPos);
	}

	public int getSize() {
		return size;
	}

	public int getTotal() {
		return total;
	}

	public int getPages() {
		return pages;
	}

	public int getFirst() {
		return first;
	}

	public int getLast() {
		return last;
	}

	public int getNext() {
		return next;
	}

	public int getPrevious() {
		return previous;
	}

	public int getCurrent() {
		return current;
	}

	public String getShowStyle() {
		return showStyle;
	}
	
}


