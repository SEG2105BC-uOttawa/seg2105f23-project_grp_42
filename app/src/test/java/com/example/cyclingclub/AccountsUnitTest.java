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

//    @Test
//    public void testSearchClub() {
//        // Create a list of clubs
//        List<CyclingClub> clubs = new ArrayList<>();
//        clubs.add(new CyclingClub("key1", "Club 1", "1234567890", "Region 1"));
//        clubs.add(new CyclingClub("key2", "Club 2", "0987654321", "Region 2"));
//        clubs.add(new CyclingClub("key3", "Club 3", "1122334455", "Region 1"));
//
//        // Create a SearchForClub object
//        SearchForClub searchForClub = new SearchForClub();
//
//        // Call the searchClub() method
//        searchForClub.searchClub(clubs, null, "Region 1", "eventName", "Club 1");
//
//        // Check if the returned list contains the expected clubs
//        assertEquals(1, clubs.size());
//        assertEquals("Club 1", clubs.get(0).getClubName());
//    }

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