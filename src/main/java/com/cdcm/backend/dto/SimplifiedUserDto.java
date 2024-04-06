package com.cdcm.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SimplifiedUserDto {
    private UUID id;
    private String username;
    private long lastConnection;
    private boolean isConnected;
    private List<RoomDto> rooms;
}
