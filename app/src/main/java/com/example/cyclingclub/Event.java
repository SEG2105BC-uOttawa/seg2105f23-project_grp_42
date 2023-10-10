package com.example.cyclingclub;

import java.util.*;

public class Event {

    public static String name;
    public static String type;
    public static String date;
    public static String location;
    public static String rewards;

    private List<Administrator> admins;

    public Event()
    {
        admins = new ArrayList<Administrator>();
    }
    public Administrator getAdmin(int index)
    {
        Administrator aAdmin = admins.get(index);
        return aAdmin;
    }
    public List<Administrator> getAdmins()
    {
        List<Administrator> newAdmins = Collections.unmodifiableList(admins);
        return newAdmins;
    }
    public int numberOfAdmins()
    {
        int number = admins.size();
        return number;
    }
    public boolean hasAdmins()
    {
        boolean has = admins.size() > 0;
        return has;
    }

    public int indexOfAdmin(Administrator aAdmin)
    {
        int index = admins.indexOf(aAdmin);
        return index;
    }
    public static int minimumNumberOfAdmins()
    {
        return 0;
    }
    public boolean addAdmin(Administrator aAdmin)
    {
        boolean wasAdded = false;
        if (admins.contains(aAdmin)) { return false; }
        admins.add(aAdmin);
        if (aAdmin.indexOfEvent(this) != -1)
        {
            wasAdded = true;
        } else
        {
            wasAdded = aAdmin.addEvent(this);
            if (!wasAdded)
            {
                admins.remove(aAdmin);
            }
        }
        return wasAdded;
    }
    public boolean removeAdmin(Administrator aAdmin)
    {
        boolean wasRemoved = false;
        if (!admins.contains(aAdmin))
        {
            return wasRemoved;
        }
        int oldIndex = admins.indexOf(aAdmin);
        admins.remove(oldIndex);
        if (aAdmin.indexOfEvent(this) == -1)
        {
            wasRemoved = true;
        } else
        {
            wasRemoved = aAdmin.removeEvent(this);
            if (!wasRemoved)
            {
                admins.add(oldIndex,aAdmin);
            }
        }
        return wasRemoved;
    }
    public boolean addAdminAt(Administrator aAdmin, int index)
    {
        boolean wasAdded = false;
        if(addAdmin(aAdmin))
        {
            if(index < 0 ) { index = 0; }
            if(index > numberOfAdmins()) { index = numberOfAdmins() - 1; }
            admins.remove(aAdmin);
            admins.add(index, aAdmin);
            wasAdded = true;
        }
        return wasAdded;
    }
    public boolean addOrMoveAdminAt(Administrator aAdmin, int index)
    {
        boolean wasAdded = false;
        if(admins.contains(aAdmin))
        {
            if(index < 0 ) { index = 0; }
            if(index > numberOfAdmins()) { index = numberOfAdmins() - 1; }
            admins.remove(aAdmin);
            admins.add(index, aAdmin);
            wasAdded = true;
        }else
        {
            wasAdded = addAdminAt(aAdmin, index);
        }
        return wasAdded;
    }

    public void delete()
    {
        ArrayList<Administrator> copyOfAdmins = new ArrayList<Administrator>(admins);
        admins.clear();
        for(Administrator aAdmin : copyOfAdmins)
        {
            aAdmin.removeEvent(this);
        }
    }
}
