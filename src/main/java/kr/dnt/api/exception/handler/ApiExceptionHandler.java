package kr.dnt.api.exception.handler;

import kr.dnt.api.exception.ApiMessageException;
import kr.dnt.api.exception.payload.ExceptionMsg;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ApiExceptionHandler {
	
	@ExceptionHandler(value = {
		MethodArgumentNotValidException.class
	})
	public <T extends BindException> ResponseEntity<ExceptionMsg> handleValidationException(final MethodArgumentNotValidException e) {
		
		log.info("**ApiExceptionHandler controller, handle validation exception*\n");
		final var badRequest = HttpStatus.BAD_REQUEST;
		
		return new ResponseEntity<>(
				ExceptionMsg.builder()
					.msg("** " + (e.getBindingResult().getFieldError().getField() + " : " + e.getBindingResult().getFieldError().getDefaultMessage()) + " !**")
					.httpStatus(badRequest)
					.timestamp(ZonedDateTime
							.now(ZoneId.systemDefault()))
					.code(-1)
					.success(false)
					.build(), badRequest);
	}

	@ExceptionHandler(value = {
		HttpMessageNotReadableException.class
	})
	public <T extends BindException> ResponseEntity<ExceptionMsg> handleValidationException2(final HttpMessageNotReadableException e) {

		log.info("**ApiExceptionHandler controller, handle validation exception*\n");
		final var badRequest = HttpStatus.BAD_REQUEST;

		return new ResponseEntity<>(
				ExceptionMsg.builder()
					.msg("** " + e.getMessage() + " !**")
					.httpStatus(badRequest)
					.timestamp(ZonedDateTime
							.now(ZoneId.systemDefault()))
					.code(-1)
					.success(false)
					.build(), badRequest);
	}

	@ExceptionHandler(value = {
		ConstraintViolationException.class
	})
	public <T extends BindException> ResponseEntity<ExceptionMsg> handleValidationException3(final ConstraintViolationException e) {

		log.info("**ApiExceptionHandler controller, handle validation exception*\n");
		final var badRequest = HttpStatus.BAD_REQUEST;

		return new ResponseEntity<>(
				ExceptionMsg.builder()
						.msg("** " + e.getMessage() + " !**")
					.httpStatus(badRequest)
					.timestamp(ZonedDateTime
							.now(ZoneId.systemDefault()))
					.code(-1)
					.success(false)
					.build(), badRequest);
	}
	


	@ExceptionHandler(value = {
			ApiMessageException.class
	})
	public <T extends RuntimeException> ResponseEntity<ExceptionMsg> handleApisRequestException(final T e) {

		log.info("**ApiExceptionHandler controller, handle API request*\n");
		final var badRequest = HttpStatus.BAD_REQUEST;
		return new ResponseEntity<>(
				ExceptionMsg.builder()
						.msg("#### " + e.getMessage() + "! ####")
						.httpStatus(badRequest)
						.timestamp(ZonedDateTime
								.now(ZoneId.systemDefault()))
						.code(-1)
						.success(false)
						.build(), badRequest);
	}
	
	
	
}










