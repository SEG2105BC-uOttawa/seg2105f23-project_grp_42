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

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;

    public UserAccountManagement() { }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account_management);

        // Fetch the user data
        List<User> users = fetchUsers();

        // Add the UserListFragment to the fragment_container
        UserListFragment fragment = new UserListFragment(users);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit();
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

                // Update the RecyclerView with the fetched users
                UserListFragment fragment = new UserListFragment(users);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
        return users;
    }
}