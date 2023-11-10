package com.example.cyclingclub;

import java.util.*;

public class Event {

    private String key;

    private  String name;
    private  String type;
    private  String date;
    private  String location;
    private  String rewards;

    private  Double duration;

    //private final List<Administrator> admins;
    //private  List<Administrator> admins;

    public Event() {}


    public Event(String key, String name, String region, String type, String time , double duration){
        this.key=key;
        this.name=name;
        this.type=type;
        this.location=region;
        this.date=time;
        this.duration=duration;
        //admins = new ArrayList<Administrator>();
    }

    public  String getKey() {
        return key;
    }

    public  void setKey(String key) {
        this.key = key;
    }

    public  String getName() {
        return name;
    }

    public  void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public  void setType(String type) {
        this.type = type;
    }

    public  String getDate() {
        return date;
    }

    public  void setDate(String date) {
        this.date = date;
    }

    public  String getLocation() {
        return location;
    }

    public  void setLocation(String location) {
        this.location = location;
    }

    public  String getRewards() {
        return rewards;
    }

    public  void setRewards(String rewards) {
        this.rewards = rewards;
    }

    public  double getDuration(){
        return duration;
    }
    public  void setDuration(double duration) {
        this.duration = duration;
    }


/*
    public Administrator getAdmin(int index)
    {
        return admins.get(index);
    }
    public List<Administrator> getAdmins()
    {
        return Collections.unmodifiableList(admins);
    }

    public int numberOfAdmins()
    {
        return admins.size();
    }

    public boolean hasAdmins()
    {
        return !admins.isEmpty();
    }

    public int indexOfAdmin(Administrator admin)
    {
        return admins.indexOf(admin);
    }

    public static int minimumNumberOfAdmins()
    {
        return 0;
    }

    public boolean addAdmin(Administrator admin)
    {
        boolean wasAdded = false;
        if (admins.contains(admin)) { return false; }
        admins.add(admin);
        if (admin.indexOfEvent(this) != -1)
        {
            wasAdded = true;
        } else
        {
            wasAdded = admin.addEvent(this);
            if (!wasAdded)
            {
                admins.remove(admin);
            }
        }
        return wasAdded;
    }

    public boolean removeAdmin(Administrator admin)
    {
        boolean wasRemoved = false;
        if (!admins.contains(admin))
        {
            return wasRemoved;
        }
        int oldIndex = admins.indexOf(admin);
        admins.remove(oldIndex);
        if (admin.indexOfEvent(this) == -1)
        {
            wasRemoved = true;
        } else
        {
            wasRemoved = admin.removeEvent(this);
            if (!wasRemoved)
            {
                admins.add(oldIndex,admin);
            }
        }
        return wasRemoved;
    }

    public boolean addAdminAt(Administrator admin, int index)
    {
        boolean wasAdded = false;
        if(addAdmin(admin))
        {
            if(index < 0 ) { index = 0; }
            if(index > numberOfAdmins()) { index = numberOfAdmins() - 1; }
            admins.remove(admin);
            admins.add(index, admin);
            wasAdded = true;
        }
        return wasAdded;
    }

<<<<<<< Updated upstream

=======
    public static double getDuration(){
        return this.duration;
    }
>>>>>>> Stashed changes

    public boolean addOrMoveAdminAt(Administrator admin, int index)
    {
        boolean wasAdded = false;
        if(admins.contains(admin))
        {
            if(index < 0 ) { index = 0; }
            if(index > numberOfAdmins()) { index = numberOfAdmins() - 1; }
            admins.remove(admin);
            admins.add(index, admin);
            wasAdded = true;
        }else
        {
            wasAdded = addAdminAt(admin, index);
        }
        return wasAdded;
    }

    public void delete()
    {
        ArrayList<Administrator> copyOfAdmins = new ArrayList<>(admins);
        admins.clear();
        for(Administrator admin : copyOfAdmins)
        {
            admin.removeEvent(this);
        }
    }

*/


}
