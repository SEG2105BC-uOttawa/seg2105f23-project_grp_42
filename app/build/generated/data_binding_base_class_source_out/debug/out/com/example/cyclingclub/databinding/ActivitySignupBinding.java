// Generated by view binder compiler. Do not edit!
package com.example.cyclingclub.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.cyclingclub.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivitySignupBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button btnDeleteUser;

  @NonNull
  public final TextView createAccount;

  @NonNull
  public final RadioButton cyclingClubRole;

  @NonNull
  public final EditText emailTextField;

  @NonNull
  public final RadioButton participantRole;

  @NonNull
  public final EditText passwordTextField;

  @NonNull
  public final RadioGroup roleRadioGroup;

  @NonNull
  public final EditText usernameTextField;

  private ActivitySignupBinding(@NonNull ConstraintLayout rootView, @NonNull Button btnDeleteUser,
      @NonNull TextView createAccount, @NonNull RadioButton cyclingClubRole,
      @NonNull EditText emailTextField, @NonNull RadioButton participantRole,
      @NonNull EditText passwordTextField, @NonNull RadioGroup roleRadioGroup,
      @NonNull EditText usernameTextField) {
    this.rootView = rootView;
    this.btnDeleteUser = btnDeleteUser;
    this.createAccount = createAccount;
    this.cyclingClubRole = cyclingClubRole;
    this.emailTextField = emailTextField;
    this.participantRole = participantRole;
    this.passwordTextField = passwordTextField;
    this.roleRadioGroup = roleRadioGroup;
    this.usernameTextField = usernameTextField;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivitySignupBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivitySignupBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_signup, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivitySignupBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnDeleteUser;
      Button btnDeleteUser = ViewBindings.findChildViewById(rootView, id);
      if (btnDeleteUser == null) {
        break missingId;
      }

      id = R.id.createAccount;
      TextView createAccount = ViewBindings.findChildViewById(rootView, id);
      if (createAccount == null) {
        break missingId;
      }

      id = R.id.cyclingClubRole;
      RadioButton cyclingClubRole = ViewBindings.findChildViewById(rootView, id);
      if (cyclingClubRole == null) {
        break missingId;
      }

      id = R.id.emailTextField;
      EditText emailTextField = ViewBindings.findChildViewById(rootView, id);
      if (emailTextField == null) {
        break missingId;
      }

      id = R.id.participantRole;
      RadioButton participantRole = ViewBindings.findChildViewById(rootView, id);
      if (participantRole == null) {
        break missingId;
      }

      id = R.id.passwordTextField;
      EditText passwordTextField = ViewBindings.findChildViewById(rootView, id);
      if (passwordTextField == null) {
        break missingId;
      }

      id = R.id.roleRadioGroup;
      RadioGroup roleRadioGroup = ViewBindings.findChildViewById(rootView, id);
      if (roleRadioGroup == null) {
        break missingId;
      }

      id = R.id.usernameTextField;
      EditText usernameTextField = ViewBindings.findChildViewById(rootView, id);
      if (usernameTextField == null) {
        break missingId;
      }

      return new ActivitySignupBinding((ConstraintLayout) rootView, btnDeleteUser, createAccount,
          cyclingClubRole, emailTextField, participantRole, passwordTextField, roleRadioGroup,
          usernameTextField);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
