package com.rest.parking.parkingspringrestserviceh2.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rest.parking.parkingspringrestserviceh2.model.Parking;
import com.rest.parking.parkingspringrestserviceh2.model.ParkingDto;
import com.rest.parking.parkingspringrestserviceh2.service.ParkingService;

@RestController
public class ParkingController {

	private static final Logger LOGGER = LogManager.getLogger(ParkingController.class);
	
	@Autowired
	private ParkingService parkingService;

	@GetMapping("/parking")
	public List<ParkingDto> getParkings() {
		LOGGER.info("/parking [GET]");
		return parkingService.findAll();
	}

	@PostMapping("/parking")
	public Long createParking(@RequestBody @Valid ParkingDto parking) {
		LOGGER.info("/parking [POST]");
		return parkingService.save(parking).getId();
	}

	@PutMapping("/parking/{id}")
	public ParkingDto updateParking(@RequestBody ParkingDto parking, @PathVariable("id") long id) {
		LOGGER.info("/parking/{} [PUT]",id);
		return parkingService.update(id, parking);
	}

	@GetMapping("/parking/complete={isComplete}")
	public List<ParkingDto> getFreePlacesParkings(@PathVariable("isComplete") boolean isComplete) {
		LOGGER.info("/parking/complete={} [GET]",isComplete);
		if (isComplete) {
			return this.parkingService.findCompletesParkings();
		} else {
			return this.parkingService.findFreePlacesParkings();
		}
	}

	@GetMapping("/parking/day={openDay}")
	public List<ParkingDto> getOpenParkingsByDate(@PathVariable("openDay") String openDay) {
		LOGGER.info("/parking/day={} [GET]",openDay);
		return parkingService.findOpenParkingByDate(openDay);
	}

	@PutMapping("/parking/free_place/{id}")
	public ParkingDto freePlace(@PathVariable("id") long id) {
		LOGGER.info("/parking/free_place/{} [PUT]",id);
		return this.parkingService.incrementFreePlaces(id);
	}

	@PutMapping("/parking/ocuppy_place/{id}")
	public ParkingDto occupyPlace(@PathVariable("id") long id) {
		LOGGER.info("/parking/ocuppy_place/{} [PUT]",id);
		return this.parkingService.decrementFreePlaces(id);
	}

}
