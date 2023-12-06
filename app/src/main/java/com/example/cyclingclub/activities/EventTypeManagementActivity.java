package com.example.cyclingclub.activities;

import android.app.ActionBar;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import com.example.cyclingclub.*;
import com.example.cyclingclub.R;
import com.example.cyclingclub.fragments.EventTypeListFragment;
import com.example.cyclingclub.User;
import com.example.cyclingclub.utils.Utils;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.*;

import java.util.*;

/**
 * The EventTypeManagementActivity class is an activity that manages event types.
 * It displays a list of event types and allows an administrator to update or delete an event type,
 * and a cycling club to create a new event type.
 */
public class EventTypeManagementActivity extends AppCompatActivity {

    private ListView listViewEventTypes;
    private List<EventType> eventTypes;
    private EventType selectedEventType;
    private User user;
    private Map<String, View> views;

    private final String ADD_TEXT = "Add";
    private final String CANCEL_TEXT = "Cancel";

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
        View dialogView = getLayoutInflater().inflate(com.example.cyclingclub.R.layout.update_event_type_dialog, null);
        dialogBuilder.setView(dialogView);

        EditText editTextName = dialogView.findViewById(com.example.cyclingclub.R.id.editTextTypeName);
        EditText editTextDetail = dialogView.findViewById(com.example.cyclingclub.R.id.editTextDescription);
        Button buttonUpdate = dialogView.findViewById(com.example.cyclingclub.R.id.buttonUpdate);
        Button buttonDelete = dialogView.findViewById(com.example.cyclingclub.R.id.buttonDelete);

        if (editTextName == null || editTextDetail == null || buttonUpdate == null || buttonDelete == null) {
            throw new NullPointerException("One or more views could not be found");
        }

        editTextName.setText("");
        editTextDetail.setText("");
        buttonUpdate.setText(ADD_TEXT);
        buttonDelete.setText(CANCEL_TEXT); // Changed text to "Cancel"
        buttonDelete.setOnClickListener(null); // Set OnClickListener to null to dismiss the dialog when clicked

        final androidx.appcompat.app.AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(view1 -> {
            String newName = getTrimmedText(editTextName);
            String newDetail = getTrimmedText(editTextDetail);

            String message = validateTypeInput(newName, newDetail);
            if (message.isEmpty()) {
                EventType newEventType = new EventType("", newName, newDetail);
                Administrator.createEventType(newEventType);
                b.dismiss();
            } else {
                displayPopupMessage(message, view1);
            }
        });

