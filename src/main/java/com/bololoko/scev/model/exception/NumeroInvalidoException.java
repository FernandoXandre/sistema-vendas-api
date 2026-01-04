package com.bololoko.scev.model.exception;

public class NumeroInvalidoException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public NumeroInvalidoException(String msg) {
		super(msg);
	}
}
