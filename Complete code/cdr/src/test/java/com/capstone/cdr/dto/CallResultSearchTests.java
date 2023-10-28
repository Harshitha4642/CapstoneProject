package com.capstone.cdr.dto;

import com.capstone.cdr.dto.CallSearchResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CallResultSearchTests {

    @Test
    public void testGettersAndSetters() {
        CallSearchResult result = new CallSearchResult();

        result.setSubscriberName("John");
        result.setSubscriberPhn("123-456-7890");
        result.setRecieverName("Alice");
        result.setRecieverPhn("987-654-3210");
        result.setDate("2023-10-20");
        result.setTime("15:30");
        result.setDuration(120);
        result.setSubscriberLocation("Location1");
        result.setRecieverLocation("Location2");
        result.setCallType("Outgoing");
        result.setHasVoiceMail("Yes");
        result.setVoiceMailDuration(30);
        result.setReason("Business call");

        assertEquals("John", result.getSubscriberName());
        assertEquals("123-456-7890", result.getSubscriberPhn());
        assertEquals("Alice", result.getRecieverName());
        assertEquals("987-654-3210", result.getRecieverPhn());
        assertEquals("2023-10-20", result.getDate());
        assertEquals("15:30", result.getTime());
        assertEquals(120, result.getDuration());
        assertEquals("Location1", result.getSubscriberLocation());
        assertEquals("Location2", result.getRecieverLocation());
        assertEquals("Outgoing", result.getCallType());
        assertEquals("Yes", result.getHasVoiceMail());
        assertEquals(30, result.getVoiceMailDuration());
        assertEquals("Business call", result.getReason());
    }
}
