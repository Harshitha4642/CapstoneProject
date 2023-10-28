package com.capstone.cdr.repository;

import com.capstone.cdr.entity.Customer;
import com.capstone.cdr.entity.MessageCDR;
import com.capstone.cdr.entity.MessageType;
import com.capstone.cdr.repository.CustomerRepository;
import com.capstone.cdr.repository.MessageCDRRepository;
import com.capstone.cdr.repository.MessageTypeRepository;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class MessageCDRRepositoryTest {

    @Autowired
    private MessageCDRRepository messageCDRRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MessageTypeRepository messageTypeRepository;

    @Test
    public void testSaveMessageCDR() {
        // Create Customer entities
        Customer subscriber = new Customer();
        subscriber.setName("Alice");
        subscriber.setPhonenumber("1234567890");

        Customer receiver = new Customer();
        receiver.setName("Bob");
        receiver.setPhonenumber("9876543210");

        customerRepository.save(subscriber);
        customerRepository.save(receiver);

        // Create MessageType entity
        MessageType messageType = new MessageType();
        messageType.setType("SMS");
        messageType.setRate(0);

        messageTypeRepository.save(messageType);

        // Create a MessageCDR entity
        MessageCDR messageCDR = new MessageCDR();
        messageCDR.setSubscriber(subscriber);
        messageCDR.setReciever(receiver);
        messageCDR.setMessageType(messageType);

        // Save the MessageCDR entity to the repository
        messageCDRRepository.save(messageCDR);

        // Retrieve the saved entity
        MessageCDR savedMessageCDR = messageCDRRepository.findById(messageCDR.getId()).orElse(null);

        // Perform assertions to check if the entity was saved and retrieved correctly
        assertThat(savedMessageCDR).isNotNull();
        assertThat(savedMessageCDR.getSubscriber()).isEqualTo(subscriber);
        assertThat(savedMessageCDR.getReciever()).isEqualTo(receiver);
        assertThat(savedMessageCDR.getMessageType()).isEqualTo(messageType);
    }

    @Test
    public void testFindMessageCDRById() {
        
        Customer subscriber = new Customer();
        subscriber.setName("Carol");
        subscriber.setPhonenumber("1111111111");

        Customer receiver = new Customer();
        receiver.setName("David");
        receiver.setPhonenumber("2222222222");

        customerRepository.save(subscriber);
        customerRepository.save(receiver);

       
        MessageType messageType = new MessageType();
        messageType.setType("MMS");
        messageType.setRate(1);

        messageTypeRepository.save(messageType);

       
        MessageCDR messageCDR = new MessageCDR();
        messageCDR.setSubscriber(subscriber);
        messageCDR.setReciever(receiver);
        messageCDR.setMessageType(messageType);

       
        messageCDRRepository.save(messageCDR);

        MessageCDR retrievedMessageCDR = messageCDRRepository.findById(messageCDR.getId()).orElse(null);

        
        assertThat(retrievedMessageCDR).isNotNull();
        assertThat(retrievedMessageCDR.getSubscriber()).isEqualTo(subscriber);
        assertThat(retrievedMessageCDR.getReciever()).isEqualTo(receiver);
        assertThat(retrievedMessageCDR.getMessageType()).isEqualTo(messageType);
    }
}
