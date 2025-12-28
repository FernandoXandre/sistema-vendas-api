package com.bololoko.scev.model.exception;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class CotrollerAdviceException {
	
	// Para erros de campos invalidos, letras no lugar de numeros e etc...
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<PadraoErro> erroCampoInvalido(HttpMessageNotReadableException e, HttpServletRequest req) {
		String erroTitulo = "Campos invalidos para a requisicao.";
		String erroMsg = "Erro nos campos do formulario. Por favor revise o formulario.";
		HttpStatus stts = HttpStatus.BAD_REQUEST;
		
		
		PadraoErro erroCorpo = new PadraoErro(
				Instant.now(),
				stts.value(),
				erroTitulo,
				erroMsg,
				req.getRequestURI()
		);
		
		return ResponseEntity.status(stts).body(erroCorpo);
	}
	
	
	
	// Para erros de validacao de campos do Hibernate/JPA
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<List<PadraoErro>> erroValidacaoCamposEntidade(ConstraintViolationException e, HttpServletRequest req) {
		String erroTitulo = "Campos invalidos para Produto.";
		HttpStatus stts = HttpStatus.BAD_REQUEST;
		
		
		List<PadraoErro> errosCorpos = e.getConstraintViolations()
				.stream()
				.map(erro -> new PadraoErro(
						Instant.now(),
						stts.value(),
						erroTitulo,
						erro.getMessage(),
						req.getRequestURI()
				))
				.collect(Collectors.toList());
		
		return ResponseEntity.status(stts).body(errosCorpos);
	}
	
	
	// Para erro de validacao dos formularios, campos nulos, numero minimo e etc...
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<PadraoErro>> erroValidacaoCamposDTO(MethodArgumentNotValidException e, HttpServletRequest req) {
		String erroTitulo = "Campos de formulario invalidos ou vazios. ";
		HttpStatus stts = HttpStatus.BAD_REQUEST;
		
		
		List<PadraoErro> errosCorpos = e.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(erro -> new PadraoErro(
						Instant.now(),
						stts.value(),
						erroTitulo,
						erro.getDefaultMessage(),
						req.getRequestURI()
				))
				.collect(Collectors.toList());
		
		return ResponseEntity.status(stts).body(errosCorpos);
	}

	
	// Para recursos ja existentes no banco de dados
	@ExceptionHandler(RecursoNaoEncontradoException.class)
	public ResponseEntity<PadraoErro> recursoNaoEncontrado(RecursoNaoEncontradoException e, HttpServletRequest requisicao) {
		String erroTitulo = "Recurso nao encontrado.";
		HttpStatus stts = HttpStatus.NOT_FOUND;
		
		
		PadraoErro erroCorpo = new PadraoErro(
			Instant.now(),
			stts.value(),
			erroTitulo,
			e.getMessage(),
			requisicao.getRequestURI()
		);
		
		return ResponseEntity.status(stts).body(erroCorpo);
	}
	
	
	// Para recurso que ja existem no banco de dados
	@ExceptionHandler(RecursoExistenteException.class)
	public ResponseEntity<PadraoErro> recursoExistente(RecursoExistenteException e, HttpServletRequest req) {
		String erroTitulo = "Recurso ja existe no sistema.";
		HttpStatus stts = HttpStatus.CONFLICT;
		
		PadraoErro erroCorpo = new PadraoErro(
				Instant.now(),
				stts.value(),
				erroTitulo,
				e.getMessage(),
				req.getRequestURI()
		);
		
		return ResponseEntity.status(stts).body(erroCorpo);
	}
}
