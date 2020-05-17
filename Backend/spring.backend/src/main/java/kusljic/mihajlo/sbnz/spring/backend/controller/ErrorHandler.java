package kusljic.mihajlo.sbnz.spring.backend.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;

import kusljic.mihajlo.sbnz.spring.backend.exception.EntityAlreadyExistsException;
import kusljic.mihajlo.sbnz.spring.backend.exception.InvalidFieldException;

@ControllerAdvice
public class ErrorHandler {
	
	@ExceptionHandler(value = { EntityAlreadyExistsException.class, InvalidFieldException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	String onBadRequests(EntityAlreadyExistsException e) {
		return e.getMessage();
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	String onEntityNotFoundException(EntityNotFoundException e) {
		return e.getMessage();
	}

}
