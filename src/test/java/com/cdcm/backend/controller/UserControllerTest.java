package com.cdcm.backend.controller;

import com.cdcm.backend.dto.UserDto;
import com.cdcm.backend.repository.UserRepository;
import com.cdcm.backend.service.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private UserService userService;
    private static UserDto user;
    @BeforeEach
    void setUp(){
        user = UserDto.builder()
                .id(UUID.randomUUID())
                .username("pepe")
                .build();
    }

    @Test
    @WithMockUser
    public void getUserData() throws Exception {
        Mockito.when(userService.getUserInfoById(user.getId())).thenReturn(user);
        mockMvc.perform(get("/api/v1/user/" + user.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(user.getUsername()));
    }

}




