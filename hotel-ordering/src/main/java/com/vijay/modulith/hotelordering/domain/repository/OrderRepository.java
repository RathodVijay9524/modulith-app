package com.vijay.modulith.hotelordering.domain.repository;

import com.vijay.modulith.hotelordering.domain.entity.Order;
import com.vijay.modulith.hotelordering.domain.entity.Order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Find order by order number
    Optional<Order> findByOrderNumber(String orderNumber);

    // Find orders by user
    List<Order> findByUserIdOrderByCreatedAtDesc(Long userId);

    // Find orders by status
    List<Order> findByStatus(OrderStatus status);

    // Find order by reservation
    Optional<Order> findByReservationId(Long reservationId);

    // Find orders by payment method
    List<Order> findByPaymentMethod(String paymentMethod);

    // Find orders by payment transaction ID
    Optional<Order> findByPaymentTransactionId(String paymentTransactionId);

    // Find orders within date range
    @Query("SELECT o FROM Order o WHERE o.createdAt >= :startDate AND o.createdAt <= :endDate")
    List<Order> findByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // Find paid orders by user
    @Query("SELECT o FROM Order o WHERE o.userId = :userId AND o.status = 'PAID' ORDER BY o.createdAt DESC")
    List<Order> findPaidOrdersByUser(@Param("userId") Long userId);

    // Calculate total revenue for a date range
    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.status = 'PAID' " +
            "AND o.paymentCompletedAt >= :startDate AND o.paymentCompletedAt <= :endDate")
    BigDecimal calculateRevenueByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // Find pending orders older than specified time
    @Query("SELECT o FROM Order o WHERE o.status = 'PENDING' AND o.createdAt < :cutoffTime")
    List<Order> findPendingOrdersOlderThan(@Param("cutoffTime") LocalDateTime cutoffTime);

    // Count orders by status
    @Query("SELECT COUNT(o) FROM Order o WHERE o.status = :status")
    Long countByStatus(@Param("status") OrderStatus status);

    // Find orders by hotel (through reservation)
    @Query("SELECT o FROM Order o WHERE o.reservation.hotel.id = :hotelId ORDER BY o.createdAt DESC")
    List<Order> findByHotelId(@Param("hotelId") Long hotelId);

    // Find failed orders for retry
    @Query("SELECT o FROM Order o WHERE o.status = 'FAILED' AND o.createdAt >= :retryAfter")
    List<Order> findFailedOrdersForRetry(@Param("retryAfter") LocalDateTime retryAfter);
}
