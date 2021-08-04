package com.example.apptcc.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private BootstrapEditText edtName, edtEmail, edtContact, edtFantasyName, edtState, edtCity, edtDistrict;
    private BootstrapEditText edtAdress, edtNumber, edtPassword, edtConfirmPassword;
    private BootstrapButton btnUpdate, btnCancel;

    private DatabaseReference reference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

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
        btnUpdate = (BootstrapButton) findViewById(R.id.btnUpdate);
        btnCancel = (BootstrapButton) findViewById(R.id.btnCancel);

        loadUserData();

        btnUpdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                firebaseAuth = FirebaseAuth.getInstance();
                String emailCurrentUser = firebaseAuth.getCurrentUser().getEmail();
                reference = FirebaseDatabase.getInstance().getReference();

                reference.child("users").orderByChild("email").equalTo(emailCurrentUser).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot userSnapshot : snapshot.getChildren()){
                            User user = userSnapshot.getValue(User.class);
                            if(edtPassword.getText().toString().equals(edtConfirmPassword.getText().toString())){
                                updateUser(edtName.getText().toString(),
                                        user.getEmail(),
                                        edtContact.getText().toString(),
                                        edtFantasyName.getText().toString(),
                                        edtState.getText().toString(),
                                        edtCity.getText().toString(),
                                        edtDistrict.getText().toString(),
                                        edtAdress.getText().toString(),
                                        edtNumber.getText().toString(),
                                        user.getKeyUser(),
                                        user.getUid(),
                                        user.getUrl(),
                                        edtPassword.getText().toString());
                            }else{
                                Toast.makeText(ProfileActivity.this, "As senhas não correspondem!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

                    }
                });

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void loadUserData(){
        firebaseAuth = FirebaseAuth.getInstance();
        String emailCurrentUser = firebaseAuth.getCurrentUser().getEmail();
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("users").orderByChild("email").equalTo(emailCurrentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot userSnapshot : snapshot.getChildren()){
                    User user = userSnapshot.getValue(User.class);
                    edtName.setText(user.getFullName());
                    edtEmail.setText(user.getEmail());
                    edtContact.setText(user.getContact());
                    edtFantasyName.setText(user.getFantasyName());
                    edtState.setText(user.getState());
                    edtCity.setText(user.getCity());
                    edtDistrict.setText(user.getDistrict());
                    edtAdress.setText(user.getAdress());
                    edtNumber.setText(user.getNumber());
                }
            }

            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

            }
        });
    }

    private void updateUser(String fullName, String email, String contact, String fantasyName, String state, String city, String district, String adress, String number, String keyUser, String uid, String url, String newPassword) {
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("users");
        User user = new User(fullName, email, contact, fantasyName, state, city, district, adress, number, keyUser, uid, url);

        Map<String, Object> userValues = user.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/users/" + keyUser, userValues);
        reference.updateChildren(childUpdates);

        if(!newPassword.equals("")){
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            firebaseUser.updatePassword(newPassword)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("TAG", "User password updated.");

                            }
                        }
                    });

        }
        Toast.makeText(ProfileActivity.this, "Dados atualizados!", Toast.LENGTH_SHORT).show();
        finish();
    }
}