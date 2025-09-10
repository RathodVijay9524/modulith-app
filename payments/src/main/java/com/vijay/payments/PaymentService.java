package com.vijay.payments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class PaymentService {
    
    private final PaymentRepository paymentRepository;
    private final ApplicationEventPublisher eventPublisher;
    
    @Autowired
    public PaymentService(PaymentRepository paymentRepository, 
                         ApplicationEventPublisher eventPublisher) {
        this.paymentRepository = paymentRepository;
        this.eventPublisher = eventPublisher;
    }
    
    public Payment processPayment(Payment payment) {
        // Generate transaction ID
        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setStatus(Payment.PaymentStatus.PROCESSING);
        
        Payment savedPayment = paymentRepository.save(payment);
        
        // Simulate payment processing
        try {
            // In real implementation, integrate with payment gateway
            Thread.sleep(1000); // Simulate processing time
            savedPayment.setStatus(Payment.PaymentStatus.COMPLETED);
            savedPayment = paymentRepository.save(savedPayment);
            
            // Publish payment completed event
        /*    eventPublisher.publishEvent(new PaymentCompletedEvent(
                savedPayment.getId(),
                savedPayment.getUserId(),
                savedPayment.getBookingId(),
                savedPayment.getAmount()
            ));*/
            
        } catch (Exception e) {
            savedPayment.setStatus(Payment.PaymentStatus.FAILED);
            savedPayment.setFailureReason(e.getMessage());
            paymentRepository.save(savedPayment);
        }
        
        return savedPayment;
    }
    
    public Optional<Payment> findById(Long id) {
        return paymentRepository.findById(id);
    }
    
    public List<Payment> findByUserId(Long userId) {
        return paymentRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }
    

}
