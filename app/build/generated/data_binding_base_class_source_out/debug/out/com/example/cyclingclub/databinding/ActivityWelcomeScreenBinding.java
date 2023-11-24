// Generated by view binder compiler. Do not edit!
package com.example.cyclingclub.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.cyclingclub.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityWelcomeScreenBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final BottomNavigationView bottomNavigationView;

  @NonNull
  public final FrameLayout fragmentContainer;

  @NonNull
  public final ConstraintLayout welcomeScreen;

  private ActivityWelcomeScreenBinding(@NonNull ConstraintLayout rootView,
      @NonNull BottomNavigationView bottomNavigationView, @NonNull FrameLayout fragmentContainer,
      @NonNull ConstraintLayout welcomeScreen) {
    this.rootView = rootView;
    this.bottomNavigationView = bottomNavigationView;
    this.fragmentContainer = fragmentContainer;
    this.welcomeScreen = welcomeScreen;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityWelcomeScreenBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityWelcomeScreenBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_welcome_screen, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityWelcomeScreenBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bottomNavigationView;
      BottomNavigationView bottomNavigationView = ViewBindings.findChildViewById(rootView, id);
      if (bottomNavigationView == null) {
        break missingId;
      }

      id = R.id.fragment_container;
      FrameLayout fragmentContainer = ViewBindings.findChildViewById(rootView, id);
      if (fragmentContainer == null) {
        break missingId;
      }

      ConstraintLayout welcomeScreen = (ConstraintLayout) rootView;

      return new ActivityWelcomeScreenBinding((ConstraintLayout) rootView, bottomNavigationView,
          fragmentContainer, welcomeScreen);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
