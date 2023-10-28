package com.capstone.cdr.repository;


import com.capstone.cdr.entity.User;
import com.capstone.cdr.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByName() {
        // Create a test user
        User user = new User();
        user.setName("John");
        userRepository.save(user);

        // Find the user by name
        Optional<User> foundUser = userRepository.findByName("John");

        assertTrue(foundUser.isPresent());
        assertEquals("John", foundUser.get().getName());
    }

    @Test
    public void testExistsByName() {
        // Create a test user
        User user = new User();
        user.setName("Alice");
        userRepository.save(user);

        // Check if the user with the given name exists
        boolean exists = userRepository.existsByName("Alice");

        assertTrue(exists);
    }

    @Test
    public void testExistsByNameNonExistent() {
        // Check if a non-existent user exists
        boolean exists = userRepository.existsByName("NonExistentUser");

        assertFalse(exists);
    }
}
