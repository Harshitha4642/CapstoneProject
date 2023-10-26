package com.capstone.cdr;

import org.junit.jupiter.api.Test;
import com.capstone.cdr.dto.NormalCallForm;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NormalCallFormTest {

    @Test
    public void testNormalCallForm() {
        // Create a NormalCallForm object
        NormalCallForm normalCallForm = new NormalCallForm();
        normalCallForm.setSubscriber(1);
        normalCallForm.setReciever(2);
        normalCallForm.setDate("2023-10-18");
        normalCallForm.setTime("14:30:00");
        normalCallForm.setDuration(300); // 5 minutes
        normalCallForm.setSubscriberLocation("Location A");
        normalCallForm.setRecieverLocation("Location B");
        normalCallForm.setCallType("Voice");
        normalCallForm.setReason("Business");
        normalCallForm.setHasVoiceMail(true);
        normalCallForm.setVoiceMailDuration(60); // 1 minute

        // Check if the fields are set correctly
        assertEquals(1, normalCallForm.getSubscriber());
        assertEquals(2, normalCallForm.getReciever());
        assertEquals("2023-10-18", normalCallForm.getDate());
        assertEquals("14:30:00", normalCallForm.getTime());
        assertEquals(300, normalCallForm.getDuration());
        assertEquals("Location A", normalCallForm.getSubscriberLocation());
        assertEquals("Location B", normalCallForm.getRecieverLocation());
        assertEquals("Voice", normalCallForm.getCallType());
        assertEquals("Business", normalCallForm.getReason());
        assertEquals(true, normalCallForm.isHasVoiceMail());
        assertEquals(60, normalCallForm.getVoiceMailDuration());
    }
}
