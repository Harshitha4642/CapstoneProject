package com.capstone.cdr.service;

import com.capstone.cdr.dto.CallSearchResult;
import com.capstone.cdr.dto.MessageSearchResult;
import com.capstone.cdr.dto.SearchContent;
import com.capstone.cdr.entity.CDR;
import com.capstone.cdr.entity.Customer;
import com.capstone.cdr.entity.MessageCDR;
import com.capstone.cdr.entity.MessageType;
import com.capstone.cdr.entity.Type;
import com.capstone.cdr.repository.CDRRepository;
import com.capstone.cdr.repository.CustomerRepository;
import com.capstone.cdr.repository.MessageCDRRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.matches;

@SpringBootTest
public class SearchServiceTests {

    @InjectMocks
    private SearchService searchService;

    @Mock
    private MessageCDRRepository messageCDRRepository;

    @Mock
    private CDRRepository cdrRepository;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setup() {
        Customer john = new Customer();
        john.setId(1);
        john.setName("John");
        john.setPhonenumber("1234567890");

        Type type = new Type();
        type.setId(1);;
        type.setRate(10);
        type.setType("test-call");

        MessageType messType = new MessageType();
        messType.setId(1);
        messType.setRate(5);
        messType.setType("test-message");

        CDR cdr1 = new CDR();
        cdr1.setSubscriber(john);
        cdr1.setReciever(john);
        cdr1.setSubscriberLocation("Location1");
        cdr1.setRecieverLocation("Location2");
        cdr1.setDate(LocalDate.parse("2023-10-20"));
        cdr1.setDuration(10);
        cdr1.setCallType(type);
        cdr1.setHasVoicemail(true);
        cdr1.setVoiceMailDuration(5);
        cdr1.setReason("Test Call");

        CDR cdr2 = new CDR();
        cdr2.setSubscriber(john);
        cdr2.setReciever(john);
        cdr2.setSubscriberLocation("Location3");
        cdr2.setRecieverLocation("Location4");
        cdr2.setDate(LocalDate.parse("2023-10-22"));
        cdr2.setDuration(15);
        cdr2.setCallType(type);
        cdr2.setHasVoicemail(false);
        cdr2.setVoiceMailDuration(0);
        cdr2.setReason("Test Call 2");

        MessageCDR messageCDR1 = new MessageCDR();
        messageCDR1.setSubscriber(john);
        messageCDR1.setReciever(john);
        messageCDR1.setSubscriberLocation("Location1");
        messageCDR1.setRecieverLocation("Location2");
        messageCDR1.setDate(LocalDate.parse("2023-10-20"));
        messageCDR1.setTime(LocalTime.now());
        messageCDR1.setSentStatus("Sent");
        messageCDR1.setMessageType(messType);

        List<CDR> cdrList = Arrays.asList(cdr1, cdr2);
        List<MessageCDR> messageCDRList = Arrays.asList(messageCDR1);
        List<String> names = Arrays.asList(john.getName());
        List<String> numbers = Arrays.asList(john.getPhonenumber());

        Mockito.when(customerRepository.findByName("John")).thenReturn(john);
        Mockito.when(customerRepository.findByPhonenumber("1234567890")).thenReturn(john);
        Mockito.when(cdrRepository.findAllByCustomerId(john.getId())).thenReturn(cdrList);
        Mockito.when(cdrRepository.findAllByLocation("Location1")).thenReturn(cdrList);
        Mockito.when(cdrRepository.findAllByDate(LocalDate.parse("2023-10-20"))).thenReturn(cdrList);
        Mockito.when(messageCDRRepository.findAllByCustomerId(john.getId())).thenReturn(messageCDRList);
        Mockito.when(messageCDRRepository.findAllByLocation("Location1")).thenReturn(messageCDRList);
        Mockito.when(messageCDRRepository.findAllByDate(LocalDate.parse("2023-10-20"))).thenReturn(messageCDRList);
        Mockito.when(searchService.getAllNames()).thenReturn(names);
        Mockito.when(searchService.getAllNumbers()).thenReturn(numbers);
    }

    @Test
    public void testGetCallDataByName() {
        // Arrange
        SearchContent searchParameters = new SearchContent();
        searchParameters.setCategory("customer-name");
        searchParameters.setContent("John");

        // Act
        List<CallSearchResult> callSearchResults = searchService.getCallDataByName(searchParameters);

        // Assert
        assertEquals(2, callSearchResults.size());
    }

