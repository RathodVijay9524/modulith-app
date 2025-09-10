package com.vijay.audit;

import com.vijay.idgeneration.IdGeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service for managing audit operations
 * Provides methods for creating and querying audit logs
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuditService {
    
    private final AuditLogRepository auditLogRepository;
    private final IdGeneratorService idGeneratorService;
    
    /**
     * Create an audit log entry
     */
    public AuditLog createAuditLog(String action, String entityId, String entityType, 
                                  String details, String performedBy) {
        return createAuditLog(action, entityId, entityType, details, performedBy, null, null);
    }
    
    /**
     * Create an audit log entry with additional security info
     */
    public AuditLog createAuditLog(String action, String entityId, String entityType, 
                                  String details, String performedBy, String ipAddress, String userAgent) {
        AuditLog auditLog = AuditLog.builder()
                .action(action)
                .entityId(entityId)
                .entityType(entityType)
                .details(details)
                .performedBy(performedBy)
                .timestamp(LocalDateTime.now())
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                .build();
        
        AuditLog saved = auditLogRepository.save(auditLog);
        log.info("📓 Audit Log Created: {} - {} by {}", action, entityId, performedBy);
        return saved;
    }
    
    /**
     * Get all audit logs for a specific entity
     */
    @Transactional(readOnly = true)
    public List<AuditLog> getAuditLogsForEntity(String entityId) {
        return auditLogRepository.findByEntityIdOrderByTimestampDesc(entityId);
    }
    
    /**
     * Get all audit logs by action type
     */
    @Transactional(readOnly = true)
    public List<AuditLog> getAuditLogsByAction(String action) {
        return auditLogRepository.findByActionOrderByTimestampDesc(action);
    }
    
    /**
     * Get all audit logs by performer
     */
    @Transactional(readOnly = true)
    public List<AuditLog> getAuditLogsByPerformer(String performedBy) {
        return auditLogRepository.findByPerformedByOrderByTimestampDesc(performedBy);
    }
    
    /**
     * Get all audit logs by entity type
     */
    @Transactional(readOnly = true)
    public List<AuditLog> getAuditLogsByEntityType(String entityType) {
        return auditLogRepository.findByEntityTypeOrderByTimestampDesc(entityType);
    }
    
    /**
     * Get recent audit logs (last 100)
     */
    @Transactional(readOnly = true)
    public List<AuditLog> getRecentAuditLogs() {
        return auditLogRepository.findTop100ByOrderByTimestampDesc();
    }
    
    /**
     * Search audit logs with filters
     */
    @Transactional(readOnly = true)
    public List<AuditLog> searchAuditLogs(String action, String entityType, String performedBy, 
                                         LocalDateTime startDate, LocalDateTime endDate) {
        return auditLogRepository.findAuditLogs(action, entityType, performedBy, startDate, endDate);
    }
}
