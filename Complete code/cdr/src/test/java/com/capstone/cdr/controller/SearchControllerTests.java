package com.capstone.cdr.controller;

import com.capstone.cdr.dto.CallSearchResult;
import com.capstone.cdr.dto.MessageSearchResult;
import com.capstone.cdr.dto.SearchContent;
import com.capstone.cdr.service.SearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest

class SearchControllerTests {

    private MockMvc mockMvc;

    @Mock
    private SearchService searchService;

    @InjectMocks
    private SearchController searchController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(searchController).build();
    }

    @Test
void testGetCallRecordsByName() throws Exception {
    // Prepare test data
    SearchContent searchContent = new SearchContent();
    searchContent.setCategory("customer-name");
    searchContent.setContent("John");

    List<CallSearchResult> callResults = Collections.singletonList(new CallSearchResult());

    // Mock the service method with argument matchers
    when(searchService.getCallDataByName(
            argThat(argument -> "customer-name".equals(argument.getCategory())
                    && "John".equals(argument.getContent())))
    ).thenReturn(callResults);

    // Perform the request and assert the response
    mockMvc.perform(post("/api/search/getCallRecords")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"category\":\"customer-name\",\"content\":\"John\"}"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").isArray());

    // Verify that the service method was called
    verify(searchService, times(1)).getCallDataByName(
            argThat(argument -> "customer-name".equals(argument.getCategory())
                    && "John".equals(argument.getContent()))
    );
}


   @Test
void testGetMessageRecordsByLocation() throws Exception {
    // Prepare test data
    SearchContent searchContent = new SearchContent();
    searchContent.setCategory("location");
    searchContent.setContent("New York");

    List<MessageSearchResult> messageResults = Collections.singletonList(new MessageSearchResult());

    // Mock the service method with argument matchers
    when(searchService.getMessageDataByLocation(
            argThat(argument -> "location".equals(argument.getCategory())
                    && "New York".equals(argument.getContent())))
    ).thenReturn(messageResults);

    // Perform the request and assert the response
    mockMvc.perform(post("/api/search/getMessageRecords")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"category\":\"location\",\"content\":\"New York\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());

    // Verify that the service method was called
    verify(searchService, times(1)).getMessageDataByLocation(
            argThat(argument -> "location".equals(argument.getCategory())
                    && "New York".equals(argument.getContent()))
    );
}

@Test
void testGetCallRecordsByNumber() throws Exception {
    // Prepare test data
    SearchContent searchContent = new SearchContent();
    searchContent.setCategory("customer-number");
    searchContent.setContent("1234567890"); // Replace with a valid customer number

    List<CallSearchResult> callResults = Collections.singletonList(new CallSearchResult());

    // Mock the service method with argument matchers
    when(searchService.getCallDataByNumber(
            argThat(argument -> "customer-number".equals(argument.getCategory())
                    && "1234567890".equals(argument.getContent())))
    ).thenReturn(callResults);

    // Perform the request and assert the response
    mockMvc.perform(post("/api/search/getCallRecords")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"category\":\"customer-number\",\"content\":\"1234567890\"}"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").isArray());

    // Verify that the service method was called
    verify(searchService, times(1)).getCallDataByNumber(
            argThat(argument -> "customer-number".equals(argument.getCategory())
                    && "1234567890".equals(argument.getContent()))
    );
}
@Test
void testGetCallRecordsByLocation() throws Exception {
    // Prepare test data
    SearchContent searchContent = new SearchContent();
    searchContent.setCategory("location");
    searchContent.setContent("New York"); // Replace with a valid location

    List<CallSearchResult> callResults = Collections.singletonList(new CallSearchResult());

    // Mock the service method with argument matchers
    when(searchService.getCallDataByLocation(
            argThat(argument -> "location".equals(argument.getCategory())
                    && "New York".equals(argument.getContent())))
    ).thenReturn(callResults);
    
    // Perform the request and assert the response
    mockMvc.perform(post("/api/search/getCallRecords")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"category\":\"location\",\"content\":\"New York\"}"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").isArray());

    // Verify that the service method was called
    verify(searchService, times(1)).getCallDataByLocation(
            argThat(argument -> "location".equals(argument.getCategory())
                    && "New York".equals(argument.getContent()))
    );
}

@Test
void testGetCallRecordsByDate() throws Exception {
    // Prepare test data
    SearchContent searchContent = new SearchContent();
    searchContent.setCategory("_date");
    searchContent.setContent("2023-10-26"); // Replace with a valid date

    List<CallSearchResult> callResults = Collections.singletonList(new CallSearchResult());

    // Mock the service method with argument matchers
    when(searchService.getCallDataByDate(
            argThat(argument -> "_date".equals(argument.getCategory())
                    && "2023-10-26".equals(argument.getContent())))
).thenReturn(callResults);

    // Perform the request and assert the response
    mockMvc.perform(post("/api/search/getCallRecords")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"category\":\"_date\",\"content\":\"2023-10-26\"}"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").isArray());

    // Verify that the service method was called
    verify(searchService, times(1)).getCallDataByDate(
            argThat(argument -> "_date".equals(argument.getCategory())
                    && "2023-10-26".equals(argument.getContent()))
    );
}

@Test
void testGetMessageRecordsByName() throws Exception {
    // Prepare test data
    SearchContent searchContent = new SearchContent();
    searchContent.setCategory("customer-name");
    searchContent.setContent("John");

    List<MessageSearchResult> messageResults = Collections.singletonList(new MessageSearchResult());

    // Mock the service method with argument matchers
    when(searchService.getMessageDataByLocation(
            argThat(argument -> "customer-name".equals(argument.getCategory())
                    && "John".equals(argument.getContent())))
    ).thenReturn(messageResults);

    // Perform the request and assert the response
    mockMvc.perform(post("/api/search/getMessageRecords")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"category\":\"customer-name\",\"content\":\"John\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());

    // Verify that the service method was called
    verify(searchService, times(1)).getMessageDataByName(
            argThat(argument -> "customer-name".equals(argument.getCategory())
                    && "John".equals(argument.getContent()))
    );
}

@Test
void testGetMessageRecordsByNumber() throws Exception {
    // Prepare test data
    SearchContent searchContent = new SearchContent();
    searchContent.setCategory("customer-number");
    searchContent.setContent("1234567890");

    List<MessageSearchResult> messageResults = Collections.singletonList(new MessageSearchResult());

    // Mock the service method with argument matchers
    when(searchService.getMessageDataByLocation(
            argThat(argument -> "customer-number".equals(argument.getCategory())
                    && "1234567890".equals(argument.getContent())))
    ).thenReturn(messageResults);

    // Perform the request and assert the response
    mockMvc.perform(post("/api/search/getMessageRecords")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"category\":\"customer-number\",\"content\":\"1234567890\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());

    // Verify that the service method was called
    verify(searchService, times(1)).getMessageDataByNumber(
            argThat(argument -> "customer-number".equals(argument.getCategory())
                    && "1234567890".equals(argument.getContent()))
    );
}

@Test
void testGetMessageRecordsByDate() throws Exception {
    // Prepare test data
    SearchContent searchContent = new SearchContent();
    searchContent.setCategory("_date");
    searchContent.setContent("2023-10-26");

    List<MessageSearchResult> messageResults = Collections.singletonList(new MessageSearchResult());

    // Mock the service method with argument matchers
    when(searchService.getMessageDataByLocation(
            argThat(argument -> "_date".equals(argument.getCategory())
                    && "2023-10-26".equals(argument.getContent())))
    ).thenReturn(messageResults);

    // Perform the request and assert the response
    mockMvc.perform(post("/api/search/getMessageRecords")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"category\":\"_date\",\"content\":\"2023-10-26\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());

    // Verify that the service method was called
    verify(searchService, times(1)).getMessageDataByDate(
            argThat(argument -> "_date".equals(argument.getCategory())
                    && "2023-10-26".equals(argument.getContent()))
    );
}

@Test
void testGetAllNames() throws Exception {
    // Prepare test data
    List<String> names = Arrays.asList("John", "Alice", "Bob"); // Replace with your expected names

    // Mock the service method
    when(searchService.getAllNames()).thenReturn(names);

    // Perform the request and assert the response
    mockMvc.perform(get("/api/search/getNames"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$[0]").value("John")) // Replace with your expected names
            .andExpect(jsonPath("$[1]").value("Alice"))
            .andExpect(jsonPath("$[2]").value("Bob"));

    // Verify that the service method was called
    verify(searchService, times(1)).getAllNames();
}

@Test
void testGetAllNumbers() throws Exception {
    // Prepare test data
    List<String> numbers = Arrays.asList("1234567890", "9876543210", "5555555555"); // Replace with your expected numbers

    // Mock the service method
    when(searchService.getAllNumbers()).thenReturn(numbers);

    // Perform the request and assert the response
    mockMvc.perform(get("/api/search/getNumbers"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$[0]").value("1234567890")) // Replace with your expected numbers
            .andExpect(jsonPath("$[1]").value("9876543210"))
            .andExpect(jsonPath("$[2]").value("5555555555"));

    // Verify that the service method was called
    verify(searchService, times(1)).getAllNumbers();
}



}
