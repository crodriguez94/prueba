package com.rest.parking.parkingspringrestserviceh2.exceptions;

public class PlacesException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public PlacesException(String message) {
		super(message);
	}

	public String getCode() { return "PLACES_ERROR"; }
}
