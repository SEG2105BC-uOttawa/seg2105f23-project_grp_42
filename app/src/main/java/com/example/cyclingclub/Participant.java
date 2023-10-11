package com.example.cyclingclub;

import java.util.ArrayList;
import java.util.List;

public class Participant extends User {
    Participant newUser;
    public Participant(String username, String password, String role) {
        super(username, password, "Participant");
    }

    public void createAccount(String username, String password){
        if(this.getUsername().equals(username) && this.getPassword().equals(password)){
            newUser = new Participant(username, password, "Participant");
        }
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
