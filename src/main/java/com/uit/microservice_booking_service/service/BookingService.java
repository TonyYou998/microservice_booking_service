package com.uit.microservice_booking_service.service;

import com.uit.microservice_booking_service.dto.BookingDto;



public interface BookingService {

    BookingDto reserve(BookingDto dto);
}
