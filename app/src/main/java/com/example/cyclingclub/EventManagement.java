package com.example.cyclingclub;

import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * The EventManagement class is an activity that manages events.
 * It displays a list of events and allows an administrator to update or delete an event,
 * and a participant to register for an event.
 */
public class EventManagement extends AppCompatActivity {

<<<<<<< HEAD
    // ListView to display the list of events
=======

>>>>>>> ffbefac830ee375163570b73837d42ecf654fee0
    private ListView listViewEvents;
    // List to store the events fetched from the database
    private List<Event> events;
    private User user;

<<<<<<< HEAD
    /**
     * Called when the activity is starting.
     * Initializes the activity and sets up the event list view.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_management);

        // Get the user passed from the previous activity
        // User currently logged in
        user = (User) getIntent().getSerializableExtra("user");

        // Initialize the events list
        events = new ArrayList<>();

        // Find the ListView in the layout
        listViewEvents = findViewById(R.id.listEvents);

        // Set up a long click listener for the ListView items
        // When an event is long clicked, a dialog is shown with the event's details and options to update, delete, or register for the event
        AdapterView.OnItemLongClickListener longClickListener = (adapterView, view, i, l) -> {
            Event event = events.get(i);
            showUpdateDeleteDialog(event);
            return true;
=======
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = (User) getIntent().getSerializableExtra("user");
        setContentView(R.layout.activity_event_management);
        listViewEvents = (ListView) findViewById(R.id.listEvents);
        events = new ArrayList<Event>();
        eventsList = new ArrayList<String>();


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
                TextView1.setText("Name:"+ev.getId()+ "     Type:"+ev.getType());
                TextView2.setText("Region:"+ev.getRegion()+"    Date:"+ev.getDate());
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
            public void onDataChange(DataSnapshot dataSnapshot) {
                events.clear();
                eventsList.clear();
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    Event event = postSnapShot.getValue(Event.class);
                    events.add(event);
                    eventsList.add(event.getId()+ " : "+ event.getType()+" : "+event.getDate());
                }
                //ArrayAdapter<String> itemsAdapter =
                //        new ArrayAdapter<String>(EventManagement.this, R.layout.item_view, R.id.textViewItem,eventsList);

                eventAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
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
>>>>>>> ffbefac830ee375163570b73837d42ecf654fee0
        };
        listViewEvents.setOnItemLongClickListener(longClickListener);
    }

<<<<<<< HEAD
    /**
     * Called after onCreate(Bundle) or after onRestart() followed by onStart().
     * Fetches the list of events from the database and sets up the event list view.
     */
=======
>>>>>>> ffbefac830ee375163570b73837d42ecf654fee0
    @Override
    protected void onStart() {
        super.onStart();

<<<<<<< HEAD
        // Get a reference to the events node in the Firebase database
        DatabaseReference databaseEvents = Administrator.getEventDB();

        // Create a ValueEventListener to listen for changes in the events node
        ValueEventListener postListener = new ValueEventListener() {
=======
    private void searchEvent(List<Event> events, User user, ArrayAdapter<Event> eventAdapter, String type, String region, String dateFrom, String dateTo){
        DatabaseReference  databaseEvents=Administrator.getEventDB();
        databaseEvents.addListenerForSingleValueEvent(new ValueEventListener() {
>>>>>>> ffbefac830ee375163570b73837d42ecf654fee0
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Clear the events list
                events.clear();
<<<<<<< HEAD

                // Loop through the events in the database
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    // Get the Event object for this snapshot
                    Event event = postSnapShot.getValue(Event.class);

                    // Add the event to the list
                    events.add(event);
                }

                // Create an ArrayAdapter with the list of events and set it on the ListView
                ArrayAdapter<Event> eventAdapter = new ArrayAdapter<>(EventManagement.this, android.R.layout.simple_list_item_1, events);
                listViewEvents.setAdapter(eventAdapter);
=======
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Event ev = snapshot.getValue(Event.class);
                    if((ev.getType().contains(type) || type.equals("")) &&
                            (ev.getRegion().contains(region) || region.equals("")) &&
                            (ev.getDate().compareTo( dateFrom) >=0 || dateFrom.equals("")) &&
                            (ev.getDate().compareTo(dateTo) <= 0 || dateTo.equals("")) ){
                        events.add(ev);
                    }
                }
                eventAdapter.notifyDataSetChanged();
>>>>>>> ffbefac830ee375163570b73837d42ecf654fee0
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
                // Handle potential errors here
            }
<<<<<<< HEAD
        };

        // Add the ValueEventListener to the DatabaseReference
        databaseEvents.addValueEventListener(postListener);
    }

