package com.cdcm.backend.service.impl;

import com.cdcm.backend.dto.ContactDto;
import com.cdcm.backend.entity.UserEntity;
import com.cdcm.backend.repository.UserRepository;
import com.cdcm.backend.service.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImpTest {
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private UserService userService;


    @BeforeEach
    void setUp() {
    }

    @Test
    public void addContactToUser(){
        UserEntity user = UserEntity.builder()
                .id(UUID.randomUUID())
                .username("pepe")
                .password("perez")
                .build();
        UserEntity contact = UserEntity.builder()
                .id(UUID.randomUUID())
                .username("contact")
                .password("contact-password")
                .build();
//        Mockito.when(userRepository.save(user)).thenReturn(void);
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.findUserEntityByUsername(contact.getUsername()))
                .thenReturn(Optional.of(contact));
        var userDto = userService.addContactToUser(ContactDto.builder().username("contact").build(), user.getId());
        assertNotEquals(user.getContacts().contains(contact), false);
        assertTrue(contact.getContacts().contains(user));
        System.out.println(user);
    }
}