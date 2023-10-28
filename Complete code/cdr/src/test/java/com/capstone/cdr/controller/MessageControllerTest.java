package com.capstone.cdr.controller;

import com.capstone.cdr.controller.MessageController;
import com.capstone.cdr.dto.ExcecutionStatus;
import com.capstone.cdr.dto.MessageForm;
import com.capstone.cdr.entity.MessageType;
import com.capstone.cdr.service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

@WebMvcTest(MessageController.class)
public class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageService messageService;

    @BeforeEach
    public void setup() {
        // Initialize or reset mock behavior before each test
        Mockito.reset(messageService);
    }
    
    @Test
    @WithMockUser
public void testSaveMessageCDR() throws Exception {
    // Arrange

    ObjectMapper mapper = new ObjectMapper();
    MessageForm form = new MessageForm();
    form.setSubscriber(1);
    form.setReciever(2);
    form.setDate("2023-10-20");
    form.setTime("15:30");
    form.setSubscriberLocation("Location1");
    form.setRecieverLocation("Location2");
    form.setStatus("Status1");
    form.setType("Type1"); // Make sure to include the 'type' field

    ExcecutionStatus status = new ExcecutionStatus();
    status.setStatus("added successfully");

    Mockito.when(messageService.saveCDRService(form)).thenReturn(status);

    // Act & Assert
    mockMvc.perform(MockMvcRequestBuilders.post("/api/message/saveMessage")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .content(mapper.writeValueAsString(form))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"status\": \"added successfully\"}"));
}
}
