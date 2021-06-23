package com.cse2216.cryptowallet.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cse2216.cryptowallet.R;
import com.cse2216.cryptowallet.activities.LandingPageActivity;
import com.cse2216.cryptowallet.activities.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import static android.content.ContentValues.TAG;

public class LoginTabFragment extends Fragment {

    View rootView;
    LandingPageActivity rootActivity;

    TextView forgotPassword;
    EditText email, password;
    Button loginButton;
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override

    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.login_fragment_layout, container, false);
        rootActivity = (LandingPageActivity) getActivity();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        email = (EditText) rootActivity.findViewById(R.id.login_email);
        password = (EditText) rootActivity.findViewById(R.id.login_password);
        forgotPassword = (TextView) rootActivity.findViewById(R.id.forgot_password);
        loginButton = (Button) rootActivity.findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().isEmpty()){
                    Toast.makeText(rootActivity, "Email Field is Empty!", Toast.LENGTH_SHORT).show();
                }
                else if(password.getText().toString().isEmpty()){
                    Toast.makeText(rootActivity, "Password Field is Empty!", Toast.LENGTH_SHORT).show();
                }
                else {
                    credentialsMatch(email.getText().toString() + "", password.getText().toString() + "");
                }
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        Log.i(TAG, "Resetting Password");
    }

    private void startMainActivity() {
        System.out.println("Starting Activity");
        Intent intent = new Intent(rootActivity, MainActivity.class);
        intent.putExtra("loginType", "signin"); //sending email to MainActivity
        startActivity(intent);
    }

    private void credentialsMatch(String email , String password) {
        rootActivity.mAuth = FirebaseAuth.getInstance();
        if(rootActivity.mAuth.getCurrentUser()!=null)
            rootActivity.mAuth.signOut();
        String TAG = "loginTag";
        rootActivity.mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this.getActivity(), task -> {
                    if (task.isSuccessful()) {
                        rootActivity.user = rootActivity.mAuth.getCurrentUser();
                        Log.d(TAG , "User " + rootActivity.user.getEmail());
                        startMainActivity();
                    } else {
                        Toast.makeText(rootActivity, "Credentials Don't Match!", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
