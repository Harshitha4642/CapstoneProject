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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.assertj.core.api.Assertions.entry;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
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

    @Test
    public void testSaveCDRService_UserNotFound() {
        // Test the saveCDRService method when the subscriber is not found
        MessageForm form = new MessageForm();
        form.setSubscriber(999); // Non-existent user
        form.setReciever(2);
        form.setDate("2023-10-20");
        form.setTime("15:30");
        form.setSubscriberLocation("Location1");
        form.setRecieverLocation("Location2");
        form.setStatus("Status1");
        form.setType("Type1");

        ExcecutionStatus status = messageService.saveCDRService(form);
        assertNotNull(status);
        assertEquals("no user exist", status.getStatus());

        // Verify that the save method was not called
        Mockito.verify(messCDRRepo, Mockito.never()).save(any(MessageCDR.class));
    }

 
@Test
public void testExportToCSV() {
    // Create a list of MessageCDR objects with the provided details
    List<MessageCDR> cdrList = new ArrayList<>();
    
    // Create MessageCDR objects for each provided detail line
    cdrList.add(createMessageCDR(1, "Likith", "76887271", "Srima", "827829027", "2023-10-03", "05:14", "Sijua, India", "Gwalior, India", "sent", "MessageType(id=1, type=not-roaming, rate=15)"));
    cdrList.add(createMessageCDR(2, "Likith", "76887271", "Srima", "827829027", "2023-10-11", "04:00", "Sijua, India", "Sūrat, India", "not-sent", "MessageType(id=1, type=not-roaming, rate=15)"));
    cdrList.add(createMessageCDR(3, "Harshitha", "8738272827", "Likith", "76887271", "2023-10-17", "05:00", "Hyderābād, India", "Vishākhapatnam, India", "not-sent", "MessageType(id=1, type=not-roaming, rate=15)"));
    cdrList.add(createMessageCDR(4, "Harshitha", "8738272827", "Ashish", "997877678", "2023-10-13", "04:00", "Sijua, India", "Philadelphia, United States", "sent", "MessageType(id=2, type=roaming, rate=150)"));

    // Define a temporary directory for testing
    String tempDirectory = System.getProperty("java.io.tmpdir");

    String path = tempDirectory + "/test_cdr.csv";
    messageService.exportToCSV(cdrList, path);

    // Assert that the CSV file was created
    File csvFile = new File(path);
    assertThat(csvFile).exists();

    // Assert the content of the CSV file using AssertJ and the order of columns
    try (CSVParser csvParser = CSVParser.parse(csvFile, Charset.defaultCharset(), CSVFormat.DEFAULT)) {
        List<CSVRecord> records = csvParser.getRecords();

        // Assert the content row by row
        assertThat(records).hasSize(4);

        // First row
        assertThat(records.get(0)).containsExactly("1", "Likith", "76887271", "Srima", "827829027", "2023-10-03", "05:14", "Sijua, India", "Gwalior, India", "sent", "MessageType(id=1, type=not-roaming, rate=15)");

        // Second row
        assertThat(records.get(1)).containsExactly("2", "Likith", "76887271", "Srima", "827829027", "2023-10-11", "04:00", "Sijua, India", "Sūrat, India", "not-sent", "MessageType(id=1, type=not-roaming, rate=15)");

        // Add assertions for the remaining rows
        // ...

    } catch (IOException e) {
        e.printStackTrace();
    }
}

private MessageCDR createMessageCDR(int id, String subscriberName, String subscriberPhoneNumber, String recieverName, String recieverPhoneNumber, String date, String time, String subscriberLocation, String recieverLocation, String sentStatus, String messageType) {
    MessageCDR cdr = new MessageCDR();
    cdr.setId(id);
    
    Customer subscriber = new Customer();
    subscriber.setName(subscriberName);
    subscriber.setPhonenumber(subscriberPhoneNumber);
    
    Customer reciever = new Customer();
    reciever.setName(recieverName);
    reciever.setPhonenumber(recieverPhoneNumber);
    
    MessageType type = new MessageType();
    type.setId(1);
    type.setRate(15);
    type.setType("not-roaming");
    
    cdr.setSubscriber(subscriber);
    cdr.setReciever(reciever);
    cdr.setDate(LocalDate.parse(date));
    cdr.setTime(LocalTime.parse(time));
    cdr.setSubscriberLocation(subscriberLocation);
    cdr.setRecieverLocation(recieverLocation);
    cdr.setSentStatus(sentStatus);
    cdr.setMessageType(type);
    
    return cdr;
}


}
