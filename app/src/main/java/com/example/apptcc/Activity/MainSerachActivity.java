package com.example.apptcc.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.apptcc.Entities.State;
import com.example.apptcc.Entities.User;
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
    private User user;
    private String stateSelected, citySelected, categorySelected;

    private List<String> stateList, cityList, categoryList;
    private List<User> userStateList, userCityList = new ArrayList<>(), userResultList;

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
                stateSelected = spState.getSelectedItem().toString();
                //String stateSelected = spState.getSelectedItem().toString();
                loadCities(stateSelected);

                userStateList = new ArrayList<>();
                databaseReference = FirebaseDatabase.getInstance().getReference("users");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        for(DataSnapshot userSnapshot : snapshot.getChildren()){
                            user = userSnapshot.getValue(User.class);
                            if (user.getState().equals(stateSelected)){
                                userStateList.add(user);
                            }
                            //userResultList.add(user);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });

            }
            @Override
            public void onNothingSelected(AdapterView arg0) {
            }
        });

        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView parent, View arg1, int arg2, long arg3) {
                citySelected = spCity.getSelectedItem().toString();

                userCityList.clear();
                //userCityList = new ArrayList<>();
                for(User item : userStateList){
                    if (item.getCity().equals(citySelected)){
                        userCityList.add(item);
                    }
                    //userResultList.add(user);
                }

            }
            @Override
            public void onNothingSelected(AdapterView arg0) {
            }
        });

        loadCategories();

        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView parent, View arg1, int arg2, long arg3) {
                categorySelected = spCategory.getSelectedItem().toString();

                userResultList = new ArrayList<>();
                for(User item : userCityList){
                    if (item.getCategory().equals(categorySelected)){
                        userResultList.add(item);
                    }
                    //userResultList.add(user);
                }
            }
            @Override
            public void onNothingSelected(AdapterView arg0) {
            }
        });

        //createListResearch();
    }

    private void createListResearch(){
        userResultList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot adsSnapshot : snapshot.getChildren()){
                    user = adsSnapshot.getValue(User.class);
                    /*if (user.getState().equals(stateSelected) &&
                        user.getCity().equals(citySelected) &&
                        user.getCategory().equals(categorySelected)) {
                        userResultList.add(user);
                    }*/
                    userResultList.add(user);
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
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
                for(DataSnapshot CategorySnapshot : snapshot.getChildren()){
                    String CategoryName = CategorySnapshot.child("name").getValue(String.class);
                    categoryList.add(CategoryName);
                }
                ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(MainSerachActivity.this, R.layout.spinner_layout_with_border, categoryList);
                spCategory.setAdapter(categoryAdapter);
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
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

    private void openSearchResultActivity() {
        Intent i = new Intent(MainSerachActivity.this, SearchResultActivity.class);
        i.putParcelableArrayListExtra("users", (ArrayList<? extends Parcelable>) userResultList);
        startActivity(i);
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

    private void toEnter(){
        Intent intent = new Intent(MainSerachActivity.this, LoginActivity.class);
        startActivity(intent);
        //finish();
    }
}