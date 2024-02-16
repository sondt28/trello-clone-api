package com.son.todolist.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginDto dto) {
        String token = service.login(dto);
        if (token == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody UserRegisterDto dto, UriComponentsBuilder ucb) {
        service.register(dto);
        URI uri = ucb.path("/auth/verify-otp").build().toUri();

        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<Void> verifyAccount(@Valid @RequestBody VerificationOTPDto dto) {
        service.enableAccount(dto);
        return ResponseEntity.noContent().build();
    }
}
