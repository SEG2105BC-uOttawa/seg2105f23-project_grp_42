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

/*    BG, no usage and error message.

    public List<Event> selectEvents(){
        events = Administrator.getEvents();
        List<Event> newEvents = Collections.unmodifiableList(events);
        return newEvents;
    }
    private void showUpdateDeleteDialog(Event event) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.event_update, null);
        dialogBuilder.setView(dialogView);

        EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        EditText editTextLocation = (EditText) dialogView.findViewById(R.id.editTextLocation);
        EditText editTextType = (EditText) dialogView.findViewById(R.id.editTextType);
        EditText editTextTime = (EditText) dialogView.findViewById(R.id.editTextTime);
        EditText editTextDuration = (EditText) dialogView.findViewById(R.id.editTextDuration);

        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.btnEventUpdate);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.btnEventDelete);

        dialogBuilder.setTitle("Selected Event Detail");
        editTextName.setText(event.getName());
        editTextLocation.setText(event.getLocation());
        editTextType.setText(event.getType());
        editTextTime.setText(event.getDate());
        editTextDuration.setText(Double.toString(event.getDuration()));


        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  key=event.getKey();
                String name = editTextName.getText().toString().trim();
                String location = editTextLocation.getText().toString().trim();
                String type = editTextType.getText().toString().trim();
                String time = editTextTime.getText().toString().trim();
                double duration = Double.parseDouble(editTextDuration.getText().toString().trim());


                Event newEvent=new Event(key, name, location, type, time , duration);
                databaseEvents.child(key).setValue(newEvent);
                b.dismiss();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                databaseEvents.child(event.getKey()).removeValue();
                b.dismiss();
            }
        });
    }
*/

    public double distributeAwards(){

        return 0;
    }

    public void acceptRegistrations(){

    }

    public void updates(){

    }

}