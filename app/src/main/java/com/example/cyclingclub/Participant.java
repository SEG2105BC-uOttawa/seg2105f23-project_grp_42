package com.example.cyclingclub;

import java.util.LinkedList;

public class Participant extends User {

    private Register registration;
    public Participant(String email, String username, String password, String salt) {
        super(email, username, "Participant", password, salt);
        registration = new Register(this);
    }

    public Participant(String username, Register register){
        username = this.getUsername();
        if(register == null || register.getParticipant() == null){

        }
    }

    public Register getRegistration(){
        return  registration;
    }

    /*
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


     */
}
