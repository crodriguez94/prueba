package com.rest.parking.parkingspringrestserviceh2.model;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Parking {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	private Integer openingTime;
	
	private Integer closingTime;
	
	private Integer totalPlaces;
	
	private Integer freePlaces;
	
	  @ElementCollection
	  @CollectionTable(
	        name="OPENING_DAYS", joinColumns=@JoinColumn(name="PARKING_ID")
	  )
	  @Column(name="DAY_OF_WEEK")
	  @Enumerated(EnumType.ORDINAL)
	private List<Days> openingDays;
	
	private Long latitude;
	
	private Long longitude;

	public Parking() {
	}

	public Parking(Long id, String name, Integer openingTime, Integer closingTime, Integer totalPlaces,
			Integer freePlaces, List<Days> openingDays, Long latitude, Long longitude) {
		super();
		this.id = id;
		this.name = name;
		this.openingTime = openingTime;
		this.closingTime = closingTime;
		this.totalPlaces = totalPlaces;
		this.freePlaces = freePlaces;
		this.openingDays = openingDays;
		this.latitude = latitude;
		this.longitude = longitude;
	}

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