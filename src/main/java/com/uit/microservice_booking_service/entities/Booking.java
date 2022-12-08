package com.uit.microservice_booking_service.entities;

import com.uit.user_service.entities.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class Booking extends BaseEntity {
    private UUID propertyId;
    private UUID userId;
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