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

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

    private EditText eventTypeName;
    private ListView listViewEventTypes;
    private List<EventType> eventTypes;
    private DatabaseReference databaseProducts;
    private Administrator admin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_type_management);

        eventTypeName=(EditText) findViewById(R.id.eventTypeName);
        eventTypeName.setText("");
        listViewEventTypes = (ListView) findViewById(R.id.eventTypeList);
        eventTypes = new ArrayList<>();
        databaseProducts = FirebaseDatabase.getInstance().getReference("EventTypes1");

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

        //The administrator who can manage the event type database;
        admin= new Administrator("admin","admin");
        admin.setEventTypeDB(databaseProducts);

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
        databaseProducts.addValueEventListener(postListener);
    }


    private void showUpdateDeleteDialog(EventType et) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.eventtype_update, null);
        dialogBuilder.setView(dialogView);

         EditText editTextID = (EditText) dialogView.findViewById(R.id.eventTypeID);
         editTextID.setEnabled(false);
         editTextID.setFocusable(false);
         EditText editTextName = (EditText) dialogView.findViewById(R.id.eventTypeNameUpdate);
         EditText editTextNumber = (EditText) dialogView.findViewById(R.id.editEventNumber);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdate);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDelete);

        dialogBuilder.setTitle("Event Time Detail");
        editTextID.setText(et.getId());
        editTextName.setText(et.getTypeName());
        editTextNumber.setText(Integer.toString(et.getNumberOfEvent()));

        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newId = editTextID.getText().toString().trim();
                String newName = editTextName.getText().toString().trim();
                int newNumber = Integer.parseInt(editTextNumber.getText().toString().trim());
                if (!TextUtils.isEmpty(newName)) {
                    EventType updatedET = new EventType(newId, newName, newNumber);
                    admin.updateEventType(updatedET);
                    b.dismiss();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et.getNumberOfEvent()==0){
                    admin.deleteEventType(et);
                    b.dismiss();
                }
                else {
                    displayPopupMessage("There are events organized, can not delete",view);
                }
            }
        });
    }


    public void onClickAddEventType(View view) {
        String typeName = eventTypeName.getText().toString().trim();

        InputValidator validator = InputValidator.getInstance();



        if(validateInput(typeName)==null){
            EventType newEventType = new EventType("", typeName,0);
            admin.createEventType(newEventType);
            eventTypeName.setText("");
        }
        else{
            displayPopupMessage(validateInput(typeName),view);
            //show message says invalid type name;
            //Snackbar.make(view,"Error: Name cannot be empty",Snackbar.LENGTH_SHORT).show();
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

    /**
     * Validate the input fields for email, username, and password.
     * @param eventTypeName The event type name.
     * @return A validation message if there's an issue, or null if everything is valid.
     */
    private String validateInput(String eventTypeName) {
        InputValidator validator = InputValidator.getInstance();

        /* Validate event type */
        if (!validator.isValidName(eventTypeName)) { return "Event type name not valid.";}

        return null;
    }

}