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

import androidx.core.content.ContextCompat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.util.Objects;

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
                User admin = new Administrator(HARDCODED_USERNAME, HARDCODED_PASSWORD);
                sendToWelcomeScreen(admin);
            } else {
                signInWithUsernameAndPassword(enteredUsername, enteredPassword, view);
            }
        });
    }

    /**
     * Sign in the user with the provided email and password.
     * @param username The username.
     * @param password The user's password.
     * @param view The current view.
     */
    private void signInWithUsernameAndPassword(String username, String password, View view) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("users");
        dbRef.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        User user = userSnapshot.getValue(User.class);
                        if (user != null) {
                            firebaseAuth.signInWithEmailAndPassword(user.getEmail(), password)
                                    .addOnCompleteListener(LoginActivity.this, task -> {
                                        if (task.isSuccessful()) {
                                            /* Sign in success, update UI with the signed-in user's information */
                                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                            if (firebaseUser != null) {
                                                Log.e("signInWithUsernameAndPassword", "Firebase User is not null.");
                                                /* User is signed in, we can now fetch additional user data from the database */
                                                getUserDataFromDatabase(firebaseUser.getUid(), password, view);
                                            }
                                        } else {
                                            /* If sign in fails, display a message to the user based on the specific error. */
                                            Log.e("signInWithEmailAndPassword", "Authentication failed.", task.getException());
                                            String errorMessage = Objects.requireNonNull(task.getException()).getMessage();

                                            if (errorMessage != null) {
                                                if (errorMessage.contains("INVALID_LOGIN_CREDENTIALS")) {
                                                    DynamicToast.makeError(LoginActivity.this, "Password is incorrect!").show();
                                                } else if (errorMessage.contains("USER_NOT_FOUND")) {
                                                    DynamicToast.makeError(LoginActivity.this, "User not found. Please register first.").show();
                                                } else if (errorMessage.contains("NETWORK_ERROR")) {
                                                    DynamicToast.makeError(LoginActivity.this, "Network error. Please check your internet connection.").show();
                                                } else if (errorMessage.contains("We have blocked all requests from this device due to unusual activity. Try again later.")) {
                                                    DynamicToast.makeError(LoginActivity.this, "Account temporarily disabled due to many failed login attempts. Try again later.").show();
                                                }
                                            }
                                        }
                                    });
                        }
                    }
                } else {
                    /* User data not found, display an error message */
                    displayPopupMessage("User data not found", view);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("signInWithUsernameAndPassword", "Database error: " + error.getMessage());
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
                    String storedHashedPassword = snapshot.child("password").getValue(String.class); /* Retrieve hashed password from the database */
                    if (salt != null && storedHashedPassword != null) {
                        /* Check if the password is in the database by hashing the entered password and comparing it to the stored hashed password */
                        if (checkPassword(enteredPassword, salt, storedHashedPassword)) {
                            /* Password matches, proceed to welcome screen */
                            User user = snapshot.getValue(User.class);
                            if (user != null) {
                                sendToWelcomeScreen(user);
                            }
                        }
                    } else {
                        /* If salt or hashedPassword is missing, handle the error accordingly */
                        Log.e("getUserDataFromDatabase", String.format("%s : %s", storedHashedPassword, salt));
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
            String hashedPassword = Utils.hashPasswordPBKDF2(enteredPassword, decodedSalt);
            Log.e("checkPassword", "Salt:" + salt + "\nHashed Password: " + hashedPassword);
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