package com.example.cyclingclub;

import java.util.ArrayList;
import java.util.List;

public class Register {

    private  Participant participant;
    private List<Event> events;
    int eventIndex;

    private Event newRegistration;
    public  Register(Participant aParticpant){
        events = new ArrayList<Event>();
        if (aParticpant == null || aParticpant.getRegistration() == null){

        }
    }

    public Register(String username){
        events = new ArrayList<Event>();
        participant = new Participant(username, this);
    }
    public void registerForEvent(String key){
        eventIndex = EventManagement.getEvents().indexOf(key);
        //newRegistration.users.add(this);
        newRegistration = EventManagement.getEvents().get(eventIndex);
        events.add(newRegistration);

    }
    public Participant getParticipant(){
        return participant;
    }
}
