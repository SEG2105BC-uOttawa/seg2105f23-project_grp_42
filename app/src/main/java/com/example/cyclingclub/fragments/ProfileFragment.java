package com.example.cyclingclub.fragments;

import android.graphics.Color;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.cyclingclub.R;
import com.example.cyclingclub.utils.Utils;
import com.example.cyclingclub.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

	private EditText editName;
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
		editName = view.findViewById(R.id.editName);
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
			String name = editName.getText().toString().trim();
			String contact = editContact.getText().toString().trim();
			String region = editRegion.getText().toString().trim();
			String phoneNumber = editPhoneNumber.getText().toString().trim();
			String mediaLink = editMediaLink.getText().toString().trim();

			if (phoneNumber.isEmpty()) {
				requireActivity().runOnUiThread(() -> DynamicToast.makeError(requireContext(), "Phone number must be entered.").show());
				return;
			} else if (mediaLink.isEmpty()) {
				requireActivity().runOnUiThread(() -> DynamicToast.makeError(requireContext(), "Social media link must be entered.").show());
				return;
			}

			String message = validateInput(name, contact, region, phoneNumber, mediaLink);
			if (!message.isEmpty()) {
				requireActivity().runOnUiThread(() -> DynamicToast.makeError(requireContext(), message).show());
			} else {
				// Create a DatabaseReference
				DatabaseReference dRef = FirebaseDatabase.getInstance().getReference("profiles");

				// Create a HashMap with the profile information
				HashMap<String, Object> profileMap = new HashMap<>();
				profileMap.put("name", user.getUsername());
				profileMap.put("contact", contact);
				profileMap.put("region", region);
				profileMap.put("phoneNumber", phoneNumber);
				profileMap.put("mediaLink", mediaLink);
				profileMap.put("role", user.getRole());

				// Use setValue method to create the node if it doesn't exist, or overwrite it if it does exist
				dRef.child(user.getUsername()).setValue(profileMap)
						.addOnSuccessListener(aVoid -> DynamicToast.make(requireContext(), "Updated profile information.", Color.WHITE, ContextCompat.getColor(requireContext(), R.color.primary_color)).show())
						.addOnFailureListener(e -> DynamicToast.make(requireContext(), "Failed to update profile information.", Color.WHITE, ContextCompat.getColor(requireContext(), R.color.primary_color)).show());

				// If the user's role is a cycling club, add the profile to 'clubProfiles'
				if ("cycling club".equals(user.getRole())) {
					DatabaseReference clubProfilesRef = FirebaseDatabase.getInstance().getReference("clubProfiles");
					clubProfilesRef.child(user.getUsername()).setValue(profileMap)
							.addOnSuccessListener(aVoid -> DynamicToast.make(requireContext(), "Added profile to 'clubProfiles'.", Color.WHITE, ContextCompat.getColor(requireContext(), R.color.primary_color)).show())
							.addOnFailureListener(e -> DynamicToast.make(requireContext(), "Failed to add profile to 'clubProfiles'.", Color.WHITE, ContextCompat.getColor(requireContext(), R.color.primary_color)).show());
				}
			}
		});
	}

	/**
	 * Loads the cycling club data from the database.
	 *
	 * @param user The current user.
	 */
	private void loadData(User user) {
		DatabaseReference dRef = FirebaseDatabase.getInstance().getReference("profiles");

		// Get the child node with the username of the user
		DatabaseReference userRef = dRef.child(user.getUsername());

		userRef.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
				// Set the data to the EditText fields
				editName.setText(user.getUsername());
				editContact.setText(dataSnapshot.child("contact").getValue(String.class));
				editRegion.setText(dataSnapshot.child("region").getValue(String.class));
				editPhoneNumber.setText(dataSnapshot.child("phoneNumber").getValue(String.class));
				editMediaLink.setText(dataSnapshot.child("mediaLink").getValue(String.class));
			}

			@Override
			public void onCancelled(@NotNull DatabaseError databaseError) {
				// Handle error
			}
		});
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