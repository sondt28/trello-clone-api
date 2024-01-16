package com.son.todolist.user;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.son.todolist.helper.JwtHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtHelper jwtHelper;
    private final PasswordEncoder passwordEncoder;
    private final GoogleIdTokenVerifier googleIdTokenVerifier;

    public UserService(@Value("${spring.security.oauth2.client.registration.google.client-id}") String clientId,
                       UserRepository userRepository,
                       AuthenticationManager authenticationManager,
                       JwtHelper jwtHelper,
                       PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtHelper = jwtHelper;
        this.passwordEncoder = passwordEncoder;
        NetHttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();
        this.googleIdTokenVerifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singleton(clientId))
                .build();
    }

    public String loginOAuthGoogle(IdTokenDto idTokenDto) {
        User user = verifyIdToken(idTokenDto.idToken());
        if (user == null) {
            return null;
        }

        user = createUser(user);

        return jwtHelper.createToken(user.getEmail());
    }

    private User createUser(User user) {
        User existedUser = userRepository.findByEmail(user.getEmail());
        if (existedUser == null) {
            return userRepository.save(user);
        }

        return existedUser;
    }

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

    private User verifyIdToken(String idToken) {
        try {
            GoogleIdToken idTokenObj = googleIdTokenVerifier.verify(idToken);

            if (idTokenObj == null) {
                return null;
            }

            GoogleIdToken.Payload payload = idTokenObj.getPayload();
            String email = payload.getEmail();
            String firstName = (String) payload.get("given_name");
            String lastName = (String) payload.get("family_name");
            String fullName = firstName + lastName;

            return new User(fullName, email, RoleEnum.ROLE_OWNER);
        } catch (GeneralSecurityException | IOException e) {
            return null;
        }
    }
}
