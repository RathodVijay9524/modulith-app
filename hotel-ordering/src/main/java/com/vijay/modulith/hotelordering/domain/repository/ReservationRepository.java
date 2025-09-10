package com.vijay.modulith.hotelordering.domain.repository;

import com.vijay.modulith.hotelordering.domain.entity.Reservation;
import com.vijay.modulith.hotelordering.domain.entity.Reservation.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // Find reservation by reservation number
    Optional<Reservation> findByReservationNumber(String reservationNumber);

    // Find reservations by user
    List<Reservation> findByUserIdOrderByCreatedAtDesc(Long userId);

    // Find reservations by status
    List<Reservation> findByStatus(ReservationStatus status);

    // Find reservations by hotel
    List<Reservation> findByHotelIdOrderByCreatedAtDesc(Long hotelId);

    // Find reservations by date range
    @Query("SELECT r FROM Reservation r WHERE r.checkInDate >= :startDate AND r.checkOutDate <= :endDate")
    List<Reservation> findByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    // Find overlapping reservations for a room type
    @Query("SELECT r FROM Reservation r WHERE r.roomType.id = :roomTypeId " +
            "AND r.status IN ('CONFIRMED', 'HELD') " +
            "AND ((r.checkInDate <= :checkOut AND r.checkOutDate >= :checkIn))")
    List<Reservation> findOverlappingReservations(@Param("roomTypeId") Long roomTypeId,
                                                  @Param("checkIn") LocalDate checkIn,
                                                  @Param("checkOut") LocalDate checkOut);

    // Find expired held reservations
    @Query("SELECT r FROM Reservation r WHERE r.status = 'HELD' AND r.holdExpiryTime < :currentTime")
    List<Reservation> findExpiredHeldReservations(@Param("currentTime") LocalDateTime currentTime);

    // Find user's active reservations
    @Query("SELECT r FROM Reservation r WHERE r.userId = :userId AND r.status IN ('CONFIRMED', 'HELD')")
    List<Reservation> findActiveReservationsByUser(@Param("userId") Long userId);

    // Count reservations by hotel and date range
    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.hotel.id = :hotelId " +
            "AND r.checkInDate >= :startDate AND r.checkOutDate <= :endDate " +
            "AND r.status = 'CONFIRMED'")
    Long countConfirmedReservationsByHotelAndDateRange(@Param("hotelId") Long hotelId,
                                                       @Param("startDate") LocalDate startDate,
                                                       @Param("endDate") LocalDate endDate);

    // Find reservations checking in today
    @Query("SELECT r FROM Reservation r WHERE r.checkInDate = :date AND r.status = 'CONFIRMED'")
    List<Reservation> findCheckingInToday(@Param("date") LocalDate date);

    // Find reservations checking out today
    @Query("SELECT r FROM Reservation r WHERE r.checkOutDate = :date AND r.status IN ('CONFIRMED', 'CHECKED_IN')")
    List<Reservation> findCheckingOutToday(@Param("date") LocalDate date);
}