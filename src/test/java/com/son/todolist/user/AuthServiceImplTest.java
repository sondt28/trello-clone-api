package com.son.todolist.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class AuthServiceImplTest {
    @InjectMocks
    private AuthServiceImpl service;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void testRegister() {
        UserRegisterDto dto = UserRegisterDto
                .builder()
                .email("son@le")
                .password("123")
                .build();

        when(passwordEncoder.encode(dto.password())).thenReturn("encodedPassword123");

        service.register(dto);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(userArgumentCaptor.capture());

        User user = userArgumentCaptor.getValue();
        assertThat("son@le").isEqualTo(user.getEmail());
        assertThat("encodedPassword123").isEqualTo(user.getPassword());
        assertThat(RoleEnum.ROLE_OWNER).isEqualTo(user.getRole());
    }
}
