package com.example.cyclingclub;

import java.util.ArrayList;
import java.util.List;

public class Participant extends User {
    Participant newUser;
    public Participant(){
        this.username = super.username;
        this.password = super.password;
        this.role = "Participant";
    }
    public void createAccount(String username, String password){
        if(this.username.equals(username) && this.password.equals(password)){
            newUser = new Participant();
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
