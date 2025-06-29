package br.uece.clinic.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private final Class<?> clazz;
	private final String campo;
	private final String identificador;

	public NotFoundException(Class<?> clazz, String campo, String identificador) {
		super("Not found " + clazz.getSimpleName() + " with " + campo + " = " + identificador);
		this.clazz = clazz;
		this.campo = campo;
		this.identificador = identificador;
	}

	public NotFoundException(Class<?> clazz, String identificador) {
		super("Not found " + clazz.getSimpleName() + " with value = " + identificador);
		this.clazz = clazz;
		this.campo = null;
		this.identificador = identificador;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public String getCampo() {
		return campo;
	}

	public String getIdentificador() {
		return identificador;
	}


}
