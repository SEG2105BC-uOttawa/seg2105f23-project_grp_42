package com.example.cyclingclub;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventType {
    private String id;
    private String typeName;
    private int numberOfEvent;

    private EventType(){}

    public EventType(String id, String typeName, int num){
        this.id=id;
        this.typeName=typeName;
        this.numberOfEvent=num;
    }
    public String getId(){
        return id;
    }
    public String getTypeName(){
        return typeName;
    }
    public int getNumberOfEvent(){
        return numberOfEvent;
    }

    public void setId(String id){
        this.id=id;
    }
    public void updateNumberOfEvent(int num){
        //Need to count the number of events from event database, which is not available yet;
        numberOfEvent=num;
    }

}
