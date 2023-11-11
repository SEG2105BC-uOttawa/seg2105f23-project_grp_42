package com.example.cyclingclub;

import static java.security.AccessController.getContext;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Administrator extends User{
    /*
    Methods to establish an association with Events
    */
    //The cardinality is many to many, both classes have a list of the other class
    private static List<Event> events;
    public static List<EventType> eventTypes;
    //private DatabaseReference eventTypesDB;

    //create a constructor with the array list of the events
    public Administrator(String username, String password) {
        super(null, username, "Administrator", password, null);

        //Initialize events;
        events = new ArrayList<Event>();
        eventTypes=new ArrayList<EventType>();

        //Read events and event types from database;
        events=getEvents();
        eventTypes=getEventTypes();

    }



    //Get the event index from the event array
    public Event getEvent(int index)
    {
        return events.get(index);
    }
    //Return the array with the events
    //public static List<Event> getEvents()
    //{
    //    return Collections.unmodifiableList(events);
    //}

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
        /*
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
        return wasAdded; */
        return true;
    }
    public static boolean removeEvent(Event event)
    //Checks if an event was removed from the array
    {
        /*
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
        */
        getEventDB().child(event.getKey()).removeValue();
        return  true;

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
        /*
        ArrayList<Event> copyOfEvents = new ArrayList<Event>(events);
        events.clear();
        for (Event aEvent : copyOfEvents) {
            aEvent.removeAdmin(this);
        }
        */

    }

    //Create a new event
    public static void createEvent(String name, String region, String type, String time, double duration){
        String key = getEventDB().push().getKey();
        Event newEvent=new Event(key, name, region, type, time , duration);
        getEventDB().child(key).setValue(newEvent);
        updateEventCounts();
    }

    public static void updateEvent(String key, String name, String region, String type, String time, double duration){
        Event newEvent=new Event(key, name, region, type, time , duration);
        getEventDB().child(key).setValue(newEvent);
        //events.add(newEvent);
    }



    public void manageContent(){

    }

    public void viewUsers(){

    }

    public void deleteUser(DatabaseReference db, String key){
        db.child(key).removeValue();
    }


    public static void createEventType(EventType et){
        String id = getEventTypeDB().push().getKey();
        et.setId(id);
        getEventTypeDB().child(id).setValue(et);
    }

    public static void updateEventType(EventType et){
        getEventTypeDB().child(et.getId()).setValue(et);
    }

    public static List<EventType> getEventTypes(){
        DatabaseReference   db=getEventTypeDB();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eventTypes.clear();
                for(DataSnapshot postSnapShot:dataSnapshot.getChildren()){
                    EventType et =  postSnapShot.getValue(EventType.class);
                    eventTypes.add(et);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return eventTypes;
    }


    public static List<Event> getEvents(){
        DatabaseReference   db=getEventDB();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                events.clear();
                for(DataSnapshot postSnapShot:dataSnapshot.getChildren()){
                    Event event =  postSnapShot.getValue(Event.class);
                    events.add(event);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        return events;
    }

    public static void updateEventCounts(){
        for(EventType et:getEventTypes()){
            for(Event e:getEvents()){
                if (e.getType().equals(et.getTypeName())){
                    et.setNumberOfEvent(et.getNumberOfEvent()+1);
                }
            }
            getEventTypeDB().child(et.getId()).setValue(et);
        }

    }

    public static void deleteEventType(EventType et){
        getEventTypeDB().child(et.getId()).removeValue();
    }

    public void viewEventType(EventType et){

    }

    public static DatabaseReference getEventDB(){
        return FirebaseDatabase.getInstance().getReference("Events1");
    };
    public static DatabaseReference getEventTypeDB(){
        return FirebaseDatabase.getInstance().getReference("EventTypes1");
    };
    public static DatabaseReference getUserDB(){
        return FirebaseDatabase.getInstance().getReference("users");
    };

    public static void deleteUser(String key){
        getUserDB().child(key).removeValue();
    }


}