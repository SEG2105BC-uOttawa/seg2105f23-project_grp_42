package com.example.cyclingclub;

import java.util.*;

public class Event {

    public static String name;
    public static String type;
    public static String date;
    public static String location;
    public static String rewards;

    private List<User> users;

    public Event()
    {
        users = new ArrayList<User>();
    }
    public User getUser(int index)
    {
        User aUser = users.get(index);
        return aUser;
    }
    public List<User> getUsers()
    {
        List<User> newUsers = Collections.unmodifiableList(users);
        return newUsers;
    }
    public int numberOfUsers()

    {
        int number = users.size();
        return number;
    }
    public boolean hasUsers()
    {
        boolean has = users.size() > 0;
        return has;
    }

    public int indexOfUser(User aUser)
    {
        int index = users.indexOf(aUser);
        return index;
    }
    public static int minimumNumberOfUsers()
    {
        return 0;
    }
    public boolean addUser(User aUser)
    {
        boolean wasAdded = false;
        if (users.contains(aUser)) { return false; }
        users.add(aUser);
        if (aUser.indexOfEvent(this) != -1)
        {
            wasAdded = true;
        } else
        {
            wasAdded = aUser.addEvent(this);
            if (!wasAdded)
            {
                users.remove(aUser);
            }
        }
        return wasAdded;
    }
    public boolean removeUser(User aUser)
    {
        boolean wasRemoved = false;
        if (!users.contains(aUser))
        {
            return wasRemoved;
        }
        int oldIndex = users.indexOf(aUser);
        users.remove(oldIndex);
        if (aUser.indexOfEvent(this) == -1)
        {
            wasRemoved = true;
        } else
        {
            wasRemoved = aUser.removeEvent(this);
            if (!wasRemoved)
            {
                users.add(oldIndex,aUser);
            }
        }
        return wasRemoved;
    }
    public boolean addUserAt(User aUser, int index)
    {
        boolean wasAdded = false;
        if(addUser(aUser))
        {
            if(index < 0 ) { index = 0; }
            if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
            users.remove(aUser);
            users.add(index, aUser);
            wasAdded = true;
        }
        return wasAdded;
    }
    public boolean addOrMoveUserAt(User aUser, int index)
    {
        boolean wasAdded = false;
        if(users.contains(aUser))
        {
            if(index < 0 ) { index = 0; }
            if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
            users.remove(aUser);
            users.add(index, aUser);
            wasAdded = true;
        }else
        {
            wasAdded = addUserAt(aUser, index);
        }
        return wasAdded;
    }

    public void delete()
    {
        ArrayList<User> copyOfUsers = new ArrayList<User>(users);
        users.clear();
        for(User aUser : copyOfUsers)
        {
            aUser.removeEvent(this);
        }
    }
}
