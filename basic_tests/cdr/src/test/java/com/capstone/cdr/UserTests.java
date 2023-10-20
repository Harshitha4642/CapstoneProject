package com.capstone.cdr;

import org.junit.jupiter.api.Test;

import com.capstone.cdr.entity.User;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTests {

    @Test
    public void testUserEntity() {
        // Create a User object
        User user = new User();
        user.setId(1L);
        user.setName("john_doe");
        user.setPassword("password123");

        // Verify the user properties
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getName()).isEqualTo("john_doe");
        assertThat(user.getPassword()).isEqualTo("password123");
    }

    @Test
    public void testUserEquality() {
        // Create two User objects with the same properties
        User user1 = new User();
        user1.setId(1L);
        user1.setName("john_doe");
        user1.setPassword("password123");

        User user2 = new User();
        user2.setId(1L);
        user2.setName("john_doe");
        user2.setPassword("password123");

        // Verify that the two User objects are equal
        assertThat(user1).isEqualTo(user2);
        assertThat(user1.hashCode()).isEqualTo(user2.hashCode());
    }

    @Test
    public void testUserToString() {
        // Create a User object
        User user = new User();
        user.setId(1L);
        user.setName("john_doe");
        user.setPassword("password123");

        // Verify the User object's toString method
        assertThat(user.toString()).isEqualTo("User(id=1, name=john_doe, password=password123)");
    }
}
