package com.boms.service;

import com.boms.entity.SystemLog;
import com.boms.entity.User;
import com.boms.repository.SystemLogRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SystemLogService {
    
    private final SystemLogRepository logRepository;
    
    public SystemLogService(SystemLogRepository logRepository) {
        this.logRepository = logRepository;
    }
    
    public void log(User user, String action, String description) {
        SystemLog log = new SystemLog(user, action, description);
        logRepository.save(log);
    }
    
    public void log(String action, String description) {
        SystemLog log = new SystemLog();
        log.setAction(action);
        log.setDescription(description);
        logRepository.save(log);
    }
    
    public List<SystemLog> getAllLogs() {
        return logRepository.findByOrderByTimestampDesc();
    }
    
    public List<SystemLog> getLogsByUser(User user) {
        return logRepository.findByUser(user);
    }
    
    public List<SystemLog> getLogsByAction(String action) {
        return logRepository.findByAction(action);
    }
}