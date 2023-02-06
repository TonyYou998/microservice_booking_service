package com.uit.microservice_booking_service.entities;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class Booking extends BaseEntity {
    @GeneratedValue(generator = "UUID")
    @Type(type = "uuid-char")
    @GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
    private UUID propertyId;
    @GeneratedValue(generator = "UUID")
    @Type(type = "uuid-char")
    @GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
    private UUID userId;
    @GeneratedValue(generator = "UUID")
    @Type(type = "uuid-char")
    @GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
    private UUID hostId;
    private String image;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private int guestAmount;
    private boolean isRefund;
    private double pricePerDay;
    private double priceForStay;
    private double taxFee;
    private double refundPaid;
    private String bookingStatus;
    private double deposit;
    private String guestStatus;
}