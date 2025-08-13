package nifreebie.pastebin.service;

import nifreebie.pastebin.domain.User;
import nifreebie.pastebin.model.dto.UserDTO;
import nifreebie.pastebin.model.request.LoginRequest;
import nifreebie.pastebin.model.request.RegisterRequest;
import nifreebie.pastebin.model.response.AuthResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest registerRequest);
    AuthResponse login(LoginRequest loginRequest);
}
