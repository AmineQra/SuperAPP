package com.meal.Dishu.service;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.meal.Dishu.config.JwtService;
import com.meal.Dishu.dto.LoginRequest;
import com.meal.Dishu.dto.RegisterRequest;
import com.meal.Dishu.enumeration.Role;
import com.meal.Dishu.model.User;
import com.meal.Dishu.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public String register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "email  already exists");
        }

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(Role.USER)
                .build();
        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return jwtToken;
    }

    public String login(LoginRequest request) {

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        if (authenticate.isAuthenticated()) {

            var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

            var jwtToken = jwtService.generateToken(user);
            return jwtToken;
        } else {
            throw new UsernameNotFoundException("Invalid user request");
        }
    }

}
