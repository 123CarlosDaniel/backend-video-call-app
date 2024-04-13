package com.cdcm.backend.controller;

import com.cdcm.backend.dto.ContactDto;
import com.cdcm.backend.dto.RecordingDto;
import com.cdcm.backend.dto.RoomDto;
import com.cdcm.backend.dto.UserDto;
import com.cdcm.backend.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/user")
@PreAuthorize("isAuthenticated()")
public class UserController {

    private final UserService userService;
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserData(@PathVariable(name = "id") UUID id) {
        var userFound = userService.getUserInfoById(id);
        return ResponseEntity.ok(userFound);
    }

    @PostMapping("/{id}/add-contact")
    public ResponseEntity<?> addContact(@PathVariable(name = "id") UUID id, @RequestBody ContactDto contactDto) {
        UserDto userDto = userService.addContactToUser(contactDto, id);
        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping("/{id}/delete-contact")
    public ResponseEntity<?> deleteContact(@PathVariable(name = "id") UUID id, @RequestBody ContactDto contactDto) {
        userService.deleteContactFromUser(contactDto, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{id}/add-room")
    public ResponseEntity<?> addRoom(@PathVariable(name = "id") UUID id, @RequestBody RoomDto roomDto) {
        userService.addRoomToUser(roomDto, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/{id}/add-recording")
    public ResponseEntity<?> addRecording(@PathVariable(name = "id") UUID id, @RequestBody RecordingDto recordingDto) {
        userService.addRecordingToUser(recordingDto, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @GetMapping("/{id}/add-recording")
    public ResponseEntity<?> getRecordingsData(@PathVariable(name = "id") UUID id) {
        return ResponseEntity.ok(userService.getRecordingsData(id));
    }
}
