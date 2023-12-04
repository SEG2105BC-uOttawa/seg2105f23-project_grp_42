package com.example.cyclingclub;

import static org.junit.Assert.*;

import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)

    public class RegisterTest {
    private Participant participant;
    private User user;

    public void setUp() {
        // Initialize Participant object or mock it as needed
        participant = new Participant("admin",);
        //user=new User();
    }
    @Test
    public void testParticipantRegistration() {
        assertThat(participant.registerForEvent);
    }
    }
  // rough code for reference// @Nick please see this if it makes sense ==>Ketan


//
//    @Before
//    public void setUp() {
//        // Initialize Participant object or mock it as needed
//        participant = new Participant();
//    }
//
//    @Test
//    public void testParticipantRegistration() {
//        assertTrue(participant.register());
//    }
//
//    @Test
//    public void testEventDiscovery() {
//        // Assuming events are available for discovery
//        assertNotNull(participant.browseAndSearchEvents("Location", "Date", "Type"));
//    }
//
//    @Test
//    public void testEventRegistration() {
//        Event event = new Event(); // Initialize Event object or mock it as needed
//        assertTrue(participant.registerForEvent(event));
//    }
//
//    @Test
//    public void testEventDetails() {
//        Event event = new Event(); // Initialize Event object or mock it as needed
//        assertNotNull(participant.viewEventDetails(event));
//    }
//
//    @Test
//    public void testRouteTracking() {
//        // Assuming the route tracking feature is available
//        assertTrue(participant.useRouteTracking());
//    }
//
//    @Test
//    public void testAccountCreation() {
//        assertTrue(participant.createAccount());
//    }
//
//    @Test
//    public void testInvalidEventRegistration() {
//        Event fullEvent = new Event(); // Initialize Event object with full capacity
//        assertFalse(participant.registerForEvent(fullEvent));
//    }
//
//    @Test
//    public void testSearchCyclingClubByName() {
//        CyclingClub cyclingClub = new CyclingClub(); // Initialize CyclingClub object or mock it as needed
//        assertNotNull(participant.searchCyclingClubByName("ClubName"));
//    }
//
//    @Test
//    public void testSearchCyclingClubByEventType() {
//        CyclingClub cyclingClub = new CyclingClub(); // Initialize CyclingClub object or mock it as needed
//        assertNotNull(participant.searchCyclingClubByEventType("EventType"));
//    }
//
//    @Test
//    public void testEventConfirmationCommunication() {
//        Event event = new Event(); // Initialize Event object or mock it as needed
//        assertTrue(participant.receiveEventConfirmationCommunication(event));git s
//    }
//}
//
