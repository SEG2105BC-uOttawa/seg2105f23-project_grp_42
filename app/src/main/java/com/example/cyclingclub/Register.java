package com.example.cyclingclub;

import java.util.List;

public class Register {

    private  Participant participant;
    private List<Event> events;
    Event newRegistration;
    public  Register(){

    }
    public void registerForEvent(){
        newRegistration = new Event();
        //newRegistration.users.add(this);
        events.add(newRegistration);
    }
}
