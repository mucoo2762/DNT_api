package kr.dnt.api.exception.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.ZonedDateTime;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
public final class ExceptionMsg implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy__HH:mm:ss:SSSSSS")
	private final ZonedDateTime timestamp;
	
	@JsonInclude(value = Include.NON_NULL)
	private Throwable throwable;
	
	private final HttpStatus httpStatus;
	
	private final String msg;

	private final int code;

	private final  boolean success;

}










