package com.example.cyclingclub;

import com.google.firebase.database.DatabaseReference;

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
    public void changeEvent(String name,DatabaseReference db){
        int index = Administrator.getEvents().indexOf(name);
        Event aEvent = Administrator.getEvents().get(index);
        String eventType = String.valueOf(Administrator.getEventType(index));
        double duration = Event.getDuration();
        changeRoute(eventType, duration);

    }

    public void changeRoute( String oldEventType, String newEventType, double duration ){
        boolean ifChange = true;
        while(ifChange == true){

        }
        if(newEventType == "Hill Climb"){
            HillClimb hillClimb = new HillClimb();
        } else if (newEventType == "Group Riders") {
            GroupRiders groupRiders= new GroupRiders();
        } else if (newEventType == "Road Stage Race"){
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
