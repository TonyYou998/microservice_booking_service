package com.uit.microservice_booking_service.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.uit.microservice_booking_service.entities.Booking;
import com.uit.microservice_booking_service.repository.BookingRepository;
import dto.BookingDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@AllArgsConstructor
@Service
public class BookingServiceImpl implements BookingService{
    private BookingRepository bookingRepository;
    private RestTemplate restTemplate;
    private ModelMapper mapper;
//    private  Stripe stripe;
    @Override
    public BookingDto reserve(Charge c, BookingDto dto, String token) {
        UUID userId=  restTemplate.getForObject("http://user/api/v1/user/get-id?token="+token, UUID.class);
        UUID hostId=restTemplate.getForObject("http://host/api/v1/host/get-host-by-property-id/"+dto.getPropertyId(), UUID.class);
        if(c.getId()!=null && userId!=null){
            Booking b=new Booking();
            b.setPropertyId(UUID.fromString(dto.getPropertyId()));
            b.setUserId(userId);
            b.setCheckInDate(dto.getCheckInDate());
            b.setCheckOutDate(dto.getCheckOutDate());
            b.setGuestStatus("NOT CHECKIN");
            b.setBookingStatus("COMING SOON");
            b.setPriceForStay(c.getAmount());
            b.setGuestAmount(dto.getGuestAmount());
            b.setHostId(hostId);
            bookingRepository.save(b);
            dto= mapper.map(b,BookingDto.class);
            return dto;
        }
        return  null;

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
    public Charge createPayment(BookingDto dto) throws StripeException {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", dto.getAmount()*10);
        chargeParams.put("currency", dto.getCurrency());
        chargeParams.put("source", dto.getToken());

        Charge chargeCreate= Charge.create(chargeParams);
       return  chargeCreate;
    }
    @Override
    public List<BookingDto> getBookingByUserId(String token) {
        try{
            UUID uuid=restTemplate.getForObject("http://user/api/v1/user/get-id?token="+token, UUID.class);
            List<BookingDto> lstDto=new LinkedList<>();
            if(uuid!=null){
               List<Booking> lstBooking= bookingRepository.findBookingByUserId(uuid);
               for(Booking i:lstBooking){
                 BookingDto dto=mapper.map(i,BookingDto.class);
                 lstDto.add(dto);
               }
            }
            return lstDto;
        }
        catch (Exception e){
            return null;
        }
    }

    @Override
    public List<BookingDto> getBookingByPropertyId(String propertyId, UUID hostId) {
        try {
             List<Booking> lstBooking= bookingRepository.findBookingByPropertyIdAndHostId(UUID.fromString(propertyId),hostId);
             List<BookingDto> lstDto=new LinkedList<>();
             lstBooking.forEach((item)->{
                 BookingDto dto= mapper.map(item,BookingDto.class);
                 lstDto.add(dto);
             });
             return lstDto;
//            bookingRepository.findBookingByPropertyId(UUID.fromString(propertyId));
        }
        catch (Exception e){
                return null;
        }

    }

    @Override
    public UUID getHostIdByToken(String token) {
        try {
            UUID hostId=restTemplate.getForObject("http://user/api/v1/user/get-id?token="+token, UUID.class);
            return hostId;
        }

        catch(Exception e){
            return null;
        }
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

