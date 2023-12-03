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
