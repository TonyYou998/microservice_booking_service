package com.uit.microservice_booking_service.service;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.checkout.Session;
import dto.BookingDto;


import java.util.List;
import java.util.UUID;


public interface BookingService {

    BookingDto reserve(Charge c, BookingDto dto, String role);

    Session createSession(BookingDto dto) throws StripeException;




    Charge createPayment(BookingDto dto) throws StripeException;

    List<BookingDto> getBookingByUserId(String token);

    List<BookingDto> getBookingByPropertyId(String propertyId, UUID hostId);

    UUID  getHostIdByToken(String token);
}
