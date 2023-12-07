package com.example.cyclingclub;

import android.widget.ArrayAdapter;
import com.example.cyclingclub.activities.SearchForClub;
import com.example.cyclingclub.utils.Utils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AppUnitTest {
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

    @Test
    public void userEmail_isCorrect() {
        User user = new User("test@example.com", "username", "role", "password", "salt");
        assertEquals("test@example.com", user.getEmail());
        assertTrue(user.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$"));
    }

    @Test
    public void eventRegionAndDate_areCorrect() {
        Event event = new Event("key", "eventId", "eventType", "eventDetail", "eventRegion", "eventDate", 1, 10.0, 100, "eventDistance", "eventElevation");
        assertEquals("eventRegion", event.getRegion());
        assertEquals("eventDate", event.getDate());
    }

    @Test
    public void eventDate_isCorrect() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        Date date = sdf.parse("2023-01-01");
        Event event = new Event("key", "eventId", "eventType", "eventDetail", "eventRegion", sdf.format(date), 1, 10.0, 100, "eventDistance", "eventElevation");
        assertTrue(sdf.parse(event.getDate()).after(new Date()));
    }

    @Test
    public void eventTypeKey_isCorrect() {
        EventType eventType = new EventType("key", "typeName", "detail");
        assertEquals("key", eventType.getKey());
    }

    @Test
    public void userPasswordAndSalt_areCorrect() {
        User user = new User("test@example.com", "username", "role", "password", "salt");
        assertEquals("password", user.getPassword());
        assertEquals("salt", user.getSalt());
    }

    @Test
    public void eventDetailDistanceAndElevation_areCorrect() {
        Event event = new Event("key", "eventId", "eventType", "eventDetail", "eventRegion", "eventDate", 1, 10.0, 100, "eventDistance", "eventElevation");
        assertEquals("eventDetail", event.getDetail());
        assertEquals("eventDistance", event.getDistance());
        assertEquals("eventElevation", event.getElevation());
    }

    @Test
    public void isValidString_handlesNull() {
        Utils utils = Utils.getInstance();
        assertFalse(utils.isValidString(null));
    }

    @Test
    public void eventTypeKey_isUnique() {
        EventType eventType1 = new EventType("key", "typeName", "detail");
        EventType eventType2 = new EventType("key", "typeName", "detail");
        assertNotEquals(eventType1.getKey(), eventType2.getKey());
    }

    @Test
    public void isValidString_handlesSpecialCharacters() {
        Utils utils = Utils.getInstance();
        assertFalse(utils.isValidString("@#%&*"));
    }
}