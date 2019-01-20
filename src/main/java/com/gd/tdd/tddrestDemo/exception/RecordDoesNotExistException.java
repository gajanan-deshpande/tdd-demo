package com.gd.tdd.tddrestDemo.exception;

public class RecordDoesNotExistException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7115668250639879664L;
	
	public RecordDoesNotExistException(String message) {
		super(message);
	}

}
