package com.capstone.cdr;

import com.capstone.cdr.dto.LoginBody;
import com.capstone.cdr.entity.User;
import com.capstone.cdr.controller.APIAuthController;
import com.capstone.cdr.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WithMockUser
@AutoConfigureMockMvc
@SpringBootTest
public class APIAuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private APIAuthController apiAuthController;

    @BeforeEach
    void setUp() {
        
        userRepository.deleteAll();
    }
    @Test
    void testToken() throws Exception {
        User mockuser = new User();
        mockuser.setId(1);
        mockuser.setName("testuser");
        mockuser.setPassword("{noop}harshi");
        userRepository.save(mockuser);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/token")
                .content("{\"username\":\"testuser\",\"password\":\"harshi\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testRegisterUser() throws Exception {
        LoginBody registrationBody = new LoginBody();
        registrationBody.setUsername("newUser");
        registrationBody.setPassword("newPassword");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                .content("{\"username\":\"newUser\",\"password\":\"newPassword\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
