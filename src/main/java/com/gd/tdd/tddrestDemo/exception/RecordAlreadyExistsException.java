package com.gd.tdd.tddrestDemo.exception;

public class RecordAlreadyExistsException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9123313129010316511L;
	
	public RecordAlreadyExistsException(String message) {
		super(message);
	}

}
