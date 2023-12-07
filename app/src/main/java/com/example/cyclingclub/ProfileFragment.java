package com.example.cyclingclub;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.Query;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

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
		if (user.getRole().equals("Administrator") || user.getRole().equals("participant")) {
			btnUpdate.setEnabled(false);
		}

		btnUpdate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				//User user = (User) bundle.getSerializable("user");

				EditText clubNameEdit =(EditText) getView().findViewById(R.id.editClubName);
				EditText contactEdit =(EditText) getView().findViewById(R.id.editContact);
				EditText regionEdit =(EditText) getView().findViewById(R.id.editClubRegion);
				EditText phoneNumberEdit =(EditText) getView().findViewById(R.id.editPhoneNumber);
				EditText mediaLinkEdit =(EditText) getView().findViewById(R.id.editMediaLink);
				//ImageView pfpEdit = (ImageView) getView().findViewById(R.id.logoView);

				String clubName= clubNameEdit.getText().toString().trim();
				String contact= contactEdit.getText().toString().trim();
				String region= regionEdit.getText().toString().trim();
				String phoneNumber= phoneNumberEdit.getText().toString().trim();
				String mediaLink= mediaLinkEdit.getText().toString().trim();

				String message=validateInput(clubName,contact,region,phoneNumber,mediaLink);
				if (!message.equals("")){
					displayPopupMessage(message, getView());
				}else{

					DatabaseReference dRef = FirebaseDatabase.getInstance().getReference("ClubProfile");
					String key;
					if (!clubProfileFound) {
						key = dRef.push().getKey();
						cyclingClub = new CyclingClub();
						cyclingClub.setKey(key);

					} else {
						key = cyclingClub.getKey();
					}

					//cyclingClub.setKey(key);
					cyclingClub.setUser(user);
					cyclingClub.setUsername(user.getUsername());


					cyclingClub.setClubName(clubName);
					cyclingClub.setMainContact(contact);
					cyclingClub.setRegion(region);
					cyclingClub.setPhoneNumber(phoneNumber);
					cyclingClub.setSocialMediaLink(mediaLink);


					dRef.child(key).setValue(cyclingClub);
					clubProfileFound=true;
				}

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
					if (club != null ){
						if (club.getUser().getUsername().equals(user.getUsername()) ) {
							clubProfileFound = true;
							cyclingClub = club;
							break;
						}
					}
				}

				EditText clubNameEdit =(EditText) getView().findViewById(R.id.editClubName);
				EditText contactEdit =(EditText) getView().findViewById(R.id.editContact);
				EditText regionEdit =(EditText) getView().findViewById(R.id.editClubRegion);
				EditText phoneNumberEdit =(EditText) getView().findViewById(R.id.editPhoneNumber);
				EditText mediaLinkEdit =(EditText) getView().findViewById(R.id.editMediaLink);

				if(clubProfileFound) {
					//TextView clubName=getView().findViewById(R.id.editClubName);
					clubNameEdit.setText(cyclingClub.getClubName());
					contactEdit.setText(cyclingClub.getMainContact());
					regionEdit.setText(cyclingClub.getRegion());
					phoneNumberEdit.setText(cyclingClub.getPhoneNumber());
					mediaLinkEdit.setText(cyclingClub.getSocialMediaLink());
				}
				else{
					//cyclingClub = new CyclingClub();
					clubNameEdit.setText("");
					contactEdit.setText("");
					regionEdit.setText("");
					phoneNumberEdit.setText("");
					mediaLinkEdit.setText("");
				}


			}

			@Override
			public void onCancelled(DatabaseError databaseError) {
				// Handle potential errors here.
			}
		});

	}


	private void displayPopupMessage(String message, View anchorView) {
		LinearLayout layout = new LinearLayout(getContext());
		layout.setLayoutParams(new ViewGroup.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));

		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setGravity(Gravity.CENTER);
		TextView textView = new TextView(getContext());
		textView.setText(message);
		textView.setTextColor(Color.RED);

		PopupWindow popupWindow = new PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
		popupWindow.setContentView(textView);
		popupWindow.showAsDropDown(anchorView, 10, 0);
	}


	private String validateInput(String clubName,String contact,String region,String phoneNumber,String mediaLink ) {
		InputValidator validator = InputValidator.getInstance();

		String message="";

		/* Validate event type */
		if (!validator.isValidString(clubName)) { message= message+ "Club name must start with letter and can not be blank,";}
		if (!validator.isValidString(contact)) { message= message+ "Contact must start with letter and can not be blank,";}
		if (!validator.isValidString(region)) { message= message+ "Region must start with letter and can not be blank,";}
		if (!validator.isValidPhoneNumber(phoneNumber)) { message= message+ "10 digit phone number required,";}
		if (!validator.isValidSocialMediaLink(mediaLink)) { message= message+ "Invalid social media link.";}

		return message;
	}


}