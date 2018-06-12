package com.rest.parking.parkingspringrestserviceh2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rest.parking.parkingspringrestserviceh2.model.Parking;
import com.rest.parking.parkingspringrestserviceh2.service.ParkingService;

@RestController
public class ParkingController {

	@Autowired
	private ParkingService parkingService;

	@GetMapping("/parking")
	public List<Parking> getParkings() {
		return parkingService.findAll();
	}

	@PostMapping("/parking")
	public Long createParking(@RequestBody Parking parking) {
		return parkingService.save(parking).getId();
	}

	@PutMapping("/parking/{id}")
	public Parking updateParking(@RequestBody Parking parking, @PathVariable("id") long id) {
		return parkingService.update(id, parking);
	}

	@GetMapping("/parking/complete={isComplete}")
	public List<Parking> getFreePlacesParkings(@PathVariable("isComplete") boolean isComplete) {
		if (isComplete) {
			return this.parkingService.findCompletesParkings();
		} else {
			return this.parkingService.findFreePlacesParkings();
		}
	}

	@GetMapping("/parking/day={openDay}")
	public List<Parking> getOpenParkingsByDate(@PathVariable("openDay") String openDay) {
		return parkingService.findOpenParkingByDate(openDay);
	}

	@PutMapping("/parking/free_place/{id}")
	public Parking freePlace(@PathVariable("id") long id) {
		return this.parkingService.incrementFreePlaces(id);
	}

	@PutMapping("/parking/ocuppy_place/{id}")
	public Parking occupyPlace(@PathVariable("id") long id) {	
		return this.parkingService.decrementFreePlaces(id);
	}

}
