package com.ceolt.exception;

public class NoTestRecordException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6676831568023781529L;

	public NoTestRecordException() {
	}

	public NoTestRecordException(String message) {
		super(message);
	}

	public NoTestRecordException(Throwable cause) {
		super(cause);
	}

	public NoTestRecordException(String message, Throwable cause) {
		super(message, cause);
	}

}
