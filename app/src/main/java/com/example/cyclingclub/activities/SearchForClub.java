package com.example.cyclingclub.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cyclingclub.CyclingClub;
import com.example.cyclingclub.Event;
import com.example.cyclingclub.User;
import com.example.cyclingclub.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SearchForClub extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.cyclingclub.R.layout.activity_search_for_club);

        user = (User) getIntent().getSerializableExtra("user");

        List<CyclingClub> clubs = new ArrayList<>();

        ArrayAdapter<CyclingClub> adapter = new ArrayAdapter<CyclingClub>(
                this,
                android.R.layout.simple_list_item_2, // Built-in layout for a two-line list item
                android.R.id.text1, // ID of the TextView for the name
                clubs
        ) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Customize the appearance of the list item
                View view = super.getView(position, convertView, parent);

                // Get the Person object for this position
                CyclingClub club = clubs.get(position);

                // Find the TextViews in the layout
                TextView nameTextView = view.findViewById(android.R.id.text1);
                TextView ageTextView = view.findViewById(android.R.id.text2);

                // Set the name and age in the TextViews
                nameTextView.setText(club.getClubName());
                ageTextView.setText(club.getPhoneNumber());

                return view;
            }
        };

        ListView listView = findViewById(R.id.listViewClubs);
        listView.setAdapter(adapter);

        DatabaseReference dRef = FirebaseDatabase.getInstance().getReference("clubProfiles");

        dRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                clubs.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CyclingClub club = snapshot.getValue(CyclingClub.class);
                    clubs.add(club);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle potential errors here.
            }
        });

        Button btnSearchClub = findViewById(R.id.btnSearchClub);
        AdapterView.OnItemLongClickListener longClickListener = (adapterView, view, i, l) -> {
            if (user.getRole().equals("participant")) {
                CyclingClub club = clubs.get(i);
                showRateClubDialog(club, user);
            } else {
                runOnUiThread(() -> DynamicToast.makeError(this, "Login as participant to rate a club").show());
            }

            return true;
        };
        listView.setOnItemLongClickListener(longClickListener);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            // This method is called when an item in the ListView is clicked
            CyclingClub selectedClub= clubs.get(position);
            showReviewDialog(selectedClub);
        });

        EditText searchClubName = findViewById(R.id.editSearchClubName);
        EditText searchEventName = findViewById(R.id.editSearchEventName);
        EditText searchEventType = findViewById(R.id.editSearchEventType);

        searchClubName.setText("");
        searchEventName.setText("");
        searchEventType.setText("");

        btnSearchClub.setOnClickListener(v -> searchClub(clubs, adapter, searchEventType.getText().toString().trim() , searchEventName.getText().toString().trim() ,searchClubName.getText().toString().trim()));

    }

    public void searchClub(List<CyclingClub> clubs, ArrayAdapter<CyclingClub> clubAdapter, String type, String eventName, String clubName){

        //Find username of the events with specified event type and event name
        List<String> userNames=new ArrayList<>();
        DatabaseReference dRef = FirebaseDatabase.getInstance().getReference("events");

        dRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Event ev = snapshot.getValue(Event.class);
                    //int x=1;
                    assert ev != null;
                    if (ev.getId().contains(eventName) && ev.getType().contains(type)){
                        userNames.add(ev.getUsername());
                    }
                }
                querySecondDatabase( clubs,clubAdapter, userNames, type, eventName, clubName);
            }
            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
                // Handle potential errors here.
            }
        });
    }
    private void querySecondDatabase(List<CyclingClub> clubs,ArrayAdapter<CyclingClub> clubAdapter, List<String> userNames,String type, String eventName,  String clubName){

        DatabaseReference dRefClub = FirebaseDatabase.getInstance().getReference("clubProfiles");

        dRefClub.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                clubs.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CyclingClub club = snapshot.getValue(CyclingClub.class);
                    assert club != null;
                    if(club.getClubName().contains(clubName) && (userNames.contains(club.getUsername()) || eventName.isEmpty() && type.isEmpty())){
                        clubs.add(club);
                    }
                }
                clubAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
                // Handle potential errors here.
            }
        });

    }


    private void  showRateClubDialog(CyclingClub club, User user){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.rate_club, null);
        dialogBuilder.setView(dialogView);

        TextView textName = dialogView.findViewById(R.id.textClubName1);
        TextView textPhone = dialogView.findViewById(R.id.textPhoneNumber);
        TextView textRegion = dialogView.findViewById(R.id.textRegion);

        textName.setText(club.getClubName());
        textPhone.setText(club.getPhoneNumber());
        textRegion.setText(club.getRegion());

        int rate=1;
        String comment="";

        for (Map<String, Object> rateComment : club.getRateComments() ) {
            if (Objects.requireNonNull(rateComment.get("userName")).toString().equals(user.getUsername())) {
                // Comment with the same username already exists, update the comment and rate
                rate = Integer.parseInt(Objects.requireNonNull(rateComment.get("rate")).toString());
                comment = Objects.requireNonNull(rateComment.get("comment")).toString();
            }
        }

        EditText editComment = dialogView.findViewById(R.id.editComment);
        editComment.setText(comment);

        Spinner spinner=dialogView.findViewById(R.id.spinnerRate);

        final AlertDialog b = dialogBuilder.create();
        // Create an ArrayAdapter with numeric values from 1 to 5
        if (spinner != null) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    b.getContext(),
                    R.array.number_array,
                    android.R.layout.simple_spinner_item
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            if(rate>=1){
                spinner.setSelection(rate-1);
            }
        }
        b.show();
        Button buttonDiscard = dialogView.findViewById(R.id.btnDiscardRating);
        buttonDiscard.setOnClickListener(view -> b.dismiss());
        Button buttonRate = dialogView.findViewById(R.id.btnRateCyclingClub);
        buttonRate.setOnClickListener(view -> {

            assert spinner != null;
            int rate1 = Integer.parseInt(spinner.getSelectedItem().toString());
            EditText editComment1 = dialogView.findViewById(R.id.editComment);
            String comment1 =  editComment1.getText().toString().trim();
            club.addRateComment(user.getUsername(), comment1, rate1);

            DatabaseReference dRef = FirebaseDatabase.getInstance().getReference("clubProfiles");
            dRef.child(club.getKey()).setValue(club);
            b.dismiss();
        });
    }

    private void  showReviewDialog(CyclingClub club){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.club_reviews, null);
        dialogBuilder.setView(dialogView);
        TextView textName= dialogView.findViewById(R.id.textClubNameReview);

        textName.setText(club.getClubName());
        List<Map<String, Object>> reviews=club.getRateComments();
        ArrayAdapter<Map<String, Object>> adapter = new ArrayAdapter<Map<String, Object>>(
                this,
                android.R.layout.simple_list_item_2, // Built-in layout for a two-line list item
                android.R.id.text1, // ID of the TextView for the name
                reviews
        ) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Customize the appearance of the list item
                View view = super.getView(position, convertView, parent);

                // Get the Person object for this position
                Map<String, Object> review = reviews.get(position);

                // Find the TextViews in the layout
                TextView text1 = view.findViewById(android.R.id.text1);
                TextView text2 = view.findViewById(android.R.id.text2);

                String t0= Objects.requireNonNull(review.get("userName")).toString();
                String t1= Objects.requireNonNull(review.get("rate")).toString();
                String t2= Objects.requireNonNull(review.get("comment")).toString();

                text1.setText("Reviewed By:" +  t0 + "     Rate:" + t1);
                text2.setText("Comments:" + t2);

                return view;
            }
        };

        ListView listView = dialogView.findViewById(R.id.listRateAndReview);
        listView.setAdapter(adapter);

        final AlertDialog b = dialogBuilder.create();
        b.show();
    }
}