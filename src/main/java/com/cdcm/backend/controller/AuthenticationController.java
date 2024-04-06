package com.cdcm.backend.controller;

import com.cdcm.backend.request.UserAuthRequest;
import com.cdcm.backend.response.AuthenticationResponse;
import com.cdcm.backend.service.interfaces.AuthenticationService;
import com.cdcm.backend.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserAuthRequest userAuthRequest) {
        AuthenticationResponse token = authenticationService.register(userAuthRequest);
        return new ResponseEntity<>(token, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserAuthRequest userAuthRequest) {
        AuthenticationResponse token = authenticationService.authenticate(userAuthRequest);
        return ResponseEntity.ok(token);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/logout/{id}")
    public ResponseEntity<?> logout(@PathVariable(name = "id") UUID id){
        authenticationService.logout(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
