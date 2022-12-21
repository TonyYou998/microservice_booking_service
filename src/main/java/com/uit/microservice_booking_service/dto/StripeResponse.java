package com.uit.microservice_booking_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StripeResponse {
    private String sessionId;
//    private String successUrl;
//    private String cancleUrl;

}
