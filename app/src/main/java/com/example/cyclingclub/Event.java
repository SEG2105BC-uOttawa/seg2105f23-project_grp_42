package com.example.cyclingclub;

import java.util.*;

public class Event {

    private String key;   //The handle of this event in the database
    private  String eventId;
    private  String eventType;
    private String eventDetail;
    private String eventRegion;
    private  String eventDate;
    private String eventRoute;
    private  int eventLevel;
    private  double eventFee;
    private  int eventLimit;
    private  int eventDistance;
    private  int eventElevation;

    private String username;


    //private final List<Administrator> admins;
    //private  List<Administrator> admins;

    public Event() {}


    public Event(String key, String id, String type, String date){
        this.key=key;
        this.eventId = id;
        this.eventType = type;
        this.eventDate=date;
    }


    public  String getKey() {
        return key;
    }
    public  String getId() {
        return eventId;
    }
    public  String getType() {
        return eventType;
    }
    public  String getDetail() {
        return eventDetail;
    }
    public  String getRegion() {
        return eventRegion;
    }
    public  String getRoute() {
        return eventRoute;
    }
    public  String getDate() {
        return eventDate;
    }
    public  int getLevel() {
        return eventLevel;
    }
    public  double getFee() {
        return eventFee;
    }
    public  int getLimit() {
        return eventLimit;
    }
    public  int getDistance() {
        return eventDistance;
    }
    public  int getElevation() {
        return eventElevation;
    }



    public  void setKey(String key) {
        this.key =  key;
    }
    public  void setId(String id) {
        this.eventId = id ;
    }
    public  void setType(String type) {
        this.eventType = type  ;
    }
    public  void setDetail(String detail) {
        this.eventDetail = detail ;
    }
    public  void setRegion(String region) {
        this.eventRegion = region ;
    }
    public  void setRoute(String route) {
        this.eventRoute = route ;
    }
    public  void setDate(String date) {
        this.eventDate =date  ;
    }
    public  void setLevel(int level) {
        this.eventLevel =  level;
    }
    public  void setFee(double fee) {
        this.eventFee =fee  ;
    }
    public  void setLimit(int limit) {
        this.eventLimit = limit ;
    }
    public  void setDistance(int distance) {
        this.eventDistance = distance ;
    }
    public  void setElevation(int elevation) {
        this.eventElevation =elevation  ;
    }



    public String getUsername() {     return username;}

    public void setUsername(String username) {this.username = username;}

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
