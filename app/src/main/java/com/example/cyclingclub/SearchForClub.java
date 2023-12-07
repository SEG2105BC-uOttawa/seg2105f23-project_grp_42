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

        //  searchClub(clubs,  user,  adapter,  "",  "","");


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


        //ListView listView = findViewById(R.id.listViewClubs);
        AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (user.getRole().equals("participant")) {
                    CyclingClub club = clubs.get(i);
                    showRateClubDialog(club, user);
                }else{
                    displayPopupMessage("Log in as participants to rate a club!", view);
                }

                return true;
            }
        };
        listView.setOnItemLongClickListener(longClickListener);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // This method is called when an item in the ListView is clicked
                CyclingClub selectedClub= (CyclingClub) clubs.get(position);
                showReviewDialog(selectedClub);
                //Toast.makeText(MainActivity.this, "Clicked on: " + selectedItem, Toast.LENGTH_SHORT).show();
            }
        });


        EditText searchClubName = findViewById(R.id.editSearchClubName);
        EditText searchEventName = findViewById(R.id.editSearchEventName);
        EditText searchEventType = findViewById(R.id.editSearchEventType);

        searchClubName.setText("");
        searchEventName.setText("");
        searchEventType.setText("");

        btnSearchClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchClub(clubs,  user,  adapter, searchEventType.getText().toString().trim() , searchEventName.getText().toString().trim() ,searchClubName.getText().toString().trim());
            }
        });

    }



    private void searchClub(List<CyclingClub> clubs, User user, ArrayAdapter<CyclingClub> clubAdapter, String type, String eventName, String clubName){

        //Find username of the events with specified event type and event name
        List<String> userNames=new ArrayList<>();
        DatabaseReference dRef = FirebaseDatabase.getInstance().getReference("Events1");

        dRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Event ev = snapshot.getValue(Event.class);
                    //int x=1;
                    if ((ev.getId().contains(eventName) || eventName.equals("")) &&
                            (ev.getType().contains(type) || type.equals(""))){
                        userNames.add(ev.getUsername());

                    }
                }

                querySecondDatabase( clubs,clubAdapter, userNames, type, eventName, clubName);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle potential errors here.
            }
        });





    }
    private void querySecondDatabase(List<CyclingClub> clubs,ArrayAdapter<CyclingClub> clubAdapter, List<String> userNames,String type, String eventName,  String clubName){

        DatabaseReference dRefClub = FirebaseDatabase.getInstance().getReference("ClubProfile");

        dRefClub.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                clubs.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CyclingClub club = snapshot.getValue(CyclingClub.class);
                    if((club.getClubName().contains(clubName) || clubName.equals("")) &&
                            (userNames.contains(club.getUsername()) || (eventName.equals("") && type.equals("")))){
                        clubs.add(club);
                    }
                }
                clubAdapter.notifyDataSetChanged();
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
            if (rateComment.get("userName").toString().equals(user.getUsername())) {
                // Comment with the same username already exists, update the comment and rate
                rate = Integer.parseInt(rateComment.get("rate").toString());
                comment = rateComment.get("comment").toString();
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




    private void  showReviewDialog(CyclingClub club){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.club_reviews, null);
        dialogBuilder.setView(dialogView);

        TextView textName=(TextView) dialogView.findViewById(R.id.textClubNameReview);

        textName.setText(club.getClubName());


        //List<Map<String, Object>> reviews= new ArrayList<>();
        List<Map<String, Object>> reviews=club.getRateComments();
        // int x=1;
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

                // Set the name and age in the TextViews
                // text1.setText(Integer.toString((int) review.get("rate")));
                // text2.setText((String) review.get("comment"));

                String t0=review.get("userName").toString();
                String t1=review.get("rate").toString();
                String t2=review.get("comment").toString();

                text1.setText("Reviewed By:"+t0+"     Rate:"+t1);
                text2.setText("Comments:" +t2);

                return view;
            }
        };

        ListView listView = dialogView.findViewById(R.id.listRateAndReview);
        listView.setAdapter(adapter);



        final AlertDialog b = dialogBuilder.create();
        b.show();




    }



    private void displayPopupMessage(String message, View anchorView) {
        LinearLayout layout = new LinearLayout(this);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));

        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        TextView textView = new TextView(this);
        textView.setText(message);
        textView.setTextColor(Color.RED);

        PopupWindow popupWindow = new PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setContentView(textView);
        popupWindow.showAsDropDown(anchorView, 10, 0);
    }



}