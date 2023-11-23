package com.example.cyclingclub;

import java.util.LinkedList;

public class Participant extends User {
    Participant newUser;
    Event newRegistration;
    LinkedList<Event> registeredEvents;
    public Participant(String email, String username, String password, String salt) {
        super(email, username, "Participant", password, salt);
    }

    public void registerForEvent(){
        newRegistration = new Event();
        //newRegistration.users.add(newUser);
        registeredEvents.add(newRegistration);
    }

    /*
    public String searchByLocation(){
        return Event.getLocation();
    }

    public String searchByType(){
        return Event.getType();
    }
    public String searchByName(){
        return Event.getName();
    }
    public String searchByDate(){ return Event.getDate(); }

    public void viewEvents(){

    }


     */
}
