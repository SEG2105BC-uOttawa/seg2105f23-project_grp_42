package com.example.cyclingclub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User {
    //User associations
    private List<Event> events;
    private CyclingClub cyclingClub;
    String username;
    String password;
    String type;
    public User(CyclingClub aCyclingClub)
    {
        events = new ArrayList<Event>();
        if (aCyclingClub == null || aCyclingClub.getUser() != null)
        {
            throw new RuntimeException("Unable to create User due to aCyclingClub");
        }
        cyclingClub = aCyclingClub;
    }

    public User()
    {
        events = new ArrayList<Event>();
        cyclingClub = new CyclingClub(this);
    }
    public Event getEvent(int index)
    {
        Event aEvent = events.get(index);
        return aEvent;
    }
    public List<Event> getEvents()
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
    public CyclingClub getCyclingClub()
    {
        return cyclingClub;
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
        if (aEvent.indexOfUser(this) != -1)
        {
            wasAdded = true;
        }else
        {
            wasAdded = aEvent.addUser(this);
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
        if (aEvent.indexOfUser(this) == -1)
        {
            wasRemoved = true;
        } else
        {
            wasRemoved = aEvent.removeUser(this);
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
    public void delete()
    {
        ArrayList<Event> copyOfEvents = new ArrayList<Event>(events);
        events.clear();
        for(Event aEvent : copyOfEvents)
        {
            aEvent.removeUser(this);
        }
        CyclingClub existingCyclingClub = cyclingClub;
        cyclingClub = null;
        if (existingCyclingClub != null)
        {
            existingCyclingClub.delete();
        }

    }

}
