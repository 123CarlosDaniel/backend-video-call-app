package com.cdcm.backend.repository;

import com.cdcm.backend.entity.RecordingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RecordingRepository extends JpaRepository<RecordingEntity, UUID> {
}
