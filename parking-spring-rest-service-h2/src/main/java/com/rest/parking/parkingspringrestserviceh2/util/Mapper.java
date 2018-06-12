package com.rest.parking.parkingspringrestserviceh2.util;

public interface Mapper<T,R> {

    R toDto(T model);
    T toModel(R dto);
}
