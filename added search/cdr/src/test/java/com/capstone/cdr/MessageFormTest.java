package com.capstone.cdr;


import org.junit.jupiter.api.Test;

import com.capstone.cdr.dto.MessageForm;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageFormTest {

    @Test
    public void testMessageForm() {
        // Create a MessageForm object
        MessageForm messageForm = new MessageForm();
        messageForm.setSubscriber(1);
        messageForm.setReciever(2);
        messageForm.setDate("2023-10-18");
        messageForm.setTime("14:30:00");
        messageForm.setSubscriberLocation("Location A");
        messageForm.setRecieverLocation("Location B");
        messageForm.setStatus("Delivered");
        messageForm.setType("SMS");

        // Check if the fields are set correctly
        assertEquals(1, messageForm.getSubscriber());
        assertEquals(2, messageForm.getReciever());
        assertEquals("2023-10-18", messageForm.getDate());
        assertEquals("14:30:00", messageForm.getTime());
        assertEquals("Location A", messageForm.getSubscriberLocation());
        assertEquals("Location B", messageForm.getRecieverLocation());
        assertEquals("Delivered", messageForm.getStatus());
        assertEquals("SMS", messageForm.getType());
    }
}
