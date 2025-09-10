package com.vijay.idgeneration;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Centralized ID generation service for all modules
 * Provides consistent, unique ID generation across the entire modulith
 * 
 * Usage Examples:
 * - Hotel Reservations: RES-10122024-001, RES-10122024-002
 * - Order Numbers: ORD-10122024-001, ORD-10122024-002
 * - Confirmation Numbers: CNF-10122024-001
 * - Booking References: BKG-10122024-001
 */
@Service
@RequiredArgsConstructor
public class IdGenerationService {

    private final IdSequenceRepository sequenceRepository;

    /**
     * Generates a simple sequential ID with prefix
     * Format: PREFIX-000001, PREFIX-000002, etc.
     */
    @Transactional
    public String generateId(String entityKey, String prefix, int width) {
        IdSequence sequence = sequenceRepository.findById(entityKey)
                .orElseGet(() -> new IdSequence(entityKey, 0L));
        sequence.setCurrentValue(sequence.getCurrentValue() + 1);
        sequenceRepository.save(sequence);
        return String.format("%s-%0" + width + "d", prefix, sequence.getCurrentValue());
    }

    /**
     * Generates a date-based ID with daily sequence reset
     * Format: PREFIX-DDMMYYYY-00000001
     * Perfect for hotel reservations, orders, bookings
     */
    @Transactional
    public String generateDateBasedId(String entityKey, String prefix) {
        LocalDate today = LocalDate.now();
        String datePart = today.format(DateTimeFormatter.ofPattern("ddMMyyyy"));

        String key = entityKey + ":" + datePart;
        IdSequence sequence = sequenceRepository.findById(key)
                .orElseGet(() -> new IdSequence(key, 0L));
        sequence.setCurrentValue(sequence.getCurrentValue() + 1);
        sequenceRepository.save(sequence);

        return String.format("%s-%s-%08d", prefix, datePart, sequence.getCurrentValue());
    }

    /**
     * Generates a simple Long ID for internal use
     * Returns: 1, 2, 3, etc.
     */
    @Transactional
    public Long generateLongId(String entityKey) {
        IdSequence sequence = sequenceRepository.findById(entityKey)
                .orElseGet(() -> new IdSequence(entityKey, 0L));
        sequence.setCurrentValue(sequence.getCurrentValue() + 1);
        sequenceRepository.save(sequence);
        return sequence.getCurrentValue();
    }

    // Hotel-specific ID generation methods for convenience
    
    /**
     * Generates hotel reservation ID: RES-10122024-00000001
     */
    public String generateReservationId() {
        return generateDateBasedId("RESERVATION", "RES");
    }

    /**
     * Generates hotel order number: ORD-10122024-00000001
     */
    public String generateOrderNumber() {
        return generateDateBasedId("ORDER", "ORD");
    }

    /**
     * Generates confirmation number: CNF-10122024-00000001
     */
    public String generateConfirmationNumber() {
        return generateDateBasedId("CONFIRMATION", "CNF");
    }

    /**
     * Generates booking reference: BKG-10122024-00000001
     */
    public String generateBookingReference() {
        return generateDateBasedId("BOOKING", "BKG");
    }

    /**
     * Generates user ID: USR-000001
     */
    public String generateUserId() {
        return generateId("USER", "USR", 6);
    }

    /**
     * Generates payment transaction ID: TXN-10122024-00000001
     */
    public String generateTransactionId() {
        return generateDateBasedId("TRANSACTION", "TXN");
    }
}
