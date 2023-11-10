package com.example.cyclingclub;

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

public class EventManagement extends AppCompatActivity {

    private EditText eventName;
    private EditText eventRegion;
    private EditText eventType;
    private EditText eventTime;
    private EditText eventDuration;
    private ListView listViewEvents;
    private List<Event> events;
    private List<String> eventNames;

    //private List<String> eventItems;




    private DatabaseReference databaseEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_management);


        eventName=(EditText) findViewById(R.id.editEventName);
        eventRegion=(EditText) findViewById(R.id.editEventRegion);
        eventType=(EditText) findViewById(R.id.editEventType);
        eventTime=(EditText) findViewById(R.id.editEventTime);
        eventDuration=(EditText) findViewById(R.id.editEventDuration);

        listViewEvents = (ListView) findViewById(R.id.listEvents);

        databaseEvents = FirebaseDatabase.getInstance().getReference("Events1");
        events = new ArrayList<Event>();
        eventNames=new ArrayList<String>();


        AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Event event = events.get(i);
                showUpdateDeleteDialog(event);
                return true;
            }
        };
        listViewEvents.setOnItemLongClickListener(longClickListener);



    }



    @Override
    protected void onStart() {
        super.onStart();
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                events.clear();
                eventNames.clear();
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    Event event = postSnapShot.getValue(Event.class);
                    events.add(event);
                    eventNames.add(event.getName());
                }
                ArrayAdapter<String> itemsAdapter =
                        new ArrayAdapter<String>(EventManagement.this, R.layout.item_view, R.id.textViewItem,eventNames);

                listViewEvents.setAdapter(itemsAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };

        databaseEvents.addValueEventListener(postListener);
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



    public void onClickAddEvent(View view) {



        String name = eventName.getText().toString().trim();
        String region = eventRegion.getText().toString().trim();
        String type = eventType.getText().toString().trim();
        String time = eventTime.getText().toString().trim();
        double duration = Double.parseDouble((eventDuration.getText().toString().trim()));


        String key = databaseEvents.push().getKey();

        Event event=new Event(key, name, region, type, time , duration);

        databaseEvents.child(key).setValue(event);

       // eventNames.add("CCC");



    }



}