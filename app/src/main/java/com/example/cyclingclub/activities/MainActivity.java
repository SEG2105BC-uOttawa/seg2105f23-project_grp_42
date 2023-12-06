package com.example.cyclingclub.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;
import com.example.cyclingclub.R;
import com.example.cyclingclub.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Load the user's selected theme from SharedPreferences */
        String savedTheme = loadSelectedTheme();

        /* Apply the selected theme to the entire app */
        int themeMode = SettingsFragment.getThemeMode(savedTheme);
        AppCompatDelegate.setDefaultNightMode(themeMode);

        setContentView(R.layout.activity_main);
    }

    private String loadSelectedTheme() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getString("appearance_mode", "system");
    }

    public void onClickLogin(View view) {
        //Application Context and Activity
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity (intent);
    }

    public void onClickSignup(View view) {
        //Application Context and Activity
        Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
        startActivity(intent);
    }
}