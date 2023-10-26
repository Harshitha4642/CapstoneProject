package com.capstone.cdr;

import org.junit.jupiter.api.Test;

import com.capstone.cdr.dto.ExcecutionStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExcecutionStatusTest {

    @Test
    public void testExcecutionStatus() {
        
        ExcecutionStatus excecutionStatus = new ExcecutionStatus();
        excecutionStatus.setStatus("Success");
        assertEquals("Success", excecutionStatus.getStatus());
    }
}
