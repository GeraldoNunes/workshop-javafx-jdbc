package model.exceptions;

import java.util.HashMap;
import java.util.Map;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	// para informar erros em cada campo específico, como no campo nome e save
	private Map<String, String> errors = new HashMap<>(); // 1 string nome do campo, o 2 a mensagem de erro

	public ValidationException(String msg) {
		super(msg);
	}

	public Map<String, String> getErrors(){
		return errors;
	}
	
	public void addError(String fieldName, String errorMessage) {
		errors.put(fieldName, errorMessage);
	}
}
