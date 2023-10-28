package com.capstone.cdr.dto;

import com.capstone.cdr.dto.MessageSearchResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageSearchResultTest {

    @Test
    public void testGettersAndSetters() {
        MessageSearchResult result = new MessageSearchResult();

        result.setSubscriberName("John");
        result.setSubscriberPhn("123-456-7890");
        result.setRecieverName("Alice");
        result.setRecieverPhn("987-654-3210");
        result.setDate("2023-10-20");
        result.setTime("15:30");
        result.setSubscriberLocation("Location1");
        result.setRecieverLocation("Location2");
        result.setStatus("Delivered");
        result.setType("Text");

        assertEquals("John", result.getSubscriberName());
        assertEquals("123-456-7890", result.getSubscriberPhn());
        assertEquals("Alice", result.getRecieverName());
        assertEquals("987-654-3210", result.getRecieverPhn());
        assertEquals("2023-10-20", result.getDate());
        assertEquals("15:30", result.getTime());
        assertEquals("Location1", result.getSubscriberLocation());
        assertEquals("Location2", result.getRecieverLocation());
        assertEquals("Delivered", result.getStatus());
        assertEquals("Text", result.getType());
    }
}
