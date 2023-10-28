package com.capstone.cdr.dto;

import org.junit.jupiter.api.Test;

import com.capstone.cdr.dto.ExcecutionStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ExcecutionStatusTest {

    @Test
    public void testExcecutionStatus() {
        
        ExcecutionStatus excecutionStatus = new ExcecutionStatus();
        excecutionStatus.setStatus("Success");
        assertEquals("Success", excecutionStatus.getStatus());
        ExcecutionStatus status2 = new ExcecutionStatus();
        status2.setStatus("he he he");
        assertNotEquals(excecutionStatus.toString(), status2.toString());
    }
}
