package com.example.cyclingclub;


import java.util.Collections;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;



//EventOrganizer is merged with CyclingClub
public class  EventOrganizer extends User {
    private static List<Event> events;
    public String name;
    private Event event;
    private EditText eventName;
    private EditText eventRegion;
    private EditText eventType;
    private EditText eventTime;
    private EditText eventDuration;
    private ListView listViewEvents;
    private List<String> eventNames;

    //private List<String> eventItems;

    private DatabaseReference databaseEvents;
    public String region;
    public int levelOfDifficulty;
    public int numOfParticipants;
    public double fees;

    public EventOrganizer(String email, String username, String role, String password, String salt) {
        super(email, username, role, password, salt);
    }

    public void createProfile(){
        //Event.name = name;
    }

    public double distributeAwards(){

        return 0;
    }

    public void acceptRegistrations(){

    }

    public void updates(){

    }

}
