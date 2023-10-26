package com.capstone.cdr;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
}
