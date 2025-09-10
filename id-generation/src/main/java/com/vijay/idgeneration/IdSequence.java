package com.vijay.idgeneration;

import com.vijay.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entity for storing ID sequence counters
 * Used for generating unique, sequential IDs across all modules
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "id_sequences")
public class IdSequence extends BaseEntity {
    

    private String sequenceKey; // e.g., "RESERVATION", "ORDER", "BOOKING"
    
    private Long currentValue;
    
    public IdSequence() {}
    
    public IdSequence(String sequenceKey, Long currentValue) {
        this.sequenceKey = sequenceKey;
        this.currentValue = currentValue;
    }
}
