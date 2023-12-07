package com.example.cyclingclub;

import java.util.*;

public class Event {

    private String key;   //The handle of this event in the database
    private  String eventId;
    private  String eventType;
    private String eventDetail;
    private String eventRegion;
    private  String eventDate;
    private String eventRoute;
    private  int eventLevel;
    private  double eventFee;
    private  int eventLimit;
    private  int eventDistance;
    private  int eventElevation;
    private String username;

    public Event() {}

    public Event(String key, String id, String type, String date){
        this.key=key;
        this.eventId = id;
        this.eventType = type;
        this.eventDate=date;
    }

    public  String getKey() {
        return key;
    }
    public  String getId() {
        return eventId;
    }
    public  String getType() {
        return eventType;
    }
    public  String getDetail() {
        return eventDetail;
    }
    public  String getRegion() {
        return eventRegion;
    }
    public  String getRoute() {
        return eventRoute;
    }
    public  String getDate() {
        return eventDate;
    }
    public  int getLevel() {
        return eventLevel;
    }
    public  double getFee() {
        return eventFee;
    }
    public  int getLimit() {
        return eventLimit;
    }
    public  int getDistance() {
        return eventDistance;
    }
    public  int getElevation() {
        return eventElevation;
    }
    public  void setKey(String key) {
        this.key =  key;
    }
    public  void setId(String id) {
        this.eventId = id ;
    }
    public  void setType(String type) {
        this.eventType = type  ;
    }
    public  void setDetail(String detail) {
        this.eventDetail = detail ;
    }
    public  void setRegion(String region) {
        this.eventRegion = region ;
    }
    public  void setRoute(String route) {
        this.eventRoute = route ;
    }
    public  void setDate(String date) {
        this.eventDate =date  ;
    }
    public  void setLevel(int level) {
        this.eventLevel =  level;
    }
    public  void setFee(double fee) {
        this.eventFee =fee  ;
    }
    public  void setLimit(int limit) {
        this.eventLimit = limit ;
    }
    public  void setDistance(int distance) {
        this.eventDistance = distance ;
    }
    public  void setElevation(int elevation) {
        this.eventElevation =elevation  ;
    }
    public String getUsername() {     return username;}
    public void setUsername(String username) {this.username = username;}


}
