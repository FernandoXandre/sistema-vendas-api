package com.bololoko.scev.model.exception;

import java.time.Instant;

public record PadraoErro(
		Instant timestamp,
	    Integer status,
	    String error,
	    String message,
	    String path
		) {

}
