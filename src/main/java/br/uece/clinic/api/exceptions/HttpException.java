package br.uece.clinic.api.exceptions;

import org.springframework.http.HttpStatus;

public abstract class HttpException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public abstract HttpStatus getHttpStatus();
	
	public HttpException() {
		super();
	}
	
	public HttpException(String message) {
		super(message);
	}
}
