package com.nida.app_cakery.Activity;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nida.app_cakery.Domain.CakeryDomain;
import com.nida.app_cakery.Listeners.FirebaseListener;
import com.nida.app_cakery.R;
import com.nida.app_cakery.Models.User;

import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {
    private String emailAddress, password;
    private String name, surname;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        mAuth = FirebaseAuth.getInstance();
        registerActivity();
    }

    private void registerActivity() {
        EditText etEmail = findViewById(R.id.etEmailReg);
        EditText etPassword = findViewById(R.id.etPasswordReg);
        EditText etPasswordCheck = findViewById(R.id.etPasswordReg2);
        EditText etName = findViewById(R.id.etName);
        EditText etSurname = findViewById(R.id.etSurname);
        Button btnSignUp = findViewById(R.id.btnSignUpReg);
        Button btnReturn = findViewById(R.id.btnLoginReg);
        btnSignUp.setOnClickListener(v -> {
            register(etEmail, etPassword, etPasswordCheck, etName, etSurname);
        });
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });
    }

    private void register(EditText etEmail, EditText etPassword, EditText etPasswordCheck,
                          EditText etName, EditText etSurname) {
        emailAddress = etEmail.getText().toString().trim();
        name = etName.getText().toString().trim();
        surname = etSurname.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        String passwordCheck = etPasswordCheck.getText().toString().trim();
        if (emailAddress.isEmpty() || name.isEmpty() || surname.isEmpty() || password.isEmpty() || passwordCheck.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.equals(passwordCheck)) {
            createUserWithEmailandPassword(emailAddress, password);
        } else {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        }
    }

    private void createUserWithEmailandPassword(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            CakeryDomain.getInstance().readIngredients(new FirebaseListener() {
                                @Override
                                public void onTaskCompleted() {
                                    CakeryDomain.getInstance().readRecipes(new FirebaseListener() {
                                        @Override
                                        public void onTaskCompleted() {
                                            User newUser = new User(emailAddress, name, surname, password);
                                            CakeryDomain.getInstance().setUser(newUser);
                                            CakeryDomain.getInstance().saveObject("User", UUID.randomUUID().toString(), newUser, new FirebaseListener() {
                                                @Override
                                                public void onTaskCompleted() {
                                                    startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                                                    //updateUI(user);
                                                }
                                            });
                                        }
                                    });
                                }
                            });

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }
}