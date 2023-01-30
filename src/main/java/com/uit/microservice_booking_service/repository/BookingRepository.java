package com.uit.microservice_booking_service.repository;

import com.uit.microservice_booking_service.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {

    List<Booking> findBookingByUserId(UUID uuid);

    List<Booking> findBookingByPropertyId(UUID uid);


    List<Booking> findBookingByPropertyIdAndHostId(UUID fromString, UUID hostId);
}
