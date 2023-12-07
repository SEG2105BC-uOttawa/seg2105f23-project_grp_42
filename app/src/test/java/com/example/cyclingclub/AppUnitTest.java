package com.example.cyclingclub;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AppUnitTest {
    @Test
    public void inputValidation_isCorrect() {
        InputValidator utils = InputValidator.getInstance();
        assertTrue(utils.isValidString("Test"));
        assertFalse(utils.isValidString(""));
        assertFalse(utils.isValidString("123"));
    }

    @Test
    public void accountCreation_isCorrect() {
        User user = new User("test@example.com", "username", "role", "password", "salt");
        assertEquals("username", user.getUsername());
        assertEquals("role", user.getRole());
    }

    @Test
    public void eventCreation_isCorrect() {
        Event event = new Event("key", "eventId", "eventType",  "eventDate");
        assertEquals("eventId", event.getId());
        assertEquals("eventType", event.getType());
    }

    @Test
    public void eventTypeCreation_isCorrect() {
        EventType eventType = new EventType("key", "typeName", "detail");
        assertEquals("typeName", eventType.getTypeName());
        assertEquals("detail", eventType.getDetail());
    }

    @Test
    public void userEmail_isCorrect() {
        User user = new User("test@example.com", "username", "role", "password", "salt");
        assertEquals("test@example.com", user.getEmail());
        assertTrue(user.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$"));
    }

    @Test
    public void eventTypeKey_isCorrect() {
        EventType eventType = new EventType("key", "typeName", "detail");
        assertEquals("key", eventType.getId());
    }

    @Test
    public void userPasswordAndSalt_areCorrect() {
        User user = new User("test@example.com", "username", "role", "password", "salt");
        assertEquals("password", user.getPassword());
        assertEquals("salt", user.getSalt());
    }


    @Test
    public void isValidString_handlesNull() {
        InputValidator utils = InputValidator.getInstance();
        assertFalse(utils.isValidString(null));
    }

    @Test
    public void eventTypeKey_isUnique() {
        EventType eventType1 = new EventType("key", "typeName", "detail");
        EventType eventType2 = new EventType("2", "typeName", "detail");
        assertNotEquals(eventType1.getId(), eventType2.getId());
    }

    @Test
    public void isValidString_handlesSpecialCharacters() {
        InputValidator utils = InputValidator.getInstance();
        assertFalse(utils.isValidString("@#%&*"));
    }
}