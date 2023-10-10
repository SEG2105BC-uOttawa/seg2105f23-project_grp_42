package com.example.cyclingclub;

import java.util.Collections;
import java.util.List;

public class EventOrganizer extends User{
    private List<Event> events;
    public void createProfile(){

    }

    public List<Event> selectEvents(){
        List<Event> newEvents = Collections.unmodifiableList(events);
        return newEvents;
    }

}
