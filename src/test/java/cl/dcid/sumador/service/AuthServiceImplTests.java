package cl.dcid.sumador.service;

import cl.dcid.sumador.controller.exceptions.UserAlreadyExistsException;
import cl.dcid.sumador.dto.SigninRequest;
import cl.dcid.sumador.dto.SigninResult;
import cl.dcid.sumador.dto.SignupRequest;
import cl.dcid.sumador.interfaces.AuthService;
import cl.dcid.sumador.model.User;
import cl.dcid.sumador.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthServiceImplTests {
    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;

    private final String testUsername = "testuser";
    private final String testPassword = "testpaswword";
    private final String testEmail = "1234@qwerty.com";

    @Test
    void signupCorrect() throws UserAlreadyExistsException {
        SignupRequest signupRequest = getTestSignupRequest();

        assertDoesNotThrow(() -> authService.signup(signupRequest));
    }

    @Test
    void signupUserAlreadyExists() throws UserAlreadyExistsException {
        when(userRepository.existsByEmail(testEmail)).thenReturn(false);
        when(userRepository.existsByUsername(testUsername)).thenReturn(true);

        SignupRequest signupRequest = getTestSignupRequest();

        assertThrows(UserAlreadyExistsException.class, () -> authService.signup(signupRequest));
    }

    @Test
    void signupEmailAlreadyExists() throws UserAlreadyExistsException {
        when(userRepository.existsByEmail(testEmail)).thenReturn(true);
        when(userRepository.existsByUsername(testUsername)).thenReturn(false);

        SignupRequest signupRequest = getTestSignupRequest();

        assertThrows(UserAlreadyExistsException.class, () -> authService.signup(signupRequest));
    }

    @Test
    void signinCorrect() {
        SigninRequest signinRequest = getTestSigninRequest();
        User user = getTestUser();

        when(userRepository.findByUsername(testUsername)).thenReturn(Optional.of(user));

        SigninResult result = authService.signin(signinRequest);

        assertThat(result).isNotNull();
        assertThat(result.getAccessToken()).isNotNull();
    }

    @Test
    void signinIncorrect() {
        SigninRequest signinRequest = getTestSigninRequest();
        User user = getTestUser();
        user.setPassword(passwordEncoder.encode("otherpassword"));

        when(userRepository.findByUsername(testUsername)).thenReturn(Optional.of(user));

        assertThrows(AuthenticationException.class, () -> authService.signin(signinRequest));
    }

    private SignupRequest getTestSignupRequest() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail(testEmail);
        signupRequest.setUsername(testUsername);
        signupRequest.setPassword(testPassword);

        return signupRequest;
    }

    private SigninRequest getTestSigninRequest() {
        SigninRequest signinRequest = new SigninRequest();
        signinRequest.setUsername(testUsername);
        signinRequest.setPassword(testPassword);

        return signinRequest;
    }

    private User getTestUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername(testUsername);
        user.setEmail(testEmail);
        user.setPassword(passwordEncoder.encode(testPassword));

        return user;
    }
}
