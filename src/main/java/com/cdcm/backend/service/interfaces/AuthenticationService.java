package com.cdcm.backend.service.interfaces;

import com.cdcm.backend.request.UserAuthRequest;
import com.cdcm.backend.response.AuthenticationResponse;

import java.util.UUID;

public interface AuthenticationService {

    AuthenticationResponse register(UserAuthRequest userAuthRequest);

    AuthenticationResponse authenticate(UserAuthRequest userAuthRequest);

    void logout(UUID id);
}
