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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class SignupActivity extends AppCompatActivity {

    private RadioButton cyclingClubRadio;
    private RadioButton participantRadio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize UI elements
        cyclingClubRadio = findViewById(R.id.cyclingClubRole);
        participantRadio = findViewById(R.id.participantRole);

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
            String role = cyclingClubRadio.isChecked() ? "cycling_club" :
                    (participantRadio.isChecked() ? "participant" : "");

            if (role.isEmpty()) {
                /* Show an error popup message if no role is selected */
                displayPopupMessage("Please select a role (Cycling Club or Participant).", view);
            } else {
                /* Validate username and password */
                String validationMessage = validateInput(enteredEmail, enteredUsername, enteredPassword);

                if (validationMessage == null) {
                    /* Save the credentials to Firebase and return to the MainActivity */
                    saveCredentials(enteredEmail, enteredUsername, enteredPassword, role);
                } else {
                    displayPopupMessage(validationMessage, view);
                }
            }
        });
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

    private void saveCredentials(String email, String username, String password, String role) {
        try {
            /* Generate secure salt */
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);

            /* Hash the password with PBKDF2 */
            String hashedPassword = hashPasswordPBKDF2(password, salt);

            /* Save the salt and hashed password to Firebase */
            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("users");
            String userID = dbRef.push().getKey(); /* Create unique key for the user */

            if (userID != null) {
                /* Create User object with the salt and hashed password */
                User user = new User(email, username, hashedPassword, role, Base64.encodeToString(salt, Base64.NO_WRAP));
                dbRef.child(userID).setValue(user); /* Push the user data to Firebase, including unique user ID */
                returnToWelcomeScreen(user);
            } else {
                Log.e("saveCredentials", "Failed to generate a unique user ID");
            }

        } catch (NoSuchAlgorithmException e) {
            Log.e("saveCredentials", "Algorithm not available: " + e.getMessage());
        } catch (InvalidKeySpecException e) {
            Log.e("saveCredentials", "Invalid key specification: " + e.getMessage());
        } catch (Exception e) {
            Log.e("saveCredentials", "Unexpected error: " + e.getMessage());
        }
    }

    private String hashPasswordPBKDF2(String password, byte[] salt) throws Exception {
        int iterations = 10000;
        int keyLength = 256; /* Key length in bits */

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] hash = factory.generateSecret(spec).getEncoded();
        return Base64.encodeToString(hash, Base64.NO_WRAP);
    }

    private void returnToWelcomeScreen(User user) {
        Intent intent = new Intent(this, WelcomeScreen.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}
