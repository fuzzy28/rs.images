package com.acss.core.rs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CommonAspectErrorController {
	
	private static Logger errorLogger = LoggerFactory.getLogger(CommonAspectErrorController.class);
	
	@ExceptionHandler(DataRetrievalFailureException.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handleNotFoundException(Exception e) {
		/**
		 * Do not log this kind of error, 404 will be handled by client.
		 */
	    return "Record not found";
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.CONFLICT)
	public String handleConstraintViolation(Exception e,Object handler) {
		errorLogger.error("ERROR encountered {}", handler, e);
	    return "Contraint violation!";
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public String genericError(Exception e,Object handler) {
		errorLogger.error("ERROR encountered {}", handler, e);
	    return "Generic Unhandled Exception Occured : "+e.getMessage();
	}
}
