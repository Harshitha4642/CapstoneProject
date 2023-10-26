package com.capstone.cdr;

import org.junit.jupiter.api.Test;
import com.capstone.cdr.dto.TokenDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenDTOTest {

    @Test
    public void testTokenDTO() {
        // Create a TokenDTO object
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setToken("exampleToken");
        tokenDTO.setUsername("john_doe");

        // Check if the fields are set correctly
        assertEquals("exampleToken", tokenDTO.getToken());
        assertEquals("john_doe", tokenDTO.getUsername());
    }
}
