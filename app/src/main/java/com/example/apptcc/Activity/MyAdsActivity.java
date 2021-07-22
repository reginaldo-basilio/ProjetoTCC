package com.example.apptcc.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.apptcc.Adapter.UserAdsAdapter;
import com.example.apptcc.Entities.Ads;
import com.example.apptcc.R;
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

public class MyAdsActivity extends AppCompatActivity {

    private Ads ads;
    private List<Ads> adsList;
    private RecyclerView mRecyclerViewAdsUser;
    private UserAdsAdapter mUserAdsAdapter;
    private DatabaseReference databaseReference;
    private FirebaseUser userAuth;

    private BootstrapButton btnInsertAds, btnInsertAssessment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ads);

        userAuth = FirebaseAuth.getInstance().getCurrentUser();
        String uid = userAuth.getUid();
        mRecyclerViewAdsUser = findViewById(R.id.recyclerViewAdsUser);
        mRecyclerViewAdsUser.setLayoutManager(new LinearLayoutManager(this));
        adsList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("ads");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                //adsList.clear();
                for(DataSnapshot adsSnapshot : snapshot.getChildren()){
                    ads = adsSnapshot.getValue((Ads.class));
                    //if (ads.getUidAds().equals(uid)) {
                        adsList.add(ads);
                    //}
                }

                mUserAdsAdapter = new UserAdsAdapter(adsList, MyAdsActivity.this);
                mRecyclerViewAdsUser.setAdapter(mUserAdsAdapter);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


        btnInsertAds = (BootstrapButton) findViewById(R.id.btnInsertAds);
        btnInsertAssessment = (BootstrapButton) findViewById(R.id.btnInsertAssessment);

        btnInsertAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateAdsActivity();
            }
        });

        btnInsertAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateAssessmentActivity();
            }
        });
    }

    private void openCreateAdsActivity() {
        Intent intent = new Intent(MyAdsActivity.this, CreateAdsActivity.class);
        startActivity(intent);
        finish();
    }

    private void openCreateAssessmentActivity() {
        Intent intent = new Intent(MyAdsActivity.this, CreateAssessmentActivity.class);
        startActivity(intent);
        finish();
    }
}