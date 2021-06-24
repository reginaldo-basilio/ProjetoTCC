package com.example.apptcc.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.apptcc.R;

public class RegisterActivity extends AppCompatActivity {

    private BootstrapEditText edtName, edtEmail, edtContact, edtFantasyName, edtState, edtCity, edtDistrict;
    private BootstrapEditText edtAdress, edtNumber, edtPassword, edtConfirmPassword;
    private BootstrapButton btnRegister, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtName = (BootstrapEditText) findViewById(R.id.edtName);
        edtEmail = (BootstrapEditText) findViewById(R.id.edtEmail);
        edtContact = (BootstrapEditText) findViewById(R.id.edtContact);
        edtFantasyName = (BootstrapEditText) findViewById(R.id.edtFantasyName);
        edtState = (BootstrapEditText) findViewById(R.id.edtState);
        edtCity = (BootstrapEditText) findViewById(R.id.edtCity);
        edtDistrict = (BootstrapEditText) findViewById(R.id.edtDistrict);
        edtAdress = (BootstrapEditText) findViewById(R.id.edtAdress);
        edtNumber = (BootstrapEditText) findViewById(R.id.edtNumber);
        edtPassword = (BootstrapEditText) findViewById(R.id.edtPassword);
        edtConfirmPassword = (BootstrapEditText) findViewById(R.id.edtConfirmPassword);
        btnRegister = (BootstrapButton) findViewById(R.id.btnRegister);
        btnCancel = (BootstrapButton) findViewById(R.id.btnCancel);

        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //login(edtEmail.getText().toString(), edtPassword.getText().toString());
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //login(edtEmail.getText().toString(), edtPassword.getText().toString());
            }
        });

    }
}