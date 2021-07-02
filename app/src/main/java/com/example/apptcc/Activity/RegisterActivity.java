package com.example.apptcc.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.apptcc.Entities.User;
import com.example.apptcc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private BootstrapEditText edtName, edtEmail, edtContact, edtFantasyName, edtState, edtCity, edtDistrict;
    private BootstrapEditText edtAdress, edtNumber, edtPassword, edtConfirmPassword;
    private BootstrapButton btnRegister, btnCancel;

    private User user;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseUser userAuth;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        user = new User();

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
                if (edtPassword.getText().toString().equals(edtConfirmPassword.getText().toString())) {
                    user.setFullName(edtName.getText().toString());
                    user.setEmail(edtEmail.getText().toString());
                    user.setContact(edtContact.getText().toString());
                    user.setFantasyName(edtFantasyName.getText().toString());
                    user.setState(edtState.getText().toString());
                    user.setCity(edtCity.getText().toString());
                    user.setDistrict(edtDistrict.getText().toString());
                    user.setAdress(edtAdress.getText().toString());
                    user.setNumber(edtNumber.getText().toString());
                    user.setPassword(edtPassword.getText().toString());

                    createAccount(user);

                } else {
                    Toast.makeText(RegisterActivity.this, "As senhas não correspondem!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void createAccount(final User user){
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userAuth = FirebaseAuth.getInstance().getCurrentUser();
                            String uid = userAuth.getUid();
                            user.setUid(uid);

                            insertUserDatabase(user);
                            Toast.makeText(RegisterActivity.this, "Usuário cadastrado com sucesso!.",
                                    Toast.LENGTH_SHORT).show();
                            openMainSerachActivity();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Falha ao cadastrar usuário!.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void insertUserDatabase(User user){
        myRef = database.getReference("users");
        String key = myRef.child("users").push().getKey();
        user.setKeyUser(key);
        myRef.child(key).setValue(user);
    }

    private void openMainSerachActivity(){
        Intent intent = new Intent(RegisterActivity.this, MainSerachActivity.class);
        startActivity(intent);
        finish();
    }

}