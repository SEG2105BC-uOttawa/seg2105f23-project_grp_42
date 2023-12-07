package com.example.cyclingclub.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.cyclingclub.activities.EventManagementActivity;
import com.example.cyclingclub.R;
import com.example.cyclingclub.activities.SearchForClub;
import com.example.cyclingclub.activities.RegistrationManagementActivity;
import com.example.cyclingclub.activities.EventTypeManagementActivity;
import com.example.cyclingclub.User;
import com.example.cyclingclub.activities.UserAccountManagementActivity;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

	public HomeFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_home, container, false);

		/* Retrieve user information from arguments */
		Bundle bundle = getArguments();
		if (bundle == null) {return rootView;}

		User user = (User) bundle.getSerializable("user");
		if (user != null) {
			TextView showUsername = rootView.findViewById(R.id.showUsername);
			TextView accountType = rootView.findViewById(R.id.accountType);
			String username = user.getUsername();

			showUsername.setText(String.format("Username: %s", username));
			accountType.setText(String.format("You are logged in as %s", user.getRole()));

			Button btnEventType = rootView.findViewById(R.id.btnEventType);
			Button btnUsers = rootView.findViewById(R.id.btnUsers);
			Button btnEvents = rootView.findViewById(R.id.btnEvent);
			Button btnRegistration = rootView.findViewById(R.id.btnRegistration);
			Button btnToClubFunction = rootView.findViewById(R.id.btnToClubFunction);


			if (user.getRole().equals("Administrator")) {
				btnRegistration.setEnabled(false);
			}
			if (user.getRole().equals("cycling club") || user.getRole().equals("participant")) {
				btnEventType.setEnabled(false);
				btnUsers.setEnabled(false);
			}

			btnEvents.setOnClickListener(view -> {
                Intent intent = new Intent(getContext(), EventManagementActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            });
			btnEventType.setOnClickListener(view -> {
                Intent intent = new Intent(getContext(), EventTypeManagementActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            });
			btnUsers.setOnClickListener(view -> {
                Intent intent = new Intent(getContext(), UserAccountManagementActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            });
			btnRegistration.setOnClickListener(view -> {
                Intent intent = new Intent(getContext(), RegistrationManagementActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            });

			btnToClubFunction.setOnClickListener(view -> {
                Intent intent = new Intent(getContext(), SearchForClub.class);
                intent.putExtra("user", user);
                startActivity(intent);
            });
		}




		return rootView;
	}
}