package com.example.cyclingclub;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

/**
 * Represents the login activity where users can sign in.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String HARDCODED_USERNAME = "admin";
    private static final String HARDCODED_PASSWORD = "admin";
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        final EditText usernameEditText = findViewById(R.id.usernameTextField);
        final EditText passwordEditText = findViewById(R.id.passwordTextField);
        Button loginButton = findViewById(R.id.btnLogin);

        loginButton.setOnClickListener(view -> {
            String enteredUsername = usernameEditText.getText().toString();
            String enteredPassword = passwordEditText.getText().toString();

            /* If the credentials are the hardcoded values for testing purposes, redirect them to administrator control panel */
            if (enteredUsername.equals(HARDCODED_USERNAME) && enteredPassword.equals(HARDCODED_PASSWORD)) {
                User admin = new Administrator(HARDCODED_USERNAME);
                sendToWelcomeScreen(admin);
            } else { /* Sign them in like a regular user if not the hardcoded values */
                signInWithEmailAndPassword(enteredUsername, enteredPassword, view);
            }
        });
    }

    /**
     * Sign in the user with the provided email and password.
     * @param email The user's email.
     * @param password The user's password.
     * @param view The current view.
     */
    private void signInWithEmailAndPassword(String email, String password, View view) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        /* Sign in success, update UI with the signed-in user's information */
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            /* User is signed in, we can now fetch additional user data from the database */
                            getUserDataFromDatabase(user.getUid(), password, view);
                        }
                    } else {
                        /* If sign in fails display a message to the user. */
                        Log.e("signInWithEmailAndPassword", "Authentication failed.", task.getException());
                        displayPopupMessage("Authentication failed", view);
                    }
                });
    }

    /**
     * Retrieve user data from the database based on the user's UID.
     * @param uid The user's UID.
     * @param enteredPassword The entered password.
     * @param view The current view.
     */
    private void getUserDataFromDatabase(String uid, String enteredPassword, View view) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("users").child(uid);

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String salt = snapshot.child("salt").getValue(String.class); /* Retrieve salt from the database */
                    String storedHashedPassword = snapshot.child("hashedPassword").getValue(String.class); /* Retrieve hashed password from the database */
                    if (salt != null && storedHashedPassword != null) {
                        /* Check if the password is in the database by hashing the entered password and comparing it to the stored hashed password */
                        if (checkPassword(enteredPassword, salt, storedHashedPassword)) {
                            /* Password matches, proceed to welcome screen */
                            User user = snapshot.getValue(User.class);
                            assert user != null;
                            sendToWelcomeScreen(user);
                        } else {
                            /* Password doesn't match, display an error message */
                            displayPopupMessage("Password is incorrect", view);
                        }
                    } else {
                        /* If salt or hashedPassword is missing, handle the error accordingly */
                        displayPopupMessage("Password data not found", view);
                    }
                } else {
                    /* User data not found, display an error message */
                    displayPopupMessage("User data not found", view);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("getUserDataFromDatabase", "Database error: " + error.getMessage());
            }
        });
    }

    /**
     * Check if the entered password matches the stored hashed password.
     * @param enteredPassword The entered password.
     * @param salt The salt used for hashing.
     * @param storedHashedPassword The stored hashed password.
     * @return True if the password matches, false otherwise.
     */
    private boolean checkPassword(String enteredPassword, String salt, String storedHashedPassword) {
        try {
            byte[] decodedSalt = Base64.decode(salt, Base64.NO_WRAP);
            /* Hash the entered password with the retrieved salt */
            String hashedPassword = SecurityUtils.hashPasswordPBKDF2(enteredPassword, decodedSalt);
            return hashedPassword.equals(storedHashedPassword);
        } catch (Exception e) {
            Log.e("checkPassword", String.format("Unexpected error when checking password: %s", e.getMessage()));
            return false;
        }
    }

    /**
     * Navigate to the welcome screen with the user object.
     * @param user The user object to pass to the welcome screen.
     */
    private void sendToWelcomeScreen(User user) {
        Intent intent = new Intent(getApplicationContext(), WelcomeScreen.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    /**
     * Display a popup message to the user.
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
}