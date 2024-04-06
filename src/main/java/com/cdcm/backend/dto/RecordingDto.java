package com.cdcm.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecordingDto {
    private String title;
    private long creationDate;
    private String recordingKey;
}
