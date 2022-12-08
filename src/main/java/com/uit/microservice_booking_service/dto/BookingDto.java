package com.uit.microservice_booking_service.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class BookingDto {
    @NotEmpty
    private LocalDateTime checkIn;
    @NotEmpty
    private LocalDateTime checkOut;
    @NotEmpty
    private int guestAmount;
    private String bookingStatus;
    private String guestStatus;
    private double priceForStay;
    @NotEmpty
    private UUID userId;
    @NotEmpty
    private UUID propertyId;


}
