package com.capstone.cdr;

import com.capstone.cdr.dto.ExcecutionStatus;
import com.capstone.cdr.dto.MessageForm;
import com.capstone.cdr.entity.Customer;
import com.capstone.cdr.entity.MessageCDR;
import com.capstone.cdr.entity.MessageType;
import com.capstone.cdr.repository.CustomerRepository;
import com.capstone.cdr.repository.MessageCDRRepository;
import com.capstone.cdr.repository.MessageTypeRepository;
import com.capstone.cdr.service.MessageService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Optional;
import java.time.LocalDate;
import java.time.LocalTime;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MessageServiceTests {

    @InjectMocks
    private MessageService messageService;

    @Mock
    private MessageTypeRepository messageTypeRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private MessageCDRRepository messCDRRepo;

    @BeforeEach
    public void setup() {
        MessageType mockMessageType = new MessageType();
        mockMessageType.setId(1);
        mockMessageType.setType("Type1");
        Customer sub = new Customer();
        sub.setId(1);
        sub.setName("harshi");
        sub.setPhonenumber("9828");

        Customer rec = new Customer();
        rec.setId(2);
        rec.setName("srima");
        rec.setPhonenumber("77882");

        when(messageTypeRepository.findByType("Type1")).thenReturn(mockMessageType);
        when(customerRepository.findById(1)).thenReturn(Optional.of(sub));
        when(customerRepository.findById(2)).thenReturn(Optional.of(rec));
    }

    @WithMockUser
    @Test
    public void testSaveCDRService() {
        MessageForm form = new MessageForm();
        
        form.setSubscriber(1);
        form.setReciever(2);
        form.setDate("2023-10-20");
        form.setTime("15:30");
        form.setSubscriberLocation("Location1");
        form.setRecieverLocation("Location2");
        form.setStatus("Status1");
        form.setType("Type1");

        ExcecutionStatus status = messageService.saveCDRService(form);
        assertNotNull(status);
        assertEquals("added successfully", status.getStatus());

        MessageCDR capturedCDR = new MessageCDR();

        capturedCDR.setReciever(customerRepository.findById(form.getReciever()).get());
        capturedCDR.setSubscriber(customerRepository.findById(form.getSubscriber()).get());
        capturedCDR.setDate(LocalDate.parse(form.getDate()));
        capturedCDR.setTime(LocalTime.parse(form.getTime()));
        capturedCDR.setMessageType(messageTypeRepository.findByType(form.getType()));
        capturedCDR.setRecieverLocation(form.getRecieverLocation());
        capturedCDR.setSubscriberLocation(form.getSubscriberLocation());
        capturedCDR.setSentStatus(form.getStatus());

        assertEquals(1, capturedCDR.getSubscriber().getId());
        assertEquals(2, capturedCDR.getReciever().getId());
        assertEquals(LocalDate.parse("2023-10-20"), capturedCDR.getDate());
        assertEquals(LocalTime.parse("15:30"), capturedCDR.getTime());
        assertEquals("Location1", capturedCDR.getSubscriberLocation());
        assertEquals("Location2", capturedCDR.getRecieverLocation());
        assertEquals("Status1", capturedCDR.getSentStatus());
    }

    @Test
    public void testSaveCDRService_InvalidDate() {
        MessageForm form = new MessageForm();
        form.setSubscriber(1);
        form.setReciever(2);
        form.setDate("invalid-date"); // Invalid date
        form.setTime("15:30");
        form.setSubscriberLocation("Location1");
        form.setRecieverLocation("Location2");
        form.setStatus("Status1");
        form.setType("Type1");

        ExcecutionStatus status = messageService.saveCDRService(form);
        assertNotNull(status);
        assertEquals("Enter valid date or time", status.getStatus());

        // Verify that save method was not called
        Mockito.verify(messCDRRepo, Mockito.never()).save(any(MessageCDR.class));
    }
}
