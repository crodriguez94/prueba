package com.rest.parking.parkingspringrestserviceh2.model;

import java.util.List;

public class ParkingDto {
	
    private Long id;

    private String name;

    private Integer openingTime;

    private Integer closingTime;

    private Integer totalPlaces;

    private Integer freePlaces;

    private List<Days> openingDays;

    private Long latitude;

    private Long longitude;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOpeningTime() {
		return openingTime;
	}

	public void setOpeningTime(Integer openingTime) {
		this.openingTime = openingTime;
	}

	public Integer getClosingTime() {
		return closingTime;
	}

	public void setClosingTime(Integer closingTime) {
		this.closingTime = closingTime;
	}

	public Integer getTotalPlaces() {
		return totalPlaces;
	}

	public void setTotalPlaces(Integer totalPlaces) {
		this.totalPlaces = totalPlaces;
	}

	public Integer getFreePlaces() {
		return freePlaces;
	}

	public void setFreePlaces(Integer freePlaces) {
		this.freePlaces = freePlaces;
	}

	public List<Days> getOpeningDays() {
		return openingDays;
	}

	public void setOpeningDays(List<Days> openingDays) {
		this.openingDays = openingDays;
	}

	public Long getLatitude() {
		return latitude;
	}

	public void setLatitude(Long latitude) {
		this.latitude = latitude;
	}

	public Long getLongitude() {
		return longitude;
	}

	public void setLongitude(Long longitude) {
		this.longitude = longitude;
	}
    
    
}
