package com.cdcm.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "recording_tbl")
public class RecordingEntity {
    @Id
    @GeneratedValue
    private UUID id;
    private String title;
    private long creationDate;
    private String recordingKey;
}
