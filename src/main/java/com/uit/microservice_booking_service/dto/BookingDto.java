package com.uit.microservice_booking_service.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class BookingDto {
    @NotNull
    private LocalDateTime checkInDate;
    @NotNull
    private LocalDateTime checkOutDate;
    @NotNull
    private int guestAmount;
    private String bookingStatus;
    private String guestStatus;
    private double priceForStay;
    private String userId;
    @NotNull
    private String propertyId;
    private String propertyName;
    @NotNull
    private int amount;
    @NotNull
    private String currency;
    @NotNull
    private String token;


}
