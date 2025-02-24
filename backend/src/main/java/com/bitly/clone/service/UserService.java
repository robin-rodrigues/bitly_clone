package com.bitly.clone.service;

import com.bitly.clone.dtos.LoginRequest;
import com.bitly.clone.models.User;
import com.bitly.clone.repository.UserRepository;
import com.bitly.clone.security.jwt.JwtAuthenticationResponse;
import com.bitly.clone.security.jwt.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private PasswordEncoder passwordEncoder;

    private UserRepository userRepository;

    private AuthenticationManager authenticationManager;

    private JwtUtils jwtUtils;

    /**
     * Registers a new user by encoding their password before saving to the database.
     * - Encodes the user's password using the configured password encoder.
     * - Saves the user entity to the database.
     *
     * @param user the user to be registered
     * @return the saved User entity with the encoded password
     */
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    /**
     * Authenticates a user based on the provided login credentials.
     * - Authenticates the user using the authentication manager.
     * - Sets the authenticated user in the SecurityContext.
     * - The Security Context is used only for the duration of this request to store authentication details.
     * - Generates a JWT token for the authenticated user.
     *
     * @param loginRequest the login request containing the username and password
     * @return a JwtAuthenticationResponse containing the generated JWT token
     */
    public JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                        loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtils.generateToken(userDetails);

        return new JwtAuthenticationResponse(jwt);
    }

    public User findByUserName(String name) {
        return userRepository.findByUsername(name).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + name)
        );
    }
}
