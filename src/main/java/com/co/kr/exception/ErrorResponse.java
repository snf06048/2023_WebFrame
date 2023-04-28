package com.co.kr.exception;

import org.springframework.http.HttpStatus;

import lombok.Setter;
import lombok.Getter;
import lombok.Builder;

@Getter
@Setter
@Builder(builderMethodName = "builder")
public class ErrorResponse {

	private Integer result;
	private String resultDesc;
	private HttpStatus httpStatus;
	private String resDate;
	private String reqNo;

}
