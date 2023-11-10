package com.example.cyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

//import java.util.Collections;

public class EventManagement extends AppCompatActivity {

    private EditText eventName;
    private EditText eventRegion;

    private EditText eventTime;
    private EditText eventDuration;


    private Spinner dropdownType ;

    private ListView listViewEvents;
    private List<Event> events;
    private ArrayList<String> eventNames;

    //private List<String> eventItems;

    private List<String>  eventTypeNames;


   // private DatabaseReference databaseEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_management);


        eventName=(EditText) findViewById(R.id.editEventName);
        eventRegion=(EditText) findViewById(R.id.editEventRegion);
        eventTime=(EditText) findViewById(R.id.editEventTime);
        eventDuration=(EditText) findViewById(R.id.editEventDuration);

        listViewEvents = (ListView) findViewById(R.id.listEvents);

       // databaseEvents = FirebaseDatabase.getInstance().getReference("Events1");

        dropdownType = findViewById(R.id.spinnerType);


        events = new ArrayList<Event>();
        eventNames=new ArrayList<String>();
        eventTypeNames=new ArrayList<String>();


        eventName.setText("");
        eventRegion.setText("");
        eventTime.setText("");
        eventDuration.setText("0");


        AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Event event = events.get(i);
                showUpdateDeleteDialog(event);
                return true;
            }
        };
        listViewEvents.setOnItemLongClickListener(longClickListener);





        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("EventTypes1");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //int i=0;
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    EventType et = postSnapShot.getValue(EventType.class);
                    eventTypeNames.add(et.getTypeName());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


        //create a list of items for the spinner.
        //String[] items = (String[]) eventNames.toArray();
        //String items[] = eventNames.toArray(new String[eventNames.size()]);
        //String[] str = new String[10];

        //for (int i = 0; i < eventNames.size(); i++) {
        //    str[i] = eventNames.get(i).toString();
       // }


        String[] Types = {"Time Trial", "Hill Climb", "Road Stage Race", "Road Race", "Group Rides"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Types);
        dropdownType.setAdapter(adapter);


    }



    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference  databaseEvents=Administrator.getEventDB();
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
        EditText editTextType=(EditText) dialogView.findViewById(R.id.editTextEventType);

        //
        EditText editTextTime = (EditText) dialogView.findViewById(R.id.editTextTime);
        EditText editTextDuration = (EditText) dialogView.findViewById(R.id.editTextDuration);

        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.btnEventUpdate);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.btnEventDelete);

        String[] Types = {"Time Trial", "Hill Climb", "Road Stage Race", "Road Race", "Group Rides"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Types);
        //editSpinnerType.setAdapter(adapter1);


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
                //String  key=event.getKey();
                String name = editTextName.getText().toString().trim();
                String location = editTextLocation.getText().toString().trim();
                String type = editTextType.getText().toString().trim();
                String time = editTextTime.getText().toString().trim();
                String durationString = editTextDuration.getText().toString().trim();

                String message=validateInput(name, location, time , durationString);
                if(message.equals("")){
                    double duration = Double.parseDouble(durationString);
                    Administrator.updateEvent(event.getKey(), name, location, type, time , duration);
                    b.dismiss();
                }
                else{
                    displayPopupMessage(message,view);

                }

            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                Administrator.removeEvent(event);

                b.dismiss();
            }
        });
    }




    public void onClickAddEvent(View view) {

        String name = eventName.getText().toString().trim();
        String region = eventRegion.getText().toString().trim();
        String type = dropdownType.getSelectedItem().toString();
        String time = eventTime.getText().toString().trim();
        String durationString= eventDuration.getText().toString().trim();

        String message=validateInput(name, region, time , durationString);
        if(message.equals("")){
            double duration = Double.parseDouble(durationString);
            Administrator.createEvent( name, region, type, time , duration);
            eventName.setText("");
            eventRegion.setText("");
            eventTime.setText("");
            eventDuration.setText("0");
        }
        else{
            displayPopupMessage(message,view);

        }

    }


    private void displayPopupMessage(String message, View anchorView) {
        LinearLayout layout = new LinearLayout(this);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));

        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        TextView textView = new TextView(this);
        textView.setText(message);
        textView.setTextColor(Color.RED);

        PopupWindow popupWindow = new PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setContentView(textView);
        popupWindow.showAsDropDown(anchorView, 10, 0);
    }


    private String validateInput(String name, String region, String time, String durationString ) {
        InputValidator validator = InputValidator.getInstance();

        String message="";

        /* Validate event type */
        if (!validator.isValidName(name)) { message= message+ "Event name can not be blank or special characters";}
        if (!validator.isValidName(region)) { message= message+ "Location/Region can not be blank or special characters";}
        if (!validator.isValidDate(time)) { message= message+ "Event Time must be in format YYYY-MM-DD";}
        if (!validator.isValidNumber(durationString)) { message= message+ "Duration must be a number";}

        return message;
    }

}