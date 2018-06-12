package com.rest.parking.parkingspringrestserviceh2.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rest.parking.parkingspringrestserviceh2.model.Parking;
import com.rest.parking.parkingspringrestserviceh2.repository.ParkingRepository;

@RestController
public class ParkingController {

	private static final String DATE_FORMAT = "dd.MM.yyyy:HH";

	@Autowired
	private ParkingRepository parkingRepository;

	@GetMapping("/parking")
	public List<Parking> getParkings() {
		return parkingRepository.findAll();
	}

	@PostMapping("/parking")
	public ResponseEntity<Long> createParking(@RequestBody Parking parking) {

		if (validationPlaces(parking)) {
			Parking savedParking = parkingRepository.save(parking);
			System.out.println("Parking created with id: " + savedParking.getId());

			return new ResponseEntity<Long>(savedParking.getId(), HttpStatus.OK);

		} else {
			System.out.println("The free places can't be greater than the total places.");
			return new ResponseEntity<Long>(-1L, HttpStatus.CONFLICT);
		}
	}

	@PutMapping("/parking/{id}")
	public ResponseEntity<Parking> updateParking(@RequestBody Parking parking, @PathVariable("id") long id) {

		Optional<Parking> parkingOptional = parkingRepository.findById(id);

		if (!parkingOptional.isPresent())
			return new ResponseEntity<Parking>(HttpStatus.NOT_FOUND);

		if (validationPlaces(parking)) {
			Parking currentParking = parkingOptional.get();

			currentParking.setId(id);
			currentParking.setName(parking.getName());
			currentParking.setClosingTime(parking.getClosingTime());
			currentParking.setFreePlaces(parking.getFreePlaces());
			currentParking.setLatitude(parking.getLatitude());
			currentParking.setLongitude(parking.getLongitude());
			currentParking.setOpeningDays(parking.getOpeningDays());
			currentParking.setOpeningTime(parking.getOpeningTime());
			currentParking.setTotalPlaces(parking.getTotalPlaces());

			this.parkingRepository.save(currentParking);

			return new ResponseEntity<Parking>(HttpStatus.OK);

		} else {
			System.out.println("The free places can't be greater than the total places.");
			return new ResponseEntity<Parking>(HttpStatus.CONFLICT);
		}

	}

	@GetMapping("/parking/complete={isComplete}")
	public List<Parking> getFreePlacesParkings(@PathVariable("isComplete") boolean isComplete) {
		if (isComplete) {
			return this.parkingRepository.getCompleteParkings();
		} else {
			return this.parkingRepository.getFreePlacesParkings();
		}
	}

	@GetMapping("/parking/day={openDay}")
	public List<Parking> getFreePlacesParkings(@PathVariable("openDay") String openDay) {
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

	@PutMapping("/parking/free_place/{id}")
	public ResponseEntity<Parking> freePlace(@PathVariable("id") long id) {
		Optional<Parking> parkingOptional = this.parkingRepository.findById(id);

		if (!parkingOptional.isPresent())
			return new ResponseEntity<Parking>(HttpStatus.NOT_FOUND);

		Parking parking = parkingOptional.get();

		if(parking.getFreePlaces().equals(parking.getTotalPlaces())) {
			System.out.println("The free places can't be greater than the total places.");
			return new ResponseEntity<Parking>(HttpStatus.CONFLICT);
		}
		
		parking.setFreePlaces(parking.getFreePlaces() + 1);

		this.parkingRepository.save(parking);

		return new ResponseEntity<Parking>(HttpStatus.OK);
	}

	@PutMapping("/parking/ocuppy_place/{id}")
	public ResponseEntity<Parking> occupyPlace(@PathVariable("id") long id) {
		Optional<Parking> parkingOptional = this.parkingRepository.findById(id);

		if (!parkingOptional.isPresent())
			return new ResponseEntity<Parking>(HttpStatus.NOT_FOUND);

		Parking parking = parkingOptional.get();
		
		if(parking.getFreePlaces() == 0) {
			System.out.println("The free places can't be less than zero.");
			return new ResponseEntity<Parking>(HttpStatus.CONFLICT);
		}

		parking.setFreePlaces(parking.getFreePlaces() - 1);

		this.parkingRepository.save(parking);

		return new ResponseEntity<Parking>(HttpStatus.OK);
	}

	public boolean validationPlaces(Parking parking) {
		if (parking.getFreePlaces() > parking.getTotalPlaces()) {
			return false;
		} else {
			return true;
		}
	}

}