    /**
     * Shows a dialog with the details of an event.
     * Allows an administrator to update or delete the event, and a participant to register for the event.
     *
     * @param event The event whose details are to be shown.
     */
=======
        });
    }

>>>>>>> ffbefac830ee375163570b73837d42ecf654fee0
    private void showUpdateDeleteDialog(Event event) {
        // Create a dialog builder
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        // Inflate the event_detail layout
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.event_detail, null);

        // Set the custom layout on the dialog builder
        dialogBuilder.setView(dialogView);

<<<<<<< HEAD
        // Find the EditTexts and Buttons in the layout
        EditText editTextId = dialogView.findViewById(R.id.editEventId);
        EditText editTextType = dialogView.findViewById(R.id.editEventType);
        EditText editTextRegion = dialogView.findViewById(R.id.editEventRegion);
        EditText editTextDate = dialogView.findViewById(R.id.editEventDate);
        EditText editTextRoute = dialogView.findViewById(R.id.editEventRoute);
        EditText editTextDistance = dialogView.findViewById(R.id.editEventDistance);
        EditText editTextElevation = dialogView.findViewById(R.id.editEventElevation);
        EditText editTextFee = dialogView.findViewById(R.id.editEventFee);
        EditText editTextLimit = dialogView.findViewById(R.id.editEventLimit);
        Spinner spinnerLevel = dialogView.findViewById(R.id.spinnerLevel);
        Button buttonUpdate = dialogView.findViewById(R.id.btnEventUpdate);
        Button buttonDelete = dialogView.findViewById(R.id.btnEventDelete);
        Button buttonRegister = dialogView.findViewById(R.id.btnRegister);

        // Set the text in the EditTexts to the event's details
        editTextId.setText(event.getId());
        editTextType.setText(event.getType());
        editTextRegion.setText(event.getRegion());
        editTextDate.setText(event.getDate());
        editTextRoute.setText(event.getRoute());
        editTextDistance.setText(String.format(Locale.US, "%d", event.getDistance()));
        editTextElevation.setText(String.format(Locale.US, "%d", event.getElevation()));
        editTextFee.setText(String.format(Locale.US, "%.2f", event.getFee()));
        editTextLimit.setText(String.format(Locale.US, "%d", event.getLimit()));

        int level = event.getLevel() - 1;
        if (level >= 0 && level < spinnerLevel.getCount()) {
            spinnerLevel.setSelection(level);
        } else {
            // Handle the case where the level is out of bounds
            spinnerLevel.setSelection(0);
=======
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
        editEventId.setText(event.getId());
        editEventRegion.setText(event.getRegion());
        editEventDate.setText(event.getDate());
        editEventRoute.setText(event.getRoute());
        editEventDistance.setText(Integer.toString(event.getDistance()));
        editEventElevation.setText(Integer.toString(event.getElevation()));
        editEventFee.setText(Double.toString(event.getFee()));
        editEventLimit.setText(Integer.toString(event.getLimit()));
        Button buttonUpdate = (Button) dialogView.findViewById(R.id.btnEventUpdate);
        Button buttonDelete = (Button) dialogView.findViewById(R.id.btnEventDelete);
        Button buttonRegister = (Button) dialogView.findViewById(R.id.btnRegister);

        if (user.getRole().equals("Administrator") ) {
            buttonUpdate.setEnabled(false);
            buttonRegister.setEnabled(false);
>>>>>>> ffbefac830ee375163570b73837d42ecf654fee0
        }
        // Set the title of the dialog
        dialogBuilder.setTitle("Update current Event");
<<<<<<< HEAD

        // Create the dialog
        AlertDialog b = dialogBuilder.create();
        b.show();

        // Set up click listeners for the Update, Delete, and Register buttons
        buttonUpdate.setOnClickListener(view -> updateEvent(event, b));
        buttonDelete.setOnClickListener(view -> deleteEvent(event, b));
        buttonRegister.setOnClickListener(view -> registerEvent(event, view));
    }

    /**
     * Updates an event in the database.
     *
     * @param event The event to be updated.
     * @param dialog The dialog to be dismissed after the event is updated.
     */
    private void updateEvent(Event event, AlertDialog dialog) {
        // Get the updated details from the EditText fields
        String id = ((EditText) dialog.findViewById(R.id.editEventId)).getText().toString();
        String type = ((EditText) dialog.findViewById(R.id.editEventType)).getText().toString();
        String region = ((EditText) dialog.findViewById(R.id.editEventRegion)).getText().toString();
        String date = ((EditText) dialog.findViewById(R.id.editEventDate)).getText().toString();
        String route = ((EditText) dialog.findViewById(R.id.editEventRoute)).getText().toString();
        int distance = Integer.parseInt(((EditText) dialog.findViewById(R.id.editEventDistance)).getText().toString());
        int elevation = Integer.parseInt(((EditText) dialog.findViewById(R.id.editEventElevation)).getText().toString());
        double fee = Double.parseDouble(((EditText) dialog.findViewById(R.id.editEventFee)).getText().toString());
        int limit = Integer.parseInt(((EditText) dialog.findViewById(R.id.editEventLimit)).getText().toString());
        int level = ((Spinner) dialog.findViewById(R.id.spinnerLevel)).getSelectedItemPosition() + 1;

        // Create a new Event object with these details
        Event updatedEvent = new Event(event.getKey(), id, type, event.getDetail(), region, date, route, level, fee, limit, distance, elevation);
        // Update the event in the database
        Administrator.updateEvent(updatedEvent);

        // Dismiss the dialog
        dialog.dismiss();
    }

    /**
     * Deletes an event from the database.
     *
     * @param event The event to be deleted.
     * @param dialog The dialog to be dismissed after the event is deleted.
     */
    private void deleteEvent(Event event, AlertDialog dialog) {
        // Delete the event from the database
        Administrator.removeEvent(event);

        // Dismiss the dialog
        dialog.dismiss();
    }

    /**
     * Registers a participant for an event.
     *
     * @param event The event to register for.
     * @param view The view to anchor the popup message to.
     */
    private void registerEvent(Event event, View view) {
        // Create a new Registration object with the details of the participant and the event
        Registration registration = new Registration(event, user);

        // Add this object to the database
        DatabaseReference databaseRegistrations = FirebaseDatabase.getInstance().getReference("registrations");
        String id = databaseRegistrations.push().getKey();
        assert id != null;
        databaseRegistrations.child(id).setValue(registration);

        // Show a popup message indicating that the registration was successful
        displayPopupMessage("Registration successful", view);
    }

    /**
     * Displays a popup message.
     *
     * @param message The message to be displayed.
     * @param anchorView The view to anchor the popup message to.
     */
    private void displayPopupMessage(String message, View anchorView) {
        // Create a TextView with the message
        TextView textView = new TextView(this);
        textView.setText(message);

        // Create a PopupWindow with the TextView
        PopupWindow popupWindow = new PopupWindow(textView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        // Show the PopupWindow anchored to the specified view
        popupWindow.showAsDropDown(anchorView, 10, 0);
    }
=======
        editEventType.setText(event.getType());
        TextView eventTypeDetail = (TextView) dialogView.findViewById(R.id.eventTypeDetail);
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

                     event.setId(id);
                    event.setType(type);
                    event.setRegion(region);
                    event.setRoute(route);
                    event.setDate(date);
                    event.setDistance(Integer.parseInt(distance));
                    event.setElevation(Integer.parseInt(elevation));
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

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference dref = FirebaseDatabase.getInstance().getReference("Registration");

                dref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean registered=false;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Registration reg = snapshot.getValue(Registration.class);
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
                            dref.child(key).setValue(reg);
                            displayPopupMessage("Registration successful",view);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle potential errors here.
                    }
                });
            }
        });
    }

    public void onClickAddEvent(View view) {

            Intent intent = new Intent(getApplicationContext(), EventTypeManagement.class);
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
        InputValidator validator = InputValidator.getInstance();
        String message="";
        /* Validate event type */
        if (!validator.isValidString(id)) { message= message+ "ID must start with letter and can not be blank.";}
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
>>>>>>> ffbefac830ee375163570b73837d42ecf654fee0
}