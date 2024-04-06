package com.cdcm.backend.service.impl;

import com.cdcm.backend.entity.UserEntity;
import com.cdcm.backend.exception.customs.DuplicateInstanceException;
import com.cdcm.backend.exception.customs.NotFoundException;
import com.cdcm.backend.repository.UserRepository;
import com.cdcm.backend.request.UserAuthRequest;
import com.cdcm.backend.response.AuthenticationResponse;
import com.cdcm.backend.security.utils.JwtUtils;
import com.cdcm.backend.service.interfaces.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class AuthenticationServiceImp implements AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;
    @Override
    public AuthenticationResponse register(UserAuthRequest userAuthRequest) {
        var foundUser = userRepository.findUserEntityByUsername(userAuthRequest.getUsername());
        if(foundUser.isPresent()) throw new DuplicateInstanceException("Username is already used");

        UserEntity user = UserEntity.builder()
                .username(userAuthRequest.getUsername())
                .password(passwordEncoder.encode(userAuthRequest.getPassword()))
                .build();
        userRepository.save(user);
        String token = jwtUtils.generateJwt(user);
        return new AuthenticationResponse(token, user.getId());
    }

    @Override
    public AuthenticationResponse authenticate(UserAuthRequest userAuthRequest) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(userAuthRequest.getUsername()
                , userAuthRequest.getPassword());
        authenticationManager.authenticate(authenticationToken);
        UserEntity user = userRepository.findUserEntityByUsername(userAuthRequest.getUsername()).get();
        String token = jwtUtils.generateJwt(user);
        user.setConnected(true);
        userRepository.save(user);
        return new AuthenticationResponse(token, user.getId());
    }

    @Override
    public void logout(UUID id) {
        var user = userRepository.findById(id).orElseThrow(()->new NotFoundException("User not found"));
        user.setConnected(false);
        user.setLastConnection(System.currentTimeMillis());
        userRepository.save(user);
    }
}
