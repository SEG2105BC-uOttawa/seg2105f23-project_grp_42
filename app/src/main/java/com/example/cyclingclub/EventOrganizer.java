package com.example.cyclingclub;

import java.util.Collections;
import java.util.List;

//EventOrganizer is merged with CyclingClub
public class EventOrganizer extends User {
    private static List<Event> events;
    public String name;
    public String region;
    public int levelOfDifficulty;
    public int numOfParticipants;
    public double fees;

    public EventOrganizer(String email, String username, String role, String password, String salt) {
        super(email, username, role, password, salt);
    }

    public void createProfile(){
        //Event.name = name;
    }

    public List<Event> selectEvents(){
        events = Administrator.getEvents();
        List<Event> newEvents = Collections.unmodifiableList(events);
        return newEvents;
    }
    public void createEvent(){
        Event aEvent = new Event();
        String eventType = Event.getType();
        createRoute(eventType);
    }

    public void createRoute(String eventType){
        if(eventType == "Hill Climb"){
            HillClimb hillClimb = new HillClimb();
        } else if (eventType == "Group Riders") {
            GroupRiders groupRiders = new GroupRiders();
        } else if (eventType == "Road Stage Race"){
            RoadStageRace roadStageRace = new RoadStageRace();
        }
    }

    public double distributeAwards(){

        return 0;
    }

    public void acceptRegistrations(){

    }

    public void updates(){

    }

}
