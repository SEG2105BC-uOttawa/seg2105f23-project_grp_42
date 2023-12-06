package com.example.cyclingclub;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ActionBar;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The RegistrationManagement class is an activity that manages registrations.
 * It displays a list of registrations and allows an administrator or a cycling club to update or delete a registration.
 */
public class RegistrationManagement extends AppCompatActivity {

    private List<Registration> registrations;
    private Registration selectedRegistration;
    private User user;

    /**
     * Called when the activity is starting.
     * Initializes the activity and sets up the registration list view.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_management);

        // Get the user passed from the previous activity
        user = (User) getIntent().getSerializableExtra("user");

        // Initialize the registrations list
        registrations = new ArrayList<>();

        // Find the ListView in the layout
        ListView listViewRegistration = findViewById(R.id.listRegistration);

        // Create a RegistrationAdapter with the list of registrations and set it on the ListView
        RegistrationAdapter regAdapter = new RegistrationAdapter(RegistrationManagement.this, registrations);
        listViewRegistration.setAdapter(regAdapter);

        // Set up a ValueEventListener to listen for changes in the registrations node in the Firebase database
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Clear the registrations list
                registrations.clear();

                // Loop through the registrations in the database
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    // Get the Registration object for this snapshot
                    Registration reg = postSnapShot.getValue(Registration.class);

                    // Add the registration to the list if the user is the participant or the organizer of the event
                    if ((user.getRole().equals("cycling club") && user.getUsername().equals(Objects.requireNonNull(reg).getEvent().getUsername())) ||
                            (user.getRole().equals("participant") && user.getUsername().equals(Objects.requireNonNull(reg).getParticipant().getUsername()))) {
                        registrations.add(reg);
                    }
                }

                // Notify the adapter that the data set has changed
                regAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
                // Handle potential errors here
            }
        };

        // Get a reference to the registrations node in the Firebase database and add the ValueEventListener to it
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Registration");
        dbRef.addValueEventListener(postListener);

        // Set up an item click listener for the ListView items
        // When a registration is clicked, it is set as the selected registration and the adapter is notified of the change
        listViewRegistration.setOnItemClickListener((parent, view, position, id) -> {
            regAdapter.setSelectedPosition(position);
            selectedRegistration = registrations.get(position);
        });

        // Find the Update and Delete buttons in the layout
        Button btnUpdate = findViewById(R.id.btnRegistrationUpdate);
        Button btnDelete = findViewById(R.id.btnDeleteRegistration);

        // Disable the Update and Delete buttons if the user is not a cycling club
        if (!user.getRole().equals("cycling club")) {
            btnUpdate.setEnabled(false);
            btnDelete.setEnabled(false);
        }

        // Set up a click listener for the Update button
        // When the Update button is clicked, the selected registration is updated in the database and the adapter is notified of the change
        btnUpdate.setOnClickListener(view -> {
            for (Registration reg : registrations) {
                dbRef.child(reg.getKey()).setValue(reg);
            }
            regAdapter.notifyDataSetChanged();
        });

        // Set up a click listener for the Delete button
        // When the Delete button is clicked, the selected registration is deleted from the database
        btnDelete.setOnClickListener(view -> {
            if (selectedRegistration == null) {
                displayPopupMessage("Must select a registration to delete", view);
            } else {
                dbRef.child(selectedRegistration.getKey()).removeValue();
            }
        });
    }

    /**
     * Displays a popup message.
     *
     * @param message The message to be displayed.
     * @param anchorView The view to anchor the popup message to.
     */
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
}