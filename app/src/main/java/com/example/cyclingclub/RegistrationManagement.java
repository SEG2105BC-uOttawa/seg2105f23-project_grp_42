package com.example.cyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import android.widget.Switch;

public class RegistrationManagement extends AppCompatActivity {

    private ListView listViewRegistration;
    private List<Registration> registrations;
    private Registration selectedRegistration;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_management);

        user = (User) getIntent().getSerializableExtra("user");


        listViewRegistration = (ListView) findViewById(R.id.listRegistration);

        Button btnUpdate = (Button) findViewById(R.id.btnRegistrationUpdate);
        Button btnDelete = (Button) findViewById(R.id.btnDeleteRegistration);

        if(!user.getRole().equals("cycling club")){
            btnUpdate.setEnabled(false);
            btnDelete.setEnabled(false);
        }

        registrations = new ArrayList<>();
        RegistrationAdapter regAdapter= new RegistrationAdapter(RegistrationManagement.this, registrations);
        listViewRegistration.setAdapter(regAdapter);

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                registrations.clear();
                for(DataSnapshot postSnapShot:dataSnapshot.getChildren()){
                    Registration reg =  postSnapShot.getValue(Registration.class);
                    if((user.getRole().equals("cycling club") && user.getUsername().equals(reg.getEvent().getUsername())) ||
                            (user.getRole().equals("participant") && user.getUsername().equals(reg.getParticipant().getUsername())) )
                    registrations.add(reg);
                }
                regAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };

        DatabaseReference dref = FirebaseDatabase.getInstance().getReference("Registration");
        dref.addValueEventListener(postListener);

        listViewRegistration.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                regAdapter.setSelectedPosition(position);
                selectedRegistration=registrations.get(position);

            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DatabaseReference dref = FirebaseDatabase.getInstance().getReference("Registration");
                for(Registration reg:registrations){
                    dref.child(reg.getKey()).setValue(reg);
                }
                regAdapter.notifyDataSetChanged();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedRegistration == null) {
                    displayPopupMessage("Must select a registration to delete", view);
                }else{
                    dref.child(selectedRegistration.getKey()).removeValue();
                }

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

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