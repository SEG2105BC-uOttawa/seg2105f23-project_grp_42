package com.example.cyclingclub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Administrator extends User{
    private static List<Event> events;
    public Administrator(){
        events = new ArrayList<Event>();
    }
    public Event getEvent(int index)
    {
        Event aEvent = events.get(index);
        return aEvent;
    }
    public static List<Event> getEvents()
    {
        List<Event> newEvents = Collections.unmodifiableList(events);
        return newEvents;
    }
    public int numberOfEvents()
    {
        int number = events.size();
        return number;
    }
    public boolean hasEvents()
    {
        boolean has = events.size() > 0;
        return has;
    }
    public int indexOfEvent(Event aEvent)
    {
        int index = events.indexOf(aEvent);
        return index;
    }
    public static int minimumNumberOfEvents()
    {
        return 0;
    }
    public boolean addEvent(Event aEvent)
    {
        boolean wasAdded = false;
        if (events.contains(aEvent)) { return false; }
        events.add(aEvent);
        if (aEvent.indexOfAdmin(this) != -1)
        {
            wasAdded = true;
        }else
        {
            wasAdded = aEvent.addAdmin(this);
            if (!wasAdded)
            {
                events.remove(aEvent);
            }
        }
        return wasAdded;
    }

    public boolean removeEvent(Event aEvent)
    {
        boolean wasRemoved = false;
        if (!events.contains(aEvent))
        {
            return wasRemoved;
        }
        int oldIndex = events.indexOf(aEvent);
        events.remove(oldIndex);
        if (aEvent.indexOfAdmin(this) == -1)
        {
            wasRemoved = true;
        } else
        {
            wasRemoved = aEvent.removeAdmin(this);
            if (!wasRemoved)
            {
                events.add(oldIndex,aEvent);
            }
        }
        return wasRemoved;
    }
    public boolean addEventAt(Event aEvent, int index)
    {
        boolean wasAdded = false;
        if(addEvent(aEvent))
        {
            if(index < 0 ) { index = 0; }
            if(index > numberOfEvents()) { index = numberOfEvents() - 1; }
            events.remove(aEvent);
            events.add(index, aEvent);
            wasAdded = true;
        }
        return wasAdded;
    }
    public boolean addOrMoveEventAt(Event aEvent, int index)
    {
        boolean wasAdded = false;
        if(events.contains(aEvent))
        {
            if(index < 0 ) { index = 0; }
            if(index > numberOfEvents()) { index = numberOfEvents() - 1; }
            events.remove(aEvent);
            events.add(index, aEvent);
            wasAdded = true;
        } else
        {
            wasAdded = addEventAt(aEvent, index);
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
        Event aEvent = new Event();
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
