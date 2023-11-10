// Generated by view binder compiler. Do not edit!
package com.example.cyclingclub.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public final class EventUpdateBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final Button btnEventDelete;

  @NonNull
  public final Button btnEventUpdate;

  @NonNull
  public final EditText editTextDuration;

  @NonNull
  public final EditText editTextEventType;

  @NonNull
  public final EditText editTextLocation;

  @NonNull
  public final EditText editTextName;

  @NonNull
  public final EditText editTextTime;

  @NonNull
  public final TextView textView10;

  @NonNull
  public final TextView textview;

  @NonNull
  public final TextView textview11;

  @NonNull
  public final TextView textview3;

  @NonNull
  public final TextView textview4;

  private EventUpdateBinding(@NonNull RelativeLayout rootView, @NonNull Button btnEventDelete,
      @NonNull Button btnEventUpdate, @NonNull EditText editTextDuration,
      @NonNull EditText editTextEventType, @NonNull EditText editTextLocation,
      @NonNull EditText editTextName, @NonNull EditText editTextTime, @NonNull TextView textView10,
      @NonNull TextView textview, @NonNull TextView textview11, @NonNull TextView textview3,
      @NonNull TextView textview4) {
    this.rootView = rootView;
    this.btnEventDelete = btnEventDelete;
    this.btnEventUpdate = btnEventUpdate;
    this.editTextDuration = editTextDuration;
    this.editTextEventType = editTextEventType;
    this.editTextLocation = editTextLocation;
    this.editTextName = editTextName;
    this.editTextTime = editTextTime;
    this.textView10 = textView10;
    this.textview = textview;
    this.textview11 = textview11;
    this.textview3 = textview3;
    this.textview4 = textview4;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static EventUpdateBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static EventUpdateBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.event_update, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static EventUpdateBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnEventDelete;
      Button btnEventDelete = ViewBindings.findChildViewById(rootView, id);
      if (btnEventDelete == null) {
        break missingId;
      }

      id = R.id.btnEventUpdate;
      Button btnEventUpdate = ViewBindings.findChildViewById(rootView, id);
      if (btnEventUpdate == null) {
        break missingId;
      }

      id = R.id.editTextDuration;
      EditText editTextDuration = ViewBindings.findChildViewById(rootView, id);
      if (editTextDuration == null) {
        break missingId;
      }

      id = R.id.editTextEventType;
      EditText editTextEventType = ViewBindings.findChildViewById(rootView, id);
      if (editTextEventType == null) {
        break missingId;
      }

      id = R.id.editTextLocation;
      EditText editTextLocation = ViewBindings.findChildViewById(rootView, id);
      if (editTextLocation == null) {
        break missingId;
      }

      id = R.id.editTextName;
      EditText editTextName = ViewBindings.findChildViewById(rootView, id);
      if (editTextName == null) {
        break missingId;
      }

      id = R.id.editTextTime;
      EditText editTextTime = ViewBindings.findChildViewById(rootView, id);
      if (editTextTime == null) {
        break missingId;
      }

      id = R.id.textView10;
      TextView textView10 = ViewBindings.findChildViewById(rootView, id);
      if (textView10 == null) {
        break missingId;
      }

      id = R.id.textview;
      TextView textview = ViewBindings.findChildViewById(rootView, id);
      if (textview == null) {
        break missingId;
      }

      id = R.id.textview11;
      TextView textview11 = ViewBindings.findChildViewById(rootView, id);
      if (textview11 == null) {
        break missingId;
      }

      id = R.id.textview3;
      TextView textview3 = ViewBindings.findChildViewById(rootView, id);
      if (textview3 == null) {
        break missingId;
      }

      id = R.id.textview4;
      TextView textview4 = ViewBindings.findChildViewById(rootView, id);
      if (textview4 == null) {
        break missingId;
      }

      return new EventUpdateBinding((RelativeLayout) rootView, btnEventDelete, btnEventUpdate,
          editTextDuration, editTextEventType, editTextLocation, editTextName, editTextTime,
          textView10, textview, textview11, textview3, textview4);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
