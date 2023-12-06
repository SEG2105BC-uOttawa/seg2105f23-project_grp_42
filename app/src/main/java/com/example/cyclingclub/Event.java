package com.example.cyclingclub;

/**
 * The Event class represents an event in the system.
 * An Event has an id, type, detail, region, date, route, level, fee, limit, distance, and elevation.
 */
public class Event {

    private String key;   //The handle of this event in the database
    private String eventId;
    private String eventType;
    private String eventDetail;
    private String eventRegion;
    private String eventDate;
    private String eventRoute;
    private int eventLevel;
    private double eventFee;
    private int eventLimit;
    private int eventDistance;
    private int eventElevation;

    private String username;

    /**
     * No-argument constructor for Firebase.
     */
    public Event() {}

    /**
     * Full constructor for the Event class.
     *
     * @param key The handle of this event in the database.
     * @param eventId The id of the event.
     * @param eventType The type of the event.
     * @param eventDetail The detail of the event.
     * @param eventRegion The region of the event.
     * @param eventDate The date of the event.
     * @param eventRoute The route of the event.
     * @param eventLevel The level of the event.
     * @param eventFee The fee of the event.
     * @param eventLimit The limit of the event.
     * @param eventDistance The distance of the event.
     * @param eventElevation The elevation of the event.
     */
    public Event(String key, String eventId, String eventType, String eventDetail, String eventRegion, String eventDate, String eventRoute, int eventLevel, double eventFee, int eventLimit, int eventDistance, int eventElevation) {
        this.key = key;
        this.eventId = eventId;
        this.eventType = eventType;
        this.eventDetail = eventDetail;
        this.eventRegion = eventRegion;
        this.eventDate = eventDate;
        this.eventRoute = eventRoute;
        this.eventLevel = eventLevel;
        this.eventFee = eventFee;
        this.eventLimit = eventLimit;
        this.eventDistance = eventDistance;
        this.eventElevation = eventElevation;
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
        return eventId;
    }

    /**
     * Returns the type of the event.
     *
     * @return The type of the event.
     */
    public String getType() {
        return eventType;
    }

    /**
     * Returns the detail of the event.
     *
     * @return The detail of the event.
     */
    public String getDetail() {
        return eventDetail;
    }

    /**
     * Returns the region of the event.
     *
     * @return The region of the event.
     */
    public String getRegion() {
        return eventRegion;
    }

    /**
     * Returns the route of the event.
     *
     * @return The route of the event.
     */
    public String getRoute() {
        return eventRoute;
    }

    /**
     * Returns the date of the event.
     *
     * @return The date of the event.
     */
    public String getDate() {
        return eventDate;
    }

    /**
     * Returns the level of the event.
     *
     * @return The level of the event.
     */
    public int getLevel() {
        return eventLevel;
    }

    /**
     * Returns the fee of the event.
     *
     * @return The fee of the event.
     */
    public double getFee() {
        return eventFee;
    }

    /**
     * Returns the limit of the event.
     *
     * @return The limit of the event.
     */
    public int getLimit() {
        return eventLimit;
    }

    /**
     * Returns the distance of the event.
     *
     * @return The distance of the event.
     */
    public int getDistance() {
        return eventDistance;
    }

    /**
     * Returns the elevation of the event.
     *
     * @return The elevation of the event.
     */
    public int getElevation() {
        return eventElevation;
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
        this.eventId = id;
    }

    /**
     * Sets the type of the event.
     *
     * @param type The type of the event.
     */
    public void setType(String type) {
        this.eventType = type;
    }

    /**
     * Sets the detail of the event.
     *
     * @param detail The detail of the event.
     */
    public void setDetail(String detail) {
        this.eventDetail = detail;
    }

    /**
     * Sets the region of the event.
     *
     * @param region The region of the event.
     */
    public void setRegion(String region) {
        this.eventRegion = region;
    }

    /**
     * Sets the route of the event.
     *
     * @param route The route of the event.
     */
    public void setRoute(String route) {
        this.eventRoute = route;
    }

    /**
     * Sets the date of the event.
     *
     * @param date The date of the event.
     */
    public void setDate(String date) {
        this.eventDate = date;
    }

    /**
     * Sets the level of the event.
     *
     * @param level The level of the event.
     */
    public void setLevel(int level) {
        this.eventLevel = level;
    }

    /**
     * Sets the fee of the event.
     *
     * @param fee The fee of the event.
     */
    public void setFee(double fee) {
        this.eventFee = fee;
    }

    /**
     * Sets the limit of the event.
     *
     * @param limit The limit of the event.
     */
    public void setLimit(int limit) {
        this.eventLimit = limit;
    }

    /**
     * Sets the distance of the event.
     *
     * @param distance The distance of the event.
     */
    public void setDistance(int distance) {
        this.eventDistance = distance;
    }

    /**
     * Sets the elevation of the event.
     *
     * @param elevation The elevation of the event.
     */
    public void setElevation(int elevation) {
        this.eventElevation = elevation;
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