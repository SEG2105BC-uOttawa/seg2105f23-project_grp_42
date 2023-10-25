package com.example.cyclingclub;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.*;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {

    private RadioButton cyclingClubRadio;
    private RadioButton participantRadio;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize UI elements
        cyclingClubRadio = findViewById(R.id.cyclingClubRole);
        participantRadio = findViewById(R.id.participantRole);
        firebaseAuth = FirebaseAuth.getInstance();

        Button signupButton = findViewById(R.id.btnFinalSignUp);

        signupButton.setOnClickListener(view -> {
            // Get the values from the input fields
            EditText emailEditText = findViewById(R.id.emailTextField);
            EditText usernameEditText = findViewById(R.id.usernameTextField);
            EditText passwordEditText = findViewById(R.id.passwordTextField);
            String enteredEmail = emailEditText.getText().toString();
            String enteredUsername = usernameEditText.getText().toString();
            String enteredPassword = passwordEditText.getText().toString();

            /* Check if one of the roles is selected */
            String role = cyclingClubRadio.isChecked() ? "cycling club" :
                    (participantRadio.isChecked() ? "participant" : "");

            if (role.isEmpty()) {
                /* Show an error popup message if no role is selected */
                displayPopupMessage("Please select a role (Cycling Club or Participant).", view);
            } else {
                /* Validate username and password */
                String validationMessage = validateInput(enteredEmail, enteredUsername, enteredPassword);

                if (validationMessage == null) {
                    /* Create the user account in Firebase Authentication */
                    try {
                        createFirebaseAccount(enteredEmail, enteredPassword, enteredUsername, role, view);
                    } catch (Exception e) {
                        Log.e("createFirebaseAccount", "Failed to create firebase account: " + e.getMessage());
                    }
                } else {
                    displayPopupMessage(validationMessage, view);
                }
            }
        });
    }

    /**
     * Create a Firebase user account using the provided email and password.
     * @param email The user's email.
     * @param password The user's password.
     * @param username The user's username.
     * @param role The user's role.
     * @param view The current view.
     */
    private void createFirebaseAccount(String email, String password, String username, String role, View view) throws Exception {
        byte[] salt = Utils.generateSalt();
        if (Utils.retHashedPassword(password, salt) != null) {
            String hashedPassword = Utils.retHashedPassword(password, salt);
            try {
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                if (firebaseUser != null) {
                                    String userID = firebaseUser.getUid();

                                    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("users").child(userID);
                                    User user = new User(email, username, role, hashedPassword, Base64.encodeToString(salt, Base64.NO_WRAP));
                                    dbRef.setValue(user);

                                    /* Redirect to the WelcomeScreen after creation */
                                    returnToWelcomeScreen(user);
                                }
                            } else {
                                /* Account creation failed */
                                Log.e("createFirebaseAccount", "Authentication failed.", task.getException());
                                displayPopupMessage("Account creation failed", view);
                            }
                        });
            } catch (Exception e) {
                Log.e("createFirebaseAccount", String.format("Unexpected error when creating account: %s", e.getMessage()));
            }
        }
    }

    /**
     * Display a popup message with the provided text.
     * @param message The message to display.
     * @param anchorView The view to anchor the popup to.
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

    /**
     * Validate the input fields for email, username, and password.
     * @param email The user's email.
     * @param username The user's username.
     * @param password The user's password.
     * @return A validation message if there's an issue, or null if everything is valid.
     */
    private String validateInput(String email, String username, String password) {
        InputValidator validator = InputValidator.getInstance();

        /* Validate email */
        if (!validator.isValidEmail(email)) { return "Email is not valid.";}

        /* Validate username */
        if (!validator.isValidUsername(username)) { return "Username must have at least 2 characters, no spaces, and no special characters."; }

        /* Validate password */
        if (!validator.isStrongPassword(password)) { return "Password must have at least 1 lowercase and uppercase letter, 1 digit, 1 special character, no spaces, and a minimum of 8 characters"; }

        return null;
    }

    /**
     * Navigate to the welcome screen with the user object.
     * @param user The user object to pass to the welcome screen.
     */
    private void returnToWelcomeScreen(User user) {
        Intent intent = new Intent(this, WelcomeScreen.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}