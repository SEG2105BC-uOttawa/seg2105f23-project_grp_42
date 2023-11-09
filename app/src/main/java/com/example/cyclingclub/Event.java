package com.example.cyclingclub;

import java.util.*;

public class Event {

    private static String name;
    private static String type;
    private static String date;
    private static String location;
    private static String rewards;

    public static Double duration;

    private final List<Administrator> admins;

    public Event()
    {
        admins = new ArrayList<Administrator>();
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Event.name = name;
    }

    public static String getType() {
        return type;
    }

    public static void setType(String type) {
        Event.type = type;
    }

    public static String getDate() {
        return date;
    }

    public static void setDate(String date) {
        Event.date = date;
    }

    public static String getLocation() {
        return location;
    }

    public static void setLocation(String location) {
        Event.location = location;
    }

    public static String getRewards() {
        return rewards;
    }

    public static void setRewards(String rewards) {
        Event.rewards = rewards;
    }

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

    public double getDuration(){
        return this.duration;
    }

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
}
