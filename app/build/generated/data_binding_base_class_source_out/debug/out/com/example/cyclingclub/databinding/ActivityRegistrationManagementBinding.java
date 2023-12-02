// Generated by view binder compiler. Do not edit!
package com.example.cyclingclub.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.cyclingclub.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityRegistrationManagementBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final Button btnRegistrationUpdate;

  @NonNull
  public final ListView listRegistration;

  @NonNull
  public final TextView textView23;

  private ActivityRegistrationManagementBinding(@NonNull LinearLayout rootView,
      @NonNull Button btnRegistrationUpdate, @NonNull ListView listRegistration,
      @NonNull TextView textView23) {
    this.rootView = rootView;
    this.btnRegistrationUpdate = btnRegistrationUpdate;
    this.listRegistration = listRegistration;
    this.textView23 = textView23;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityRegistrationManagementBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityRegistrationManagementBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_registration_management, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityRegistrationManagementBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnRegistrationUpdate;
      Button btnRegistrationUpdate = ViewBindings.findChildViewById(rootView, id);
      if (btnRegistrationUpdate == null) {
        break missingId;
      }

      id = R.id.listRegistration;
      ListView listRegistration = ViewBindings.findChildViewById(rootView, id);
      if (listRegistration == null) {
        break missingId;
      }

      id = R.id.textView23;
      TextView textView23 = ViewBindings.findChildViewById(rootView, id);
      if (textView23 == null) {
        break missingId;
      }

      return new ActivityRegistrationManagementBinding((LinearLayout) rootView,
          btnRegistrationUpdate, listRegistration, textView23);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
