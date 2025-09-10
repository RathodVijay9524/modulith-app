package com.vijay.audit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for AuditLog entity operations
 * Provides methods for querying audit logs by various criteria
 */
@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    
    // Find by entity ID (e.g., all logs for a specific user or booking)
    List<AuditLog> findByEntityIdOrderByTimestampDesc(String entityId);
    
    // Find by action type (e.g., all USER_CREATED events)
    List<AuditLog> findByActionOrderByTimestampDesc(String action);
    
    // Find by who performed the action
    List<AuditLog> findByPerformedByOrderByTimestampDesc(String performedBy);
    
    // Find by entity type (e.g., all USER events)
    List<AuditLog> findByEntityTypeOrderByTimestampDesc(String entityType);
    
    // Find logs within a date range
    List<AuditLog> findByTimestampBetweenOrderByTimestampDesc(LocalDateTime startDate, LocalDateTime endDate);
    
    // Find recent logs (last N records)
    List<AuditLog> findTop100ByOrderByTimestampDesc();
    
    // Custom query for complex searches
    @Query("SELECT a FROM AuditLog a WHERE " +
           "(:action IS NULL OR a.action = :action) AND " +
           "(:entityType IS NULL OR a.entityType = :entityType) AND " +
           "(:performedBy IS NULL OR a.performedBy = :performedBy) AND " +
           "(:startDate IS NULL OR a.timestamp >= :startDate) AND " +
           "(:endDate IS NULL OR a.timestamp <= :endDate) " +
           "ORDER BY a.timestamp DESC")
    List<AuditLog> findAuditLogs(@Param("action") String action,
                                @Param("entityType") String entityType,
                                @Param("performedBy") String performedBy,
                                @Param("startDate") LocalDateTime startDate,
                                @Param("endDate") LocalDateTime endDate);
}
