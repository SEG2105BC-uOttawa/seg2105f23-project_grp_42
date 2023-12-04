package com.example.cyclingclub;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Registration {
    private String key;
    private Event event;
    private User participant;
    private String date;
    private boolean Accepted;
    private boolean Awarded;


    // Constructor (if needed)
    private Registration(){}
    public Registration(Event event, User participant){
        this.event=event;
        this.participant=participant;
        //this.date="2023-12-01";
        this.Accepted=false;
        this.Awarded=false;

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        this.date = dateFormat.format(currentDate);
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
    public boolean getAccepted() {
        return Accepted;
    }

    public void setAccepted(boolean Accepted) {
        this.Accepted = Accepted;
    }

    // Getter and Setter methods for 'award'
    public boolean getAwarded() {
        return Awarded;
    }

    public void setAwarded(boolean Awarded) {
        this.Awarded = Awarded;
    }

}

