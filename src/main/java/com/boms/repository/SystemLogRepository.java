package com.boms.repository;

import com.boms.entity.SystemLog;
import com.boms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SystemLogRepository extends JpaRepository<SystemLog, Long> {
    List<SystemLog> findByOrderByTimestampDesc();
    List<SystemLog> findByUser(User user);
    List<SystemLog> findByAction(String action);
}