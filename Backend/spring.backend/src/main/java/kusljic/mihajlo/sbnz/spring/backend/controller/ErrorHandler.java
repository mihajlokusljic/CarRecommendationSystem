package kusljic.mihajlo.sbnz.spring.backend.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;

import kusljic.mihajlo.sbnz.spring.backend.exception.EntityAlreadyExistsException;

@ControllerAdvice
public class ErrorHandler {
	
	@ExceptionHandler(EntityAlreadyExistsException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	String onEntityAlreadyExistsException(EntityAlreadyExistsException e) {
		return e.getMessage();
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	String onEntityNotFoundException(EntityNotFoundException e) {
		return e.getMessage();
	}

}
