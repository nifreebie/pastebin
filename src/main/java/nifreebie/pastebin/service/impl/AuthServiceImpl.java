package nifreebie.pastebin.service.impl;

import lombok.RequiredArgsConstructor;
import nifreebie.pastebin.domain.User;
import nifreebie.pastebin.model.request.LoginRequest;
import nifreebie.pastebin.model.request.RegisterRequest;
import nifreebie.pastebin.model.response.AuthResponse;
import nifreebie.pastebin.repository.UserRepository;
import nifreebie.pastebin.service.AuthService;
import nifreebie.pastebin.util.DuplicateUsernameException;
import nifreebie.pastebin.util.JwtUtil;
import nifreebie.pastebin.util.NotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            throw new DuplicateUsernameException("Уже существует юзер с таким username");
        }

        if (userRepository.existsByEmail(req.getEmail())) {
            throw new DuplicateUsernameException("Уже существует юзер с таким email");
        }

        User user = User.builder()
                .id(UUID.randomUUID())
                .username(req.getUsername())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .roles(Set.of("ROLE_USER"))
                .build();

        userRepository.save(user);
        String token = jwtUtil.generateToken(user.getUsername());

        return AuthResponse.builder()
                .accessToken(token)
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        String token = jwtUtil.generateToken(request.getUsername());

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(NotFoundException::new);

        return AuthResponse.builder()
                .accessToken(token)
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}