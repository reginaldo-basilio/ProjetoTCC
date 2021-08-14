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
import android.widget.ImageView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.apptcc.Entities.Ads;
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
import java.util.HashMap;
import java.util.Map;

public class UpdateAdsActivity extends AppCompatActivity {

    private ImageView imgAds;
    private BootstrapEditText edtTitle, edtDescription;
    private BootstrapButton btnUpdateAds, btnCancel;
    private Uri mUri;
    private int flag;
    private Ads adsImport;

    private DatabaseReference reference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseStorage storage;
    private StorageReference storageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_ads);

        adsImport = getIntent().getExtras().getParcelable("ads");

        imgAds = (ImageView) findViewById(R.id.imgAds);
        edtTitle = (BootstrapEditText) findViewById(R.id.edtTitle);
        edtDescription = (BootstrapEditText) findViewById(R.id.edtDescription);
        btnUpdateAds = (BootstrapButton) findViewById(R.id.btnUpdateAds);
        btnCancel = (BootstrapButton) findViewById(R.id.btnCancel);

        loadAdsData();

        imgAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        btnUpdateAds.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String nameKeyAds = adsImport.getKeyAds();
                reference = FirebaseDatabase.getInstance().getReference();
                reference.child("ads").orderByChild("keyAds").equalTo(nameKeyAds).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot adsSnapshot : snapshot.getChildren()){
                            Ads ads = adsSnapshot.getValue(Ads.class);
                            if(ads.getKeyAds().equals(adsImport.getKeyAds())){
                                updateAds(edtTitle.getText().toString(),
                                        edtDescription.getText().toString(),
                                        ads.getKeyAds(),
                                        ads.getUidAds(),
                                        ads.getUrl(),
                                        ads.getImageName());
                            }else{
                                Toast.makeText(UpdateAdsActivity.this, "Erro ao atualizar anúncio!", Toast.LENGTH_SHORT).show();
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

    private void loadAdsData(){

        reference = FirebaseDatabase.getInstance().getReference("ads");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot adsSnapshot : snapshot.getChildren()){
                    Ads ads = adsSnapshot.getValue(Ads.class);
                    if(ads.getKeyAds().equals(adsImport.getKeyAds())){
                        edtTitle.setText(ads.getTitle());
                        edtDescription.setText(ads.getDescription());
                        loadAdsImage();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

            }
        });
    }

    private void updateAds(String title, String description, String keyAds, String uidAds, String url, String imageName) {

        if(flag == 1){
            String name = adsImport.getImageName();
            storageRef = FirebaseStorage.getInstance().getReference("/images/" + name);
            storageRef.putFile(mUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String url = uri.toString();
                                    reference = FirebaseDatabase.getInstance().getReference();
                                    reference.child("ads");
                                    Ads ads = new Ads(title, description, keyAds, uidAds, url, name);
                                    Map<String, Object> adsValues = ads.toMap();
                                    Map<String, Object> childUpdates = new HashMap<>();
                                    childUpdates.put("/ads/" + keyAds, adsValues);
                                    reference.updateChildren(childUpdates);
                                    openMyAdsActivity();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Toast.makeText(UpdateAdsActivity.this, "Falha ao carregar imagem!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }else{
            reference = FirebaseDatabase.getInstance().getReference();
            reference.child("ads");
            Ads ads = new Ads(title, description, keyAds, uidAds, url, imageName);
            Map<String, Object> adsValues = ads.toMap();
            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("/ads/" + keyAds, adsValues);
            reference.updateChildren(childUpdates);
            openMyAdsActivity();
        }


        Toast.makeText(UpdateAdsActivity.this, "Anúncio atualizado!", Toast.LENGTH_SHORT).show();

        finish();
    }

    private void loadAdsImage(){
        String name = adsImport.getImageName();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        storageRef.child("/images/" + name).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                imgAds.setBackgroundColor(Color.TRANSPARENT);
                Picasso.get().load(uri.toString()).into(imgAds);
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

    private void openMyAdsActivity() {
        Intent intent = new Intent(UpdateAdsActivity.this, MyAdsActivity.class);
        startActivity(intent);
        finish();
    }
}