    @Test
    public void testGetCallDataByNumber() {
        // Arrange
        SearchContent searchParameters = new SearchContent();
        searchParameters.setCategory("customer-number");
        searchParameters.setContent("1234567890");

        // Act
        List<CallSearchResult> callSearchResults = searchService.getCallDataByNumber(searchParameters);

        // Assert
        assertEquals(2, callSearchResults.size());
    }

    @Test
    public void testGetCallDataByLocation() {
        // Arrange
        SearchContent searchParameters = new SearchContent();
        searchParameters.setCategory("location");
        searchParameters.setContent("Location1");

        // Act
        List<CallSearchResult> callSearchResults = searchService.getCallDataByLocation(searchParameters);

        // Assert
        assertEquals(2, callSearchResults.size());
    }

    @Test
    public void testGetCallDataByDate() {
        // Arrange
        SearchContent searchParameters = new SearchContent();
        searchParameters.setCategory("_date");
        searchParameters.setContent("2023-10-20");

        // Act
        List<CallSearchResult> callSearchResults = searchService.getCallDataByDate(searchParameters);

        // Assert
        assertEquals(2, callSearchResults.size());

        searchParameters.setCategory("_date");
        searchParameters.setContent("random");
        //callSearchResults = searchService.getCallDataByDate(searchParameters);
        //assertThrows(DateTimeParseException.class, ()->{ LocalDateTime.parse("1-2-2003"); },"date format error")\
        assertThrows(DateTimeParseException.class, () -> {
            searchService.getCallDataByDate(searchParameters);
        });

    }



    @Test
    public void testGetAllNames() {
        // Act
        List<String> names = searchService.getAllNames();

        // Assert
        assertEquals(1, names.size());
    }

    @Test
    public void testGetAllNumbers() {
        // Act
        List<String> numbers = searchService.getAllNumbers();

        // Assert
        assertEquals(1, numbers.size());
    }

     @Test
    public void testGetMessageDataByNumber() {
        // Arrange
        SearchContent searchParameters = new SearchContent();
        searchParameters.setCategory("customer-number");
        searchParameters.setContent("1234567890");

        // Act
        List<MessageSearchResult> messageSearchResults = searchService.getMessageDataByNumber(searchParameters);

        // Assert
        assertEquals(1, messageSearchResults.size());
    }

    @Test
    public void testGetMessageDataByName() {
        // Arrange
        SearchContent searchParameters = new SearchContent();
        searchParameters.setCategory("customer-name");
        searchParameters.setContent("John");

        // Act
        List<MessageSearchResult> messageSearchResults = searchService.getMessageDataByName(searchParameters);

        // Assert
        assertEquals(1, messageSearchResults.size());
    }

     @Test
    public void testGetMessageDataByDate() {
        SearchContent searchParameters = new SearchContent();
        searchParameters.setCategory("_date");
        searchParameters.setContent("2023-10-20");

        List<MessageSearchResult> messageSearchResults = searchService.getMessageDataByDate(searchParameters);

        assertEquals(1, messageSearchResults.size());

        searchParameters.setCategory("_date");
        searchParameters.setContent("random");
        assertThrows(DateTimeParseException.class, () -> {
        searchService.getCallDataByDate(searchParameters);
        });
    }

    @Test
    public void testGetMessageDataByLocation() {
        // Arrange
        SearchContent searchParameters = new SearchContent();
        searchParameters.setCategory("location");
        searchParameters.setContent("Location1");

        List<MessageSearchResult> messageSearchResults = searchService.getMessageDataByLocation(searchParameters);

        assertEquals(1, messageSearchResults.size());
    }

