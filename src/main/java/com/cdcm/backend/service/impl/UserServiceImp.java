package com.cdcm.backend.service.impl;

import com.cdcm.backend.dto.*;
import com.cdcm.backend.entity.RecordingEntity;
import com.cdcm.backend.entity.RoomEntity;
import com.cdcm.backend.exception.customs.NotFoundException;
import com.cdcm.backend.repository.RecordingRepository;
import com.cdcm.backend.repository.RoomRepository;
import com.cdcm.backend.repository.UserRepository;
import com.cdcm.backend.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RecordingRepository recordingRepository;
    @Override
    public UserDto getUserInfoById(UUID id) {
        var user = userRepository.findById(id).orElseThrow(()-> new NotFoundException("User not found"));
        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .lastConnection(user.getLastConnection())
                .isConnected(user.isConnected())
                .contacts(user.getContacts()
                        .stream()
                        .map(c -> SimplifiedUserDto.builder()
                                .id(c.getId())
                                .username(c.getUsername())
                                .lastConnection(c.getLastConnection())
                                .isConnected(c.isConnected())
                                .rooms(c.getRooms().stream().map(r -> RoomDto.builder()
                                                .id(r.getId())
                                                .name(r.getName())
                                                .description(r.getDescription())
                                                .build())
                                        .collect(Collectors.toList()))
                                .build())
                        .collect(Collectors.toList())
                )
                .rooms(user.getRooms().stream()
                        .map(r -> RoomDto.builder()
                                .id(r.getId())
                                .name(r.getName())
                                .description(r.getDescription())
                                .build())
                        .collect(Collectors.toList()))
                .recordings(user.getRecordings())
                .build();
        return userDto;
    }

    @Override
    public UserDto addContactToUser(ContactDto contactDto, UUID id) {
        var user = userRepository.findById(id).orElseThrow(()->new NotFoundException("User not found"));
        var contact = userRepository.findUserEntityByUsername(contactDto.getUsername())
                .orElseThrow(()->new NotFoundException("User not found"));
        user.getContacts().add(contact);
        contact.getContacts().add(user);
        userRepository.save(user);
        userRepository.save(contact);

        UserDto userDto = getUserInfoById(id);
        return userDto;
    }

    @Override
    public void deleteContactFromUser(ContactDto contactDto, UUID id) {
        var user = userRepository.findById(id).orElseThrow(()->new NotFoundException("User not found"));
        var contact = userRepository.findUserEntityByUsername(contactDto.getUsername())
                .orElseThrow(()->new NotFoundException("User not found"));
        user.getContacts().remove(contact);
        contact.getContacts().remove(user);
        userRepository.save(user);
        userRepository.save(contact);
    }

    @Override
    public void addRoomToUser(RoomDto roomDto, UUID id) {
        var user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));

        user.getRooms().add(RoomEntity.builder()
                        .name(roomDto.getName())
                        .description(roomDto.getDescription())
                        .owner(user)
                .build());
        userRepository.save(user);
    }

    @Override
    public void addRecordingToUser(RecordingDto recordingDto, UUID id) {
        var user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        user.getRecordings().add(RecordingEntity.builder()
                        .id(recordingDto.getId())
                        .name(recordingDto.getName())
                        .recordingKey(recordingDto.getRecordingKey())
                        .creationDate(recordingDto.getCreationDate())
                .build());
        userRepository.save(user);
    }

    @Override
    public List<RecordingEntity> getRecordingsData(UUID id) {
        var user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        return user.getRecordings();
    }

    @Override
    public void deleteRecording(UUID recordingId) {
        recordingRepository.findById(recordingId).orElseThrow(() -> new NotFoundException("Recording not found"));
        recordingRepository.deleteById(recordingId);
    }
}
