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
import com.pranavpandey.android.dynamic.toasts.DynamicToast;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

	private EditText editClubName;
	private EditText editContact;
	private EditText editRegion;
	private EditText editPhoneNumber;
	private EditText editMediaLink;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_profile, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// Initialize the views
		editClubName = view.findViewById(R.id.editClubName);
		editContact = view.findViewById(R.id.editContact);
		editRegion = view.findViewById(R.id.editRegion);
		editPhoneNumber = view.findViewById(R.id.editPhoneNumber);
		editMediaLink = view.findViewById(R.id.editMediaLink);
		Button btnUpdate = view.findViewById(R.id.btnProfileUpdate);;

		// Retrieve user information from arguments
		Bundle bundle = getArguments();
		User user = (User) bundle.getSerializable("user");

		// Load the data
		loadData(user);

		btnUpdate.setOnClickListener(v -> {
			String clubName = editClubName.getText().toString().trim();
			String contact = editContact.getText().toString().trim();
			String region = editRegion.getText().toString().trim();
			String phoneNumber = editPhoneNumber.getText().toString().trim();
			String mediaLink = editMediaLink.getText().toString().trim();

			String message = validateInput(clubName, contact, region, phoneNumber, mediaLink);
			if (!message.isEmpty()) {
				displayPopupMessage(message, getView());
			} else {
				// Create a DatabaseReference
				DatabaseReference dRef = FirebaseDatabase.getInstance().getReference("clubProfiles");

				// Create a HashMap with the profile information
				HashMap<String, Object> profileMap = new HashMap<>();
				profileMap.put("clubName", clubName);
				profileMap.put("contact", contact);
				profileMap.put("region", region);
				profileMap.put("phoneNumber", phoneNumber);
				profileMap.put("mediaLink", mediaLink);

				// Use setValue method to create the node if it doesn't exist, or overwrite it if it does exist
				dRef.child(user.getUsername()).setValue(profileMap)
						.addOnSuccessListener(aVoid -> DynamicToast.make(requireContext(), "Updated profile information.", Color.WHITE, ContextCompat.getColor(requireContext(), R.color.primary_color)).show())
						.addOnFailureListener(e -> DynamicToast.make(requireContext(), "Failed to update profile information.", Color.WHITE, ContextCompat.getColor(requireContext(), R.color.primary_color)).show());
			}
		});
	}

	/**
	 * Loads the cycling club data from the database.
	 *
	 * @param user The current user.
	 */
	private void loadData(User user) {
		DatabaseReference dRef = FirebaseDatabase.getInstance().getReference("clubProfiles");

		dRef.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
				for (DataSnapshot ds : dataSnapshot.getChildren()) {
					if (Objects.equals(ds.getKey(), user.getUsername())) {
						// Set the data to the EditText fields
						editClubName.setText(ds.child("clubName").getValue(String.class));
						editContact.setText(ds.child("contact").getValue(String.class));
						editRegion.setText(ds.child("region").getValue(String.class));
						editPhoneNumber.setText(ds.child("phoneNumber").getValue(String.class));
						editMediaLink.setText(ds.child("mediaLink").getValue(String.class));
					}
				}
			}

			@Override
			public void onCancelled(@NotNull DatabaseError databaseError) {
				// Handle error
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
		Utils utils = Utils.getInstance();

		String message="";

		/* Validate event type */
		if (!utils.isValidString(clubName)) { message= message+ "Club name must start with letter and can not be blank,";}
		if (!utils.isValidString(contact)) { message= message+ "Contact must start with letter and can not be blank,";}
		if (!utils.isValidString(region)) { message= message+ "Region must start with letter and can not be blank,";}
		if (!utils.isValidPhoneNumber(phoneNumber)) { message= message+ "10 digit phone number required,";}
		if (!utils.isValidSocialMediaLink(mediaLink)) { message= message+ "Invalid social media link.";}

		return message;
	}

}