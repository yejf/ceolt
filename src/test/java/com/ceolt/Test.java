package com.ceolt;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.PrintWriter;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String path = "datas/ce-vocabulary.dat";
		File file = new File(path);
		File outFile = new File("datas/temp.dat");
		BufferedReader br = null;
		PrintWriter out = null;
		StringBuilder builder = new StringBuilder();
		try {
			br = new BufferedReader(new FileReader(file));
			out = new PrintWriter(outFile);
			String line = null;
			while((line = br.readLine()) != null){
				//写入到另一个文件中
				String[] temp = line.split(" ");
				for(String str : temp){
					//把每个单词的第一个字母变成大写
					builder.append(str.toUpperCase().charAt(0));
					builder.append(str.substring(1));
					builder.append(" ");
				}
				System.out.println(builder.toString());
				out.println(builder.toString());
				builder.delete(0, builder.capacity());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
