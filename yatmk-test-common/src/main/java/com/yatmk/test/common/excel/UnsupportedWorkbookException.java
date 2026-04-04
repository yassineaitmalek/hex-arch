package com.yatmk.test.common.excel;


public class UnsupportedWorkbookException extends RuntimeException {

	public UnsupportedWorkbookException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnsupportedWorkbookException(String message) {
		super(message);
	}

	public UnsupportedWorkbookException() {
		super("unsupported workbook");
	}

}
