package com.example.cyclingclub;

import java.util.Collections;
import java.util.List;
//EventOrganizer is merged withe CyclingClub
public class EventOrganizer extends User{
    private static List<Event> events;
    public String name;
    public String region;
    public int levelOfDifficulty;
    public int numOfParticipants;
    public double fees;

    public void createProfile(){

    }

    public List<Event> selectEvents(){
        events = Administrator.getEvents();
        List<Event> newEvents = Collections.unmodifiableList(events);
        return newEvents;
    }
    public void createEvent(){
        Event aEvent = new Event();
    }

    public void createRoute(){

    }

    public double distributeAwards(){

        return 0;
    }

    public void acceptRegistrations(){

    }

    public void updates(){

    }

}
