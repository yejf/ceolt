package com.ceolt.exception;

/****************************
 * 自定义业务异常：　无历史文件异常
 * @description 
 * @author yejf
 * @date 2013-7-4 下午2:08:10
 * @version jdk1.6
 *
 */
public class NoHistoryException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2995073676232342621L;

	private String historyFile;
	
	public NoHistoryException() {
		// TODO Auto-generated constructor stub
	}

	public NoHistoryException(String message, String historyFile) {
		super(message);
		this.historyFile = historyFile;
		// TODO Auto-generated constructor stub
	}

	public NoHistoryException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public NoHistoryException(String message, Throwable cause, String historyFile) {
		super(message, cause);
		this.historyFile = historyFile;
		// TODO Auto-generated constructor stub
	}

	public String getHistoryFile() {
		return historyFile;
	}

	
}
