package com.example.cyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RegistrationManagement extends AppCompatActivity {

    private ListView listViewRegistration;
    private List<Registration> registrations;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_management);

        user = (User) getIntent().getSerializableExtra("user");


        listViewRegistration = (ListView) findViewById(R.id.listRegistration);

        Button btnUpdate = (Button) findViewById(R.id.btnRegistrationUpdate);

        if((user.getRole().equals("Administrator"))){
            btnUpdate.setEnabled(false);
        }

        registrations = new ArrayList<>();


    }


    @Override
    protected void onStart() {
        super.onStart();
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                registrations.clear();
                for(DataSnapshot postSnapShot:dataSnapshot.getChildren()){
                    Registration reg =  postSnapShot.getValue(Registration.class);
                    registrations.add(reg);
                }

                RegistrationAdapter regAdapter= new RegistrationAdapter(RegistrationManagement.this, registrations);
                listViewRegistration.setAdapter(regAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        DatabaseReference dref = FirebaseDatabase.getInstance().getReference("Registration");
        dref.addValueEventListener(postListener);
    }





}