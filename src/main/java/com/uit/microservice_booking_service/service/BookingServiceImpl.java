package com.uit.microservice_booking_service.service;

import com.uit.microservice_booking_service.dto.BookingDto;
import com.uit.microservice_booking_service.entities.Booking;
import com.uit.microservice_booking_service.repository.BookingRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BookingServiceImpl implements BookingService{
    private BookingRepository bookingRepository;
    private ModelMapper mapper;
    @Override
    public BookingDto reserve(BookingDto dto) {
        Booking b=new Booking();
        b.setPropertyId(dto.getPropertyId());
        b.setUserId(dto.getUserId());
        b.setCheckInDate(dto.getCheckIn());
        b.setCheckOutDate(dto.getCheckOut());
        b.setGuestStatus("NOT CHECKIN");
        b.setGuestAmount(dto.getGuestAmount());
        bookingRepository.save(b);
        dto= mapper.map(b,BookingDto.class);
        return dto;
    }
}
