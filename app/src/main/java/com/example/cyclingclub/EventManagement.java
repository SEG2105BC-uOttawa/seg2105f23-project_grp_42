package com.example.cyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Intent;
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


   // private Spinner dropdownType ;

    private ListView listViewEvents;
    private List<Event> events;
    private List<String> eventsList;


   // private DatabaseReference databaseEvents;

    //private List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_management);

        listViewEvents = (ListView) findViewById(R.id.listEvents);

       // databaseEvents = FirebaseDatabase.getInstance().getReference("Events1");




        events = new ArrayList<Event>();
        eventsList = new ArrayList<String>();


        AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Event event = events.get(i);
                showUpdateDeleteDialog(event);
                return true;
            }
        };
        listViewEvents.setOnItemLongClickListener(longClickListener);

        //dropdownType = findViewById(R.id.spinnerType);
        //String[] Types = {"Time Trial", "Hill Climb", "Road Stage Race", "Road Race", "Group Rides"};

        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Types);
        //dropdownType.setAdapter(adapter);


    }



    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference  databaseEvents=Administrator.getEventDB();
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                events.clear();
                eventsList.clear();
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    Event event = postSnapShot.getValue(Event.class);
                    events.add(event);
                    eventsList.add(event.getId()+ " : "+ event.getType()+" : "+event.getDate());
                }
                ArrayAdapter<String> itemsAdapter =
                        new ArrayAdapter<String>(EventManagement.this, R.layout.item_view, R.id.textViewItem,eventsList);

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
        final View dialogView = inflater.inflate(R.layout.event_detail, null);
        dialogBuilder.setView(dialogView);

        EditText editEventId = (EditText) dialogView.findViewById(R.id.editEventId);
        EditText editEventType = (EditText) dialogView.findViewById(R.id.editEventType);
        EditText editEventRegion = (EditText) dialogView.findViewById(R.id.editEventRegion);
        EditText editEventDate = (EditText) dialogView.findViewById(R.id.editEventDate);
        EditText editEventRoute = (EditText) dialogView.findViewById(R.id.editEventRoute);
        EditText editEventDistance = (EditText) dialogView.findViewById(R.id.editEventDistance);
        EditText editEventElevation = (EditText) dialogView.findViewById(R.id.editEventElevation);
        EditText editEventLevel = (EditText) dialogView.findViewById(R.id.editEventLevel);
        EditText editEventFee = (EditText) dialogView.findViewById(R.id.editEventFee);
        EditText editEventLimit = (EditText) dialogView.findViewById(R.id.editEventLimit);

        editEventId.setText(event.getId());
        editEventRegion.setText(event.getRegion());
        editEventDate.setText(event.getDate());
        editEventRoute.setText(event.getRoute());


        editEventDistance.setText(Integer.toString(event.getDistance()));
        editEventElevation.setText(Integer.toString(event.getElevation()));
        editEventLevel.setText(Integer.toString(event.getLevel()));
        editEventFee.setText(Double.toString(event.getFee()));
        editEventLimit.setText(Integer.toString(event.getLimit()));


        Button buttonUpdate = (Button) dialogView.findViewById(R.id.btnEventUpdate);
        Button buttonDelete = (Button) dialogView.findViewById(R.id.btnEventDelete);

        dialogBuilder.setTitle("Update current Event");
        editEventType.setText(event.getType());
        TextView eventTypeDetail = (TextView) dialogView.findViewById(R.id.eventTypeDetail);
        eventTypeDetail.setText(event.getDetail());

        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String  key=event.getKey();
                String id = editEventId.getText().toString().trim();
                String type = editEventType.getText().toString().trim();
                String region = editEventRegion.getText().toString().trim();
                String date = editEventDate.getText().toString().trim();
                String route = editEventRoute.getText().toString().trim();

                String distance = editEventDistance.getText().toString().trim();
                String elevation = editEventElevation.getText().toString().trim();
                String level = editEventLevel.getText().toString().trim();
                String fee = editEventFee.getText().toString().trim();
                String limit = editEventLimit.getText().toString().trim();


                 String message=validateInput(id,route,region,date,distance,elevation,level,fee,limit);
                 if(message.equals("")){


                     event.setId(id);
                    event.setType(type);
                    event.setRegion(region);
                    event.setRoute(route);
                    event.setDate(date);
                    event.setDistance(Integer.parseInt(distance));
                    event.setElevation(Integer.parseInt(elevation));
                    event.setLevel(Integer.parseInt(level));
                    event.setFee(Double.parseDouble(fee));
                    event.setLimit(Integer.parseInt(limit));

                    event.setDetail(event.getDetail());
                    Administrator.updateEvent(event);
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

            Intent intent = new Intent(getApplicationContext(), EventTypeManagement.class);
            startActivity (intent);

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


    private String validateInput(String id,String route,String region,String date,String distance,String elevation,String level,String fee,String limit ) {
        InputValidator validator = InputValidator.getInstance();

        String message="";

        /* Validate event type */
        if (!validator.isValidName(id)) { message= message+ "ID name can not be blank or special characters";}
        if (!validator.isValidName(route)) { message= message+ "Route can not be blank or special characters";}
        if (!validator.isValidName(region)) { message= message+ "Region name can not be blank or special characters";}
        if (!validator.isValidDate(date)) { message= message+ "Date must be in format YYYY-MM-DD";}
        if (!validator.isValidNumber(distance)) { message= message+ "Distance must be a number";}
        if (!validator.isValidNumber(elevation)) { message= message+ "Elevation must be a number";}
        if (!validator.isValidNumber(level)) { message= message+ "Level must be a number";}
        if (!validator.isValidNumber(fee)) { message= message+ "Fee must be a number";}
        if (!validator.isValidNumber(limit)) { message= message+ "Limit must be a number";}

        return message;
    }

}