        buttonDelete.setOnClickListener(view2 -> b.dismiss());
    }

    private View inflateView(int layoutId) {
        LayoutInflater inflater = getLayoutInflater();
        return inflater.inflate(layoutId, null);
    }

    /**
     * Handles the click event of the Add Event button.
     * It shows a dialog to add a new event.
     *
     * @param view The view that triggered this onClick method (in this case, the Add Event button).
     */
    public void onClickAddEvent(View view) {
        if (selectedEventType == null) {
            displayPopupMessage("Must select event type first!", view);
            return;
        }

        AlertDialog.Builder dialogBuilder = createDialogBuilder(com.example.cyclingclub.R.layout.update_event_type_dialog);
        View dialogView = inflateView(com.example.cyclingclub.R.layout.event_detail);
        views = setupDialogView(dialogView, dialogBuilder, user);

        EditText editEventId = findView(views, "editEventId", EditText.class);
        EditText editEventType = findView(views, "editEventType", EditText.class);
        EditText editEventRegion = findView(views, "editEventRegion", EditText.class);
        EditText editEventDate = findView(views, "editEventDate", EditText.class);
        EditText editEventRoute = findView(views, "editEventRoute", EditText.class);
        EditText editEventDistance = findView(views, "editEventDistance", EditText.class);
        EditText editEventElevation = findView(views, "editEventElevation", EditText.class);
        Spinner spinner = findView(views, "spinnerLevel", Spinner.class);
        EditText editEventFee = findView(views, "editEventFee", EditText.class);
        EditText editEventLimit = findView(views, "editEventLimit", EditText.class);
        Button buttonUpdate = findView(views, "buttonUpdate", Button.class);
        Button buttonDelete = findView(views, "buttonDelete", Button.class);

        if (editEventId == null || editEventType == null || editEventRegion == null || editEventDate == null || editEventRoute == null || editEventDistance == null || editEventElevation == null || spinner == null || editEventFee == null || editEventLimit == null || buttonUpdate == null || buttonDelete == null) {
            throw new NullPointerException("One or more views could not be found");
        }

        // Disable the Update and Delete buttons if the user is not a cycling club
        if (!user.getRole().equals("cycling club")) {
            buttonUpdate.setEnabled(false);
            buttonDelete.setEnabled(false);
        }

        buttonUpdate.setText(ADD_TEXT);
        buttonDelete.setText(CANCEL_TEXT); // Changed text to "Cancel"
        buttonDelete.setOnClickListener(null); // Set OnClickListener to null to dismiss the dialog when clicked

        dialogBuilder.setTitle("Create New Event");
        editEventType.setText(selectedEventType.getTypeName());
        TextView eventTypeDetail = findView(views, "eventTypeDetail", TextView.class);
        eventTypeDetail.setText(selectedEventType.getDetail());

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        setupUpdateButtonListener(buttonUpdate, editEventId, editEventType, editEventRegion, editEventDate, editEventRoute, editEventDistance, editEventElevation, spinner, editEventFee, editEventLimit, dialog);
    }

    /**
     * Sets up the dialog view and the buttons.
     * Disables the Update and Delete buttons if the user is not an Administrator.
     *
     * @param dialogView The view of the dialog.
     * @param dialogBuilder The builder for the dialog.
     * @param user The current user.
     * @return A map of the views in the dialog.
     */
    private Map<String, View> setupDialogView(View dialogView, AlertDialog.Builder dialogBuilder, User user) {
        Map<String, View> views = new HashMap<>();

        views.put("editTextName", dialogView.findViewById(com.example.cyclingclub.R.id.editTextTypeName));
        views.put("editTextDetail", dialogView.findViewById(com.example.cyclingclub.R.id.editTextDescription));
        views.put("buttonUpdate", dialogView.findViewById(com.example.cyclingclub.R.id.buttonUpdate));
        views.put("buttonDelete", dialogView.findViewById(com.example.cyclingclub.R.id.buttonDelete));
        views.put("editEventId", dialogView.findViewById(com.example.cyclingclub.R.id.editEventId));
        views.put("editEventType", dialogView.findViewById(com.example.cyclingclub.R.id.editEventType));
        views.put("editEventRegion", dialogView.findViewById(com.example.cyclingclub.R.id.editEventRegion));
        views.put("editEventDate", dialogView.findViewById(com.example.cyclingclub.R.id.editEventDate));
        views.put("editEventRoute", dialogView.findViewById(com.example.cyclingclub.R.id.editEventRoute));
        views.put("editEventDistance", dialogView.findViewById(com.example.cyclingclub.R.id.editEventDistance));
        views.put("editEventElevation", dialogView.findViewById(com.example.cyclingclub.R.id.editEventElevation));
        views.put("spinnerLevel", dialogView.findViewById(com.example.cyclingclub.R.id.spinnerLevel));
        views.put("editEventFee", dialogView.findViewById(com.example.cyclingclub.R.id.editEventFee));
        views.put("editEventLimit", dialogView.findViewById(R.id.editEventLimit));

        // Disable the Update and Delete buttons if the user is not an Administrator
        if (!user.getRole().equals("Administrator")) {
            Objects.requireNonNull(views.get("buttonUpdate")).setEnabled(false);
            Objects.requireNonNull(views.get("buttonDelete")).setEnabled(false);
        }

        dialogBuilder.setTitle("Event Types Detail");

        return views;
    }

    /**
     * Sets up the OnClickListener for the update button.
     *
     * @param button The update button.
     * @param editEventId The EditText for the event id.
     * @param editEventType The EditText for the event type.
     * @param editEventRegion The EditText for the event region.
     * @param editEventDate The EditText for the event date.
     * @param editEventRoute The EditText for the event route.
     * @param editEventDistance The EditText for the event distance.
     * @param editEventElevation The EditText for the event elevation.
     * @param spinner The Spinner for the event level.
     * @param editEventFee The EditText for the event fee.
     * @param editEventLimit The EditText for the event limit.
     * @param dialog The dialog to be dismissed after the event is updated.
     */
    private void setupUpdateButtonListener(Button button, EditText editEventId, EditText editEventType, EditText editEventRegion, EditText editEventDate, EditText editEventRoute, EditText editEventDistance, EditText editEventElevation, Spinner spinner, EditText editEventFee, EditText editEventLimit, AlertDialog dialog) {
        button.setOnClickListener(view -> {
            String id = getTrimmedText(editEventId);
            String type = getTrimmedText(editEventType);
            String region = getTrimmedText(editEventRegion);
            String date = getTrimmedText(editEventDate);
            String route = getTrimmedText(editEventRoute);
            int level = getParsedInt(spinner);
            double fee = parseDouble(getTrimmedText(editEventFee));
            int limit = parseInt(getTrimmedText(editEventLimit));
            int distance = parseInt(getTrimmedText(editEventDistance));
            int elevation = parseInt(getTrimmedText(editEventElevation));

            String message = validateInput(id, region, date, route, fee, limit, distance, elevation);
            if (TextUtils.isEmpty(message)) {
                Event event = new Event("", id, type, selectedEventType.getDetail(), region, date, route, level, fee, limit, distance, elevation);

                Administrator.createEvent(event);
                dialog.dismiss();
            } else {
                displayPopupMessage(message, view);
            }
        });
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
        validations.put("Event description can not be blank", !TextUtils.isEmpty(eventTypeDetail));

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
     * @param eventRoute The event route.
     * @param eventFee The event fee.
     * @param eventLimit The event limit.
     * @param eventDistance The event distance.
     * @param eventElevation The event elevation.
     * @return A validation message if there's an issue, or an empty string if everything is valid.
     */
    private String validateInput(String eventId, String eventRegion, String eventDate, String eventRoute, double eventFee, int eventLimit, int eventDistance, int eventElevation) {
        // Get the singleton instance of the Utils class
        Utils utils = Utils.getInstance();

        // Initialize a StringBuilder to construct the validation message
        StringBuilder message = new StringBuilder();

        // Create a map to store the validation messages and the corresponding validation checks
        Map<String, Boolean> validations = new LinkedHashMap<>();
        validations.put("ID name must start with letter and can not be blank. ", utils.isValidString(eventId));
        validations.put("Region must start with letter and can not be blank. ", utils.isValidString(eventRegion));
        validations.put("Date must be in format YYYY-MM-DD. ", utils.isValidDate(eventDate));
        validations.put("Route must start with letter and can not be blank. ", utils.isValidString(eventRoute));
        validations.put("Fee must be a number. ", utils.isValidNumber(String.valueOf(eventFee)));
        validations.put("Limit must be a number. ", utils.isValidNumber(String.valueOf(eventLimit)));
        validations.put("Distance must be a number. ", utils.isValidNumber(String.valueOf(eventDistance)));
        validations.put("Elevation must be a number. ", utils.isValidNumber(String.valueOf(eventElevation)));

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
     * Displays a popup message.
     *
     * @param message The message to be displayed.
     * @param anchorView The view to anchor the popup message to.
     */
    private void displayPopupMessage(String message, View anchorView) {
        LinearLayout layout = createNewLinearLayout();
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
     * Retrieves the text from an EditText, trims any leading or trailing whitespace, and returns the result.
     *
     * @param editText The EditText from which to retrieve the text.
     * @return The trimmed text from the EditText.
     */
    private String getTrimmedText(EditText editText) {
        return editText.getText().toString().trim();
    }

    /**
     * Creates a new LinearLayout with the current context.
     *
     * @return A new LinearLayout instance.
     */
    private LinearLayout createNewLinearLayout() {
        return new LinearLayout(this);
    }

    /**
     * Finds a view by its id and casts it to the specified type.
     *
     * @param views The parent views that contains the view to find.
     * @param key The key of the view to find.
     * @param type The type to cast the found view to.
     * @param <T> The type of the view.
     * @return The found view, cast to the specified type.
     */
    private <T extends View> T findView(Map<String, View> views, String key, Class<T> type) {
        View view = views.get(key);
        if (view == null) {
            throw new NullPointerException("View with key " + key + " not found in views map");
        }
        return type.cast(view);
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

    /**
     * Creates a new AlertDialog.Builder and sets the view.
     *
     * @param layoutId The layout resource ID of the view to inflate and set on the dialog.
     * @return A new AlertDialog.Builder with the view set.
     */
    private AlertDialog.Builder createDialogBuilder(int layoutId) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(layoutId, null);
        dialogBuilder.setView(dialogView);
        return dialogBuilder;
    }

    /**
     * Parses a string to a double.
     *
     * @param value The string to parse.
     * @return The parsed double.
     */
    private double parseDouble(String value) {
        return Double.parseDouble(value);
    }

    /**
     * Parses a string to an integer.
     *
     * @param value The string to parse.
     * @return The parsed integer.
     */
    private int parseInt(String value) {
        return Integer.parseInt(value);
    }
}