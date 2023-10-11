package com.example.cyclingclub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    private RadioButton cyclingClubRadio;
    private RadioButton participantRadio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize UI elements
        cyclingClubRadio = findViewById(R.id.cyclingClub);
        participantRadio = findViewById(R.id.participant);

        Button signupButton = findViewById(R.id.button2);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the values from the input fields
                EditText usernameEditText = findViewById(R.id.editTextText3);
                EditText passwordEditText = findViewById(R.id.editTextText4);
                String enteredUsername = usernameEditText.getText().toString();
                String enteredPassword = passwordEditText.getText().toString();

                // Check which role is selected
                String role = "";
                if (cyclingClubRadio.isChecked()) {
                    role = "Cycling Club";
                } else if (participantRadio.isChecked()) {
                    role = "Participant";
                }

                // Save the credentials in SharedPreferences
                saveCredentials(enteredUsername, enteredPassword, role);

                // Return to the MainActivity
                returnToMainActivity();
            }
        });
    }

    private void saveCredentials(String username, String password, String role) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("username", username);
        editor.putString("password", password);
        editor.putString("role", role);

        editor.apply();
    }

    private void returnToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
