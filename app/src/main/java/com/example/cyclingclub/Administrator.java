package com.example.cyclingclub;

import static java.security.AccessController.getContext;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class Administrator extends User{
    /*
    Methods to establish an association with Events
    */
    //The cardinality is many to many, both classes have a list of the other class
    private static List<Event> events;
    public static List<EventType> eventTypes;
    private DatabaseReference eventTypesDB;

    //create a constructor with the array list of the events
    public Administrator(String username, String password) {
        super(null, username, "Administrator", password, null);
        events = new ArrayList<Event>();

        eventTypes=new ArrayList<EventType>();
    }

    //Get the event index from the event array
    public Event getEvent(int index)
    {
        return events.get(index);
    }
    //Return the array with the events
    public static List<Event> getEvents()
    {
        return Collections.unmodifiableList(events);
    }

    //Create a counter to add and subtract events from the array
    public int numberOfEvents()
    {
        return events.size();
    }
    //Check if cardinality is > 0
    public boolean hasEvents()
    {
        return !events.isEmpty();
    }
    //Return the index of any given event
    public int indexOfEvent(Event event)
    {
        return events.indexOf(event);
    }

    //Minimum cardinality is 0
    public static int minimumNumberOfEvents()
    {
        return 0;
    }

    public boolean addEvent(Event event)
    //Checks if an event was added onto the array
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
    //Checks if an event was removed from the array
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
    //Add an event in the array at a given index
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
    //Move or add any event at any given index
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

    //Clear the events' array
    public void delete() {
        ArrayList<Event> copyOfEvents = new ArrayList<Event>(events);
        events.clear();
        for (Event aEvent : copyOfEvents) {
            aEvent.removeAdmin(this);
        }
    }

    //Create a new event
    public void createEvent(){
        Event event = new Event();
    }

    public void manageContent(){

    }

    public void viewUsers(){

    }

    public void deleteUser(DatabaseReference db, String key){
        db.child(key).removeValue();
    }

    public void setEventTypeDB(DatabaseReference db){
        this.eventTypesDB=db;
    }

    public void createEventType(EventType et){
        String id = eventTypesDB.push().getKey();
        et.setId(id);
        eventTypesDB.child(id).setValue(et);
    }

    public void updateEventType(EventType et){
        eventTypesDB.child(et.getId()).setValue(et);
    }

    public List<EventType>   getEventTypes(){
        return eventTypes;
    }

    public void deleteEventType(EventType et){
        eventTypesDB.child(et.getId()).removeValue();
    }

    public void viewEventType(EventType et){

    }


}