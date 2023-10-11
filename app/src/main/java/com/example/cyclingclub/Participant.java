package com.example.cyclingclub;

public class Participant extends User {
    Participant newUser;
    public Participant(String email, String username, String password, String salt) {
        super(email, username, password, "Participant", salt);
    }

    public void createAccount(String email, String username, String password, String salt){
        if(this.getUsername().equals(username) && this.getPassword().equals(password)){
            newUser = new Participant(email, username, password, salt);
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
