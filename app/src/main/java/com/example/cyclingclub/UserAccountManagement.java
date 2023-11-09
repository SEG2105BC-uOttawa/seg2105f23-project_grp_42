package com.example.cyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.ArrayList;


public class UserAccountManagement extends AppCompatActivity {

    private ListView listViewUser;
    // private List<User> users;
    private List<User> users;
    private List<String> userKeys;
    private DatabaseReference databaseUser;

    private Administrator admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account_management);

        users = new ArrayList<>();
        userKeys = new ArrayList<String>();

        listViewUser = (ListView) findViewById(R.id.userListView);
        admin= new Administrator("admin","admin");
        admin.setEventTypeDB(databaseUser);

        //AdapterView.OnItemSelectedListener;
        AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //databaseUser.child(users.get(i).getUsername()).removeValue();
                User user = users.get(i);
                String key= userKeys.get(i);
                showUpdateDeleteDialog(user,key);
                return true;
            }
        };
        listViewUser.setOnItemLongClickListener(longClickListener);

    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseUser = FirebaseDatabase.getInstance().getReference("users");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users.clear();
                userKeys.clear();
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    User user = postSnapShot.getValue(User.class);
                    String key= postSnapShot.getKey();
                    users.add(user);
                    userKeys.add(key);
                }

                UserList userAdapter = new UserList(UserAccountManagement.this, users);
                listViewUser.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };

        databaseUser.addValueEventListener(postListener);
    }


    private void showUpdateDeleteDialog(User user, String key) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.user_detail, null);
        dialogBuilder.setView(dialogView);

        //editTextID.setEnabled(false);
        //editTextID.setFocusable(false);
        EditText editTextName = (EditText) dialogView.findViewById(R.id.usernameTextField);
        EditText editTextEmail = (EditText) dialogView.findViewById(R.id.emailTextField);
        EditText editTextPassword = (EditText) dialogView.findViewById(R.id.passwordTextField);
        EditText editTextRole = (EditText) dialogView.findViewById(R.id.roleTextField);
        final Button buttonBack = (Button) dialogView.findViewById(R.id.btnBack);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.btnDeleteUser);

        dialogBuilder.setTitle("Selected User Detail");
        editTextName.setText(user.getUsername());
        editTextEmail.setText(user.getEmail());
        editTextPassword.setText(user.getPassword());
        editTextRole.setText(user.getRole());

        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    admin.deleteUser(databaseUser,key);
                    b.dismiss();

            }
        });
    }



}