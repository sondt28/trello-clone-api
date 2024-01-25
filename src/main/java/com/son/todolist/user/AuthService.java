package com.son.todolist.user;

import com.son.todolist.common.helper.JwtHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtHelper jwtHelper;
    private final PasswordEncoder passwordEncoder;

    public String login(UserLoginDto dto) {
        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(dto.email(), dto.password());
        Authentication authenticationResponse = this.authenticationManager.authenticate(authenticationRequest);

        if (authenticationResponse.isAuthenticated()) {
            return jwtHelper.createToken(dto.email());
        }

        return null;
    }

    public void register(UserRegisterDto dto) {
        String encodedPassword = passwordEncoder.encode(dto.password());
        User user = UserMapper.INSTANCE.registerDtoToUser(dto);
        user.setPassword(encodedPassword);

        userRepository.save(user);
    }
}