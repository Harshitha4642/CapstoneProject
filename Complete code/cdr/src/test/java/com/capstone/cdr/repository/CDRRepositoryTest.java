package com.capstone.cdr.repository;

import com.capstone.cdr.entity.CDR;
import com.capstone.cdr.repository.CDRRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CDRRepositoryTest {

    @Autowired
    private CDRRepository cdrRepository;

    @Test
    public void testSaveCDR() {
        
        CDR cdr = new CDR();
        cdr.setSubscriberLocation("Location1");
        cdr.setRecieverLocation("Location2");

        
        cdrRepository.save(cdr);

        
        CDR savedCDR = cdrRepository.findById(cdr.getId()).orElse(null);

        
        assertThat(savedCDR).isNotNull();
        assertThat(savedCDR.getSubscriberLocation()).isEqualTo("Location1");
        assertThat(savedCDR.getRecieverLocation()).isEqualTo("Location2");
    }

    @Test
    public void testFindCDRById() {
        CDR cdr = new CDR();
        cdr.setSubscriberLocation("Location1");
        cdr.setRecieverLocation("Location2");
        cdrRepository.save(cdr);
        CDR retrievedCDR = cdrRepository.findById(cdr.getId()).orElse(null);
        assertThat(retrievedCDR).isNotNull();
        assertThat(retrievedCDR.getSubscriberLocation()).isEqualTo("Location1");
        assertThat(retrievedCDR.getRecieverLocation()).isEqualTo("Location2");
    }
}
