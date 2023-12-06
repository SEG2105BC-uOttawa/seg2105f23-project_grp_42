package com.example.cyclingclub;

import androidx.annotation.NonNull;
import com.example.cyclingclub.utils.UserIDCallback;
import com.google.firebase.database.*;

import java.io.Serializable;

public class User implements Serializable {
    private String email;
    private String username;
    private String role;
    private String salt;
    private String password;
    private boolean admin;

    public User() { }

    public User(String email, String username, String role, String password, String salt)
    {
        this.email = email;
        this.username = username;
        this.role = role;
        this.salt = salt;
        this.password = password;
    }

    public String getEmail() { return email; }

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public String getRole() { return role;}

    public String getSalt() { return salt; }

    public void getUserID(String username, UserIDCallback callback) {
        DatabaseReference databaseUser = FirebaseDatabase.getInstance().getReference("users");
        databaseUser.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String userID = userSnapshot.getKey();
                    callback.onCallback(userID);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
