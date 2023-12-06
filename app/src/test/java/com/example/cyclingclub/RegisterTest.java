package com.example.cyclingclub;

import static org.junit.Assert.*;

import static org.junit.Assert.assertThat;

import android.widget.ArrayAdapter;

import com.example.cyclingclub.activities.RegistrationManagementActivity;
import com.example.cyclingclub.adapters.RegistrationAdapter;
import com.example.cyclingclub.utils.SearchForClub;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;


@RunWith(JUnit4.class)

    public class RegisterTest {
    private User testUser;
    private RegistrationAdapter registrationAdapter;
    private RegistrationManagementActivity registrationManager;
    private List<Registration> registrations;
    private Event testEvent;

    private Registration register;

    private List<CyclingClub> clubs;

    private ArrayAdapter<CyclingClub> clubAdapter;
    private String type;
    private String eventName;
    private String clubName;
    private SearchForClub searcher;


    public void setUp() {
        // Initialize Participant object or mock it as needed
        testUser = new User();
        register = new Registration(testEvent,testUser);
        registrations.add(register);
        //user=new User();
        searcher = new SearchForClub(clubs,testUser,clubAdapter,type,eventName,clubName);
    }
    @Test
    public void testParticipantRegistration() {
        assertNotNull(registrations);

    }
    @Test
    public void testEventDiscovery(){
        assertNotNull(searcher);
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
