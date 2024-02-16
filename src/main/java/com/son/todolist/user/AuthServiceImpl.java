package com.son.todolist.user;

import com.son.todolist.common.exception.NotFoundException;
import com.son.todolist.common.helper.TokenHelper;
import com.son.todolist.mail.EmailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import static com.son.todolist.common.helper.TokenHelper.generateJwtToken;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;
    private final EmailService emailService;
    private final VerificationTokenRepository verificationTokenRepository;
    @Override
    public String login(UserLoginDto dto) {
        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(dto.getEmail(), dto.getPassword());
        Authentication authenticationResponse = this.authenticationManager.authenticate(authenticationRequest);

        if (authenticationResponse.isAuthenticated()) {
            return jwtEncoder.encode(JwtEncoderParameters.from(generateJwtToken(authenticationResponse))).getTokenValue();
        }

        return null;
    }

    @Transactional
    @Override
    public void register(UserRegisterDto dto) {
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        User user = UserMapper.INSTANCE.registerDtoToUser(dto);
        user.setPassword(encodedPassword);
        user.setRole(RoleEnum.ROLE_OWNER);
        userRepository.save(user);

        String otp = TokenHelper.generateOtp();
        emailService.sendVerificationToken(otp, dto.getEmail());

        VerificationOTP verificationOTP = new VerificationOTP(otp, user);
        verificationTokenRepository.save(verificationOTP);
    }

    @Override
    public void enableAccount(VerificationOTPDto verificationOTPDto) {
        VerificationOTP verificationOTP = verificationTokenRepository.findByOtp(verificationOTPDto.getOtp())
                .orElseThrow(() -> new NotFoundException("Token is not correct."));

        User user = userRepository.findById(verificationOTP.getUser().getId())
                .orElseThrow(() -> new NotFoundException("User not found."));

        user.setEnabled(true);

        userRepository.save(user);
    }
}
