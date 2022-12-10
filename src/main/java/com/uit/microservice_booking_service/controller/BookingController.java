package com.uit.microservice_booking_service.controller;

import com.uit.microservice_base_project.common.BaseConstant;
import com.uit.microservice_base_project.config.ResponseHandler;
import com.uit.microservice_booking_service.common.utils.BookingConstants;
import com.uit.microservice_booking_service.dto.BookingDto;
import com.uit.microservice_booking_service.service.BookingService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.http.HttpStatus;
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
}
