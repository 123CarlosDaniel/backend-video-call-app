package com.cdcm.backend.service.impl;

import com.cdcm.backend.entity.RoomEntity;
import com.cdcm.backend.exception.customs.NotFoundException;
import com.cdcm.backend.repository.RoomRepository;
import com.cdcm.backend.service.interfaces.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RoomServiceImp implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public void deleteRoom(UUID id) {
        RoomEntity room = roomRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Not found room"));
        roomRepository.delete(room);
    }
}
