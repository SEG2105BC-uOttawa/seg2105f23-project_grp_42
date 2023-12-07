package com.example.cyclingclub.activities;

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

import com.example.cyclingclub.*;
import com.example.cyclingclub.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.database.Query;
import org.jetbrains.annotations.NotNull;

public class EventManagementActivity extends AppCompatActivity {
    private ListView listViewEvents;
    public static List<Event> events;
    public static List<String> eventsList;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = (User) getIntent().getSerializableExtra("user");
        setContentView(R.layout.activity_event_management);
        listViewEvents = findViewById(R.id.listEvents);
        events = new ArrayList<>();
        eventsList = new ArrayList<>();


        ArrayAdapter<Event> eventAdapter = new ArrayAdapter<Event>(
                this,
                android.R.layout.simple_list_item_2, // Built-in layout for a two-line list itemme
                android.R.id.text1, // ID of the TextView for the name
                events
        ) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Customize the appearance of the list item
                View view = super.getView(position, convertView, parent);

                // Get the Person object for this position
                Event ev = events.get(position);

                // Find the TextViews in the layout
                TextView TextView1 = view.findViewById(android.R.id.text1);
                TextView TextView2 = view.findViewById(android.R.id.text2);

                // Set the name and age in the TextViews
                TextView1.setText("Name: " + ev.getId()+ "     Type: " + ev.getType());
                TextView2.setText("Region: " + ev.getRegion() + "    Date: " + ev.getDate());
                return view;
            }
        };

        listViewEvents.setAdapter(eventAdapter);
        DatabaseReference  databaseEvents=Administrator.getEventDB();
        Query query;

        if(user.getRole().equals("cycling club")){
            query = databaseEvents.orderByChild("username").equalTo(user.getUsername());
        }else{
            query = databaseEvents;
        }

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                events.clear();
                eventsList.clear();
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    Event event = postSnapShot.getValue(Event.class);
                    events.add(event);
                    assert event != null;
                    eventsList.add(event.getId()+ " : "+ event.getType()+" : "+event.getDate());
                }
                eventAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
                System.out.println("Error querying the database: " + databaseError.getMessage());
            }
        });

        AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Event event = events.get(i);
                showUpdateDeleteDialog(event);
                return true;
            }
        };
        listViewEvents.setOnItemLongClickListener(longClickListener);


        Button buttonNewEvent = findViewById(R.id.btnAddEvent);
        if (user.getRole().equals("Administrator") || user.getRole().equals("participant")) {
            buttonNewEvent.setEnabled(false);
        }

        Button buttonSearch = findViewById(R.id.btnSearchEvent);

        EditText editSearchType = findViewById(R.id.editSearchType);
        EditText editSearchRegion = findViewById(R.id.editSearchRegion);
        EditText editSearchFrom = findViewById(R.id.editSearchFrom);
        EditText editSearchTo = findViewById(R.id.editSearchTo);
        editSearchType.setText("");
        editSearchRegion.setText("");
        editSearchFrom.setText("");
        editSearchTo.setText("");

        if (user.getRole().equals("cycling club") ) {
            buttonSearch.setEnabled(false);
            editSearchType.setEnabled(false);
            editSearchRegion.setEnabled(false);
            editSearchFrom.setEnabled(false);
            editSearchTo.setEnabled(false);
        }

        buttonSearch.setOnClickListener(view -> searchEvent(events, eventAdapter,editSearchType.getText().toString().trim(),editSearchRegion.getText().toString().trim(),editSearchFrom.getText().toString().trim(),editSearchTo.getText().toString().trim() ));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void searchEvent(List<Event> events, ArrayAdapter<Event> eventAdapter, String type, String region, String dateFrom, String dateTo){
        DatabaseReference  databaseEvents=Administrator.getEventDB();
        databaseEvents.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                events.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Event ev = snapshot.getValue(Event.class);
                    assert ev != null;
                    if(ev.getType().contains(type) && ev.getRegion().contains(region) && (ev.getDate().compareTo(dateFrom) >= 0 || dateFrom.isEmpty()) && (ev.getDate().compareTo(dateTo) <= 0 || dateTo.isEmpty())){
                        events.add(ev);
                    }
                }
                eventAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
                // Handle potential errors here.
            }
        });
    }

    private void showUpdateDeleteDialog(Event event) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.event_detail, null);
        dialogBuilder.setView(dialogView);

        EditText editEventId = dialogView.findViewById(R.id.editEventId);
        EditText editEventType = dialogView.findViewById(R.id.editEventType);
        EditText editEventRegion = dialogView.findViewById(R.id.editEventRegion);
        EditText editEventDate = dialogView.findViewById(R.id.editEventDate);
        EditText editEventRoute = dialogView.findViewById(R.id.editEventRoute);
        EditText editEventDistance = dialogView.findViewById(R.id.editEventDistance);
        EditText editEventElevation = dialogView.findViewById(R.id.editEventElevation);
        Spinner spinner = dialogView.findViewById(R.id.spinnerLevel);
        EditText editEventFee = dialogView.findViewById(R.id.editEventFee);
        EditText editEventLimit = dialogView.findViewById(R.id.editEventLimit);
        editEventId.setText(event.getId());
        editEventRegion.setText(event.getRegion());
        editEventDate.setText(event.getDate());
        editEventDistance.setText(event.getDistance());
        editEventElevation.setText(event.getElevation());
        editEventFee.setText(String.valueOf(event.getFee()));
        editEventLimit.setText(String.valueOf(event.getLimit()));
        Button buttonUpdate = dialogView.findViewById(R.id.btnEventUpdate);
        Button buttonDelete = dialogView.findViewById(R.id.btnEventDelete);
        Button buttonRegister = dialogView.findViewById(R.id.btnRegister);

        if (user.getRole().equals("Administrator") ) {
            buttonUpdate.setEnabled(false);
            buttonRegister.setEnabled(false);
        }

        if (user.getRole().equals("cycling club") ) {
            buttonRegister.setEnabled(false);
        }

        if (user.getRole().equals("participant")) {
            buttonUpdate.setEnabled(false);
            buttonDelete.setEnabled(false);
        }


        dialogBuilder.setTitle("Update current Event");
        editEventType.setText(event.getType());
        TextView eventTypeDetail = dialogView.findViewById(R.id.eventTypeDetail);
        eventTypeDetail.setText(event.getDetail());

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
            spinner.setSelection(event.getLevel()-1);
        }
        b.show();

        buttonUpdate.setOnClickListener(view -> {
            String id = editEventId.getText().toString().trim();
            String type = editEventType.getText().toString().trim();
            String region = editEventRegion.getText().toString().trim();
            String date = editEventDate.getText().toString().trim();
            String route = editEventRoute.getText().toString().trim();
            String distance = editEventDistance.getText().toString().trim();
            String elevation = editEventElevation.getText().toString().trim();
            assert spinner != null;
            int level = Integer.parseInt( spinner.getSelectedItem().toString());
            String fee = editEventFee.getText().toString().trim();
            String limit = editEventLimit.getText().toString().trim();


            String message=validateInput(id,route,region,date,distance,elevation,fee,limit);
            if(message.isEmpty()){

                event.setId(id);
                event.setType(type);
                event.setRegion(region);
                event.setDate(date);
                event.setDistance(distance);
                event.setElevation(elevation);
                event.setLevel(level);
                event.setFee(Double.parseDouble(fee));
                event.setLimit(Integer.parseInt(limit));

                event.setDetail(event.getDetail());
                Administrator.updateEvent(event);
                b.dismiss();

            }
            else{
                displayPopupMessage(message,view);
            }
        });

        buttonDelete.setOnClickListener(view -> {
            Administrator.removeEvent(event);
            b.dismiss();
        });

        buttonRegister.setOnClickListener(view -> {
            DatabaseReference dref = FirebaseDatabase.getInstance().getReference("registration");

            dref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                    boolean registered=false;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Registration reg = snapshot.getValue(Registration.class);
                        assert reg != null;
                        if (reg.getEvent().getId().equals(event.getId()) &&
                                reg.getParticipant().getUsername().equals(user.getUsername())){
                            registered=true;
                        }
                    }
                    if(registered){
                        displayPopupMessage("Registration exists, operation ignored",view);
                    }else{
                        Registration reg = new Registration(event,user);

                        String key = dref.push().getKey();
                        reg.setKey(key);
                        assert key != null;
                        dref.child(key).setValue(reg);
                        displayPopupMessage("Registration successful",view);
                    }
                }

                @Override
                public void onCancelled(@NotNull DatabaseError databaseError) {
                    // Handle potential errors here.
                }
            });
        });
    }

    public void onClickAddEvent(View view) {

        Intent intent = new Intent(getApplicationContext(), EventTypeManagementActivity.class);
        intent.putExtra("user", user);
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


    private String validateInput(String id,String route,String region,String date,String distance,String elevation,String fee,String limit ) {
        Utils validator = Utils.getInstance();
        String message="";
        /* Validate event type */
        if (!validator.isValidString(id)) { message= message+ "ID must start with letter and can not be blank.";}
        if (!validator.isValidString(route)) { message= message+ "Route must start with letter and can not be blank.";}
        if (!validator.isValidString(region)) { message= message+ "Region must start with letter and can not be blank.";}
        if (!validator.isValidDate(date)) { message= message+ "Date must be in format YYYY-MM-DD.";}
        if (!validator.isValidNumber(distance)) { message= message+ "Distance must be a number.";}
        if (!validator.isValidNumber(elevation)) { message= message+ "Elevation must be a number.";}
        if (!validator.isValidNumber(fee)) { message= message+ "Fee must be a number.";}
        if (!validator.isValidNumber(limit)) { message= message+ "Limit must be a number.";}
        return message;
    }
}