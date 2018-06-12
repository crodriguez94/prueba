package com.rest.parking.parkingspringrestserviceh2.exceptions;

public class DateParseException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public DateParseException(String message) {
		super(message);
	}

	public String getCode() { return "DATE_PARSE_EXCEPTION"; }
}
