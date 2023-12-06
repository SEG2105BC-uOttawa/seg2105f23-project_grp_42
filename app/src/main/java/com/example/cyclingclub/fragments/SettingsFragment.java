package com.example.cyclingclub.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import com.example.cyclingclub.R;

public class SettingsFragment extends PreferenceFragmentCompat {
	private boolean themeChanged = false;

	@Override
	public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
		setPreferencesFromResource(R.xml.settings_preferences, rootKey);

		Preference themePreference = findPreference("appearance_preference");
		if (themePreference != null) {
			themePreference.setOnPreferenceChangeListener((preference, newValue) -> {
				String selectedTheme = (String) newValue;
				int themeMode = getThemeMode(selectedTheme);

				setAppTheme(themeMode);
				saveSelectedTheme(selectedTheme);

				themeChanged = true;

				return true;
			});
		}
	}

	@Override
	public void onDetach() {
		if (themeChanged) {
			themeChanged = false;
			requireActivity().recreate();
		}
		super.onDetach();
	}

	private void setAppTheme(int mode) {
		AppCompatDelegate.setDefaultNightMode(mode);
	}

	private void saveSelectedTheme(String theme) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString("appearance_mode", theme);
		editor.apply();
	}

	public static int getThemeMode(String selectedTheme) {
		switch (selectedTheme) {
			case "light":
				return AppCompatDelegate.MODE_NIGHT_NO;
			case "dark":
				return AppCompatDelegate.MODE_NIGHT_YES;
			default:
				return AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
		}
	}
}