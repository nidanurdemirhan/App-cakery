package com.nida.app_cakery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends MainActivity {
    private String emailAddress, password;
    private String name, surname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
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
            //DATABASE'E REGISTER İÇİN GÖNDER!!!! BURDA EVET BURDA
            //HERE AŞKOM
        } else {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        }
    }

}