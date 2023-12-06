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
import com.example.cyclingclub.utils.SearchForClub;
import com.example.cyclingclub.activities.RegistrationManagementActivity;
import com.example.cyclingclub.activities.EventTypeManagementActivity;
import com.example.cyclingclub.User;
import com.example.cyclingclub.activities.UserAccountManagementActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	public HomeFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment HomeFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static HomeFragment newInstance(String param1, String param2) {
		HomeFragment fragment = new HomeFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
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

			Button btnEventType = (Button) rootView.findViewById(R.id.btnEventType);
			Button btnUsers = (Button) rootView.findViewById(R.id.btnUsers);
			Button btnEvents = (Button) rootView.findViewById(R.id.btnEvent);
			Button btnRegistration = (Button) rootView.findViewById(R.id.btnRegistration);
			Button btnToClubFunction = (Button) rootView.findViewById(R.id.btnToClubFunction);


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
                //Intent intent = new Intent(getContext(), SearchForClub.class);
                Intent intent = new Intent(getContext(), RegistrationManagementActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            });

			btnToClubFunction.setOnClickListener(view -> {
                Intent intent = new Intent(getContext(), SearchForClub.class);
                //Intent intent = new Intent(getContext(), RegistrationManagementActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            });
		}




		return rootView;
	}
}