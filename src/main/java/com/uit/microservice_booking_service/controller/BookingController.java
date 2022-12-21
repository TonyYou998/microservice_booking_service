package com.uit.microservice_booking_service.controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;


import com.uit.microservice_base_project.common.BaseConstant;
import com.uit.microservice_base_project.config.ResponseHandler;
import com.uit.microservice_booking_service.common.utils.BookingConstants;
import com.uit.microservice_booking_service.dto.BookingDto;
import com.uit.microservice_booking_service.dto.CheckoutRequest;
import com.uit.microservice_booking_service.dto.StripeResponse;
import com.uit.microservice_booking_service.service.BookingService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(BaseConstant.BASE_URL+ BookingConstants.SERVICE_NAME)
@AllArgsConstructor
public class BookingController {

    private  BookingService bookingService;
    private final String AUTHORIZATION_HEADER="Authorization";
    @PostMapping(BookingConstants.RESERVE)
    public Object reserveProperty(@Valid @RequestBody BookingDto dto, @RequestHeader(AUTHORIZATION_HEADER) String token, BindingResult result){
        if(result.hasErrors()){
          return ResponseHandler.getResponse(HttpStatus.BAD_REQUEST);
        }
        return ResponseHandler.getResponse(bookingService.reserve(dto),HttpStatus.OK);
    }
    @GetMapping("/test")
    public Object test(){
        return "test";
    }

    @PostMapping(BookingConstants.CREATE_CHECKOUT_SESSION)
    public ResponseEntity createCheckoutSession(@RequestBody BookingDto dto) throws StripeException {
        Session session=bookingService.createSession(dto);
        StripeResponse stripeResponse=new StripeResponse(session.getId());
        return ResponseHandler.getResponse(stripeResponse,HttpStatus.SEE_OTHER);
    }

    @PostMapping(BookingConstants.CHECKOUT)
    public ResponseEntity checkout (@RequestBody CheckoutRequest checkoutRequest ){
        try {
                bookingService.createPayment(checkoutRequest.getAmount(),checkoutRequest.getToken(),checkoutRequest.getCurrency());
                return  ResponseEntity.ok().build();
        }
        catch (StripeException e){
                return ResponseEntity.badRequest().build();
        }

    }

}


