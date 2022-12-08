package com.uit.microservice_booking_service.controller;

import com.uit.microservice_base_project.common.BaseConstant;
import com.uit.microservice_base_project.config.ResponseHandler;
import com.uit.microservice_booking_service.common.utils.BookingConstants;
import com.uit.microservice_booking_service.dto.BookingDto;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController(BaseConstant.BASE_URL+ BookingConstants.SERVICE_NAME)
public class BookingController {

    @PostMapping(BookingConstants.RESERVE)
    public Object reserveProperty( @Valid @RequestBody BookingDto dto,BindingResult result){
        if(result.hasErrors()){
          return   ResponseHandler.getResponse(HttpStatus.BAD_REQUEST);
        }


    }
}
