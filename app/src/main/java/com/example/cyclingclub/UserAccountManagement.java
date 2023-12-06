package com.example.cyclingclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * The UserAccountManagement class is an activity that manages user accounts.
 * It displays a list of users and allows an administrator to delete a user account.
 */
public class UserAccountManagement extends AppCompatActivity {

<<<<<<< HEAD
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;

    public UserAccountManagement() { }

=======
    private ListView listViewUser;
    // private List<User> users;
    private List<User> users;
    private List<String> userKeys;
    private DatabaseReference databaseUser;
    private Administrator admin;
>>>>>>> ffbefac830ee375163570b73837d42ecf654fee0
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account_management);
<<<<<<< HEAD

        // Fetch the user data
        List<User> users = fetchUsers();

        // Add the UserListFragment to the fragment_container
        UserListFragment fragment = new UserListFragment(users);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit();
=======
        users = new ArrayList<>();
        userKeys = new ArrayList<String>();
        listViewUser = (ListView) findViewById(R.id.userListView);

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
>>>>>>> ffbefac830ee375163570b73837d42ecf654fee0
    }

    private List<User> fetchUsers() {
        List<User> users = new ArrayList<>();

        DatabaseReference databaseUser = FirebaseDatabase.getInstance().getReference("users");
        databaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users.clear();
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    User user = postSnapShot.getValue(User.class);
                    users.add(user);
                }
<<<<<<< HEAD

                // Update the RecyclerView with the fetched users
                UserListFragment fragment = new UserListFragment(users);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
=======
                UserList userAdapter = new UserList(UserAccountManagement.this, users);
                listViewUser.setAdapter(userAdapter);
>>>>>>> ffbefac830ee375163570b73837d42ecf654fee0
            }
            @Override
<<<<<<< HEAD
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
=======
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
>>>>>>> ffbefac830ee375163570b73837d42ecf654fee0
            }
        });
        return users;
    }
}