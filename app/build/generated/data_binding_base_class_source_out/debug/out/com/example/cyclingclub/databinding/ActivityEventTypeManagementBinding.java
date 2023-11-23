// Generated by view binder compiler. Do not edit!
package com.example.cyclingclub.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.cyclingclub.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityEventTypeManagementBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final Button btnNewEvent;

  @NonNull
  public final Button btnNewEventType;

  @NonNull
  public final Button button;

  @NonNull
  public final ListView eventTypeList;

  @NonNull
  public final TextView textView6;

  private ActivityEventTypeManagementBinding(@NonNull RelativeLayout rootView,
      @NonNull Button btnNewEvent, @NonNull Button btnNewEventType, @NonNull Button button,
      @NonNull ListView eventTypeList, @NonNull TextView textView6) {
    this.rootView = rootView;
    this.btnNewEvent = btnNewEvent;
    this.btnNewEventType = btnNewEventType;
    this.button = button;
    this.eventTypeList = eventTypeList;
    this.textView6 = textView6;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityEventTypeManagementBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityEventTypeManagementBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_event_type_management, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityEventTypeManagementBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnNewEvent;
      Button btnNewEvent = ViewBindings.findChildViewById(rootView, id);
      if (btnNewEvent == null) {
        break missingId;
      }

      id = R.id.btnNewEventType;
      Button btnNewEventType = ViewBindings.findChildViewById(rootView, id);
      if (btnNewEventType == null) {
        break missingId;
      }

      id = R.id.button;
      Button button = ViewBindings.findChildViewById(rootView, id);
      if (button == null) {
        break missingId;
      }

      id = R.id.eventTypeList;
      ListView eventTypeList = ViewBindings.findChildViewById(rootView, id);
      if (eventTypeList == null) {
        break missingId;
      }

      id = R.id.textView6;
      TextView textView6 = ViewBindings.findChildViewById(rootView, id);
      if (textView6 == null) {
        break missingId;
      }

      return new ActivityEventTypeManagementBinding((RelativeLayout) rootView, btnNewEvent,
          btnNewEventType, button, eventTypeList, textView6);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
