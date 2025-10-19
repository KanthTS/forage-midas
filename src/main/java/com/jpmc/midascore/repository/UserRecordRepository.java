package com.jpmc.midascore.repository;

import com.jpmc.midascore.entity.UserRecord;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRecordRepository extends JpaRepository<UserRecord, Long> {
    Optional<UserRecord> findById(Long id);
    List<UserRecord> findByName(String name);
}
