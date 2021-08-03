package com.example.apptcc.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private BootstrapEditText edtName, edtEmail, edtContact, edtFantasyName, edtDistrict;
    private Spinner spState, spCity;
    private BootstrapEditText edtAdress, edtNumber, edtPassword, edtConfirmPassword;
    private BootstrapButton btnRegister, btnCancel;
    private List<String> stateList, cityList;

    private User user;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseUser userAuth;
    private DatabaseReference myRef, databaseReference;

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
        spState = (Spinner) findViewById(R.id.spState);
        spCity = (Spinner) findViewById(R.id.spCity);
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
                    user.setState(spState.getSelectedItem().toString());
                    user.setCity(spCity.getSelectedItem().toString());
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

        loadStates();

        spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView parent, View arg1, int arg2, long arg3) {
                String currentSelectedItem = spState.getSelectedItem().toString();
                loadCities(currentSelectedItem);
            }
            @Override
            public void onNothingSelected(AdapterView arg0) {
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

    private void createUser(){

    }

    private void loadStates(){
        databaseReference = FirebaseDatabase.getInstance().getReference("states");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                stateList = new ArrayList<String>();
                for(DataSnapshot stateSnapshot : snapshot.getChildren()){
                    String stateName = stateSnapshot.child("name").getValue(String.class);
                    stateList.add(stateName);
                }

                ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(RegisterActivity.this, R.layout.spinner_layout_with_border, stateList);
                spState.setAdapter(stateAdapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void loadCities(String currentSelectedItem){
        databaseReference = FirebaseDatabase.getInstance().getReference("cities");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                cityList = new ArrayList<String>();
                for(DataSnapshot citySnapshot : snapshot.getChildren()){

                    String stateName = citySnapshot.child("stateName").getValue(String.class);
                    String cityName = citySnapshot.child("name").getValue(String.class);

                    if (currentSelectedItem.equals(stateName)) {
                        cityList.add(cityName);
                    }
                }

                ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(RegisterActivity.this, R.layout.spinner_layout_with_border, cityList);
                spCity.setAdapter(cityAdapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }



}