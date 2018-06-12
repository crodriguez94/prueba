package com.rest.parking.parkingspringrestserviceh2.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.rest.parking.parkingspringrestserviceh2.model.Days;
import com.rest.parking.parkingspringrestserviceh2.model.Parking;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ParkingRepositoryTest {
	
	@Autowired
	private ParkingRepository parkingRepository;
	
	@Test
	public void freePlacesParkingsTest() {
		List<Parking> found = parkingRepository.getFreePlacesParkings();

		assertThat(found.size()).isEqualTo(2);
	    assertThat(found.get(0).getFreePlaces()).isGreaterThan(0);
	    assertThat(found.get(1).getFreePlaces()).isGreaterThan(0);
	}
	
	@Test
	public void completeParkingsTest() {
		List<Parking> found = parkingRepository.getCompleteParkings();

		assertThat(found.size()).isEqualTo(1);
	    assertThat(found.get(0).getFreePlaces()).isEqualTo(0);
	}
	
	@Test
	public void openParkingsByDateTest() {
		// MONDAY -> 1
		Integer day = new Integer(1);
		Integer hour = new Integer(9);
		
		List<Parking> found = parkingRepository.getOpenParkingsByDate(day, hour);

		assertThat(found.size()).isEqualTo(1);
	    assertThat(found.get(0).getOpeningDays()).contains(Days.MONDAY);
	    assertThat(hour).isBetween(found.get(0).getOpeningTime(), found.get(0).getClosingTime());
	}
	
}
