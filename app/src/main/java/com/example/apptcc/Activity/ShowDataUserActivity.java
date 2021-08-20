package com.example.apptcc.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.apptcc.Adapter.UserAdsAdapter;
import com.example.apptcc.Adapter.UserResultAdapter;
import com.example.apptcc.Entities.Ads;
import com.example.apptcc.Entities.User;
import com.example.apptcc.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ShowDataUserActivity extends AppCompatActivity {

    private Ads ads;
    private User userImport;
    private List<Ads> adsList;
    private RecyclerView mRecyclerViewAdsUser;
    private UserResultAdapter mUserResultAdapter;
    private DatabaseReference databaseReference;
    private FirebaseUser userAuth;
    private BootstrapButton btnViewAssessments;

    private TextView mFantasyName, mAddressNumber, mDistrictCityState, mContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data_user);

        userImport = getIntent().getExtras().getParcelable("user");

        mFantasyName = (TextView) findViewById(R.id.txtFantasyName);
        mAddressNumber = (TextView) findViewById(R.id.txtAddressNumber);
        mDistrictCityState = (TextView) findViewById(R.id.txtDistrictName);
        mContact = (TextView) findViewById(R.id.txtContactNumber);
        btnViewAssessments = (BootstrapButton) findViewById(R.id.btnViewAssessments);

        loadDataUser();

        btnViewAssessments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateAssessment();
            }
        });

        String uid = userImport.getUid();
        mRecyclerViewAdsUser = findViewById(R.id.recyclerViewDataUser);
        mRecyclerViewAdsUser.setLayoutManager(new LinearLayoutManager(this));
        adsList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("ads");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                //adsList.clear();
                for(DataSnapshot adsSnapshot : snapshot.getChildren()){
                    ads = adsSnapshot.getValue(Ads.class);
                    if (ads.getUidAds().equals(uid)) {
                        adsList.add(ads);
                    }
                }
                mUserResultAdapter = new UserResultAdapter(adsList, ShowDataUserActivity.this);
                mRecyclerViewAdsUser.setAdapter(mUserResultAdapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void loadDataUser(){
        String address = userImport.getAddress();
        String number = userImport.getNumber();
        String district = userImport.getDistrict();

        mFantasyName.setText(userImport.getFantasyName());
        mAddressNumber.setText(address + ", " + number);
        mDistrictCityState.setText(district);
        mContact.setText(userImport.getContact());
    }

    private void openCreateAssessment(){
        Intent intent = new Intent(ShowDataUserActivity.this, CreateAssessmentActivity.class);
        intent.putExtra("user", userImport);
        startActivity(intent);
    }

}