package com.nida.app_cakery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private String mailAddress;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        EditText etEmail = findViewById(R.id.etMail);
        EditText etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnSignUp = findViewById(R.id.btnSignup);

        btnLogin.setOnClickListener(v -> {
            login(etEmail, etPassword);
        });

        btnSignUp.setOnClickListener(v -> {
            setContentView(R.layout.register_page);
            RegisterActivity regAc = new RegisterActivity();
            regAc.onCreate(savedInstanceState);
        });
    }

    public void login(EditText etEmail, EditText etPassword){
        mailAddress = etEmail.getText().toString().trim();
        password =  etPassword.getText().toString().trim();

        if (mailAddress.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else {
            //BURDA DATABASE'E LOGİN İÇİN YOLLAAAA
            //lütfen <3
        }
    }
}
