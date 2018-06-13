package com.rest.parking.parkingspringrestserviceh2.service;

import java.util.List;

import com.rest.parking.parkingspringrestserviceh2.model.ParkingDto;

public interface ParkingService{

	List<ParkingDto> findAll();
	
	ParkingDto save(ParkingDto parking);
	
	ParkingDto update(long id, ParkingDto newParking);
	
	List<ParkingDto> findCompletesParkings();
	
	List<ParkingDto> findFreePlacesParkings();
	
	List<ParkingDto> findOpenParkingByDate(String openDay);
	
	ParkingDto incrementFreePlaces(long id);
	
	ParkingDto decrementFreePlaces(long id);
}
