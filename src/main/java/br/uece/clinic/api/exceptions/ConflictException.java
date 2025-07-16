package br.uece.clinic.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends HttpException{
	
	private static final long serialVersionUID = 1L; 
	
	public ConflictException(Object obj) {
		super("Element " + obj.toString() + " already found in database.");
	}
	public ConflictException(String message) {
		super(message);
	}
	
	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.CONFLICT;
	}


}
