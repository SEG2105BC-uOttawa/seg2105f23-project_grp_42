package com.example.cyclingclub;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class WelcomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        User user = (User) getIntent().getSerializableExtra("user");

        TextView showUsername = findViewById(R.id.showUsername);
        TextView accountType = findViewById(R.id.accountType);

        if (user != null) {
            String username = user.getUsername();
            showUsername.setText(String.format("Username: %s", username));
            accountType.setText(String.format("You are logged in as %s", user.getRole()));
        }
    }
}