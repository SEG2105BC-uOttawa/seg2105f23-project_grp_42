package com.example.cyclingclub;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.util.HashMap;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private final List<User> users;
    private final FragmentActivity activity;

    public UserAdapter(FragmentActivity activity, List<User> users) {
        this.activity = activity;
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_recycler_row, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);
        holder.usernameText.setText(user.getUsername());
        holder.emailText.setText(user.getEmail());

        holder.optionsButton.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(activity, v);
            popup.getMenuInflater().inflate(R.menu.user_options_menu, popup.getMenu());

            popup.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();
                if (id == R.id.action_delete) {
                    handleAccountDeletion(user);
                } else if (id == R.id.action_update) {
                    // Handle "Modify" button press
                    handleUserModification(user);
                }
                return true;
            });

            popup.show();
        });
    }
    private void handleUserModification(User user) {
        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(activity);
        View dialogView = LayoutInflater.from(activity).inflate(R.layout.update_user_dialog, null);
        dialogBuilder.setView(dialogView);

        EditText editTextUsername = dialogView.findViewById(R.id.editTextUsername);
        EditText editTextEmail = dialogView.findViewById(R.id.editTextEmail);
        EditText editTextPassword = dialogView.findViewById(R.id.editTextPassword);

        editTextUsername.setText(user.getUsername());
        editTextEmail.setText(user.getEmail());
        editTextPassword.setText(user.getPassword());

        dialogBuilder.setPositiveButton("Update", (dialog, id) -> {
            String username = editTextUsername.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            // Encrypt the password
            byte[] salt = Utils.generateSalt();
            String encryptedPassword;
            try {
                encryptedPassword = Utils.hashPasswordPBKDF2(password, salt);
            } catch (Exception e) {
                Log.e("handleUserModification", String.format("Unexpected error when updating password: %s", e.getMessage()));
                throw new RuntimeException(e);
            }

            user.getUserID(username, userID -> {
                // Create a DatabaseReference to the user's data
                DatabaseReference dRef = FirebaseDatabase.getInstance().getReference("users").child(userID);

                // Create a HashMap with the updated user information
                HashMap<String, Object> userMap = new HashMap<>();
                userMap.put("username", username);
                userMap.put("email", email);
                userMap.put("salt", Base64.encodeToString(salt, Base64.NO_WRAP));
                userMap.put("role", user.getRole());
                userMap.put("password", encryptedPassword); // Store the encrypted password

                // Use setValue method to update the user information in the database
                dRef.setValue(userMap)
                        .addOnSuccessListener(aVoid -> DynamicToast.makeSuccess(activity, "User information updated successfully.").show())
                        .addOnFailureListener(e -> DynamicToast.makeError(activity, "Failed to update user information.").show());
            });
        });

        dialogBuilder.setNegativeButton("Cancel", (dialog, id) -> {
            // User cancelled the dialog
            dialog.dismiss();
        });

        dialogBuilder.create().show();
    }

    private void handleAccountDeletion(User user) {
        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(activity);
        dialogBuilder.setTitle("Confirm Deletion");
        dialogBuilder.setMessage("Are you sure you want to delete this account? This action cannot be undone.");

        dialogBuilder.setPositiveButton("Confirm", (dialog, id) -> {
            user.getUserID(user.getUsername(), userID -> {
                // Create a DatabaseReference to the user's data
                Administrator.deleteUser(userID)
                        .addOnCompleteListener(aVoid -> DynamicToast.makeSuccess(activity, "Successfully deleted user"))
                        .addOnCompleteListener(bVoid -> users.remove(user))
                        .addOnFailureListener(a -> DynamicToast.makeError(activity, "Failed to delete user"));
            });
        });

        dialogBuilder.setNegativeButton("Cancel", (dialog, id) -> {
            // User cancelled the dialog
            dialog.dismiss();
        });

        dialogBuilder.create().show();
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView usernameText;
        TextView emailText;
        ImageButton optionsButton;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameText = itemView.findViewById(R.id.username_text);
            emailText = itemView.findViewById(R.id.email_text);
            optionsButton = itemView.findViewById(R.id.options_button);
        }
    }
}