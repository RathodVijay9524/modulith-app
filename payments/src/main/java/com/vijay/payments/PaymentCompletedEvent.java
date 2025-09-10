package com.vijay.payments;

import java.math.BigDecimal;

/**
 * Event published when a payment is completed successfully.
 * Other modules can listen to this event to perform post-payment operations.
 */
public record PaymentCompletedEvent(Long paymentId, Long userId, Long bookingId, BigDecimal amount) {
}
