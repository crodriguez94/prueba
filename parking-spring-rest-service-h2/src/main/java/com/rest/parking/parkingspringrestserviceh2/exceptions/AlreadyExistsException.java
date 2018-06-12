package com.rest.parking.parkingspringrestserviceh2.exceptions;

public class AlreadyExistsException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public AlreadyExistsException(String message) {
		super(message);
	}

	public String getCode() { return "ALREADY_EXISTS"; }
}
