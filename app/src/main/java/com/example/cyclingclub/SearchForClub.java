package com.example.cyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.Button;
import android.text.TextUtils;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import java.util.Iterator;
import java.util.Map;


public class SearchForClub extends AppCompatActivity {

    private ListView listViewClubs;
    private List<CyclingClub> clubs;
    private CyclingClub selectedClub;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_club);

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

        DatabaseReference dRef = FirebaseDatabase.getInstance().getReference("ClubProfile");

        dRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
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

        // Using an anonymous inner class
        btnSearchClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSearchResult(clubs);

               // int x=1;
                adapter.notifyDataSetChanged();
            }
        });

        //ListView listView = findViewById(R.id.listViewClubs);
        AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                CyclingClub club = clubs.get(i);
                showRateClubDialog(club, user);
                return true;
            }
        };
        listView.setOnItemLongClickListener(longClickListener);

        EditText searchClubName = findViewById(R.id.editSearchClubName);
        EditText searchEventName = findViewById(R.id.editSearchEventName);
        EditText searchEventType = findViewById(R.id.editSearchEventType);

        searchClubName.setText("");
        searchEventName.setText("");
        searchEventType.setText("");


    }

    private void getSearchResult(List<CyclingClub> clubs){
        EditText searchClubName = findViewById(R.id.editSearchClubName);
        String clubName=searchClubName.getText().toString().trim();


        Iterator<CyclingClub> iterator = clubs.iterator();
        while (iterator.hasNext()) {
            CyclingClub club = iterator.next();
            if (!club.getClubName().contains(clubName)) {
                iterator.remove();
            }
        }

        EditText searchEventName = findViewById(R.id.editSearchEventName);
        String eventName=searchEventName.getText().toString().trim();
        EditText searchEventType = findViewById(R.id.editSearchEventType);
        String eventType=searchEventType.getText().toString().trim();

        List<String> userNames=new ArrayList<>();
        DatabaseReference dRef = FirebaseDatabase.getInstance().getReference("events1");
        dRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Event ev = snapshot.getValue(Event.class);
                    if (ev.getId().contains(eventName)  && ev.getType().contains(eventType)){
                        userNames.add(ev.getUsername());
                    }
                }
                // Iterate through clubs and remove clubs whose username does not contain any string usernames
                Iterator<CyclingClub> iterator = clubs.iterator();
                while (iterator.hasNext()) {
                    CyclingClub club = iterator.next();
                    boolean containsAny = userNames.stream().anyMatch(club.getUsername()::contains);
                    if (!containsAny) {
                        iterator.remove();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle potential errors here.
            }
        });


    }


    private void  showRateClubDialog(CyclingClub club, User user){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.rate_club, null);
        dialogBuilder.setView(dialogView);

        TextView textName=(TextView) dialogView.findViewById(R.id.textClubName1);
        TextView textPhone=(TextView) dialogView.findViewById(R.id.textPhoneNumber);
        TextView textRegion=(TextView) dialogView.findViewById(R.id.textRegion);

        textName.setText(club.getClubName());
        textPhone.setText(club.getPhoneNumber());
        textRegion.setText(club.getRegion());

        int rate=1;
        String comment="";

        for (Map<String, Object> rateComment : club.getRateComments() ) {
            if (rateComment.get("userName").equals(user.getUsername())) {
                // Comment with the same username already exists, update the comment and rate
                rate = (int) rateComment.get("rate");
                comment = (String) rateComment.get("comment");
            }
        }

        EditText editComment= (EditText) dialogView.findViewById(R.id.editComment);
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
        buttonDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                b.dismiss();
            }
        });


        Button buttonRate = dialogView.findViewById(R.id.btnRateCyclingClub);
        buttonRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int rate = Integer.parseInt(spinner.getSelectedItem().toString());
                EditText editComment = dialogView.findViewById(R.id.editComment);
                String comment =  editComment.getText().toString().trim();
                club.addRateComment(user.getUsername(), comment, rate);

                DatabaseReference dRef = FirebaseDatabase.getInstance().getReference("ClubProfile");
                dRef.child(club.getKey()).setValue(club);



                b.dismiss();
            }
        });

    }




}