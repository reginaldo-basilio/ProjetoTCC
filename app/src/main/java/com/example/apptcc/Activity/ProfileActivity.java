package com.example.apptcc.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.apptcc.Entities.User;
import com.example.apptcc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private ImageView imgImageProfile;
    private BootstrapEditText edtName, edtEmail, edtContact, edtFantasyName, edtDistrict;
    private Spinner spState, spCity, spCategory;
    private List<String> stateList, cityList, categoryList;
    private BootstrapEditText edtAddress, edtNumber, edtPassword, edtConfirmPassword;
    private BootstrapButton btnUpdate, btnCancel;

    private DatabaseReference reference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private Uri mUri;
    private int flag;

    private ArrayAdapter<String> categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imgImageProfile = (ImageView) findViewById(R.id.imgImageProfile);
        edtName = (BootstrapEditText) findViewById(R.id.edtName);
        edtEmail = (BootstrapEditText) findViewById(R.id.edtEmail);
        edtContact = (BootstrapEditText) findViewById(R.id.edtContact);
        edtFantasyName = (BootstrapEditText) findViewById(R.id.edtFantasyName);
        spCategory = (Spinner) findViewById(R.id.spCategory);
        spState = (Spinner) findViewById(R.id.spState);
        spCity = (Spinner) findViewById(R.id.spCity);
        edtDistrict = (BootstrapEditText) findViewById(R.id.edtDistrict);
        edtAddress = (BootstrapEditText) findViewById(R.id.edtAddress);
        edtNumber = (BootstrapEditText) findViewById(R.id.edtNumber);
        edtPassword = (BootstrapEditText) findViewById(R.id.edtPassword);
        edtConfirmPassword = (BootstrapEditText) findViewById(R.id.edtConfirmPassword);
        btnUpdate = (BootstrapButton) findViewById(R.id.btnUpdate);
        btnCancel = (BootstrapButton) findViewById(R.id.btnCancel);

        loadUserData();

        imgImageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

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
                                        spCategory.getSelectedItem().toString(),
                                        spState.getSelectedItem().toString(),
                                        spCity.getSelectedItem().toString(),
                                        edtDistrict.getText().toString(),
                                        edtAddress.getText().toString(),
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

        loadCategories();

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
                    edtDistrict.setText(user.getDistrict());
                    edtAddress.setText(user.getAddress());
                    edtNumber.setText(user.getNumber());

                    loadProfileImage();
                }
            }

            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

            }
        });
    }

    private void updateUser(String fullName, String email, String contact, String fantasyName, String category, String state, String city, String district, String adress, String number, String keyUser, String uid, String url, String newPassword) {
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("users");
        User user = new User(fullName, email, contact, fantasyName, category, state, city, district, adress, number, keyUser, uid, url);

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
        if(flag == 1){
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            String fileName = firebaseUser.getUid();
            storageRef = FirebaseStorage.getInstance().getReference("/images/" + fileName);
            storageRef.putFile(mUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String url = uri.toString();
                                    Log.i("Teste", uri.toString());
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Toast.makeText(ProfileActivity.this, "Falha ao carregar imagem!", Toast.LENGTH_SHORT).show();
                        }
                    });

        }


        Toast.makeText(ProfileActivity.this, "Dados atualizados!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void loadStates(){
        reference = FirebaseDatabase.getInstance().getReference("states");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                stateList = new ArrayList<String>();
                for(DataSnapshot stateSnapshot : snapshot.getChildren()){
                    String stateName = stateSnapshot.child("name").getValue(String.class);
                    stateList.add(stateName);
                }

                ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(ProfileActivity.this, R.layout.spinner_layout_with_border, stateList);
                spState.setAdapter(stateAdapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void loadCities(String currentSelectedItem){
        reference = FirebaseDatabase.getInstance().getReference("cities");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
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

                ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(ProfileActivity.this, R.layout.spinner_layout_with_border, cityList);
                spCity.setAdapter(cityAdapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void loadCategories(){
        reference = FirebaseDatabase.getInstance().getReference("categories");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                categoryList = new ArrayList<String>();
                for(DataSnapshot CategorySnapshot : snapshot.getChildren()){
                    String CategoryName = CategorySnapshot.child("name").getValue(String.class);
                    categoryList.add(CategoryName);
                }

                categoryAdapter = new ArrayAdapter<String>(ProfileActivity.this, R.layout.spinner_layout_with_border, categoryList);
                spCategory.setAdapter(categoryAdapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void loadProfileImage(){

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = firebaseUser.getUid();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        storageRef.child("/images/" + uid).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                imgImageProfile.setBackgroundColor(Color.TRANSPARENT);
                Picasso.get().load(uri.toString()).into(imgImageProfile);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        flag = 0;
        if(requestCode == 0){
            mUri = data.getData();
            flag = 1;
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mUri);
                imgImageProfile.setImageDrawable(new BitmapDrawable(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void selectImage(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

}