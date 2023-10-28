package com.capstone.cdr.controller;

import com.capstone.cdr.dto.ExcecutionStatus;
import com.capstone.cdr.dto.NormalCallForm;
import com.capstone.cdr.entity.Type;
import com.capstone.cdr.service.HomeService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")

public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private HomeService homeService;

    @Autowired
    private ObjectMapper objectMapper; 

    @BeforeEach
    public void setup() {
        
       

       
        ExcecutionStatus successStatus = new ExcecutionStatus();
        successStatus.setStatus("set");
        when(homeService.saveCDRService(any(NormalCallForm.class), any(Boolean.class))).thenReturn(successStatus);
    }

    @Test
    @WithMockUser
    public void testSaveNormalCall() throws Exception {
        NormalCallForm form = new NormalCallForm();
        form.setSubscriber(1);
        form.setReciever(2);
        form.setRecieverLocation("Pune");
        form.setSubscriberLocation("Hyderabad");
        form.setHasVoiceMail(true);
        form.setTime("13:08");
        form.setCallType("roaming");
        form.setDate("13-06-2023");
        form.setVoiceMailDuration(1);
        form.setReason("declined");


        mockMvc.perform(MockMvcRequestBuilders.post("/api/home/saveNormalCall")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser
    public void testSaveFailedCall() throws Exception {
        NormalCallForm form = new NormalCallForm();

        form.setSubscriber(1);
        form.setReciever(2);
        form.setRecieverLocation("Pune");
        form.setSubscriberLocation("Hyderabad");
        form.setHasVoiceMail(true);
        form.setTime("13:08");
        form.setCallType("roaming");
        form.setDate("13-06-2023");
        form.setVoiceMailDuration(1);
        form.setReason("declined");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/home/saveFailedCall")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form))) 
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
