package com.example.cyclingclub;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * The Administrator class extends the User class and represents an administrator in the system.
 * An Administrator has the ability to manage events and event types.
 */
public class Administrator extends User {
    // List of events managed by the administrator
    private static List<Event> events;
    // List of event types managed by the administrator
    public static List<EventType> eventTypes;

    /**
     * Constructor for the Administrator class.
     * Initializes the events and eventTypes lists and fetches their current state from the database.
     *
     * @param username The username of the administrator.
     * @param password The password of the administrator.
     */
    public Administrator(String username, String password) {
        super("admin@gcc.ca", username, "Administrator", password, "yxgR8rdVb9q6P4NPvexREQ==");

        // Initialize events and eventTypes lists
        events = new ArrayList<Event>();
        eventTypes = new ArrayList<EventType>();

        // Fetch current state of events and eventTypes from the database
        events = getEvents();
        eventTypes = getEventTypes();
    }

    /**
     * Creates a new event and adds it to the database.
     *
     * @param event The event to be created.
     */
    public static void createEvent(Event event) {
        String key = getEventDB().push().getKey();
        event.setKey(key);
        assert key != null;
        getEventDB().child(key).setValue(event);
    }

    /**
     * Updates an existing event in the database.
     *
     * @param event The event to be updated.
     */
    public static void updateEvent(Event event) {
        getEventDB().child(event.getKey()).setValue(event);
    }

    /**
     * Creates a new event type and adds it to the database.
     *
     * @param et The event type to be created.
     */
    public static void createEventType(EventType et) {
        String id = getEventTypeDB().push().getKey();
        et.setId(id);
        assert id != null;
        getEventTypeDB().child(id).setValue(et);
    }

    /**
     * Updates an existing event type in the database.
     *
     * @param et The event type to be updated.
     */
    public static void updateEventType(EventType et) {
        getEventTypeDB().child(et.getId()).setValue(et);
    }

    /**
     * Fetches the list of event types from the database.
     *
     * @return The list of event types.
     */
    public static List<EventType> getEventTypes() {
        DatabaseReference db = getEventTypeDB();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                eventTypes.clear();
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    EventType et = postSnapShot.getValue(EventType.class);
                    eventTypes.add(et);
                }
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
            }
        });

        return eventTypes;
    }

    /**
     * Fetches the list of events from the database.
     *
     * @return The list of events.
     */
    public static List<Event> getEvents() {
        DatabaseReference db = getEventDB();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                events.clear();
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    Event event = postSnapShot.getValue(Event.class);
                    events.add(event);
                }
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
            }
        });

        return events;
    }

    /**
     * Deletes an event type from the database.
     *
     * @param et The event type to be deleted.
     */
    public static Task<Void> deleteEventType(EventType et) {
        return getEventTypeDB().child(et.getId()).removeValue();
    }

    /**
     * Deletes a user from the database.
     *
     * @param key The key of the user to be deleted.
     */
    public static Task<Void> deleteUser(String key) {
        return getUserDB().child(key).removeValue();
    }

    /**
     * Removes an existing event from the database.
     *
     * @param event The event to be removed.
     */
    public static void removeEvent(Event event) {
        getEventDB().child(event.getKey()).removeValue();
    }

    /**
     * Returns a reference to the `events` node in the Firebase database.
     *
     * @return A DatabaseReference pointing to the `events` node.
     */
    public static DatabaseReference getEventDB() {
        return FirebaseDatabase.getInstance().getReference("events");
    }

    /**
     * Returns a reference to the `clubProfiles` node in the Firebase database.
     *
     * @return A DatabaseReference pointing to the `clubProfiles` node.
     */
    public static DatabaseReference getClubDB() {
        return FirebaseDatabase.getInstance().getReference("clubProfiles");
    }

    /**
     * Returns a reference to the `eventTypes` node in the Firebase database.
     *
     * @return A DatabaseReference pointing to the `eventTypes` node.
     */
    public static DatabaseReference getEventTypeDB() {
        return FirebaseDatabase.getInstance().getReference("eventTypes");
    }

    /**
     * Returns a reference to the users node in the Firebase database.
     *
     * @return A DatabaseReference pointing to the users node.
     */
    public static DatabaseReference getUserDB() {
        return FirebaseDatabase.getInstance().getReference("users");
    }
}