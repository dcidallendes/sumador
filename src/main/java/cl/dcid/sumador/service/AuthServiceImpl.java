package cl.dcid.sumador.service;

import cl.dcid.sumador.controller.exceptions.UserAlreadyExistsException;
import cl.dcid.sumador.dto.SigninRequest;
import cl.dcid.sumador.dto.SigninResult;
import cl.dcid.sumador.dto.SignupRequest;
import cl.dcid.sumador.interfaces.AuthService;
import cl.dcid.sumador.model.User;
import cl.dcid.sumador.repository.UserRepository;
import cl.dcid.sumador.security.JwtUtils;
import cl.dcid.sumador.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Override
    public SigninResult signin(SigninRequest signinRequest) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(signinRequest.getUsername(),
                                signinRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String accessToken = jwtUtils.generate(userDetails.getUsername());

        SigninResult result = new SigninResult(accessToken);
        return result;
    }

    @Override
    public void signup(SignupRequest signupRequest) throws UserAlreadyExistsException {
        Map<String, String> errors = new HashMap<>();

        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            errors.put("username", "Username already exists");
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            errors.put("email", "Email already exists");
        }

        if (!errors.isEmpty()) {
            throw new UserAlreadyExistsException(errors);
        }

        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setPassword(encoder.encode(signupRequest.getPassword()));
        user.setUsername(signupRequest.getUsername());

        userRepository.save(user);
    }
}
