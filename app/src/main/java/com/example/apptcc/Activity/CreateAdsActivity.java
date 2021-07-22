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
import android.widget.ImageView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.apptcc.Adapter.UserAdsAdapter;
import com.example.apptcc.Entities.Ads;
import com.example.apptcc.Entities.User;
import com.example.apptcc.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

public class CreateAdsActivity extends AppCompatActivity {

    private ImageView imgAds;
    private BootstrapEditText edtTitle, edtDescription;
    private BootstrapButton btnInsert, btnCancel;
    private Uri mUri;

    private FirebaseStorage storage;
    private User user;
    private FirebaseAuth mAuth;
    private FirebaseUser userAuth;
    private DatabaseReference myRef;
    private StorageReference storageRef;
    private FirebaseDatabase database;
    private Ads ads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ads);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        imgAds = (ImageView) findViewById(R.id.imgAds);
        edtTitle = (BootstrapEditText) findViewById(R.id.edtTitle);
        edtDescription = (BootstrapEditText) findViewById(R.id.edtDescription);
        btnInsert = (BootstrapButton) findViewById(R.id.btnInsert);
        btnCancel = (BootstrapButton) findViewById(R.id.btnCancel);

        imgAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(edtTitle.getText().toString() == null || edtTitle.getText().toString().isEmpty() || edtDescription.getText().toString() == null || edtDescription.getText().toString().isEmpty()){
                    Toast.makeText(CreateAdsActivity.this, "Título e descrição devem ser preenchidos!", Toast.LENGTH_SHORT).show();
                }else{

                    userAuth = FirebaseAuth.getInstance().getCurrentUser();

                    String fileName = UUID.randomUUID().toString();
                    storageRef = FirebaseStorage.getInstance().getReference("/images/" + fileName);
                    storageRef.putFile(mUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String url = uri.toString();
                                            createAds(url);
                                        }
                                    });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull @NotNull Exception e) {
                                    Toast.makeText(CreateAdsActivity.this, "Falha ao carregar imagem!", Toast.LENGTH_SHORT).show();
                                }
                            });


                    //openMyAdsActivity();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMyAdsActivity();
            }
        });

    }

    private void createAds(String url){
        ads = new Ads();
        ads.setTitle(edtTitle.getText().toString());
        ads.setDescription(edtDescription.getText().toString());
        ads.setUidAds(userAuth.getUid());
        ads.setUrl(url);
        myRef = database.getReference("ads");
        String key = myRef.child("ads").push().getKey();
        ads.setKeyAds(key);
        myRef.child(key).setValue(ads);
        openMyAdsActivity();
        //insertAdsDatabase(ads);
    }

    private void insertAdsDatabase(Ads ads){
        myRef = database.getReference("ads");
        String key = myRef.child("ads").push().getKey();
        ads.setKeyAds(key);
        myRef.child(key).setValue(ads);
    }

    private void openMyAdsActivity() {
        Intent intent = new Intent(CreateAdsActivity.this, MyAdsActivity.class);
        startActivity(intent);
        finish();
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
                imgAds.setImageDrawable(new BitmapDrawable(bitmap));
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