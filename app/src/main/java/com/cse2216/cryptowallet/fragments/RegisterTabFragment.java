package com.cse2216.cryptowallet.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.util.Log;
import android.util.Patterns;
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
import com.cse2216.cryptowallet.classes.domain.PortfolioItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

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
                else
                    if(isValidEmail(email.getText().toString())){
                        Toast.makeText(rootActivity, "Provide a correct email!", Toast.LENGTH_SHORT).show();
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
                    registerUser(email.getText().toString() , password.getText().toString());
                }
            }
        });
    }

    private void registerUser(String email ,String password) {

        rootActivity.mAuth = FirebaseAuth.getInstance() ;
        if(rootActivity.mAuth.getCurrentUser()!=null)
            rootActivity.mAuth.signOut();

        String TAG = "register";
        rootActivity.mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this.getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            rootActivity.user = rootActivity.mAuth.getCurrentUser();
                           // Log.d("intent" , rootActivity.user.getEmail());
                            createAndUpload();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(rootActivity, "Authentication failed : Email address already taken or Use atleast 6 character password.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }
                    }
                });

    }

    private void createAndUpload(){
        // Create and upload userInfo in Firebase
        String email = rootActivity.mAuth.getCurrentUser().getEmail() ;
        String token = rootActivity.mAuth.getCurrentUser().getUid();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference("UserInfo");
        com.cse2216.cryptowallet.classes.domain.UserInfo newUser = new com.cse2216.cryptowallet.classes.domain.UserInfo(email,rootActivity.mAuth.getCurrentUser().getUid(),new ArrayList <PortfolioItem>() , new ArrayList<Integer>());
        reference.child(token).setValue(newUser);
        startMainActivity();
    }
    private void startMainActivity(){
        Intent intent = new Intent(rootActivity, MainActivity.class);
        intent.putExtra("loginType", "register"); //sending email to MainActivity
        startActivity(intent);
    }
    private boolean isEmailTaken() {
        return false;
    }
    private boolean isValidEmail(String email){
        if(Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return false;
        else
            return  true ;
    }
}
