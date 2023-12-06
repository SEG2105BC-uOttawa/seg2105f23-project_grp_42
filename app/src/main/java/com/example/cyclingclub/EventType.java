package com.example.cyclingclub;

public class EventType {
    private String id;
    private String typeName;
    private String detail;
    private int numberOfEvent;

    // No-argument constructor
    public EventType() {}

    public EventType(String id, String typeName, String detail){
        this.id = id;
        this.typeName = typeName;
        this.detail = detail;
        this.numberOfEvent = 0;
    }

    public String getId() {
        return id;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getDetail() {
        return detail;
    }

    public int getNumberOfEvent() {
        return numberOfEvent;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNumberOfEvent(int numberOfEvent) {
        this.numberOfEvent = numberOfEvent;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}