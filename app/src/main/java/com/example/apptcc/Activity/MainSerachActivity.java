package com.example.apptcc.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.apptcc.Adapter.UserAdsAdapter;
import com.example.apptcc.Entities.Ads;
import com.example.apptcc.Entities.City;
import com.example.apptcc.Entities.State;
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

public class MainSerachActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference databaseReference;

    private Spinner spState, spCity, spCategory;
    private BootstrapButton btnResearch, btnAddAds;
    private TextView txtQuestion, txtRegisterOrLogin;

    private List<String> stateList, cityList, categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_serach);

        spState = (Spinner) findViewById(R.id.spState);
        spCity = (Spinner) findViewById(R.id.spCity);
        spCategory = (Spinner) findViewById(R.id.spCategory);
        btnResearch = (BootstrapButton) findViewById(R.id.btnResearch);
        btnAddAds = (BootstrapButton) findViewById(R.id.btnAddAds);
        txtRegisterOrLogin = (TextView) findViewById(R.id.txtRegister);
        txtQuestion = (TextView) findViewById(R.id.txtQuestion);

        btnResearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearchResultActivity();
            }
        });

        btnAddAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMyAdsActivity();
            }
        });

        txtRegisterOrLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                toEnter();
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

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null){
            txtQuestion.setVisibility(View.INVISIBLE);
            txtRegisterOrLogin.setVisibility(View.INVISIBLE);
        }else{
            btnAddAds.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(currentUser != null){
            getMenuInflater().inflate(R.menu.menu_tres_pontinhos, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.itemDisconnect) {
            disconnectUser();
            return true;
        }else if (id == R.id.itemProfile) {
            openProfileActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void disconnectUser() {
        FirebaseAuth.getInstance().signOut();
        openMainSerachActivity();
        Toast.makeText(MainSerachActivity.this, "Usuário Desconectado!", Toast.LENGTH_SHORT).show();
    }

    private void openMainSerachActivity() {
        Intent intent = new Intent(MainSerachActivity.this, MainSerachActivity.class);
        startActivity(intent);
        finish();
    }

    private void openMyAdsActivity() {
        Intent intent = new Intent(MainSerachActivity.this, MyAdsActivity.class);
        startActivity(intent);
        //finish();
    }

    private void openProfileActivity() {
        Intent intent = new Intent(MainSerachActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    private void openSearchResultActivity() {
        Intent intent = new Intent(MainSerachActivity.this, SearchResultActivity.class);
        startActivity(intent);
    }

    private void toEnter(){
        Intent intent = new Intent(MainSerachActivity.this, LoginActivity.class);
        startActivity(intent);
        //finish();
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

                ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(MainSerachActivity.this, R.layout.spinner_layout_with_border, stateList);
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

                ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(MainSerachActivity.this, R.layout.spinner_layout_with_border, cityList);
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
                for(DataSnapshot stateSnapshot : snapshot.getChildren()){
                    String stateName = stateSnapshot.child("name").getValue(String.class);
                    categoryList.add(stateName);
                }

                ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(MainSerachActivity.this, R.layout.spinner_layout_with_border, categoryList);
                spCategory.setAdapter(categoryAdapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

}