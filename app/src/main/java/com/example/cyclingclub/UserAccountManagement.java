package com.example.cyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
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
    private DatabaseReference databaseUser;

    private Administrator admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account_management);

        users = new ArrayList<>();

        listViewUser = (ListView) findViewById(R.id.userListView);
        admin= new Administrator("admin","admin");
        admin.setEventTypeDB(databaseUser);

        //AdapterView.OnItemSelectedListener;
        AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //String id = eventTypes.get(i).getId();
                User user = users.get(i);
                showUpdateDeleteDialog(user);
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
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    User user = postSnapShot.getValue(User.class);
                    users.add(user);
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


    private void showUpdateDeleteDialog(User user) {

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
                //deleteProduct(productId);
                admin.deleteUser();
                b.dismiss();
            }
        });
    }

}