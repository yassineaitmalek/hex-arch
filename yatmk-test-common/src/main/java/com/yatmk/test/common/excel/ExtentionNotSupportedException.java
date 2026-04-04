package com.yatmk.test.common.excel;


public class ExtentionNotSupportedException extends RuntimeException {

	public ExtentionNotSupportedException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExtentionNotSupportedException(String message) {
		super(message);
	}

	public ExtentionNotSupportedException() {
		super("the extention is not supported");
	}

}
