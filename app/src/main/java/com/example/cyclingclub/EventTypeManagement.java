package com.example.cyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
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

public class EventTypeManagement extends AppCompatActivity {


    private ListView listViewEventTypes;
    private List<EventType> eventTypes;
   // private DatabaseReference databaseProducts;
    //private Administrator admin;

    private EventType selectedEventType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_type_management);

        listViewEventTypes = (ListView) findViewById(R.id.eventTypeList);
        eventTypes = new ArrayList<>();


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


        listViewEventTypes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Clear the previous selection
                listViewEventTypes.clearChoices();

                // Set the current item as selected
                listViewEventTypes.setItemChecked(position, true);
                selectedEventType=eventTypes.get(position);
            }
        });


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
                if (!TextUtils.isEmpty(newName)) {
                    EventType updatedET = new EventType(et.getId(), newName, newDetail);
                    Administrator.updateEventType(updatedET);
                    b.dismiss();
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

        //editTextID.setEnabled(false);
        //editTextID.setFocusable(false);
        EditText editTextName = (EditText) dialogView.findViewById(R.id.eventTypeNameUpdate);
        EditText editTextDetail = (EditText) dialogView.findViewById(R.id.editEventTypeDetail);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdate);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDelete);

        dialogBuilder.setTitle("Event Types Detail");
        editTextName.setText("");
        editTextDetail.setText("");

        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = editTextName.getText().toString().trim();
                String newDetail = editTextDetail.getText().toString().trim();
                if (!TextUtils.isEmpty(newName)) {
                    EventType newEventType = new EventType("", newName,newDetail);
                    Administrator.createEventType(newEventType);
                    b.dismiss();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    b.dismiss();
            }
        });




        /*

        String typeName = eventTypeName.getText().toString().trim();

       // InputValidator validator = InputValidator.getInstance();



        if(validateInput(typeName)==null){
            EventType newEventType = new EventType("", typeName,0);
            Administrator.createEventType(newEventType);
            eventTypeName.setText("");
        }
        else{
            displayPopupMessage(validateInput(typeName),view);
            //show message says invalid type name;
            //Snackbar.make(view,"Error: Name cannot be empty",Snackbar.LENGTH_SHORT).show();
        }


         */
    }


    public void onClickAddEvent(View view) {

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


        Button buttonUpdate = (Button) dialogView.findViewById(R.id.btnEventUpdate);
        Button buttonDelete = (Button) dialogView.findViewById(R.id.btnEventDelete);


        dialogBuilder.setTitle("Create New Event");
        editEventType.setText(selectedEventType.getTypeName());
        TextView eventTypeDetail = (TextView) dialogView.findViewById(R.id.eventTypeDetail);
        eventTypeDetail.setText(selectedEventType.getDetail());

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

                int distance = Integer.parseInt(editEventDistance.getText().toString().trim());
                int elevation = Integer.parseInt(editEventElevation.getText().toString().trim());
                int level = Integer.parseInt(editEventLevel.getText().toString().trim());
                double fee = Double.parseDouble(editEventFee.getText().toString().trim());
                int limit = Integer.parseInt(editEventLimit.getText().toString().trim());


                // String message=validateInput(name, location, time , durationString);
                // if(message.equals("")){
                //     double duration = Double.parseDouble(durationString);
                Event event = new Event("", id, type, date);
                Administrator.createEvent(event);
                b.dismiss();
                //}
                //else{
                //    displayPopupMessage(message,view);

                //}

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
    private String validateInput(String eventTypeName) {
        InputValidator validator = InputValidator.getInstance();

        /* Validate event type */
        if (!validator.isValidName(eventTypeName)) { return "Event type name can not be blank or numbers";}

        return null;
    }

}