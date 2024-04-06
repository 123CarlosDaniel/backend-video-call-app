package com.cdcm.backend.repository;

import com.cdcm.backend.entity.UserEntity;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private TestEntityManager testEntityManager;
    private static UserEntity user;

    @BeforeEach
    void setUp(){
        user = UserEntity.builder()
                .username("pepe")
                .password("peerez")
                .build();
        testEntityManager.persist(user);
    }

    @DisplayName("FindUser should return a UserEntity")
    @Test
    public void findUserById() {
        UserEntity userFound = userRepository.findById(user.getId()).orElseThrow();
        assertEquals(userFound, user);
    }

    @Test
    public void notFoundUser() {
        UserEntity user = userRepository.findById(UUID.randomUUID()).orElse(null);
        assertNull(user);
    }
}