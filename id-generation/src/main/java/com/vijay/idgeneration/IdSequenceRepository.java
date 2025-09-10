package com.vijay.idgeneration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for ID sequence management
 * Handles persistence of sequence counters for ID generation
 */
@Repository
public interface IdSequenceRepository extends JpaRepository<IdSequence, String> {
    
    // Additional query methods can be added here if needed
    // e.g., findBySequenceKeyStartingWith for pattern-based queries
}