    @Test
    public void testCDRToCSRMapperWithNonNullTime() {

        Customer john = new Customer();
        john.setId(1);
        john.setName("John");
        john.setPhonenumber("1234567890");

        CDR cdr = new CDR();

        Type type = new Type();
        type.setId(1);
        type.setRate(10);
        type.setType("test-call");

        cdr.setCallType(type);
        cdr.setDate(LocalDate.now());
        cdr.setDuration(0);
        cdr.setHasVoicemail(true);
        cdr.setReason("lines busy");
        cdr.setRecieverLocation("location -1");
        cdr.setSubscriberLocation("location -2");
        cdr.setReciever(john);
        cdr.setSubscriber(john);
        cdr.setTime(LocalTime.parse("15:27"));
        cdr.setVoiceMailDuration(2);

        CallSearchResult csr = searchService.CDRToCSRMapper(cdr);

        CallSearchResult  mockCsr = new CallSearchResult();
        mockCsr.setCallType(type.getType());
        mockCsr.setDate(LocalDate.now().toString());
        mockCsr.setDuration(0);
        mockCsr.setHasVoiceMail("true");
        mockCsr.setReason("lines busy");
        mockCsr.setRecieverLocation("location -1");
        mockCsr.setSubscriberLocation("location -2");
        mockCsr.setRecieverName(john.getName());
        mockCsr.setSubscriberName(john.getName());
        mockCsr.setRecieverPhn(john.getPhonenumber());
        mockCsr.setSubscriberPhn(john.getPhonenumber());
        mockCsr.setTime("15:27");
        mockCsr.setVoiceMailDuration(2);

        assertNotNull(csr);
        assertEquals(mockCsr.getCallType(), csr.getCallType());
        assertEquals(mockCsr.getDate(), csr.getDate());
        assertEquals(mockCsr.getDuration(), csr.getDuration());
        assertEquals(mockCsr.getHasVoiceMail(), csr.getHasVoiceMail());
        assertEquals(mockCsr.getReason(), csr.getReason());
        assertEquals(mockCsr.getRecieverLocation(), csr.getRecieverLocation());
        assertEquals(mockCsr.getSubscriberLocation(), csr.getSubscriberLocation());
        assertEquals(mockCsr.getRecieverName(), csr.getRecieverName());
        assertEquals(mockCsr.getSubscriberName(), csr.getSubscriberName());
        assertEquals(mockCsr.getRecieverPhn(), csr.getRecieverPhn());
        assertEquals(mockCsr.getSubscriberPhn(), csr.getSubscriberPhn());
        assertEquals(mockCsr.getTime(), csr.getTime());
        assertEquals(mockCsr.getVoiceMailDuration(), csr.getVoiceMailDuration());

    }

    @Test
    public void testCDRToCSRMapperWithNullTime() {
        
        Customer john = new Customer();
        john.setId(1);
        john.setName("John");
        john.setPhonenumber("1234567890");
       
        CDR cdr = new CDR();
        Type type = new Type();
        type.setId(1);
        type.setRate(10);
        type.setType("test-call");

        cdr.setCallType(type);
        cdr.setDate(LocalDate.now());
        cdr.setDuration(0);
        cdr.setHasVoicemail(true);
        cdr.setReason("lines busy");
        cdr.setRecieverLocation("location -1");
        cdr.setSubscriberLocation("location -2");
        cdr.setReciever(john);
        cdr.setSubscriber(john);
        cdr.setTime(null);
        cdr.setVoiceMailDuration(2);


        CallSearchResult csr = searchService.CDRToCSRMapper(cdr);

         CallSearchResult  mockCsr = new CallSearchResult();
        mockCsr.setCallType(type.getType());
        mockCsr.setDate(LocalDate.now().toString());
        mockCsr.setDuration(0);
        mockCsr.setHasVoiceMail("true");
        mockCsr.setReason("lines busy");
        mockCsr.setRecieverLocation("location -1");
        mockCsr.setSubscriberLocation("location -2");
        mockCsr.setRecieverName(john.getName());
        mockCsr.setSubscriberName(john.getName());
        mockCsr.setRecieverPhn(john.getPhonenumber());
        mockCsr.setSubscriberPhn(john.getPhonenumber());
        mockCsr.setTime("N/A");
        mockCsr.setVoiceMailDuration(2);

        
        assertEquals("N/A", csr.getTime()); 
        assertEquals(mockCsr.getCallType(), csr.getCallType());
        assertEquals(mockCsr.getDate(), csr.getDate());
        assertEquals(mockCsr.getDuration(), csr.getDuration());
        assertEquals(mockCsr.getHasVoiceMail(), csr.getHasVoiceMail());
        assertEquals(mockCsr.getReason(), csr.getReason());
        assertEquals(mockCsr.getRecieverLocation(), csr.getRecieverLocation());
        assertEquals(mockCsr.getSubscriberLocation(), csr.getSubscriberLocation());
        assertEquals(mockCsr.getRecieverName(), csr.getRecieverName());
        assertEquals(mockCsr.getSubscriberName(), csr.getSubscriberName());
        assertEquals(mockCsr.getRecieverPhn(), csr.getRecieverPhn());
        assertEquals(mockCsr.getSubscriberPhn(), csr.getSubscriberPhn());
        assertEquals(mockCsr.getTime(), csr.getTime());
        assertEquals(mockCsr.getVoiceMailDuration(), csr.getVoiceMailDuration());
    }

