package com.example.cyclingclub;

import android.widget.ArrayAdapter;
import com.example.cyclingclub.activities.SearchForClub;
import com.example.cyclingclub.utils.Utils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AccountsUnitTest {
    @Test
    public void inputValidation_isCorrect() {
        Utils utils = Utils.getInstance();
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
        Event event = new Event("key", "eventId", "eventType", "eventDetail", "eventRegion", "eventDate", 1, 10.0, 100, "eventDistance", "eventElevation");
        assertEquals("eventId", event.getId());
        assertEquals("eventType", event.getType());
    }

    @Test
    public void eventTypeCreation_isCorrect() {
        EventType eventType = new EventType("key", "typeName", "detail");
        assertEquals("typeName", eventType.getTypeName());
        assertEquals("detail", eventType.getDetail());
    }
}