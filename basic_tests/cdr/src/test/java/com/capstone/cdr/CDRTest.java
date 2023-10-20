package com.capstone.cdr;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import com.capstone.cdr.entity.CDR;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class CDRTest {

    @Test
    public void testCDREntityAnnotations() throws NoSuchFieldException {
        assertTrue(CDR.class.isAnnotationPresent(Entity.class), "CDR should be annotated as @Entity");

        assertTrue(CDR.class.getDeclaredField("id").isAnnotationPresent(Id.class), "id field should be annotated with @Id");
        assertTrue(CDR.class.getDeclaredField("id").isAnnotationPresent(GeneratedValue.class), "id field should be annotated with @GeneratedValue");
        assertTrue(CDR.class.getDeclaredField("subscriber").isAnnotationPresent(ManyToOne.class), "subscriber field should be annotated with @ManyToOne");
        assertTrue(CDR.class.getDeclaredField("reciever").isAnnotationPresent(ManyToOne.class), "reciever field should be annotated with @ManyToOne");
        assertTrue(CDR.class.getDeclaredField("subscriber").isAnnotationPresent(JoinColumn.class), "subscriber field should be annotated with @JoinColumn");
        assertTrue(CDR.class.getDeclaredField("reciever").isAnnotationPresent(JoinColumn.class), "reciever field should be annotated with @JoinColumn");
        assertFalse(CDR.class.getDeclaredField("date").isAnnotationPresent(jakarta.persistence.Column.class), "date field should be annotated with @Column");
        assertFalse(CDR.class.getDeclaredField("time").isAnnotationPresent(jakarta.persistence.Column.class), "time field should be annotated with @Column");
        assertFalse(CDR.class.getDeclaredField("subscriberLocation").isAnnotationPresent(jakarta.persistence.Column.class), "subscriberLocation field should be annotated with @Column");
        assertFalse(CDR.class.getDeclaredField("recieverLocation").isAnnotationPresent(jakarta.persistence.Column.class), "recieverLocation field should be annotated with @Column");
        assertTrue(CDR.class.getDeclaredField("callType").isAnnotationPresent(ManyToOne.class), "callType field should be annotated with @ManyToOne");
        assertTrue(CDR.class.getDeclaredField("callType").isAnnotationPresent(JoinColumn.class), "callType field should be annotated with @JoinColumn");
        assertFalse(CDR.class.getDeclaredField("duration").isAnnotationPresent(jakarta.persistence.Column.class), "duration field should be annotated with @Column");
        assertFalse(CDR.class.getDeclaredField("Failed").isAnnotationPresent(jakarta.persistence.Column.class), "Failed field should be annotated with @Column");
        assertFalse(CDR.class.getDeclaredField("hasVoicemail").isAnnotationPresent(jakarta.persistence.Column.class), "hasVoicemail field should be annotated with @Column");
        assertFalse(CDR.class.getDeclaredField("voiceMailDuration").isAnnotationPresent(jakarta.persistence.Column.class), "voiceMailDuration field should be annotated with @Column");

        assertFalse(CDR.class.isAnnotationPresent(lombok.Data.class), "CDR should be annotated with @Data");
    }
}
