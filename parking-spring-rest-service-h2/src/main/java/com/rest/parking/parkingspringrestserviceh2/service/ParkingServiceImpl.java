package com.rest.parking.parkingspringrestserviceh2.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest.parking.parkingspringrestserviceh2.exceptions.DateParseException;
import com.rest.parking.parkingspringrestserviceh2.exceptions.PlacesException;
import com.rest.parking.parkingspringrestserviceh2.exceptions.ResourceNotFoundException;
import com.rest.parking.parkingspringrestserviceh2.model.Parking;
import com.rest.parking.parkingspringrestserviceh2.model.ParkingDto;
import com.rest.parking.parkingspringrestserviceh2.repository.ParkingRepository;
import com.rest.parking.parkingspringrestserviceh2.util.Mapper;

@Service
public class ParkingServiceImpl implements ParkingService,Mapper<Parking,ParkingDto>  {

	private static final String DATE_FORMAT = "dd.MM.yyyy:HH";
	
	@Autowired
	ParkingRepository parkingRepository;

	@Override
	public List<ParkingDto> findAll() {
		return parkingRepository.findAll().stream().map(it -> {
		      return toDto(it);
		    }).collect(Collectors.toList());
	}

	@Override
	public ParkingDto save(ParkingDto parking) {
		if (validationPlaces(parking)) {
			return toDto(parkingRepository.save(toModel(parking)));
		} else {
			throw new PlacesException("The free places can't be greater than the total places.");
		}

	}

	@Override
	public ParkingDto update(long id, ParkingDto newParking) {
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

			return toDto(parkingRepository.save(currentParking));
			
		} else {
			throw new PlacesException("The free places can't be greater than the total places.");
		}
	}
	
	@Override
	public List<ParkingDto> findCompletesParkings() {
		return parkingRepository.getCompleteParkings().stream().map(it -> {
		      return toDto(it);
	    }).collect(Collectors.toList());
	}
	
	@Override
	public List<ParkingDto> findFreePlacesParkings() {
		return parkingRepository.getFreePlacesParkings().stream().map(it -> {
		      return toDto(it);
	    }).collect(Collectors.toList());
	}
	
	@Override
	public List<ParkingDto> findOpenParkingByDate(String openDay) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			Date date = sdf.parse(openDay);

			Calendar cal = Calendar.getInstance();
			cal.setTime(date);

			Integer day = cal.get(Calendar.DAY_OF_WEEK);
			Integer hour = cal.get(Calendar.HOUR_OF_DAY);

			return this.parkingRepository.getOpenParkingsByDate(day-1, hour).stream().map(it -> {
		        return toDto(it);
		      }).collect(Collectors.toList());
			
		} catch (ParseException e) {
			throw new DateParseException("Parse date error");
		}
	}
	
	@Override
	public ParkingDto incrementFreePlaces(long id) {
		Optional<Parking> parkingOptional = this.parkingRepository.findById(id);

		if (!parkingOptional.isPresent()) {
			throw new ResourceNotFoundException("Parking with id "+ id + " not found.");			
		}

		Parking parking = parkingOptional.get();

		if(parking.getFreePlaces().equals(parking.getTotalPlaces())) {
			throw new PlacesException("The free places can't be greater than the total places.");
		}
		
		parking.setFreePlaces(parking.getFreePlaces() + 1);

		return toDto(this.parkingRepository.save(parking));
	}
	
	@Override
	public ParkingDto decrementFreePlaces(long id) {
		Optional<Parking> parkingOptional = this.parkingRepository.findById(id);

		if (!parkingOptional.isPresent()) {
			throw new ResourceNotFoundException("Parking with id "+ id + " not found.");			
		}

		Parking parking = parkingOptional.get();

		if(parking.getFreePlaces() == 0) {
			throw new PlacesException("The free places can't be less than zero.");
		}
		
		parking.setFreePlaces(parking.getFreePlaces() - 1);

		return toDto(this.parkingRepository.save(parking));
	}

	public boolean validationPlaces(ParkingDto parking) {
		if (parking.getFreePlaces() > parking.getTotalPlaces()) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public ParkingDto toDto(Parking model) {
		ParkingDto result = new ParkingDto();
		
	    result.setId(model.getId());
	    result.setClosingTime(model.getClosingTime());
	    result.setFreePlaces(model.getFreePlaces());
	    result.setLatitude(model.getLatitude());
	    result.setLongitude(model.getLongitude());
	    result.setName(model.getName());
	    result.setOpeningDays(model.getOpeningDays());
	    result.setOpeningTime(model.getOpeningTime());
	    result.setTotalPlaces(model.getTotalPlaces());
	    
	    return result;
	}

	@Override
	public Parking toModel(ParkingDto dto) {
		Parking result = new Parking();
		
	    result.setId(dto.getId());
	    result.setClosingTime(dto.getClosingTime());
	    result.setFreePlaces(dto.getFreePlaces());
	    result.setLatitude(dto.getLatitude());
	    result.setLongitude(dto.getLongitude());
	    result.setName(dto.getName());
	    result.setOpeningDays(dto.getOpeningDays());
	    result.setOpeningTime(dto.getOpeningTime());
	    result.setTotalPlaces(dto.getTotalPlaces());
	    
		return result;
	}
}
