package com.rest.parking.parkingspringrestserviceh2.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rest.parking.parkingspringrestserviceh2.model.ParkingDto;
import com.rest.parking.parkingspringrestserviceh2.service.ParkingService;

@RestController
public class ParkingController {

	private static final Logger LOGGER = LogManager.getLogger(ParkingController.class);
	
	@Autowired
	private ParkingService parkingService;

	@GetMapping("/parking")
	public ResponseEntity<List<ParkingDto>> getParkings() {
		LOGGER.info("/parking [GET]");
		return new ResponseEntity<List<ParkingDto>>(this.parkingService.findAll(),HttpStatus.CREATED);
	}

	@PostMapping("/parking")
	public ResponseEntity<Long> createParking(@RequestBody @Valid ParkingDto parking) {
		LOGGER.info("/parking [POST]");
		return new ResponseEntity<Long>(this.parkingService.save(parking).getId(),HttpStatus.CREATED);
	}

	@PutMapping("/parking/{id}")
	public ResponseEntity<ParkingDto> updateParking(@RequestBody ParkingDto parking, @PathVariable("id") long id) {
		LOGGER.info("/parking/{} [PUT]",id);
		return new ResponseEntity<ParkingDto>(this.parkingService.update(id, parking),HttpStatus.CREATED);
	}

	@GetMapping("/parking/complete={isComplete}")
	public ResponseEntity<List<ParkingDto>> getFreePlacesParkings(@PathVariable("isComplete") boolean isComplete) {
		LOGGER.info("/parking/complete={} [GET]",isComplete);
		if (isComplete) {
			return new ResponseEntity<List<ParkingDto>>(this.parkingService.findCompletesParkings(),HttpStatus.CREATED);
		} else {
			return new ResponseEntity<List<ParkingDto>>(this.parkingService.findFreePlacesParkings(),HttpStatus.CREATED);
		}
	}

	@GetMapping("/parking/day={openDay}")
	public ResponseEntity<List<ParkingDto>> getOpenParkingsByDate(@PathVariable("openDay") String openDay) {
		LOGGER.info("/parking/day={} [GET]",openDay);
		return new ResponseEntity<List<ParkingDto>>(this.parkingService.findOpenParkingByDate(openDay),HttpStatus.CREATED);
	}

	@PutMapping("/parking/free_place/{id}")
	public ResponseEntity<ParkingDto> freePlace(@PathVariable("id") long id) {
		LOGGER.info("/parking/free_place/{} [PUT]",id);
		return new ResponseEntity<ParkingDto>(this.parkingService.incrementFreePlaces(id),HttpStatus.CREATED);
	}

	@PutMapping("/parking/ocuppy_place/{id}")
	public ResponseEntity<ParkingDto> occupyPlace(@PathVariable("id") long id) {
		LOGGER.info("/parking/ocuppy_place/{} [PUT]",id);
		return new ResponseEntity<ParkingDto>(this.parkingService.decrementFreePlaces(id),HttpStatus.CREATED);
	}

}
