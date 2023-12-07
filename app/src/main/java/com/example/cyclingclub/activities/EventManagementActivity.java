package com.example.cyclingclub.activities;

import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import com.example.cyclingclub.*;
import com.example.cyclingclub.R;
import com.example.cyclingclub.User;

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
public class EventManagementActivity extends AppCompatActivity {

    // ListView to display the list of events
    private ListView listViewEvents;
    // List to store the events fetched from the database
    public static List<Event> events;
    private User user;

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
        };
        listViewEvents.setOnItemLongClickListener(longClickListener);
    }

    /**
     * Called after onCreate(Bundle) or after onRestart() followed by onStart().
     * Fetches the list of events from the database and sets up the event list view.
     */
    @Override
    protected void onStart() {
        super.onStart();

        // Get a reference to the events node in the Firebase database
        DatabaseReference databaseEvents = Administrator.getEventDB();

        // Create a ValueEventListener to listen for changes in the events node
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Clear the events list
                events.clear();

                // Loop through the events in the database
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    // Get the Event object for this snapshot
                    Event event = postSnapShot.getValue(Event.class);

                    // Add the event to the list
                    events.add(event);
                }

                // Create an ArrayAdapter with the list of events and set it on the ListView
                ArrayAdapter<Event> eventAdapter = new ArrayAdapter<>(EventManagementActivity.this, android.R.layout.simple_list_item_1, events);
                listViewEvents.setAdapter(eventAdapter);
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
                // Handle potential errors here
            }
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
    private void showUpdateDeleteDialog(Event event) {
        // Create a dialog builder
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        // Inflate the event_detail layout
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.event_detail, null);

        // Set the custom layout on the dialog builder
        dialogBuilder.setView(dialogView);

        // Find the EditTexts and Buttons in the layout
        EditText editTextId = dialogView.findViewById(R.id.editEventId);
        EditText editTextType = dialogView.findViewById(R.id.editEventType);
        EditText editTextRegion = dialogView.findViewById(R.id.editEventRegion);
        EditText editTextDate = dialogView.findViewById(R.id.editEventDate);
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
        }
        // Set the title of the dialog
        dialogBuilder.setTitle("Update current Event");

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
        String distance = ((EditText) dialog.findViewById(R.id.editEventDistance)).getText().toString();
        String elevation = ((EditText) dialog.findViewById(R.id.editEventElevation)).getText().toString();
        double fee = Double.parseDouble(((EditText) dialog.findViewById(R.id.editEventFee)).getText().toString());
        int limit = Integer.parseInt(((EditText) dialog.findViewById(R.id.editEventLimit)).getText().toString());
        int level = ((Spinner) dialog.findViewById(R.id.spinnerLevel)).getSelectedItemPosition() + 1;

        // Create a new Event object with these details
        Event updatedEvent = new Event(event.getKey(), id, type, event.getDetail(), region, date, level, fee, limit, distance, elevation);
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
}