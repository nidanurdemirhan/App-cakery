package com.nida.app_cakery.Activity;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

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

public class LoginActivity extends AppCompatActivity {
    private String email;
    private String password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        loginActivity();
    }

    private void loginActivity() {
        EditText etEmail = findViewById(R.id.etMail);
        EditText etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnSignUp = findViewById(R.id.btnSignup);
        btnLogin.setOnClickListener(v -> {
            login(etEmail, etPassword);
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void login(EditText etEmail, EditText etPassword){
        email = etEmail.getText().toString().trim();
        password =  etPassword.getText().toString().trim();
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();

                                CakeryDomain.getInstance().readIngredients(new FirebaseListener() {
                                    @Override
                                    public void onTaskCompleted() {
                                        CakeryDomain.getInstance().readRecipes(new FirebaseListener() {
                                            @Override
                                            public void onTaskCompleted() {
                                                CakeryDomain.getInstance().fetchUser(email, password, new FirebaseListener() {
                                                    @Override
                                                    public void onTaskCompleted() {
                                                        CakeryDomain cakeryDomain = CakeryDomain.getInstance();
                                                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                                        //updateUI(user);
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                                //updateUI(null);
                            }
                        }
                    });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }
}
