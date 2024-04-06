package com.cdcm.backend.dto;

import com.cdcm.backend.entity.RecordingEntity;
import com.cdcm.backend.entity.RoomEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserDto {
    private UUID id;
    private String username;
    private long lastConnection;
    private boolean isConnected;
    private List<SimplifiedUserDto> contacts;
    private List<RoomDto> rooms;
    private List<RecordingEntity> recordings;
}
