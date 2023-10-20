package com.capstone.cdr;

import com.capstone.cdr.entity.MessageType;
import com.capstone.cdr.repository.MessageTypeRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest

public class MessageTypeRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MessageTypeRepository messageTypeRepository;

    @Test
    public void testFindByType() {
        // Create a MessageType and persist it to the database
        MessageType messageType = new MessageType();
        messageType.setType("TestType");
        messageType.setRate(42);

        entityManager.persist(messageType);
        entityManager.flush();

        // Use the repository to find the MessageType by type
        MessageType foundMessageType = messageTypeRepository.findByType("TestType");

        // Assertions
        assertNotNull(foundMessageType);
        assertEquals("TestType", foundMessageType.getType());
        assertEquals(42, foundMessageType.getRate());
    }

    @Test
    public void testFindByTypeNotFound() {
        // Use the repository to find a non-existing MessageType
        MessageType notFoundMessageType = messageTypeRepository.findByType("NonExistentType");

        // Assertion: Should return null for a type that doesn't exist
        assertNull(notFoundMessageType);
    }
}
