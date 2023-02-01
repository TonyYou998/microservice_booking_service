package com.uit.microservice_booking_service.controller;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.checkout.Session;
import com.uit.microservice_booking_service.common.utils.BookingConstants;

import com.uit.microservice_booking_service.service.BookingService;
import common.BaseConstant;
import common.ResponseHandler;
import dto.BookingDto;
import dto.StripeResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(BaseConstant.BASE_URL+ BookingConstants.SERVICE_NAME)
@AllArgsConstructor
public class BookingController {
    private  BookingService bookingService;
    private final String AUTHORIZATION_HEADER="Authorization";
    private final String ROLE_HEADER="Role";
    @PostMapping(BookingConstants.CREATE_CHECKOUT_SESSION)
    public ResponseEntity createCheckoutSession(@RequestBody BookingDto dto) throws StripeException {
        Session session=bookingService.createSession(dto);
        StripeResponse stripeResponse=new StripeResponse(session.getId());
        Object response= ResponseHandler.getResponse(stripeResponse);
        return  new ResponseEntity(response,HttpStatus.SEE_OTHER);
    }

    @PostMapping(BookingConstants.CHECKOUT)
    public Object checkout (@Valid @RequestBody BookingDto dto,@RequestHeader(name=AUTHORIZATION_HEADER,defaultValue = "") String role ,BindingResult result){
        if(role.isBlank())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        if(result.hasErrors())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        try {
              Charge c=  bookingService.createPayment(dto);
                Object response=  ResponseHandler.getResponse (bookingService.reserve(c,dto,role));
                return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch (StripeException e){
                 return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(BookingConstants.GET_BOOKING_BY_USER_ID)
    public Object getBookingByUserId(@RequestHeader(name=AUTHORIZATION_HEADER,defaultValue = "") String token){
        if(token.isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Object response=ResponseHandler.getResponse(bookingService.getBookingByUserId(token));
         return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping(BookingConstants.GET_BOOKING_BY_PROPERTY_ID)
    public Object getBookingByPropertyId(@RequestHeader(name = AUTHORIZATION_HEADER,defaultValue = "") String token, @RequestHeader(name=ROLE_HEADER,defaultValue = "")String role, @RequestParam("propertyId") String propertyId){
        UUID hostId= bookingService.getHostIdByToken(token);
        Object response=ResponseHandler.getResponse(bookingService.getBookingByPropertyId(propertyId,hostId));
        return new ResponseEntity<>(response,HttpStatus.OK);


    }



}


