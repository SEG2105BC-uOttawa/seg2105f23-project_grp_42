// Generated by view binder compiler. Do not edit!
package com.example.cyclingclub.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.cyclingclub.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class EventTypeListBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView Text5;

  @NonNull
  public final TextView eventName;

  @NonNull
  public final TextView eventNumber;

  @NonNull
  public final TextView text7;

  private EventTypeListBinding(@NonNull LinearLayout rootView, @NonNull TextView Text5,
      @NonNull TextView eventName, @NonNull TextView eventNumber, @NonNull TextView text7) {
    this.rootView = rootView;
    this.Text5 = Text5;
    this.eventName = eventName;
    this.eventNumber = eventNumber;
    this.text7 = text7;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static EventTypeListBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static EventTypeListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.event_type_list, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static EventTypeListBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.Text5;
      TextView Text5 = ViewBindings.findChildViewById(rootView, id);
      if (Text5 == null) {
        break missingId;
      }

      id = R.id.eventName;
      TextView eventName = ViewBindings.findChildViewById(rootView, id);
      if (eventName == null) {
        break missingId;
      }

      id = R.id.eventNumber;
      TextView eventNumber = ViewBindings.findChildViewById(rootView, id);
      if (eventNumber == null) {
        break missingId;
      }

      id = R.id.text7;
      TextView text7 = ViewBindings.findChildViewById(rootView, id);
      if (text7 == null) {
        break missingId;
      }

      return new EventTypeListBinding((LinearLayout) rootView, Text5, eventName, eventNumber,
          text7);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
