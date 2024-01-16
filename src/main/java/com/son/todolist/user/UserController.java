package com.son.todolist.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/login/oauth2/google")
    public ResponseEntity<String> login(@RequestBody IdTokenDto idTokenDto) {
        String token = service.loginOAuthGoogle(idTokenDto);
        if (token == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDto dto) {
        String token = service.login(dto);
        if (token == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody UserRegisterDto dto, UriComponentsBuilder ucb) {
        service.register(dto);
        URI uri = ucb.path("/users/login").build().toUri();

        return ResponseEntity.created(uri).build();
    }
}
