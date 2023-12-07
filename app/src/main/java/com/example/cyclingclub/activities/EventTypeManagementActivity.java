package com.example.cyclingclub.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cyclingclub.Administrator;
import com.example.cyclingclub.Event;
import com.example.cyclingclub.EventType;
import com.example.cyclingclub.R;
import com.example.cyclingclub.fragments.EventTypeListFragment;
import com.example.cyclingclub.utils.Utils;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.*;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.util.*;

/**
 * The EventTypeManagementActivity class is an activity that manages event types.
 * It displays a list of event types and allows an administrator to update or delete an event type,
 * and a cycling club to create a new event type.
 */
public class EventTypeManagementActivity extends AppCompatActivity {

    private EventType selectedEventType;

    public EventTypeManagementActivity() { }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.cyclingclub.R.layout.activity_event_type_management);

        // Fetch the event type data
        List<EventType> eventTypes = fetchEventTypes();

        // Add the EventTypeListFragment to the fragment_container
        EventTypeListFragment fragment = new EventTypeListFragment(eventTypes);
        getSupportFragmentManager().beginTransaction()
                .add(com.example.cyclingclub.R.id.fragment_container, fragment)
                .commit();
    }

    private List<EventType> fetchEventTypes() {
        List<EventType> eventTypes = new ArrayList<>();

        DatabaseReference databaseEventType = FirebaseDatabase.getInstance().getReference("eventTypes");
        databaseEventType.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventTypes.clear();
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    EventType eventType = postSnapShot.getValue(EventType.class);
                    eventTypes.add(eventType);
                }

                // Update the RecyclerView with the fetched event types
                EventTypeListFragment fragment = new EventTypeListFragment(eventTypes);
                getSupportFragmentManager().beginTransaction()
                        .replace(com.example.cyclingclub.R.id.fragment_container, fragment)
                        .commit();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
        return eventTypes;
    }

    /**
     * Handles the click event of the Add Event Type button.
     * It shows a dialog to add a new event type.
     *
     * @param view The view that triggered this onClick method (in this case, the Add Event Type button).
     */
    public void onClickAddEventType(View view) {
        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.update_event_type_dialog, null);
        dialogBuilder.setView(dialogView);

        EditText editTextTypeName = dialogView.findViewById(R.id.editTextTypeName);
        EditText editTextDescription = dialogView.findViewById(R.id.editTextDescription);

        editTextTypeName.setText("");
        editTextDescription.setText("");

        dialogBuilder.setTitle("Create Event Type");

        dialogBuilder.setPositiveButton("Add", (dialog, id) -> {
            String newEventTypeName = getTrimmedText(editTextTypeName);
            String newDescription = getTrimmedText(editTextDescription);

            String message = validateTypeInput(newEventTypeName, newDescription);
            if (message.isEmpty()) {
                EventType eventType = new EventType("", newEventTypeName, newDescription);
                Administrator.createEventType(eventType);
                DynamicToast.makeSuccess(this, "Event type '" + newEventTypeName + "' has been created.");
                dialog.dismiss();
            } else {
                DynamicToast.makeError(this, message);
            }
        });

        dialogBuilder.setNegativeButton("Cancel", (dialog, id) -> {
            // User cancelled the dialog
            dialog.dismiss();
        });

        dialogBuilder.create().show();
    }

    public void setSelectedEventType(EventType eventType) {
        this.selectedEventType = eventType;
    }

    /**
     * Handles the click event of the Add Event button.
     * It shows a dialog to add a new event.
     *
     * @param view The view that triggered this onClick method (in this case, the Add Event button).
     */
    public void onClickAddEvent(View view) {
        Log.d("Debug", "onClickAddEvent() called");

        if (selectedEventType == null) {
            runOnUiThread(() -> DynamicToast.makeError(this, "Please select an event type first.").show());
            Log.d("Debug", "selectedEventType is null");
            return;
        }

        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.add_event_dialog, null);
        dialogBuilder.setView(dialogView);

        EditText editEventName = dialogView.findViewById(R.id.edit_event_name);
        EditText editEventRegion = dialogView.findViewById(R.id.edit_event_region);
        EditText editEventDate = dialogView.findViewById(R.id.edit_event_date);
        EditText editEventDistance = dialogView.findViewById(R.id.edit_event_distance);
        EditText editEventElevation = dialogView.findViewById(R.id.edit_route_eleveation);
        Spinner spinnerDifficultyLevel = dialogView.findViewById(R.id.spinner_difficulty_level);
        EditText editEventFee = dialogView.findViewById(R.id.edit_event_fee);
        EditText editEventLimit = dialogView.findViewById(R.id.edit_participant_limit);

        dialogBuilder.setTitle("Create '" + selectedEventType.getTypeName() + "' Type Event");

        // Create an ArrayAdapter with numeric values from 1 to 5
        if (spinnerDifficultyLevel != null) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    dialogBuilder.getContext(),
                    R.array.number_array,
                    android.R.layout.simple_spinner_item
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerDifficultyLevel.setAdapter(adapter);
            spinnerDifficultyLevel.setSelection(0);
        }

        dialogBuilder.setPositiveButton("Add", (dialog, id) -> {
            String eventName = getTrimmedText(editEventName);
            String eventRegion = getTrimmedText(editEventRegion);
            String eventDate = getTrimmedText(editEventDate);
            String eventDistance = getTrimmedText(editEventDistance);
            String eventElevation = getTrimmedText(editEventElevation);
            int difficultyLevel;
            if (spinnerDifficultyLevel != null) {
                difficultyLevel = getParsedInt(spinnerDifficultyLevel);
            } else {
                difficultyLevel = 1;
            }

            String eventFeeText = getTrimmedText(editEventFee);
            if (eventFeeText.isEmpty()) {
                runOnUiThread(() -> DynamicToast.makeError(this, "Event fee must be entered. ").show());
                return;
            }
            double eventFee = Double.parseDouble(eventFeeText);

            String eventLimitText = getTrimmedText(editEventLimit);
            if (eventLimitText.isEmpty()) {
                runOnUiThread(() -> DynamicToast.makeError(this, "Event limit must be entered. ").show());
                return;
            }
            int eventLimit = Integer.parseInt(eventLimitText);

            String validationMessage = validateInput(eventName, eventRegion, eventDate, eventFee, eventLimit, eventDistance, eventElevation);
            if (!validationMessage.isEmpty()) {
                runOnUiThread(() -> DynamicToast.makeError(this, validationMessage).show());
                Log.d("Debug", "Validation failed: " + validationMessage);
                return;
            }

            Event event = new Event("", eventName, selectedEventType.getTypeName(), selectedEventType.getDetail(), eventRegion, eventDate, difficultyLevel, eventFee, eventLimit, eventDistance, eventElevation);

            // Add the event to the Firebase Realtime Database
            Administrator.createEvent(event);
            runOnUiThread(() -> DynamicToast.makeSuccess(this, "Event '" + eventName + "' has been created. ").show());
            dialog.dismiss();
        });

        dialogBuilder.setNegativeButton("Cancel", (dialog, id) -> {
            // User cancelled the dialog
            dialog.dismiss();
        });

        dialogBuilder.create().show();
    }

    /**
     * Validates the input fields for event type name and detail.
     *
     * @param eventTypeName The event type name.
     * @param eventTypeDetail The event type detail.
     * @return A validation message if there's an issue, or an empty string if everything is valid.
     */
    private String validateTypeInput(String eventTypeName, String eventTypeDetail) {
        Utils utils = Utils.getInstance();
        StringBuilder message = new StringBuilder();

        Map<String, Boolean> validations = new LinkedHashMap<>();
        validations.put("Event type must start with letter and can not be blank. ", utils.isValidString(eventTypeName));
        validations.put("Event description can not be blank. ", !TextUtils.isEmpty(eventTypeDetail));

        for (Map.Entry<String, Boolean> entry : validations.entrySet()) {
            if (!entry.getValue()) {
                message.append(entry.getKey());
            }
        }

        return message.toString();
    }

    /**
     * Validates the input fields for an event.
     *
     * @param eventId The event id.
     * @param eventRegion The event region.
     * @param eventDate The event date.
     * @param eventFee The event fee.
     * @param eventLimit The event limit.
     * @param eventDistance The event distance.
     * @param eventElevation The event elevation.
     * @return A validation message if there's an issue, or an empty string if everything is valid.
     */
    private String validateInput(String eventId, String eventRegion, String eventDate, double eventFee, int eventLimit, String eventDistance, String eventElevation) {
        // Get the singleton instance of the Utils class
        Utils utils = Utils.getInstance();

        // Initialize a StringBuilder to construct the validation message
        StringBuilder message = new StringBuilder();

        // Create a map to store the validation messages and the corresponding validation checks
        Map<String, Boolean> validations = new LinkedHashMap<>();
        validations.put("Event name can only have alphabetical letters. ", utils.isValidString(eventId));
        validations.put("Region can only have alphabetical letters. ", utils.isValidString(eventRegion));
        validations.put("Date must be in format YYYY-MM-DD. ", utils.isValidDate(eventDate));
        validations.put("Fee must be a number. (ex: 49.99). ", utils.isValidNumber(String.valueOf(eventFee)));
        validations.put("Limit must be a number (ex: 5). ", utils.isValidNumber(String.valueOf(eventLimit)));
        validations.put("Distance must be using a valid unit (km, m, or mi). ", utils.isValidUnit(String.valueOf(eventDistance)));
        validations.put("Elevation must be using a valid unit (km, m, or mi). ", utils.isValidUnit(String.valueOf(eventElevation)));

        validations.put("Event name cannot be blank. ", eventId.isEmpty());
        validations.put("Region cannot be blank. ", eventRegion.isEmpty());
        validations.put("Date cannot be empty. ", eventDate.isEmpty());
        validations.put("Distance cannot be null. ", eventDistance.isEmpty());
        validations.put("Elevation cannot be empty. ", eventElevation.isEmpty());

        // Iterate over the entries in the map
        for (Map.Entry<String, Boolean> entry : validations.entrySet()) {
            // If the validation check is false, append the validation message to the StringBuilder
            if (!entry.getValue()) {
                message.append(entry.getKey());
            }
        }

        // Return the constructed validation message
        return message.toString();
    }

    /**
     * Retrieves the text from an EditText, trims any leading or trailing whitespace, and returns the result.
     *
     * @param editText The EditText from which to retrieve the text.
     * @return The trimmed text from the EditText.
     */
    private String getTrimmedText(EditText editText) {
        return editText.getText().toString().trim();
    }

    /**
     * Retrieves the selected item from a Spinner, converts it to an integer, and returns the result.
     *
     * @param spinner The Spinner from which to retrieve the selected item.
     * @return The integer value of the selected item in the Spinner.
     */
    private int getParsedInt(Spinner spinner) {
        return Integer.parseInt(spinner.getSelectedItem().toString());
    }
}