package com.example.cyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.Button;
import android.text.TextUtils;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;


public class EventTypeManagement extends AppCompatActivity {

    private ListView listViewEventTypes;
    private List<EventType> eventTypes;
   // private DatabaseReference databaseProducts;
    //private Administrator admin;
    private EventType selectedEventType;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = (User) getIntent().getSerializableExtra("user");

        setContentView(R.layout.activity_event_type_management);

        listViewEventTypes = (ListView) findViewById(R.id.eventTypeList);
        //EventTypeList eventTypeAdapter= new EventTypeList(EventTypeManagement.this, eventTypes);
        //listViewEventTypes.setAdapter(eventTypeAdapter);
        Button btnAddEventType = (Button) findViewById(R.id.btnNewEventType);
        if(!(user.getRole().equals("Administrator"))){
            btnAddEventType.setEnabled(false);
        }
        eventTypes = new ArrayList<>();
        //listViewEventTypes.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        //AdapterView.OnItemSelectedListener;
        AdapterView.OnItemLongClickListener longClickListener= new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //String id = eventTypes.get(i).getId();
                EventType et = eventTypes.get(i);
                showUpdateDeleteDialog(et);
                return true;
            }
        };
        listViewEventTypes.setOnItemLongClickListener(longClickListener);
        Button buttonNewEventType = (Button) findViewById(R.id.btnNewEventType);
        if (!user.getRole().equals("Administrator")) {
            buttonNewEventType.setEnabled(false);
        }
        Button buttonNewEvent = (Button) findViewById(R.id.btnNewEvent);
        if (!user.getRole().equals("cycling club")) {
            buttonNewEvent.setEnabled(false);
        }
        //The administrator who can manage the event type database;
        //admin= new Administrator("admin","admin");
    }

    @Override
    protected void onStart() {
        super.onStart();
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eventTypes.clear();
                for(DataSnapshot postSnapShot:dataSnapshot.getChildren()){
                    EventType et =  postSnapShot.getValue(EventType.class);
                    eventTypes.add(et);
                }

                EventTypeList eventTypeAdapter= new EventTypeList(EventTypeManagement.this, eventTypes);
                listViewEventTypes.setAdapter(eventTypeAdapter);

                listViewEventTypes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        eventTypeAdapter.setSelectedPosition(position);

                        selectedEventType=eventTypes.get(position);

                        // view.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        Administrator.getEventTypeDB().addValueEventListener(postListener);
    }


    private void showUpdateDeleteDialog(EventType et) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.eventtype_detail, null);
        dialogBuilder.setView(dialogView);
         //editTextID.setEnabled(false);
         //editTextID.setFocusable(false);
         EditText editTextName = (EditText) dialogView.findViewById(R.id.eventTypeNameUpdate);
         EditText editTextDetail = (EditText) dialogView.findViewById(R.id.editEventTypeDetail);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdate);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDelete);
        if (!user.getRole().equals("Administrator")) {
            buttonUpdate.setEnabled(false);
            buttonDelete.setEnabled(false);
        }
        dialogBuilder.setTitle("Event Types Detail");
        editTextName.setText(et.getTypeName());
        editTextDetail.setText(et.getDetail());
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String newName = editTextName.getText().toString().trim();
                String newDetail = editTextDetail.getText().toString().trim();

                String message=validateTypeInput(newName,newDetail);
                if(message.equals("")){
                    EventType updatedET = new EventType(et.getId(), newName, newDetail);
                    Administrator.updateEventType(updatedET);
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
                if(et.getNumberOfEvent()==0){
                    Administrator.deleteEventType(et);
                    b.dismiss();
                }
                else {
                    displayPopupMessage("There are events organized, can not delete",view);
                }
            }
        });
    }

    public void onClickAddEventType(View view) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.eventtype_detail, null);
        dialogBuilder.setView(dialogView);
        EditText editTextName = (EditText) dialogView.findViewById(R.id.eventTypeNameUpdate);
        EditText editTextDetail = (EditText) dialogView.findViewById(R.id.editEventTypeDetail);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdate);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDelete);
        if (!user.getRole().equals("Administrator")) {
            buttonUpdate.setEnabled(false);
            buttonDelete.setEnabled(false);
        }
        dialogBuilder.setTitle("Event Types Detail");
        editTextName.setText("");
        editTextDetail.setText("");
        buttonUpdate.setText("Add");
        buttonDelete.setText("Discard");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = editTextName.getText().toString().trim();
                String newDetail = editTextDetail.getText().toString().trim();

                String message=validateTypeInput(newName,newDetail);
                if(message.equals("")){

                    EventType newEventType = new EventType("", newName,newDetail);
                    Administrator.createEventType(newEventType);
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
                    b.dismiss();
            }
        });
    }

    public void onClickAddEvent(View view) {

        if(selectedEventType == null) {
            displayPopupMessage("Must select event type first!", view);
            return;
        }

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
        Spinner spinner = (Spinner) dialogView.findViewById(R.id.spinnerLevel);
        EditText editEventFee = (EditText) dialogView.findViewById(R.id.editEventFee);
        EditText editEventLimit = (EditText) dialogView.findViewById(R.id.editEventLimit);
        editEventId.setText("");
        editEventRegion.setText("");
        editEventDate.setText("");
        editEventRoute.setText("");
        editEventDistance.setText("0");
        editEventElevation.setText("0");
        editEventFee.setText("0");
        editEventLimit.setText("0");
        Button buttonUpdate = (Button) dialogView.findViewById(R.id.btnEventUpdate);
        Button buttonDelete = (Button) dialogView.findViewById(R.id.btnEventDelete);
        Button buttonRegister = (Button) dialogView.findViewById(R.id.btnRegister);
        if (!user.getRole().equals("cycling club")) {
            buttonUpdate.setEnabled(false);
            buttonDelete.setEnabled(false);
        }
        buttonRegister.setVisibility(View.INVISIBLE);
        buttonUpdate.setText("Add");
        buttonDelete.setText("Discard");
        dialogBuilder.setTitle("Create New Event");
        editEventType.setText(selectedEventType.getTypeName());
        TextView eventTypeDetail = (TextView) dialogView.findViewById(R.id.eventTypeDetail);
        eventTypeDetail.setText(selectedEventType.getDetail());
        final AlertDialog b = dialogBuilder.create();

        // Create an ArrayAdapter with numeric values from 1 to 5
        if (spinner != null) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    b.getContext(),
                    R.array.number_array,
                    android.R.layout.simple_spinner_item
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setSelection(0);
        }
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
                int level = Integer.parseInt( spinner.getSelectedItem().toString());
                String fee = editEventFee.getText().toString().trim();
                String limit = editEventLimit.getText().toString().trim();


                String message=validateInput(id,route,region,date,distance,elevation,fee,limit);
                if(message.equals("")){


                    Event event = new Event("", id, type, date);
                    event.setRegion(region);
                    event.setRoute(route);
                    event.setDistance(Integer.parseInt(distance));
                    event.setElevation(Integer.parseInt(elevation));
                    event.setLevel(level);
                    event.setFee(Double.parseDouble(fee));
                    event.setLimit(Integer.parseInt(limit));

                    event.setDetail(selectedEventType.getDetail());
                    event.setUsername(user.getUsername());

                    Administrator.createEvent(event);
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
                //administrator.removeEvent(event);

                b.dismiss();

            }
        });

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

    /**
     * Validate the input fields for email, username, and password.
     * @param eventTypeName The event type name.
     * @return A validation message if there's an issue, or null if everything is valid.
     */
    private String validateTypeInput(String eventTypeName, String eventTypeDetail) {
        InputValidator validator = InputValidator.getInstance();
        String message="";
        /* Validate event type */
        if (!validator.isValidString(eventTypeName)) { message = "Event type must start with letter and can not be blank.";}
        if (eventTypeDetail.length()<1) { message = message + "Event description can not be blank";}
        return message;
    }

    private String validateInput(String id,String route,String region,String date,String distance,String elevation,String fee,String limit ) {
        InputValidator validator = InputValidator.getInstance();

        String message="";

        /* Validate event type */
        if (!validator.isValidString(id)) { message= message+ "ID name must start with letter and can not be blank.";}
        if (!validator.isValidString(route)) { message= message+ "Route must start with letter and can not be blank.";}
        if (!validator.isValidString(region)) { message= message+ "Region must start with letter and can not be blank.";}
        if (!validator.isValidDate(date)) { message= message+ "Date must be in format YYYY-MM-DD.";}
        if (!validator.isValidNumber(distance)) { message= message+ "Distance must be a number.";}
        if (!validator.isValidNumber(elevation)) { message= message+ "Elevation must be a number.";}
       // if (!validator.isValidNumber(level)) { message= message+ "Level must be a number";}
        if (!validator.isValidNumber(fee)) { message= message+ "Fee must be a number.";}
        if (!validator.isValidNumber(limit)) { message= message+ "Limit must be a number.";}

        return message;
    }

}