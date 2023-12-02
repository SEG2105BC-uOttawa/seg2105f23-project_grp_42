package com.example.cyclingclub;

public class Registration {
    private String key;
    private Event event;
    private User participant;
    private String date;
    private boolean isAccepted;
    private boolean isAwarded;


    // Constructor (if needed)
    private Registration(){}
    public Registration(Event event, User participant){
        this.event=event;
        this.participant=participant;
        this.date="2023-12-01";
        this.isAccepted=false;
        this.isAwarded=false;
    }


    // Getter and Setter methods for 'key'
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    // Getter and Setter methods for 'event'
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    // Getter and Setter methods for 'participant'
    public User getParticipant() {
        return participant;
    }

    public void setParticipant(User participant) {
        this.participant = participant;
    }

    // Getter and Setter methods for 'date'
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // Getter and Setter methods for 'accepted'
    public boolean isAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(boolean isAccepted) {
        this.isAccepted = isAccepted;
    }

    // Getter and Setter methods for 'award'
    public boolean isAwarded() {
        return isAwarded;
    }

    public void setIsAward(boolean isAwarded) {
        this.isAwarded = isAwarded;
    }

}