    @Test
    public void testCDRToMSRMapperWithNonNullTime() {
       
        MessageCDR cdr = new MessageCDR();
        Customer john = new Customer();
        john.setId(1);
        john.setName("John");
        john.setPhonenumber("1234567890");
       
        MessageType type = new MessageType();
        type.setId(1);
        type.setRate(10);
        type.setType("test-call");

        cdr.setDate(LocalDate.now());
        cdr.setRecieverLocation("Location-1");
        cdr.setSubscriberLocation("Location-2");
        cdr.setReciever(john);
        cdr.setSubscriber(john);
        cdr.setTime(LocalTime.parse("16:52"));
        cdr.setMessageType(type);
        cdr.setSentStatus("sent");
        cdr.setId(1);
        cdr.setTime(LocalTime.parse("16:54"));
        
        MessageSearchResult msr = searchService.CDRToMSRMapper(cdr);

        MessageSearchResult mockmsr = new MessageSearchResult();

        mockmsr.setType(type.getType());
        mockmsr.setDate(LocalDate.now().toString());
        mockmsr.setRecieverLocation("Location-1");
        mockmsr.setSubscriberLocation("Location-2");
        mockmsr.setRecieverName(john.getName());
        mockmsr.setSubscriberName(john.getName());
        mockmsr.setRecieverPhn(john.getPhonenumber());
        mockmsr.setSubscriberPhn(john.getPhonenumber());
        mockmsr.setTime("16:54");
        mockmsr.setStatus("sent");

        assertNotNull(msr);
        assertEquals(mockmsr.getType(), msr.getType());
        assertEquals(mockmsr.getDate(), msr.getDate());
        assertEquals(mockmsr.getRecieverLocation(), msr.getRecieverLocation());
        assertEquals(mockmsr.getSubscriberLocation(), msr.getSubscriberLocation());
        assertEquals(mockmsr.getRecieverName(), msr.getRecieverName());
        assertEquals(mockmsr.getSubscriberName(), msr.getSubscriberName());
        assertEquals(mockmsr.getRecieverPhn(), msr.getRecieverPhn());
        assertEquals(mockmsr.getSubscriberPhn(), msr.getSubscriberPhn());
        assertEquals(mockmsr.getTime(), msr.getTime());
        assertEquals(mockmsr.getStatus(), msr.getStatus());
    }

    @Test
    public void testCDRToMSRMapperWithNullTime() {
        MessageCDR cdr = new MessageCDR();

        Customer john = new Customer();
        john.setId(1);
        john.setName("John");
        john.setPhonenumber("1234567890");
        
        MessageType type = new MessageType();
        type.setId(1);
        type.setRate(10);
        type.setType("test-call");

        cdr.setDate(LocalDate.now());
        cdr.setRecieverLocation("Location-1");
        cdr.setSubscriberLocation("Location-2");
        cdr.setReciever(john);
        cdr.setSubscriber(john);
        cdr.setTime(LocalTime.parse("16:52"));
        cdr.setMessageType(type);
        cdr.setSentStatus("sent");
        cdr.setId(1);
        cdr.setTime(null);
        
        MessageSearchResult msr = searchService.CDRToMSRMapper(cdr);

        MessageSearchResult mockmsr = new MessageSearchResult();

        mockmsr.setType(type.getType());
        mockmsr.setDate(LocalDate.now().toString());
        mockmsr.setRecieverLocation("Location-1");
        mockmsr.setSubscriberLocation("Location-2");
        mockmsr.setRecieverName(john.getName());
        mockmsr.setSubscriberName(john.getName());
        mockmsr.setRecieverPhn(john.getPhonenumber());
        mockmsr.setSubscriberPhn(john.getPhonenumber());
        mockmsr.setTime("N/A");
        mockmsr.setStatus("sent");

        assertNotNull(msr);
        assertEquals(mockmsr.getType(), msr.getType());
        assertEquals(mockmsr.getDate(), msr.getDate());
        assertEquals(mockmsr.getRecieverLocation(), msr.getRecieverLocation());
        assertEquals(mockmsr.getSubscriberLocation(), msr.getSubscriberLocation());
        assertEquals(mockmsr.getRecieverName(), msr.getRecieverName());
        assertEquals(mockmsr.getSubscriberName(), msr.getSubscriberName());
        assertEquals(mockmsr.getRecieverPhn(), msr.getRecieverPhn());
        assertEquals(mockmsr.getSubscriberPhn(), msr.getSubscriberPhn());
        assertEquals(mockmsr.getTime(), msr.getTime());
        assertEquals(mockmsr.getStatus(), msr.getStatus());
        assertNotNull(msr);
    }

}
