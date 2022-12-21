package com.uit.microservice_booking_service.service;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.checkout.Session;
import com.uit.microservice_booking_service.dto.BookingDto;



public interface BookingService {

    BookingDto reserve(BookingDto dto);

    Session createSession(BookingDto dto) throws StripeException;




    Charge createPayment(int amount, String token, String currency) throws StripeException;
}
