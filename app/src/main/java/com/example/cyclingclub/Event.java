package com.example.cyclingclub;

/**
 * The Event class represents an event in the system.
 * An Event has an id, type, detail, region, date, route, level, fee, limit, distance, and elevation.
 */
public class Event {

    private String key;   //The handle of this event in the database
    private String id;
    private String type;
    private String detail;
    private String region;
    private String date;
    private int level;
    private double fee;
    private int limit;
    private String distance;
    private String elevation;

    private String username;

    /**
     * No-argument constructor for Firebase.
     */
    public Event() {}

    /**
     * Full constructor for the Event class.
     *
     * @param key The handle of this event in the database.
     * @param id The id of the event.
     * @param type The type of the event.
     * @param detail The detail of the event.
     * @param region The region of the event.
     * @param date The date of the event.
     * @param level The level of the event.
     * @param fee The fee of the event.
     * @param limit The limit of the event.
     * @param distance The distance of the event.
     * @param elevation The elevation of the event.
     */
    public Event(String key, String id, String type, String detail, String region, String date, int level, double fee, int limit, String distance, String elevation) {
        this.key = key != null ? key : "";
        this.id = id != null ? id : "";
        this.type = type != null ? type : "";
        this.detail = detail != null ? detail : "";
        this.region = region != null ? region : "";
        this.date = date != null ? date : "";
        this.level = level;
        this.fee = fee;
        this.limit = limit;
        this.distance = distance != null ? distance : "";
        this.elevation = elevation != null ? elevation : "";
    }

    /**
     * Returns the handle of this event in the database.
     *
     * @return The handle of this event in the database.
     */
    public String getKey() {
        return key;
    }

    /**
     * Returns the Cycling Club account's username
     *
     * @return The username that created this event
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the id of the event.
     *
     * @return The id of the event.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the type of the event.
     *
     * @return The type of the event.
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the detail of the event.
     *
     * @return The detail of the event.
     */
    public String getDetail() {
        return detail;
    }

    /**
     * Returns the region of the event.
     *
     * @return The region of the event.
     */
    public String getRegion() {
        return region;
    }

    /**
     * Returns the date of the event.
     *
     * @return The date of the event.
     */
    public String getDate() {
        return date;
    }

    /**
     * Returns the level of the event.
     *
     * @return The level of the event.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Returns the fee of the event.
     *
     * @return The fee of the event.
     */
    public double getFee() {
        return fee;
    }

    /**
     * Returns the limit of the event.
     *
     * @return The limit of the event.
     */
    public int getLimit() {
        return limit;
    }

    /**
     * Returns the distance of the event.
     *
     * @return The distance of the event.
     */
    public String getDistance() {
        return distance;
    }

    /**
     * Returns the elevation of the event.
     *
     * @return The elevation of the event.
     */
    public String getElevation() {
        return elevation;
    }

    /**
     * Sets the handle of this event in the database.
     *
     * @param key The handle of this event in the database.
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Sets the id of the event.
     *
     * @param id The id of the event.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Sets the username associated with the event.
     *
     * @param username The updated username.
     */
    public void setUsername(String username) {
        this.username = username;
    }
}