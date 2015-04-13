package com.ceolt.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import com.ceolt.biz.IBroswerBiz;
import com.ceolt.biz.IGameBiz;
import com.ceolt.biz.ITestingBiz;
import com.ceolt.biz.impl.BroswerBizImpl;
import com.ceolt.biz.impl.GameBizImpl;
import com.ceolt.biz.impl.TestingBizImpl;
import com.ceolt.entity.BaseTerm;
import com.ceolt.entity.TestRecord;
import com.ceolt.entity.Vocabulary;
import com.ceolt.entity.Word;
import com.ceolt.exception.NoHistoryException;
import com.ceolt.exception.NoTestRecordException;
import com.ceolt.util.DateUtil;
import com.ceolt.util.PageBean;
import com.ceolt.util.StringUtil;

public class ClientMain {

	/** 每页显示记录的个数 */
	private static final int PAGE_SIZE = 10;
	
	private ClientUI ui;
	
	//业务对象
	private IBroswerBiz broswerBiz;
	private IGameBiz gameBiz;
	private ITestingBiz testingBiz;
	
	//缓存数据
	
	/*******************************
	 * 初始化相关的属性数据
	 */
	public ClientMain(){
		//初始化界面对象
		this.ui = ClientUI.getUI();
		//初始化业务对象
		this.broswerBiz = new BroswerBizImpl();
		this.gameBiz = new GameBizImpl();
		this.testingBiz = new TestingBizImpl();
	}
	
	/*******************
	 * 读取一个整数
	 * @return 整数
	 */
	private int inputInt(){
		Scanner scan = new Scanner(System.in);
		try{
			int input = scan.nextInt();
			return input;
		} catch (RuntimeException e){
			System.out.println("输入有误");
			System.out.print("请重新输入>");
			return inputInt();
		}
	}
	
	/********************
	 * 读到整行字母或单个字母
	 * @return
	 */
	private String inputLetter(){
		Scanner scan = new Scanner(System.in);
		try {
			String str = scan.nextLine();
			//判断，如果输入的不是字母，则重输
			if(str.toLowerCase().charAt(0)  < 97 || str.toLowerCase().charAt(0) > 122){
				System.out.println("输入的不是字母");
				System.out.print("请重新输入>");
				return inputLetter();
			}
			return str;
		} catch (RuntimeException e){
			System.out.println("输入有误");
			System.out.print("请重新输入>");
			return inputLetter();
		}
	}
	
