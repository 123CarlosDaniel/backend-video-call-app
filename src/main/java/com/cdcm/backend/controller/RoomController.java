package com.cdcm.backend.controller;

import com.cdcm.backend.service.interfaces.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteRoom(@PathVariable(name = "id")UUID id) {
        roomService.deleteRoom(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
