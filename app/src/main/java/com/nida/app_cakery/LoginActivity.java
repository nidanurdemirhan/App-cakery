package com.nida.app_cakery;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private String mailAddress;
    private String password;

    public LoginActivity(){

    }
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
            register(R.layout.register_page);
        });
    }

    public void login(EditText etEmail, EditText etPassword){
         mailAddress = etEmail.getText().toString();
         password =  etPassword.getText().toString();
        if (mailAddress.length() + password.length() != 0) {
            //BURDA DATABASE'E YOLLA
        }
    }

    public void register(int view){

    }
}
