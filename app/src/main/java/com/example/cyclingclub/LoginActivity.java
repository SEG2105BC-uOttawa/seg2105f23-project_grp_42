package com.example.cyclingclub;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.view.ViewGroup.LayoutParams;
import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText usernameEditText = findViewById(R.id.textUsernameField);
        final EditText passwordEditText = findViewById(R.id.textPasswordField);
        Button loginButton = findViewById(R.id.btnLogin);

        loginButton.setOnClickListener(view -> {
            String enteredUsername = usernameEditText.getText().toString();
            String enteredPassword = passwordEditText.getText().toString();

            if (enteredUsername.equals("admin") && enteredPassword.equals("admin")) {
                sendToWelcomeScreen();
            } else {
                /* Display a popup message for an invalid login */
                displayPopupMessage(view);
            }
        });
    }

    private void sendToWelcomeScreen() {
        User user = new User("admin", "admin", "admin");
        Intent intent = new Intent(getApplicationContext(), WelcomeScreen.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    private void displayPopupMessage(View anchorView) {
        LinearLayout layout = new LinearLayout(this);
        layout.setLayoutParams(new LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));

        layout.setOrientation(LinearLayout.VERTICAL);
        TextView textView = new TextView(this);
        textView.setText(R.string.invalid_username_or_password);

        PopupWindow popupWindow = new PopupWindow(layout, LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setContentView(textView);
        popupWindow.showAsDropDown(anchorView, 10, 0);
    }
}