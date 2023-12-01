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


public class SearchForClub extends AppCompatActivity {

    private ListView listViewClubs;
    private List<CyclingClub> clubs;
    private CyclingClub selectedClub;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_club);

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


        DatabaseReference dRef = FirebaseDatabase.getInstance().getReference("ClubProfile");

        dRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                clubs.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CyclingClub club = snapshot.getValue(CyclingClub.class);
                    clubs.add(club);
                }
                ListView listView = findViewById(R.id.listViewClubs);
                listView.setAdapter(adapter);
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

    }
}