package com.bitly.clone.service;

import com.bitly.clone.models.User;
import com.bitly.clone.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    /**
     * Loads a user by their username for authentication.
     * - Retrieves the user from the database using the provided username.
     * - Throws a UsernameNotFoundException if the user is not found.
     * - Converts the User entity into a UserDetailsImpl instance for Spring Security.
     *
     * @param username the username of the user to be loaded
     * @return a UserDetails object containing the authenticated user's details
     * @throws UsernameNotFoundException if the user is not found in the database
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return UserDetailsImpl.build(user);
    }
}
