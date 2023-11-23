package com.example.cyclingclub;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.Query;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	public ProfileFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment ProfileFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static ProfileFragment newInstance(String param1, String param2) {
		ProfileFragment fragment = new ProfileFragment();
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
		// Inflate the layout for this fragment
		View rootView  = inflater.inflate(R.layout.fragment_profile, container, false);

		/* Retrieve user information from arguments */
		Bundle bundle = getArguments();
		if (bundle != null) {
			User user = (User) bundle.getSerializable("user");

			if (user != null) {
				TextView showTitle = rootView.findViewById(R.id.profileTitle);
				String username = user.getUsername();
				showTitle.setText(String.format("Cycling Club Profile: %s", username));

			}
		}
		DatabaseReference dRef=FirebaseDatabase.getInstance().getReference("ClubProfile");

		/*
		Query query = dRef.orderByChild("category").equalTo("Sports");

		query.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
					// Access the data for the found club(s)
					String clubName = snapshot.child("name").getValue(String.class);
					String clubCategory = snapshot.child("category").getValue(String.class);

					// Now 'clubName' and 'clubCategory' contain the data for the found club(s)
					Log.d("FirebaseData", "Club Name: " + clubName + ", Category: " + clubCategory);
				}
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {
				// Handle error
			}
		});


		 */

		final Button btnUpdate = (Button) rootView.findViewById(R.id.btnProfileUpdate);
		btnUpdate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				User user = (User) bundle.getSerializable("user");


				CyclingClub club=new CyclingClub();
				club.setUserEmail(user.getEmail());
				TextView tv1=rootView.findViewById(R.id.editClubName);
				TextView tv2=rootView.findViewById(R.id.editContact);
				club.setClubName(tv1.getText().toString().trim());
				club.setMainContact(tv2.getText().toString().trim());
				String key=dRef.push().getKey();
				dRef.child(key).setValue(club);

			}
		});
		return rootView;

	}





}