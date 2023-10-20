package com.capstone.cdr;

import org.junit.jupiter.api.Test;

import com.capstone.cdr.dto.UserForm;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserFormTest {

    @Test
    public void testUserForm() {
        // Create a UserForm object
        UserForm userForm = new UserForm();
        userForm.setName("john_doe");
        userForm.setPassword("password");
        userForm.setPasswordRepeat("password");

        // Check if the fields are set correctly
        assertEquals("john_doe", userForm.getName());
        assertEquals("password", userForm.getPassword());
        assertEquals("password", userForm.getPasswordRepeat());
    }
}
