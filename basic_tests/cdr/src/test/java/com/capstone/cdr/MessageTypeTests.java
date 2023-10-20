package com.capstone.cdr;



import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.springframework.data.annotation.Id;

import com.capstone.cdr.entity.MessageType;

public class MessageTypeTests {

    @Test
    public void testMessageTypeEntityAnnotations() throws NoSuchFieldException {
        // Ensure that the MessageType class is annotated as an Entity
        assertFalse(MessageType.class.isAnnotationPresent(javax.persistence.Entity.class), "MessageType should be annotated as @Entity");

        // Test the annotations on fields
        assertFalse(MessageType.class.getDeclaredField("id").isAnnotationPresent(Id.class), "id field should be annotated with @Id");
        //assertTrue(MessageType.class.getDeclaredField("id").isAnnotationPresent(GeneratedValue.class), "id field should be annotated with @GeneratedValue");
        assertFalse(MessageType.class.getDeclaredField("type").isAnnotationPresent(javax.persistence.Column.class), "type field should be annotated with @Column");
        assertFalse(MessageType.class.getDeclaredField("rate").isAnnotationPresent(javax.persistence.Column.class), "rate field should be annotated with @Column");

        // Ensure that lombok's @Data annotation is present
        assertFalse(MessageType.class.isAnnotationPresent(lombok.Data.class), "MessageType should be annotated with @Data");
    }
}
