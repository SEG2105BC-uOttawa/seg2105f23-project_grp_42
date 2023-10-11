package com.example.cyclingclub;

import java.util.ArrayList;
import java.util.List;

public class Participant extends User {

    public void createAccount(){

    }

    public void registerForEvent(){

    }

    public String searchByLocation(){
        return Event.getLocation();
    }

    public String searchByType(){
        return Event.getType();
    }
    public String searchByName(){
        return Event.getName();
    }
    public String searchByDate(){ return Event.getDate(); }

    public void viewEvents(){

    }
}
