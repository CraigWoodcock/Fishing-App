package com.craigwoodcock.fishingapp.service;

import com.craigwoodcock.fishingapp.model.entity.AuditAction;
import com.craigwoodcock.fishingapp.model.entity.AuditLog;
import com.craigwoodcock.fishingapp.repository.AuditLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditLogService {
    private final AuditLogRepository auditLogRepository;

    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    /**
     * Records a single admin action. targetDescription should be a
     * short, human-readable identifier of what was acted on — a
     * username, a session's venue, an angler's name — rather than
     * a raw database id, so the log is readable without cross-
     * referencing other tables.
     */

    public void log(String performedBy, AuditAction action, String targetDescription) {
        AuditLog entry = new AuditLog();
        entry.setPerformedBy(performedBy);
        entry.setAction(action);
        entry.setTargetDescription(targetDescription);
        entry.setTimestamp(LocalDateTime.now());
        auditLogRepository.save(entry);
    }

    public List<AuditLog> getRecentLogs() {
        return auditLogRepository.findAllByOrderByTimestampDesc();
    }
}
