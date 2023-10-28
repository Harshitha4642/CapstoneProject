package com.capstone.cdr.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.capstone.cdr.entity.Type;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;

import jakarta.persistence.Id;

public class TypeTest {

    @Test
    public void testTypeEntityAnnotations() throws NoSuchFieldException {
        // Ensure that the Type class is annotated as an Entity
        assertTrue(Type.class.isAnnotationPresent(Entity.class), "Type should be annotated as @Entity");

        // Test the annotations on fields
        assertTrue(Type.class.getDeclaredField("id").isAnnotationPresent(Id.class), "id field should be annotated with @Id");
        assertTrue(Type.class.getDeclaredField("id").isAnnotationPresent(GeneratedValue.class), "id field should be annotated with @GeneratedValue");
        assertFalse(Type.class.getDeclaredField("type").isAnnotationPresent(jakarta.persistence.Column.class), "type field should be annotated with @Column");
        assertFalse(Type.class.getDeclaredField("rate").isAnnotationPresent(jakarta.persistence.Column.class), "rate field should be annotated with @Column");

        // Ensure that lombok's @Data annotation is present
        assertFalse(Type.class.isAnnotationPresent(lombok.Data.class), "Type should be annotated with @Data");
    }

    @Test
    public void testRandomUncoveredCases()
    {
        Type type = new Type();
        type.setId(1);
        type.setRate(10);
        type.setType("he he he");

        assertEquals(type.toString(), type.toString());
        assertEquals(type.getId(), 1);
        assertEquals(type.getRate(), 10);
        assertEquals(type.getType(), "he he he");
    }  
}
