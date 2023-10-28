package com.capstone.cdr.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import com.capstone.cdr.entity.MessageCDR;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class MessageCDRTest {

    @Test
    public void testMessageCDREntityAnnotations() throws NoSuchFieldException {
        // Ensure that the MessageCDR class is annotated as an Entity
        assertTrue(MessageCDR.class.isAnnotationPresent(Entity.class), "MessageCDR should be annotated as @Entity");

        // Test the annotations on fields
        assertTrue(MessageCDR.class.getDeclaredField("id").isAnnotationPresent(Id.class), "id field should be annotated with @Id");
        assertTrue(MessageCDR.class.getDeclaredField("id").isAnnotationPresent(GeneratedValue.class), "id field should be annotated with @GeneratedValue");
        assertTrue(MessageCDR.class.getDeclaredField("subscriber").isAnnotationPresent(ManyToOne.class), "subscriber field should be annotated with @ManyToOne");
        assertTrue(MessageCDR.class.getDeclaredField("reciever").isAnnotationPresent(ManyToOne.class), "reciever field should be annotated with @ManyToOne");
        assertTrue(MessageCDR.class.getDeclaredField("subscriber").isAnnotationPresent(JoinColumn.class), "subscriber field should be annotated with @JoinColumn");
        assertTrue(MessageCDR.class.getDeclaredField("reciever").isAnnotationPresent(JoinColumn.class), "reciever field should be annotated with @JoinColumn");
        assertFalse(MessageCDR.class.getDeclaredField("date").isAnnotationPresent(jakarta.persistence.Column.class), "date field should be annotated with @Column");
        assertFalse(MessageCDR.class.getDeclaredField("time").isAnnotationPresent(jakarta.persistence.Column.class), "time field should be annotated with @Column");
        assertFalse(MessageCDR.class.getDeclaredField("subscriberLocation").isAnnotationPresent(jakarta.persistence.Column.class), "subscriberLocation field should be annotated with @Column");
        assertFalse(MessageCDR.class.getDeclaredField("recieverLocation").isAnnotationPresent(jakarta.persistence.Column.class), "recieverLocation field should be annotated with @Column");
        assertTrue(MessageCDR.class.getDeclaredField("messageType").isAnnotationPresent(ManyToOne.class), "messageType field should be annotated with @ManyToOne");
        assertTrue(MessageCDR.class.getDeclaredField("messageType").isAnnotationPresent(JoinColumn.class), "messageType field should be annotated with @JoinColumn");
        assertFalse(MessageCDR.class.getDeclaredField("sentStatus").isAnnotationPresent(jakarta.persistence.Column.class), "sentStatus field should be annotated with @Column");

        // Ensure that lombok's @Data annotation is present
        assertFalse(MessageCDR.class.isAnnotationPresent(lombok.Data.class), "MessageCDR should be annotated with @Data");
    }
    @Test
    void testEquals() {
        // Create two instances of MessageCDR
        Customer harshitha = new Customer();
        harshitha.setId(1);
        harshitha.setName("harshi");
        harshitha.setPhonenumber("8987828");

        MessageType type = new MessageType();
        type.setId(1);
        type.setRate(12);
        type.setType("test");

        MessageCDR messageCDR1 = new MessageCDR();
        messageCDR1.setId(1);
        messageCDR1.setSubscriber(harshitha);
        messageCDR1.setReciever(harshitha);
        messageCDR1.setDate(LocalDate.of(2023, 10, 26));
        messageCDR1.setTime(LocalTime.of(10, 0));
        messageCDR1.setSubscriberLocation("Location1");
        messageCDR1.setRecieverLocation("Location2");
        messageCDR1.setMessageType(type);
        messageCDR1.setSentStatus("sent");

        MessageCDR messageCDR2 = new MessageCDR();
        messageCDR2.setId(1);
        messageCDR2.setSubscriber(harshitha);
        messageCDR2.setReciever(harshitha);
        messageCDR2.setDate(LocalDate.of(2023, 10, 26));
        messageCDR2.setTime(LocalTime.of(10, 0));
        messageCDR2.setSubscriberLocation("Location1");
        messageCDR2.setRecieverLocation("Location2");
        messageCDR2.setMessageType(type);
        messageCDR2.setSentStatus("sent");
        assertTrue(messageCDR1.canEqual(messageCDR2));
        assertTrue(messageCDR1.equals(messageCDR2));
        assertEquals(messageCDR1.hashCode(), messageCDR2.hashCode());

    }
}
