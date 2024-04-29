package com.nida.app_cakery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

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





        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
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