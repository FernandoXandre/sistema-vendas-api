package com.bololoko.scev.model.exception;

public class RecursoExistenteException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public RecursoExistenteException(String msg) {
		super(msg);
	}
}
