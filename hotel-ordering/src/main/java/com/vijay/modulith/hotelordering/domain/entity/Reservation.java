package com.vijay.modulith.hotelordering.domain.entity;

import com.vijay.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Reservation extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    private String reservationNumber;

    @Column(nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_type_id", nullable = false)
    private RoomType roomType;

    @Column(nullable = false)
    private LocalDate checkInDate;

    @Column(nullable = false)
    private LocalDate checkOutDate;

    @Column(nullable = false)
    private Integer numberOfRooms;

    @Column(nullable = false)
    private Integer numberOfGuests;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(length = 100)
    private String guestName;

    @Column(length = 100)
    private String guestEmail;

    @Column(length = 20)
    private String guestPhone;

    @Column(length = 1000)
    private String specialRequests;

    private LocalDateTime holdExpiryTime;

    @Column(length = 500)
    private String cancellationReason;

    public enum ReservationStatus {
        HELD, CONFIRMED, CANCELLED, EXPIRED, CHECKED_IN, CHECKED_OUT
    }
}