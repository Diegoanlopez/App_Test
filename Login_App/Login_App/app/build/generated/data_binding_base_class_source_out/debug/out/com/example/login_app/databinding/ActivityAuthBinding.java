// Generated by view binder compiler. Do not edit!
package com.example.login_app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.login_app.R;
import com.google.android.material.button.MaterialButton;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityAuthBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final MaterialButton GoogleButton;

  @NonNull
  public final MaterialButton SignUpButton;

  @NonNull
  public final ImageView imageView;

  @NonNull
  public final TextView textView;

  @NonNull
  public final TextView textView2;

  @NonNull
  public final TextView textView3;

  @NonNull
  public final View view;

  @NonNull
  public final View view2;

  private ActivityAuthBinding(@NonNull ConstraintLayout rootView,
      @NonNull MaterialButton GoogleButton, @NonNull MaterialButton SignUpButton,
      @NonNull ImageView imageView, @NonNull TextView textView, @NonNull TextView textView2,
      @NonNull TextView textView3, @NonNull View view, @NonNull View view2) {
    this.rootView = rootView;
    this.GoogleButton = GoogleButton;
    this.SignUpButton = SignUpButton;
    this.imageView = imageView;
    this.textView = textView;
    this.textView2 = textView2;
    this.textView3 = textView3;
    this.view = view;
    this.view2 = view2;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityAuthBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityAuthBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_auth, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityAuthBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.GoogleButton;
      MaterialButton GoogleButton = ViewBindings.findChildViewById(rootView, id);
      if (GoogleButton == null) {
        break missingId;
      }

      id = R.id.SignUpButton;
      MaterialButton SignUpButton = ViewBindings.findChildViewById(rootView, id);
      if (SignUpButton == null) {
        break missingId;
      }

      id = R.id.imageView;
      ImageView imageView = ViewBindings.findChildViewById(rootView, id);
      if (imageView == null) {
        break missingId;
      }

      id = R.id.textView;
      TextView textView = ViewBindings.findChildViewById(rootView, id);
      if (textView == null) {
        break missingId;
      }

      id = R.id.textView2;
      TextView textView2 = ViewBindings.findChildViewById(rootView, id);
      if (textView2 == null) {
        break missingId;
      }

      id = R.id.textView3;
      TextView textView3 = ViewBindings.findChildViewById(rootView, id);
      if (textView3 == null) {
        break missingId;
      }

      id = R.id.view;
      View view = ViewBindings.findChildViewById(rootView, id);
      if (view == null) {
        break missingId;
      }

      id = R.id.view2;
      View view2 = ViewBindings.findChildViewById(rootView, id);
      if (view2 == null) {
        break missingId;
      }

      return new ActivityAuthBinding((ConstraintLayout) rootView, GoogleButton, SignUpButton,
          imageView, textView, textView2, textView3, view, view2);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}