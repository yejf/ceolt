package com.ceolt.client;

/*****************************
 * 
 * @description 本类绘制相关的界面 , 单例类
 * @author yejf
 * @date 2013-7-1 上午9:32:56
 * @version jdk1.6
 *
 */
public class ClientUI {
	
	private static ClientUI ui;
	
	private ClientUI(){
		
	}
	
	public synchronized static ClientUI getUI(){
		if(ui == null){
			ui = new ClientUI();
		}
		return ui;
	}
	
	/*****************************
	 * 欢迎界面
	 */
	public void welcome(){
		System.out.println("/************************************************");
		System.out.println(" *描述：Computer English OnLine Testing");
		System.out.println(" *##本项目是为了让使用者充份利用零星时间学习计算机英语词汇或单词，");
		System.out.println(" *##它采用一种趣味性的学习方式－做游戏，并利用业余时间来玩.");
		System.out.println(" *##来帮助学员提高自己的计算机英语单词量,从而帮助编程;");
		System.out.println(" *##本产品采用 MAVEN 构建，不做商业用途，纯为学员学习使用，欢迎交流");
		System.out.println(" *##版本：1.0-SNAPSHOT");
		System.out.println(" * groupId：ceolt");
		System.out.println(" * author: yejf");
		System.out.println(" * mailto: leton.ye@gmail.com");
		System.out.println(" * 如果你发现任何Bug，请邮件给作者或直接联系我!");
		System.out.println(" ************************************************/");
	}
	
	/**********************
	 * 主菜单
	 */
	public void mainMenu(){
		System.out.println("————————————————————\n");
		System.out.println("# 1.\t 按字母顺序浏览基库[单词与词汇]");
		System.out.println("# 2.\t 做游戏学习单词[随机性]");
		System.out.println("# 3.\t 测试自己的水平[随机性]");
		System.out.println("# 0.\t 退出!");
		System.out.println("\n————————————————————");
	}
	
	/****************************
	 * 显示浏览相关的子菜单
	 */
	public void showBrowserSubMenu(){
		System.out.println(" -> 1.浏览单词");
		System.out.println(" -> 2.浏览词汇");
		System.out.println(" -> 0.返回上一级");
		System.out.println("————————————");
	}
	
	/*********************
	 * 显示游戏相关的子菜单
	 */
	public void showGameSubMenu(){
		System.out.println(" -> 1.提示英文，回答中文[只要答对一个就算对，汉字要输入正确]");
		System.out.println(" -> 2.提示中文，回答英文[不能有拼写错误，不区分大小写]");
		System.out.println(" -> 0.返回上一级");
		System.out.println("————————————");
	}
	
	/***************************************
	 * 显示测试相关的子菜单
	 */
	public void showTestingSubMenu(){
		System.out.println(" -> 1.开始测试");
		System.out.println(" -> 2.我的测试记录");
		System.out.println(" -> 0.返回上一级");
		System.out.println("————————————");
	}
	
}
