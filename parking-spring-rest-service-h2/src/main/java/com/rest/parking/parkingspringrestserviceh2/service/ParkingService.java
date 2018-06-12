package com.rest.parking.parkingspringrestserviceh2.service;

import java.util.List;

import com.rest.parking.parkingspringrestserviceh2.model.Parking;

public interface ParkingService {

	List<Parking> findAll();
	
	Parking save(Parking parking);
	
	Parking update(long id, Parking newParking);
	
	List<Parking> findCompletesParkings();
	
	List<Parking> findFreePlacesParkings();
	
	List<Parking> findOpenParkingByDate(String openDay);
	
	Parking incrementFreePlaces(long id);
	
	Parking decrementFreePlaces(long id);
}
