package com.cse2216.cryptowallet.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cse2216.cryptowallet.R;
import com.cse2216.cryptowallet.activities.LandingPageActivity;
import com.cse2216.cryptowallet.activities.MainActivity;

import org.jetbrains.annotations.NotNull;

public class RegisterTabFragment extends Fragment {
    View rootView;
    LandingPageActivity rootActivity;
    EditText email, password, confirmPassword;
    Button registerButton;
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.register_fragment_layout, container, false);
        rootActivity = (LandingPageActivity) getActivity();
        return  rootView;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        email = rootActivity.findViewById(R.id.register_email);
        password = rootActivity.findViewById(R.id.regsiter_password);
        confirmPassword = rootActivity.findViewById(R.id.regsiter_confirm_password);
        registerButton = rootActivity.findViewById(R.id.register_button);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().isEmpty()){
                    Toast.makeText(rootActivity, "Email Field is Empty!", Toast.LENGTH_SHORT).show();
                }
                else if(password.getText().toString().isEmpty()){
                    Toast.makeText(rootActivity, "Password Field is Empty!", Toast.LENGTH_SHORT).show();
                }
                else if(!password.getText().toString().equals(confirmPassword.getText().toString())){
                    Toast.makeText(rootActivity, "Passwords Don't Match!", Toast.LENGTH_SHORT).show();
                }
                else if(isEmailTaken()){
                    Toast.makeText(rootActivity, "This Email is Already in Use!", Toast.LENGTH_SHORT).show();
                }
                else {
                    registerUser();
                    Intent intent = new Intent(rootActivity, MainActivity.class);
                    intent.putExtra("email", email.getText().toString()); //sending email to MainActivity
                    startActivity(intent);
                }
            }
        });
    }

    private void registerUser() {

    }

    private boolean isEmailTaken() {
        return false;
    }
}
