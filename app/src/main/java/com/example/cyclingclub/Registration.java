package com.example.cyclingclub;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * The Registration class represents a registration in the system.
 * A Registration has an event, a participant, a date, and flags for acceptance and award.
 */
public class Registration {
    private String key;
    private Event event;
    private User participant;
    private String date;
    private boolean accepted;
    private boolean awarded;

    /**
     * No-argument constructor for Firebase.
     */
    public Registration() {}

    /**
     * Full constructor for the Registration class.
     *
     * @param event The event of the registration.
     * @param participant The participant of the registration.
     */
    public Registration(Event event, User participant) {
        this.event = event;
        this.participant = participant;
        this.accepted = false;
        this.awarded = false;

        // Set the date to the current date
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        this.date = dateFormat.format(currentDate);
    }

    /**
     * Returns the key of the registration in the database.
     *
     * @return The key of the registration in the database.
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the key of the registration in the database.
     *
     * @param key The key of the registration in the database.
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Returns the event of the registration.
     *
     * @return The event of the registration.
     */
    public Event getEvent() {
        return event;
    }

    /**
     * Sets the event of the registration.
     *
     * @param event The event of the registration.
     */
    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     * Returns the participant of the registration.
     *
     * @return The participant of the registration.
     */
    public User getParticipant() {
        return participant;
    }

    /**
     * Sets the participant of the registration.
     *
     * @param participant The participant of the registration.
     */
    public void setParticipant(User participant) {
        this.participant = participant;
    }

    /**
     * Returns the date of the registration.
     *
     * @return The date of the registration.
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date of the registration.
     *
     * @param date The date of the registration.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Returns whether the registration is accepted.
     *
     * @return True if the registration is accepted, false otherwise.
     */
    public boolean isAccepted() {
        return accepted;
    }

    /**
     * Sets whether the registration is accepted.
     *
     * @param accepted True if the registration is accepted, false otherwise.
     */
    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    /**
     * Returns whether the registration is awarded.
     *
     * @return True if the registration is awarded, false otherwise.
     */
    public boolean isAwarded() {
        return awarded;
    }

    /**
     * Sets whether the registration is awarded.
     *
     * @param awarded True if the registration is awarded, false otherwise.
     */
    public void setAwarded(boolean awarded) {
        this.awarded = awarded;
    }
}