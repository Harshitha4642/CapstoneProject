package com.capstone.cdr;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.capstone.cdr.entity.User;
import com.capstone.cdr.service.CustomUserDetailsService;
import com.capstone.cdr.service.UserService;

public class CustomUserDetailsServiceTest {

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLoadUserByUsername_UserFound() {
        // Arrange
        String username = "testUser";
        String password = "testPassword"; // You may use an encoded password here
        // Create a mock user entity
        User myUserEntity = new User();
        myUserEntity.setName(username);
        myUserEntity.setPassword(password);

        // Mock the UserService to return the user entity when called with the username
        when(userService.getByName(username)).thenReturn(Optional.of(myUserEntity));

        // Act
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        // Assert
        assertEquals(username, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());
        // You can add more assertions for other UserDetails properties if needed
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        // Arrange
        String username = "nonExistentUser";
        // Mock the UserService to return an empty Optional to simulate a user not found
        when(userService.getByName(username)).thenReturn(Optional.empty());

        // Act and Assert
        try {
            customUserDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            assertEquals("No user found with username " + username, e.getMessage());
        }
    }
}
