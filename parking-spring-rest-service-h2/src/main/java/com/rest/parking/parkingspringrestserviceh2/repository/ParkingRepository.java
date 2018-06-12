package com.rest.parking.parkingspringrestserviceh2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rest.parking.parkingspringrestserviceh2.model.Parking;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, Long> {

	@Query(value = "SELECT * FROM PARKING p WHERE p.FREE_PLACES > 0", nativeQuery = true)
	public List<Parking> getFreePlacesParkings();

	@Query(value = "SELECT * FROM PARKING p WHERE p.FREE_PLACES = 0", nativeQuery = true)
	public List<Parking> getCompleteParkings();

	@Query(value = "SELECT * FROM PARKING p LEFT JOIN OPENING_DAYS o ON p.ID = o.PARKING_ID WHERE o.DAY_OF_WEEK = :day AND :hour BETWEEN  p.OPENING_TIME and p.CLOSING_TIME", nativeQuery = true)
	public List<Parking> getOpenParkingsByDate(@Param("day") Integer day, @Param("hour") Integer hour);
}
