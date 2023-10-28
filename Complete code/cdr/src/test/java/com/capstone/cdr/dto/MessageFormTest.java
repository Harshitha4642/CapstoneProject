package com.capstone.cdr.dto;


import org.junit.jupiter.api.Test;

import com.capstone.cdr.dto.MessageForm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    @Test
    void testEquals() {
        MessageForm messageForm1 = new MessageForm();
        messageForm1.setSubscriber(1);
        messageForm1.setReciever(2);
        messageForm1.setDate("2023-10-26");
        messageForm1.setTime("10:00");
        messageForm1.setSubscriberLocation("Location1");
        messageForm1.setRecieverLocation("Location2");
        messageForm1.setStatus("sent");
        messageForm1.setType("type");

        MessageForm messageForm2 = new MessageForm();
        messageForm2.setSubscriber(1);
        messageForm2.setReciever(2);
        messageForm2.setDate("2023-10-26");
        messageForm2.setTime("10:00");
        messageForm2.setSubscriberLocation("Location1");
        messageForm2.setRecieverLocation("Location2");
        messageForm2.setStatus("sent");
        messageForm2.setType("type");

        MessageForm differentMessageForm = new MessageForm();
        differentMessageForm.setSubscriber(3);
        differentMessageForm.setReciever(4);
        differentMessageForm.setDate("2023-10-27");
        differentMessageForm.setTime("11:00");
        differentMessageForm.setSubscriberLocation("Location3");
        differentMessageForm.setRecieverLocation("Location4");
        differentMessageForm.setStatus("received");
        differentMessageForm.setType("otherType");

        // Test for equality
        assertEquals(messageForm1, messageForm2);
        assertNotEquals(messageForm1, differentMessageForm);

        assertTrue(messageForm1.equals(messageForm2));
        assertTrue(messageForm1.canEqual(differentMessageForm));
        assertEquals(messageForm1.hashCode(), messageForm1.hashCode());
    }
}