	/******************
	 * 读取一整行字符串 
	 * 考虑编码问题，全部采用UTF-8
	 * @return
	 */
	private String inputString() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			String result = br.readLine();
//			result = new String(result.getBytes("GBK"),"UTF-8");
			return result;
		} catch (IOException e) {
			System.out.println("输出有错..");
			return inputString();
		}
	}
	
	/********************
	 * 启动程序<br/> 
	 * 本程序是控制的核心程序，采用对答式方式编写,同时存在有大量的变量控制.
	 */
	public void start(){
		ui.welcome();
		boolean exit = false;
		while(!exit) {
			//输出主菜单
			ui.mainMenu();
			System.out.print("请选择>");
			int choice = inputInt();
			//定义公共变量
			boolean returnTop = false;
			//判断选择
			switch (choice) {
				case 1:
					while(!returnTop){
						ui.showBrowserSubMenu();
						System.out.print("请选择>");
//						int subChoice = s.nextInt();
						int subChoice = inputInt();
						switch (subChoice) {
						case 1:
//							System.out.println("你选择了 浏览单词");
//							System.out.println("\n\n\n");
							//把所有单词的首字母及单词个数打印出来
							Map<Character, Set<Word>> firstMap = broswerBiz.getFirstMap();
							Set<Character> keySet = firstMap.keySet();
						
							for(Character first : keySet){
								System.out.printf("%-2s[%-2d]%-4s",first,firstMap.get(first).size()," ");
								//当字母为 g、n、t、z 时，换行，最多四行
								char temp = first.charValue();
								if(temp == 'G' || temp == 'N' || temp == 'T' || temp == 'Z'){
									System.out.println(); //换行
								}
							}
							System.out.println("\n————————————————————");
							//提示，用户选择首字母
							System.out.print("请您选择要查看的首字母>");
							String letter = inputLetter();
							Character first = letter.toUpperCase().charAt(0);
							Set<Word> wordSet = firstMap.get(first);
							//输出所有的以此首字母打头的单词
							System.out.println(); //空行
							int count = 1;
							for(Word w : wordSet){
								System.out.printf("%s [%d. %s => 解释：%s]\n\n","☆",count,w.getEn(), StringUtil.toString(w.getCn()));
								//每页显示　PAGE_SIZE 个单词，如果某个字母的单词数刚好是PAGE_SIZE的倍数，则最后一页显示完后，就无需再提示　是否查看 下一页 了
								if(count % PAGE_SIZE == 0 && count != wordSet.size()){
									System.out.print("是否查看 下一页[y,n]? ");
									String tip = inputLetter();
									if(!tip.equalsIgnoreCase("y")){
										break;
									} 
								}
								count++;
							}
							System.out.println(" .... ... ... ...\n"); //空行
							
							break;
						case 2:
//							System.out.println("你选择了 浏览词汇");
//							System.out.println("\n\n\n");
							Set<Vocabulary> vsSet = broswerBiz.getAllVocabularies();
							//本功能需要使用分页显示，故创建PageBean
							PageBean pb = new PageBean(PAGE_SIZE, vsSet);
							//通过PageBean获取相关的信息
							int pages = pb.getPages(); //获取总页数
							
							while(true){
							
								System.out.printf("每页【%d】行 ※ 共计【%d】页 ※ 词汇量总数: 【%d】.   返回上一级  0 \n", PAGE_SIZE, pages, pb.getTotal());
								//打印分页样式
								System.out.println(pb.getShowStyle());
								System.out.println("——————————");
								//提示用户选择第几页查看
								System.out.printf("请选择页码【%d ~ %d】>", pb.getFirst(),pb.getPages());
								int page = inputInt();
								if(page == 0){
									//返回到上一级
									break;
								}
								//再根据页码来获取数据
								List<Vocabulary> showData = pb.getShowData(page);
								//打印数据
								count = 1;
								//输出头
								System.out.printf("第【%d】页数据有：\n",pb.getCurrent());
								System.out.println("————————————————————\n");
								for(Vocabulary v : showData){
									System.out.printf("%s [%d. %s => 解释：%s]","※",count,v.getEn(), StringUtil.toString(v.getCn()));
									String acronym = v.getAcronym();
									if(acronym != null){
										//打印输出
										System.out.printf("%6s简称：%s"," ",v.getAcronym());
									}
									System.out.println("\n");
									count ++ ;
								}
								System.out.println(" .... ... ... ...\n"); //空行
							
							} //循环结束
							
							break;
						case 0:
							returnTop = true;
							break;
						default:
							System.out.println("##警告：选择不正确.");
							break;
						}
					}
					break;
				case 2:
					while(!returnTop){
						ui.showGameSubMenu();
						System.out.print("请选择>");
						int subChoice = inputInt();
						switch (subChoice) {
						case 1:
//							System.out.println("你选择了 提示英文，回答中文[只要答对一个就算对，汉字要输入正确]");
//							System.out.println("\n\n\n");
							//1.　获取业务数据
							Map<String, String> en2cnMap = gameBiz.en2cn();
							//2. 从读历史记录，如果没有，则不提示是否要从上次继续，否则，提示　是否要从上次继续
							Map<String, String> en2cnBackup = null;
							try {
								en2cnBackup = gameBiz.readFromFile(IGameBiz.EN_TO_CN_BACKUP);
								//提示　是否要从上次继续
								System.out.printf("%4s 是否要从上次继续? Y or N >","※");
								//　输入
								String s = inputLetter();
								if(s.toLowerCase().charAt(0) == 'y') {
									//说明继续从上次开始
									onGame(en2cnBackup, true);
								} else {
									//说明不从上次开始，那就重新开始
									onGame(en2cnMap, true);
								}
							} catch (NoHistoryException e) {
								//出现异常，说明是第一次使用，或没有历史记录，则直接从重新开始
								onGame(en2cnMap, true);
							}
							
							break;
						case 2:
//							System.out.println("你选择了 提示中文，回答英文[不能有拼写错误，不区分大小写]");
//							System.out.println("\n\n\n");
							//1.　获取业务数据
							Map<String, String> cn2enMap = gameBiz.cn2en();
							//2. 从读历史记录，如果没有，则不提示是否要从上次继续，否则，提示　是否要从上次继续
							Map<String, String> cn2enBackup = null;
							try {
								cn2enBackup = gameBiz.readFromFile(IGameBiz.CN_TO_EN_BACKUP);
								//提示　是否要从上次继续
								System.out.printf("%4s 是否要从上次继续? Y or N >","※");
								//　输入
								String s = inputLetter();
								if(s.toLowerCase().charAt(0) == 'y') {
									//说明继续从上次开始
									onGame(cn2enBackup, false);
								} else {
									//说明不从上次开始，那就重新开始
									onGame(cn2enMap, false);
								}
							} catch (NoHistoryException e) {
								//出现异常，说明是第一次使用，或没有历史记录，则直接从重新开始
								onGame(cn2enMap, false);
							}
							
							break;
						case 0:
							returnTop = true;
							break;
						default:
							System.out.println("##警告：选择不正确.");
							break;
						}
					}
					break;
				case 3:
					while(!returnTop){
						ui.showTestingSubMenu();
						System.out.print("请选择>");
						int subChoice = inputInt();
						switch (subChoice) {
						case 1:
//							System.out.println("你选择了 开始测试");
//							System.out.println("\n\n\n");
							System.out.print("请输入要测试的题目数量[5~100]>");
							int capacity = inputInt();
							//获取测试数据 通过业务对象
							List<BaseTerm> testDatas = testingBiz.getTestingData(capacity);
							//开始测试
							onTesting(testDatas); 
							
							break;
							
						case 2:
//							System.out.println("你选择了 我的测试记录");
//							System.out.println("\n\n\n");
							//1.获取历史记录
							try {
								List<TestRecord> records = testingBiz.reviewTestRecord();
								//如果存在，则迭代出来，因为，这个记录我们是已经按测试时间排过序的
								for (int i = 0; i < records.size(); i++) {
									//首先，只显示最近的两次，然后，提示用户是否要查看所有的历史记录
									if(i != 2) { 
										System.out.println(records.get(i));
									} else {
										//提示用户，是否要继续
										System.out.print("[y or n]是否要继续? >");
										String letter = inputLetter();
										if(letter.toLowerCase().charAt(0) == 'n' ) {
											break;
										}
									}
								}
							} catch (NoTestRecordException e) {
								//说明没有过历史记录
								System.out.println(e.getMessage());
							}
							
							break;
							
						case 0:
							returnTop = true;
							break;
						default:
							System.out.println("##警告：选择不正确.");
							break;
						}
					}
					break;
				case 0:
					//退出程序
					exit = true;
					System.out.println(":)谢谢使用");
					break;
					
				default:
					System.out.println("菜单选择不对.");
					break;
			}
				
		}
		
	}
	

	/**************************************
	 * 游戏业务
	 * @param map 玩回答单词或词汇游戏时的数据
	 * @param flag 如果为true，则表示是由en2cn　业务来的请求，则写入到en2cn的历史文件中，否则，
	 * 则表示是由 cn2en　的业务来的请求，则写入到　cn2en　的历史文件中
	 */
	private void onGame(Map<String, String> map, boolean flag) {
		// TODO Auto-generated method stub
		//定义变量，保存本次玩的结果
		int count = 0; //记录本次玩了多少个题目　
		double correct = 0 ; //记录本次答对的题目个数
		//定义不同的游戏规则，提示符不一样
		String tip = "";
		if(flag) {
			tip = "中文";
		} else {
			tip = "英文";
		}
		
		//1.创建一个新的Map，存放暂时还没有玩过的数据，起始数据与 形参 map一样
		Map<String, String> backupMap = new HashMap<String, String>(map);
		//2.迭代map，依次询问用户回答
		Set<Entry<String, String>> entrySet = map.entrySet();
		long start = System.currentTimeMillis(); //用于记录本次游戏　的时长
		
		for(Entry<String, String> entry : entrySet) {
			String key = entry.getKey(); //获取　key，也就是要打印出来让用户回答的题目　
			String value = entry.getValue();
			//输出
			System.out.printf("【退出: e or q】 %4s〖%s〗的%s是： ","※",key,tip);
			String answer = inputString();
			//判断用户是否输入退出指令
			if(answer.equalsIgnoreCase("e") || answer.equalsIgnoreCase("q") ) {
				break; 
			}
			
			count ++; //
			
			//判断　用户是否答对
			if(isRight(answer, StringUtil.toStringArray(value))) {
				//回答正确
				correct ++;
				System.out.printf("√ 回答正确, 完整解释是：【%s】\n", value);
				System.out.println("————————————————");
				//只有回答正确的单词或词汇　才从原集合中删除
				backupMap.remove(key);
				
			} else {
				//回答错误
				System.out.printf("〤回答错误,完整解释是：【%s】\n", value);
				System.out.println("————————————————");
			}
			
		}
		long end = System.currentTimeMillis(); //用于记录本次游戏的时长
		
		//循环退出后，说明用户结束了本次游戏　
		//1. 把剩下没有回答的集合中的数据保存起来，方便以后用户再次游戏
		if(backupMap.size() < map.size()) {  //表示，如果所有都回答错误，那就相录于本次游戏没有改变结果，无需再写入到文件
			if(flag) { 
				gameBiz.writeToFile(backupMap, IGameBiz.EN_TO_CN_BACKUP);
			} else {
				gameBiz.writeToFile(backupMap, IGameBiz.CN_TO_EN_BACKUP);
			}
		}
		
		//2. 输出消息，告诉用户本次共游戏了多少个词汇，并正确了多少，正确率是多少。
		double zql = correct / count;
		
		System.out.printf("本次游戏共回答了【%d】次，正确个数【%.0f】,正确率 【%.1f%%】, 共花费时长：【%s】.\n",
											count,correct,zql*100,DateUtil.convertSeconds((end-start) / 1000));
		if(zql < 0.75) {
			System.out.println("计算机英文水平较差哦  :(  :(  :(\n");
		} else {
			System.out.println(":)  :)  :) 哎哟！不错哦\n");
		}
		
	}

	/*************************************
	 * 英文测试
	 * @param testDatas
	 */
	private void onTesting(List<BaseTerm> testDatas) {
		// 开始测试
		//定义变量，保存本次测试的一些结果
		Date d = DateUtil.getCurrentDate(); //获取测试时间
		long start = System.currentTimeMillis(); //测试开始时间
		int correct = 0; //正确个数
		int error = 0;  //错误个数
		int total = testDatas.size(); //题目总个数
		int count = 0; //记录本次测试了多少个题目
		
		//创建一个测试对象
		TestRecord record = new TestRecord();
		//由于要考虑用户中途退出，所以，我们要创建另一集合，来保存用户回答过的数据
		List<BaseTerm> tested = new ArrayList<BaseTerm>();
		//循环，提示用户回答
		for(BaseTerm bt : testDatas) {
			String en = bt.getEn();
			//输出
			System.out.printf("【退出: e or q】 %4s %d〖%s〗的中文含义是： ","※",count+1,en);
			String answer = inputString();
			//判断用户是否输入退出指令
			if(answer.equalsIgnoreCase("e") || answer.equalsIgnoreCase("q") ) {
				break; 
			}
			//把回答过的单词添加到 tested集合中
			tested.add(bt);
			//测试个数++
			count++;
			//判断　用户回答是否正确
			String[] cn = bt.getCn();
			//判断　用户是否答对
			if(isRight(answer, bt.getCn())) {
				//回答正确
				correct ++;
				System.out.printf("√ 回答正确, 完整解释是：【%s】\n", StringUtil.toString(cn));
				System.out.println("————————————————");
				
			} else {
				//回答错误
				error++;
				System.out.printf("〤回答错误,完整解释是：【%s】\n", StringUtil.toString(cn));
				System.out.println("————————————————");
			}
		}
		//测试结束　时间
		long end = System.currentTimeMillis();
		//只有当用户参与了测试，才需要写到历史文件中
		if(tested.size() > 0) {
			//更新 TestRecord 对象的属性
			record.setDatas(tested); //设置数据
			record.setCorrect(correct); //设置正确数
			record.setCount(count); //设置回答题目数
			record.setError(error); //设置错误数
			record.setTotal(total); //测试总题数
			record.setSeconds((end - start) / 1000);//设置测试时长
			record.setTestDate(d);  //设置测试时间
			
			//循环结束后，要写文件
			testingBiz.saveTestRecord(record);
			//然后，打印一些提示信息.
			double zql = (double)correct  /  count; //求正确率 ,　本处以 count　为分母，而不是 total，也是考虑到用户中途结束测试.
			System.out.printf("本测试共有【%d】个题目，回答了【%d】个，正确【%d】个, 错误【%d】个, 正确率 【%.1f%%】, 共花费时长：【%s】.\n",
					total,count,correct,error,zql*100,DateUtil.convertSeconds((end-start) / 1000));
			
			if(zql < 0.7) {
				System.out.println("考得太差了，加油哦  :(  :(  :(\n");
			} else {
				System.out.println(":)  :)  :) 哎哟！考得不错哦\n");
			}
			
		} else {
			//说明用户直接退出了，一道题都没有答，则提示：
			System.out.println("本次考试一题都没有答，将不会保存记录.");
		}
	}
	
	/************
	 * 判断 answer是否存在于 数组中
	 * @param answer 答案
	 * @param valueArr 数组
	 * @return
	 */
	private boolean isRight(String answer, String[] valueArr) {
		// TODO Auto-generated method stub
		boolean result = false;
		for(int i=0;i<valueArr.length;i++) {
			//只要回答的值与原始中的解释有任一一个匹配，都算正确
//			System.out.println(answer+"=>"+valueArr[i]);
			if(answer.equalsIgnoreCase(valueArr[i])) {
				return true;
			}
		}
		return result;
	}

	/**
	 * 程序入口
	 * @param args
	 */
	public static void main(String[] args) {
		ClientMain cm = new ClientMain();
		cm.start();
	}

}
