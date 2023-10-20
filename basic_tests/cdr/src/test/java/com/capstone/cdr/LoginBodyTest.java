package com.capstone.cdr;

import org.junit.jupiter.api.Test;
import com.capstone.cdr.dto.LoginBody;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginBodyTest {

    @Test
    public void testLoginBody() {
       
        LoginBody loginBody = new LoginBody();
        loginBody.setUsername("john.doe");
        loginBody.setPassword("secret");

        
        assertEquals("john.doe", loginBody.getUsername());
        assertEquals("secret", loginBody.getPassword());
    }
}
