package com.example.cyclingclub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Administrator extends User{
    private static List<Event> events;

    public Administrator(){
        this.username = "admin";
        this.password = "admin";
        this.role = "admin";
        events = new ArrayList<Event>();
    }

    public Event getEvent(int index)
    {
        return events.get(index);
    }

    public static List<Event> getEvents()
    {
        return Collections.unmodifiableList(events);
    }

    public int numberOfEvents()
    {
        return events.size();
    }

    public boolean hasEvents()
    {
        return !events.isEmpty();
    }

    public int indexOfEvent(Event event)
    {
        return events.indexOf(event);
    }

    public static int minimumNumberOfEvents()
    {
        return 0;
    }

    public boolean addEvent(Event event)
    {
        boolean wasAdded = false;
        if (events.contains(event)) { return false; }
        events.add(event);
        if (event.indexOfAdmin(this) != -1)
        {
            wasAdded = true;
        } else {
            wasAdded = event.addAdmin(this);
            if (!wasAdded)
            {
                events.remove(event);
            }
        }
        return wasAdded;
    }

    public boolean removeEvent(Event event)
    {
        boolean wasRemoved = false;
        if (!events.contains(event))
        {
            return wasRemoved;
        }
        int oldIndex = events.indexOf(event);
        events.remove(oldIndex);
        if (event.indexOfAdmin(this) == -1)
        {
            wasRemoved = true;
        } else {
            wasRemoved = event.removeAdmin(this);
            if (!wasRemoved)
            {
                events.add(oldIndex,event);
            }
        }
        return wasRemoved;
    }

    public boolean addEventAt(Event event, int index)
    {
        boolean wasAdded = false;
        if(addEvent(event))
        {
            if(index < 0 ) { index = 0; }
            if(index > numberOfEvents()) { index = numberOfEvents() - 1; }
            events.remove(event);
            events.add(index, event);
            wasAdded = true;
        }
        return wasAdded;
    }

    public boolean addOrMoveEventAt(Event event, int index)
    {
        boolean wasAdded = false;
        if(events.contains(event))
        {
            if(index < 0 ) { index = 0; }
            if(index > numberOfEvents()) { index = numberOfEvents() - 1; }
            events.remove(event);
            events.add(index, event);
            wasAdded = true;
        } else
        {
            wasAdded = addEventAt(event, index);
        }
        return wasAdded;
    }

    public void delete() {
        ArrayList<Event> copyOfEvents = new ArrayList<Event>(events);
        events.clear();
        for (Event aEvent : copyOfEvents) {
            aEvent.removeAdmin(this);
        }
    }

    public void createEvent(){
        Event event = new Event();
    }

    public void manageContent(){

    }

    public void viewUsers(){

    }

    public void deleteUser(){

    }

    public void createEventType(){

    }

    public void deleteEventType(){

    }

    public void viewEventType(){

    }
}
