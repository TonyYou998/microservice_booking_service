package com.uit.microservice_booking_service.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.uit.microservice_booking_service.dto.BookingDto;
import com.uit.microservice_booking_service.entities.Booking;
import com.uit.microservice_booking_service.repository.BookingRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class BookingServiceImpl implements BookingService{
    private BookingRepository bookingRepository;
    private ModelMapper mapper;
//    private  Stripe stripe;
    @Override
    public BookingDto reserve(BookingDto dto) {
        Booking b=new Booking();
        b.setPropertyId(dto.getPropertyId());
        b.setUserId(dto.getUserId());
        b.setCheckInDate(dto.getCheckIn());
        b.setCheckOutDate(dto.getCheckOut());
        b.setGuestStatus("NOT CHECKIN");
        b.setGuestAmount(dto.getGuestAmount());
        bookingRepository.save(b);
        dto= mapper.map(b,BookingDto.class);
        return dto;
    }

    @Override
    public Session createSession(BookingDto dto) throws StripeException {
        String baseURL="http://localhost:3000/";
        String apiKey="sk_test_51MFgtADG7fwzFQTe8OyidrYMyAe45WaFn3hDvGTW5nKm5AtbImyjucKZEKlBOKmfJGzGGiSSAgp2WofGNNWoOOiC00CZdCo6sa";
        String successURL = baseURL + "payment/success";
        String failedURL = baseURL + "payment/failed";

        // set the private key
        Stripe.apiKey = apiKey;

        List<SessionCreateParams.LineItem> sessionItemsList = new ArrayList<>();

        // for each product compute SessionCreateParams.LineItem
        sessionItemsList.add(createSessionLineItem(dto));


        // build the session param
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setCancelUrl(failedURL)
                .addAllLineItem(sessionItemsList)
                .setSuccessUrl(successURL)
                .build();
        return Session.create(params);
    }

    @Override
    public Charge createPayment(int amount, String token, String currency) throws StripeException {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", amount*10);
        chargeParams.put("currency", currency);
        chargeParams.put("source", token);

        return Charge.create(chargeParams);
//        PaymentIntentCreateParams parmas=PaymentIntentCreateParams
//                .builder()
//                .setAmount((long) amount)
//                .setCurrency("usd")
//                .addPaymentMethodType("card")
//                .build();
//        PaymentIntent paymentIntent= PaymentIntent.create(parmas);
//        return  paymentIntent;
    }

    SessionCreateParams.LineItem createSessionLineItem(BookingDto dto) {
        return SessionCreateParams.LineItem.builder()
                // set price for each product
                .setPriceData(createPriceData(dto))
                // set quantity for each product
                .setQuantity((long)dto.getGuestAmount())
                .build();
    }
    SessionCreateParams.LineItem.PriceData createPriceData(BookingDto dto) {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("usd")
                .setUnitAmount( ((long) dto.getPriceForStay()) * 100)
                .setProductData(
                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName(dto.getPropertyName())
                                .build())
                .build();
    }

}

