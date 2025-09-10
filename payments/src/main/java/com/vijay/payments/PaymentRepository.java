package com.vijay.payments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    
    List<Payment> findByUserIdOrderByCreatedAtDesc(Long userId);
    
    Optional<Payment> findByTransactionId(String transactionId);
    
    List<Payment> findByBookingId(Long bookingId);
    
    List<Payment> findByStatus(Payment.PaymentStatus status);
    
    long countByUserIdAndStatus(Long userId, Payment.PaymentStatus status);
}
