package com.capstone.cdr.controller;

// import com.capstone.cdr.dto.LoginBody;
// import com.capstone.cdr.entity.User;
// import com.capstone.cdr.controller.APIAuthController;
// import com.capstone.cdr.repository.UserRepository;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.MediaType;
// import org.springframework.security.test.context.support.WithMockUser;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
// import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

// @WithMockUser
// @AutoConfigureMockMvc
// @SpringBootTest
// public class APIAuthControllerTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @Autowired
//     private UserRepository userRepository;

//     @MockBean
//     private APIAuthController apiAuthController;

//     @BeforeEach
//     void setUp() {
        
//         userRepository.deleteAll();
//     }
//     @Test
//     void testToken() throws Exception {
//         User mockuser = new User();
//         mockuser.setId(1);
//         mockuser.setName("testuser");
//         mockuser.setPassword("{noop}harshi");
//         userRepository.save(mockuser);

//         mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/token")
//                 .content("{\"username\":\"testuser\",\"password\":\"harshi\"}")
//                 .contentType(MediaType.APPLICATION_JSON))
//                 .andExpect(MockMvcResultMatchers.status().isOk());
//     }

//     @Test
//     void testRegisterUser() throws Exception {
//         LoginBody registrationBody = new LoginBody();
//         registrationBody.setUsername("newUser");
//         registrationBody.setPassword("newPassword");

//         mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
//                 .content("{\"username\":\"newUser\",\"password\":\"newPassword\"}")
//                 .contentType(MediaType.APPLICATION_JSON))
//                 .andExpect(MockMvcResultMatchers.status().isOk());
//     }
// }

 
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
 
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;

import com.capstone.cdr.dto.LoginBody;
import com.capstone.cdr.dto.TokenDTO;
import com.capstone.cdr.entity.User;
import com.capstone.cdr.repository.UserRepository;

 
@SpringBootTest
 
class APIAuthControllerTest {
 
    @Mock
    private JwtEncoder jwtEncoder;
 
    @Mock
    private UserRepository userRepository;
 
    @Mock
    private AuthenticationManager authenticationManager;
 
    @Mock
    private PasswordEncoder passwordEncoder;
 
    @InjectMocks
    private APIAuthController authController;
 
    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }
 
    @Test
    void testToken() {
        // Your token test logic here
 
        // Example: mock authentication
        LoginBody loginBody = new LoginBody();
        loginBody.setUsername("testuser");
        loginBody.setPassword("testpassword");
 
        Authentication authentication = new UsernamePasswordAuthenticationToken("testuser", "testpassword",
                new ArrayList<>());
 
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", "testuser"); // Sample subject claim
        claims.put("exp", Instant.now().plusSeconds(3600).getEpochSecond()); // Sample expiration claim
 
        Jwt jwt = new Jwt("tokenValue", Instant.now(), Instant.now().plusSeconds(3600),
                Collections.singletonMap("alg", "HS256"), claims);
 
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtEncoder.encode(any())).thenReturn(jwt);
 
        TokenDTO token = authController.token(loginBody);
        assertEquals("tokenValue", token.getToken());
 
    }
 
    @Test
    void testRegisterUser() {
        // Test when user does not exist
        LoginBody loginBody = new LoginBody();
        loginBody.setUsername("newuser");
        loginBody.setPassword("newpassword");
        User dummyUser = new User();
        dummyUser.setId(1);
        dummyUser.setName("test");
        dummyUser.setPassword("dummy");
 
        when(userRepository.existsByName("newuser")).thenReturn(false);
 
        ResponseEntity<String> responseEntity = authController.registerUser(dummyUser);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
 
    }

 
}