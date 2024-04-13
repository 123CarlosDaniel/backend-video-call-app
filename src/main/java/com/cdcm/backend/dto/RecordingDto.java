package com.cdcm.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecordingDto {
    private UUID id;
    private String name;
    private long creationDate;
    private String recordingKey;
}
