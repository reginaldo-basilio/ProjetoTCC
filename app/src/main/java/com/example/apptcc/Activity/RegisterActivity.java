package com.example.apptcc.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.example.apptcc.Entities.LoadingDialog;
import com.example.apptcc.Entities.User;
import com.example.apptcc.Helper.MaskEditText;
import com.example.apptcc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private ImageView imgFrontImage;
    private BootstrapEditText edtName, edtEmail, edtContact, edtFantasyName, edtDistrict;
    private Spinner spState, spCity, spCategory;
    private BootstrapEditText edtAddress, edtNumber, edtPassword, edtConfirmPassword;
    private BootstrapButton btnRegister, btnCancel;
    private List<String> stateList, cityList, categoryList;
    private LoadingDialog loadingDialog;

    private User user;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseUser userAuth;
    private DatabaseReference myRef, databaseReference;
    private FirebaseStorage storage;
    private Uri mUri;
    private StorageReference storageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        loadingDialog = new LoadingDialog(RegisterActivity.this);

        imgFrontImage = (ImageView) findViewById(R.id.imgFrontImage);
        edtName = (BootstrapEditText) findViewById(R.id.edtName);
        edtEmail = (BootstrapEditText) findViewById(R.id.edtEmail);
        edtContact = (BootstrapEditText) findViewById(R.id.edtContact);
        edtContact.addTextChangedListener(MaskEditText.mask(edtContact, MaskEditText.FORMAT_CELULAR));
        edtFantasyName = (BootstrapEditText) findViewById(R.id.edtFantasyName);
        spCategory = (Spinner) findViewById(R.id.spCategory);
        spState = (Spinner) findViewById(R.id.spState);
        spCity = (Spinner) findViewById(R.id.spCity);
        edtDistrict = (BootstrapEditText) findViewById(R.id.edtDistrict);
        edtAddress = (BootstrapEditText) findViewById(R.id.edtAddress);
        edtNumber = (BootstrapEditText) findViewById(R.id.edtNumber);
        edtPassword = (BootstrapEditText) findViewById(R.id.edtPassword);
        edtConfirmPassword = (BootstrapEditText) findViewById(R.id.edtConfirmPassword);
        btnRegister = (BootstrapButton) findViewById(R.id.btnRegister);
        btnCancel = (BootstrapButton) findViewById(R.id.btnCancel);

        imgFrontImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                loadingDialog.startLoadingDialog();
                if (edtPassword.getText().toString().equals(edtConfirmPassword.getText().toString())) {
                    createAccount();
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

        loadCategories();
    }

    private void createAccount(){
        mAuth.createUserWithEmailAndPassword(edtEmail.getText().toString(), edtPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            createUserWithImage();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Falha ao cadastrar usuário!.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void createUserWithImage(){

        userAuth = FirebaseAuth.getInstance().getCurrentUser();
        String fileName = userAuth.getUid();
        storageRef = FirebaseStorage.getInstance().getReference("/images/" + fileName);
        storageRef.putFile(mUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String url = uri.toString();
                                user = new User();
                                user.setAssessmentAvg("0");
                                user.setCounter(0);
                                user.setFullName(edtName.getText().toString());
                                user.setEmail(edtEmail.getText().toString());
                                user.setContact(edtContact.getText().toString());
                                user.setFantasyName(edtFantasyName.getText().toString());
                                user.setUrl(url);
                                user.setCategory(spCategory.getSelectedItem().toString());
                                user.setState(spState.getSelectedItem().toString());
                                user.setCity(spCity.getSelectedItem().toString());
                                user.setDistrict(edtDistrict.getText().toString());
                                user.setAddress(edtAddress.getText().toString());
                                user.setNumber(edtNumber.getText().toString());
                                user.setPassword(edtPassword.getText().toString());

                                userAuth = FirebaseAuth.getInstance().getCurrentUser();
                                String uid = userAuth.getUid();
                                user.setUid(uid);

                                insertUserDatabase(user);
                                Toast.makeText(RegisterActivity.this, "Usuário cadastrado com sucesso!.",
                                        Toast.LENGTH_SHORT).show();
                                openMainSerachActivity();

                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(RegisterActivity.this, "Falha ao carregar imagem!", Toast.LENGTH_SHORT).show();
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

    private void loadCategories(){
        databaseReference = FirebaseDatabase.getInstance().getReference("categories");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                categoryList = new ArrayList<String>();
                for(DataSnapshot CategorySnapshot : snapshot.getChildren()){
                    String CategoryName = CategorySnapshot.child("name").getValue(String.class);
                    categoryList.add(CategoryName);
                }

                ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(RegisterActivity.this, R.layout.spinner_layout_with_border, categoryList);
                spCategory.setAdapter(categoryAdapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            mUri = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mUri);
                imgFrontImage.setImageDrawable(new BitmapDrawable(bitmap));
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