package com.example.cyclingclub;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventType {
    private String id;
    private String typeName;
    private String detail;
    private int numberOfEvent;

    private EventType(){}

    public EventType(String id, String typeName, String detail){
        this.id=id;
        this.typeName=typeName;
        this.detail=detail;
        this.numberOfEvent =0;
    }

    public String getId(){
        return id;
    }
    public String getTypeName(){
        return typeName;
    }
    public String getDetail() {return detail;}
    public void setDetail(String detail){this.detail=detail;}
    public int getNumberOfEvent(){
        return numberOfEvent;
    }

    public void setId(String id){
        this.id=id;
    }
    public void setNumberOfEvent(int num){
        //Need to count the number of events from event database, which is not available yet;
        numberOfEvent=num;
    }

}
