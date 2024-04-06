package com.cdcm.backend.service.interfaces;

import com.cdcm.backend.dto.ContactDto;
import com.cdcm.backend.dto.RecordingDto;
import com.cdcm.backend.dto.RoomDto;
import com.cdcm.backend.dto.UserDto;
import com.cdcm.backend.entity.UserEntity;

import java.util.UUID;

public interface UserService {

    UserDto getUserInfoById(UUID id);

    UserDto addContactToUser(ContactDto contactDto, UUID id);

    void deleteContactFromUser(ContactDto contactDto, UUID id);

    void addRoomToUser(RoomDto roomDto, UUID id);

    void addRecordingToUser(RecordingDto recordingDto, UUID id);
}
