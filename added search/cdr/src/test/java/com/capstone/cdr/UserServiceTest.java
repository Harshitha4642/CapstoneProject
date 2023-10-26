package com.capstone.cdr;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.capstone.cdr.entity.User;
import com.capstone.cdr.repository.UserRepository;
import com.capstone.cdr.service.UserService;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setName("testuser");
        user.setPassword("password");

        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.create(user);

        // Ensure the password has been encoded
        assertNotEquals("password", savedUser.getPassword());

        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testGetUserById() {
        long userId = 1;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById((int) userId)).thenReturn(Optional.of(user));

        Optional<User> retrievedUser = userService.getById(userId);

        assertTrue(retrievedUser.isPresent());
        assertEquals(userId, retrievedUser.get().getId());

        verify(userRepository, times(1)).findById((int) userId);
    }

    @Test
    public void testGetUserByName() {
        String username = "testuser";
        User user = new User();
        user.setName(username);

        when(userRepository.findByName(username)).thenReturn(Optional.of(user));

        Optional<User> retrievedUser = userService.getByName(username);

        assertTrue(retrievedUser.isPresent());
        assertEquals(username, retrievedUser.get().getName());

        verify(userRepository, times(1)).findByName(username);
    }
}
