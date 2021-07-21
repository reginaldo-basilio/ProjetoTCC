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
    private StorageReference profileRef;
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
                ads = new Ads();
                ads.setTitle(edtTitle.getText().toString());
                ads.setDescription(edtDescription.getText().toString());
                String title = edtTitle.getText().toString();
                String description = edtDescription.getText().toString();

                if(title == null || title.isEmpty() || description == null || description.isEmpty()){
                    Toast.makeText(CreateAdsActivity.this, "Título e descrição devem ser preenchidos!", Toast.LENGTH_SHORT).show();
                }else{

                    userAuth = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = userAuth.getUid();
                    ads.setUidAds(uid);

                    storageRef = storage.getReference();
                    profileRef = storageRef.child("images/" + uid + ".jpg");

                    imgAds.setDrawingCacheEnabled(true);
                    imgAds.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) imgAds.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] data = baos.toByteArray();
                    UploadTask uploadTask = profileRef.putBytes(data);

                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            Toast.makeText(CreateAdsActivity.this, "Falha ao Enviar Imagem!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                            // ...

                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            Toast.makeText(CreateAdsActivity.this, "Anúncio cadastrado!", Toast.LENGTH_LONG).show();

                            insertAdsDatabase(ads);
                            openMyAdsActivity();
                        }
                    });
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

}