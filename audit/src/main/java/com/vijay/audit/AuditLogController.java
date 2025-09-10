package com.vijay.audit;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * REST Controller for audit log operations
 * Provides endpoints for querying audit logs with various filters
 */
@RestController
@RequestMapping("/api/audit-logs")
@RequiredArgsConstructor
public class AuditLogController {
    
    private final AuditService auditService;
    
    /**
     * Get recent audit logs (last 100)
     */
    @GetMapping
    public ResponseEntity<List<AuditLog>> getRecentAuditLogs() {
        List<AuditLog> logs = auditService.getRecentAuditLogs();
        return ResponseEntity.ok(logs);
    }
    
    /**
     * Get audit logs for a specific entity
     */
    @GetMapping("/entity/{entityId}")
    public ResponseEntity<List<AuditLog>> getAuditLogsForEntity(@PathVariable String entityId) {
        List<AuditLog> logs = auditService.getAuditLogsForEntity(entityId);
        return ResponseEntity.ok(logs);
    }
    
    /**
     * Get audit logs by action type
     */
    @GetMapping("/action/{action}")
    public ResponseEntity<List<AuditLog>> getAuditLogsByAction(@PathVariable String action) {
        List<AuditLog> logs = auditService.getAuditLogsByAction(action);
        return ResponseEntity.ok(logs);
    }
    
    /**
     * Get audit logs by performer
     */
    @GetMapping("/performer/{performedBy}")
    public ResponseEntity<List<AuditLog>> getAuditLogsByPerformer(@PathVariable String performedBy) {
        List<AuditLog> logs = auditService.getAuditLogsByPerformer(performedBy);
        return ResponseEntity.ok(logs);
    }
    
    /**
     * Get audit logs by entity type
     */
    @GetMapping("/entity-type/{entityType}")
    public ResponseEntity<List<AuditLog>> getAuditLogsByEntityType(@PathVariable String entityType) {
        List<AuditLog> logs = auditService.getAuditLogsByEntityType(entityType);
        return ResponseEntity.ok(logs);
    }
    
    /**
     * Search audit logs with multiple filters
     */
    @GetMapping("/search")
    public ResponseEntity<List<AuditLog>> searchAuditLogs(
            @RequestParam(required = false) String action,
            @RequestParam(required = false) String entityType,
            @RequestParam(required = false) String performedBy,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        
        List<AuditLog> logs = auditService.searchAuditLogs(action, entityType, performedBy, startDate, endDate);
        return ResponseEntity.ok(logs);
    }
    
    /**
     * Get audit statistics
     */
    @GetMapping("/stats")
    public ResponseEntity<AuditStats> getAuditStats() {
        // This could be expanded to provide audit statistics
        List<AuditLog> recentLogs = auditService.getRecentAuditLogs();
        AuditStats stats = new AuditStats(recentLogs.size(), "Recent activity count");
        return ResponseEntity.ok(stats);
    }
    
    /**
     * Simple stats class for audit statistics
     */
    public record AuditStats(int count, String description) {}
}
