package com.uit.microservice_booking_service.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class BookingDto {
    @NotNull
    private LocalDateTime checkIn;
    @NotNull
    private LocalDateTime checkOut;
    @NotNull
    private int guestAmount;
    private String bookingStatus;
    private String guestStatus;
    private double priceForStay;
    @NotNull
    private UUID userId;
    @NotNull
    private UUID propertyId;


}
