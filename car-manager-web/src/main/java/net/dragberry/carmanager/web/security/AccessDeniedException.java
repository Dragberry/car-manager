package net.dragberry.carmanager.web.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "You don't have permissions to do action!")
public class AccessDeniedException extends RuntimeException {

	private static final long serialVersionUID = 634295764750382126L;

}
