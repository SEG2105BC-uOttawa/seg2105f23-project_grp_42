package com.example.cyclingclub;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class WelcomeScreen extends AppCompatActivity {

	private HomeFragment homeFragment;
	private ProfileFragment profileFragment;
	private SettingsFragment settingsFragment;
	private boolean recreateActivity = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        User user = (User) getIntent().getSerializableExtra("user");

		BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
		Animation btnAnimation = AnimationUtils.loadAnimation(this, R.anim.button_click);

		initFragments();

		if (user != null) {
			Bundle bundle = new Bundle(); /* Create a Bundle to pass user information */
			bundle.putSerializable("user", user);
			homeFragment.setArguments(bundle);
		}

		if (savedInstanceState == null) {
			setFragment(homeFragment);
			bottomNavigationView.setSelectedItemId(R.id.homeItem);
			bottomNavigationView.getMenu().findItem(R.id.homeItem).setIcon(R.drawable.baseline_home);
		} else {
			Fragment activeFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
			if (activeFragment == null) {
				setFragment(homeFragment);
				bottomNavigationView.setSelectedItemId(R.id.homeItem);
				bottomNavigationView.getMenu().findItem(R.id.homeItem).setIcon(R.drawable.baseline_home);
			}
		}

		bottomNavigationView.setOnItemSelectedListener(item -> {

			/* Reset icons to their normal state for all items */
			resetMenuIcons(bottomNavigationView);

			/* Apply the animation to the selected menu item */
			View view = bottomNavigationView.findViewById(item.getItemId());
			if (view != null) {
				view.startAnimation(btnAnimation);
			}

			/* Set the selected item's icon to the filled version */
			item.setIcon(getSelectedIconResource(item.getItemId()));

			if (item.getItemId() == R.id.homeItem) {
				if (user != null) {
					Bundle bundle = new Bundle(); /* Create a Bundle to pass user information */
					bundle.putSerializable("user", user);

					homeFragment.setArguments(bundle);
				}
				if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) != homeFragment) {
					setFragment(homeFragment);
				}
				return true;
			} else if (item.getItemId() == R.id.profileItem) {
				Bundle bundle = new Bundle(); /* Create a Bundle to pass user information */
				bundle.putSerializable("user", user);
				profileFragment.setArguments(bundle);
				setFragment(profileFragment);
				return true;
			} else if (item.getItemId() == R.id.settingsItem) {
				setFragment(settingsFragment);
				recreateActivity = true;
				return true;
			}

			return false;
		});
	}

	private void initFragments() {
		/* Initialize the fragments */
		homeFragment = new HomeFragment();
		profileFragment = new ProfileFragment();
		settingsFragment = new SettingsFragment();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (recreateActivity) {
			recreateActivity = false;
			recreate();
		}
	}

	private void setFragment(Fragment fragment) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace(R.id.fragment_container, fragment);
		transaction.commit();
	}

	private void resetMenuIcons(BottomNavigationView bottomNavigationView) {
		bottomNavigationView.getMenu().findItem(R.id.homeItem).setIcon(R.drawable.baseline_home_outline);
		bottomNavigationView.getMenu().findItem(R.id.profileItem).setIcon(R.drawable.baseline_person_outline_24);
		bottomNavigationView.getMenu().findItem(R.id.settingsItem).setIcon(R.drawable.baseline_settings_outline);
	}

	private int getSelectedIconResource(int itemID) {
		int iconResource = 0; /* Default value if the resource is not found */

		if (itemID == R.id.homeItem) {
			iconResource = R.drawable.baseline_home;
		} else if (itemID == R.id.profileItem) {
			iconResource = R.drawable.baseline_person_24;
		} else if (itemID == R.id.settingsItem) {
			iconResource = R.drawable.baseline_settings;
		}

		return iconResource;
	}
}