package com.rest.parking.parkingspringrestserviceh2.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest.parking.parkingspringrestserviceh2.exceptions.AlreadyExistsException;
import com.rest.parking.parkingspringrestserviceh2.exceptions.PlacesException;
import com.rest.parking.parkingspringrestserviceh2.exceptions.ResourceNotFoundException;
import com.rest.parking.parkingspringrestserviceh2.model.Parking;
import com.rest.parking.parkingspringrestserviceh2.repository.ParkingRepository;

@Service
public class ParkingServiceImpl implements ParkingService {

	private static final String DATE_FORMAT = "dd.MM.yyyy:HH";
	
	@Autowired
	ParkingRepository parkingRepository;

	@Override
	public List<Parking> findAll() {
		return parkingRepository.findAll();
	}

	@Override
	public Parking save(Parking parking) {
		if (parkingRepository.findById(parking.getId()) != null) {
			throw new AlreadyExistsException("Parking with id " + parking.getId().toString() + " already exists.");
		}
		if (validationPlaces(parking)) {
			return parkingRepository.save(parking);
		} else {
			throw new PlacesException("The free places can't be greater than the total places.");
		}

	}

	@Override
	public Parking update(long id, Parking newParking) {
		if (validationPlaces(newParking)) {

			Optional<Parking> parkingOptional = parkingRepository.findById(id);
			Parking currentParking = parkingOptional.get();

			currentParking.setId(id);
			currentParking.setName(newParking.getName());
			currentParking.setClosingTime(newParking.getClosingTime());
			currentParking.setFreePlaces(newParking.getFreePlaces());
			currentParking.setLatitude(newParking.getLatitude());
			currentParking.setLongitude(newParking.getLongitude());
			currentParking.setOpeningDays(newParking.getOpeningDays());
			currentParking.setOpeningTime(newParking.getOpeningTime());
			currentParking.setTotalPlaces(newParking.getTotalPlaces());

			return parkingRepository.save(currentParking);
			
		} else {
			throw new PlacesException("The free places can't be greater than the total places.");
		}
	}
	
	@Override
	public List<Parking> findCompletesParkings() {
		return parkingRepository.getCompleteParkings();
	}
	
	@Override
	public List<Parking> findFreePlacesParkings() {
		return parkingRepository.getFreePlacesParkings();
	}
	
	@Override
	public List<Parking> findOpenParkingByDate(String openDay) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			Date date = sdf.parse(openDay);

			Calendar cal = Calendar.getInstance();
			cal.setTime(date);

			Integer day = cal.get(Calendar.DAY_OF_WEEK);
			Integer hour = cal.get(Calendar.HOUR_OF_DAY);

			return this.parkingRepository.getOpenParkingsByDate(day-1, hour);
			
		} catch (ParseException e) {
			System.out.println("Error parsing date.");
			return null;
		}
	}
	
	@Override
	public Parking incrementFreePlaces(long id) {
		Optional<Parking> parkingOptional = this.parkingRepository.findById(id);

		if (!parkingOptional.isPresent()) {
			throw new ResourceNotFoundException("Parking with id "+ id + " not found.");			
		}

		Parking parking = parkingOptional.get();

		if(parking.getFreePlaces().equals(parking.getTotalPlaces())) {
			throw new PlacesException("The free places can't be greater than the total places.");
		}
		
		parking.setFreePlaces(parking.getFreePlaces() + 1);

		return this.parkingRepository.save(parking);
	}
	
	@Override
	public Parking decrementFreePlaces(long id) {
		Optional<Parking> parkingOptional = this.parkingRepository.findById(id);

		if (!parkingOptional.isPresent()) {
			throw new ResourceNotFoundException("Parking with id "+ id + " not found.");			
		}

		Parking parking = parkingOptional.get();

		if(parking.getFreePlaces() == 0) {
			throw new PlacesException("The free places can't be less than zero.");
		}
		
		parking.setFreePlaces(parking.getFreePlaces() - 1);

		return this.parkingRepository.save(parking);
	}

	public boolean validationPlaces(Parking parking) {
		if (parking.getFreePlaces() > parking.getTotalPlaces()) {
			return false;
		} else {
			return true;
		}
	}
}
