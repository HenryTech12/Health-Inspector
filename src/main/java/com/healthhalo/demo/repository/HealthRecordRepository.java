package com.healthhalo.demo.repository;

import com.healthhalo.demo.model.HealthRecordModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthRecordRepository extends JpaRepository<HealthRecordModel,Long> {
}
