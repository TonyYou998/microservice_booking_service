package com.uit.microservice_booking_service.dto;

import lombok.Data;

@Data
public class CheckoutRequest {
    private int amount;
    private String currency;
    private String token;
}
