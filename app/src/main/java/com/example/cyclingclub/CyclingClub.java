package com.example.cyclingclub;

public class CyclingClub{
    String name;
    String region;
    String levelOfDifficulty;
    int numberofParticipants;
    float fees;
    private User user;


    public Event createEvent(){

    }
    public void createRoute(){

    }
    public String recordResults(){

        return "";
    }

    public void distributeAwards(){

    }
    public void acceptRegistration(){

    }

    public void updates(){

    }
    public CyclingClub(User aUser)
    {
        if (aUser == null || aUser.getCyclingClub() != null)
        {
            throw new RuntimeException("Unable to create CyclingClub due to aUser. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
        user = aUser;
    }
    public CyclingClub()
    {
        user = new User(this);
    }
    public User getUser()
    {
        return user;
    }
    public void delete()
    {
        User existingUser = user;
        user = null;
        if (existingUser != null)
        {
            existingUser.delete();
        }
    }
}
