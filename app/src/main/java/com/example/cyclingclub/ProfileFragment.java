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
	private boolean clubProfileFound;
	private CyclingClub cyclingClub;

	//private TextView clubName,contact,region,phoneNumber,mediaLink;

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
		clubProfileFound=false;

		//Bundle bundle = getArguments();
		//User user = (User) bundle.getSerializable("user");;


	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View rootView  = inflater.inflate(R.layout.fragment_profile, container, false);



		TextView showTitle = rootView.findViewById(R.id.profileTitle);

		/* Retrieve user information from arguments */
		Bundle bundle = getArguments();
		User user = (User) bundle.getSerializable("user");
		String username = user.getUsername();
		showTitle.setText(String.format("Cycling Club Profile: %s", username));

		//Retrieve club profile is exist;


		loadData(user);


		final Button btnUpdate = (Button) rootView.findViewById(R.id.btnProfileUpdate);

		btnUpdate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				//User user = (User) bundle.getSerializable("user");
				DatabaseReference dRef=FirebaseDatabase.getInstance().getReference("ClubProfile");
				String key;
				if(!clubProfileFound) {
					cyclingClub=new CyclingClub();
					key=dRef.push().getKey();
					cyclingClub.setKey(key);
				}
				else {
					key=cyclingClub.getKey();
				}

				//cyclingClub.setKey(key);
				cyclingClub.setUser(user);

				TextView clubName=getView().findViewById(R.id.editClubName);
				TextView contact=getView().findViewById(R.id.editContact);
				TextView region=getView().findViewById(R.id.editClubRegion);
				TextView phoneNumber=getView().findViewById(R.id.editPhoneNumber);
				TextView mediaLink=getView().findViewById(R.id.editMediaLink);
				cyclingClub.setClubName(clubName.getText().toString().trim());
				cyclingClub.setMainContact(contact.getText().toString().trim());
				cyclingClub.setRegion(region.getText().toString().trim());
				cyclingClub.setPhoneNumber(phoneNumber.getText().toString().trim());
				cyclingClub.setSocialMediaLink(mediaLink.getText().toString().trim());


				dRef.child(key).setValue(cyclingClub);

			}
		});
		return rootView;

	}

	private void loadData(User user) {
		DatabaseReference dRef=FirebaseDatabase.getInstance().getReference("ClubProfile");

		dRef.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
					CyclingClub club = snapshot.getValue(CyclingClub.class);

					// Check if the category property matches the desired category
					if (club != null && club.getUser().getUsername().equals(user.getUsername()) &&
							club.getUser().getEmail().equals(user.getEmail())) {
						clubProfileFound=true;
						cyclingClub = club;
						//String clubKey = snapshot.getKey();
						// Now 'clubKey' and 'club' contain the data for the found club(s)
						//Log.d("FirebaseData", "Club Key: " + clubKey + ", Name: " + club.getClubName() );
					}
				}

				TextView clubName=getView().findViewById(R.id.editClubName);
				TextView contact=getView().findViewById(R.id.editContact);
				TextView region=getView().findViewById(R.id.editClubRegion);
				TextView phoneNumber=getView().findViewById(R.id.editPhoneNumber);
				TextView mediaLink=getView().findViewById(R.id.editMediaLink);

				if(clubProfileFound) {
					//TextView clubName=getView().findViewById(R.id.editClubName);
					clubName.setText(cyclingClub.getClubName());
					contact.setText(cyclingClub.getMainContact());
					region.setText(cyclingClub.getRegion());
					phoneNumber.setText(cyclingClub.getPhoneNumber());
					mediaLink.setText(cyclingClub.getSocialMediaLink());
				}
				else{
					clubName.setText("");
					contact.setText("");
					region.setText("");
					phoneNumber.setText("");
					mediaLink.setText("");
				}


			}

			@Override
			public void onCancelled(DatabaseError databaseError) {
				// Handle potential errors here.
			}
		});

	}




}