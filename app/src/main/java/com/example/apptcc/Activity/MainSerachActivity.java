package com.example.apptcc.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.apptcc.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainSerachActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private Spinner spState, spCity, spCategory, spSubCategory;
    private BootstrapButton btnResearch, btnAddAds;
    private TextView txtQuestion, txtRegisterOrLogin;

    private String[] stateListSpinner = new String []{"Selecione um Estado", "Acre", "Alagoas", "Amapá", "Amazonas", "Bahia", "Ceará", "Distrito Federal",
            "Espírito Santo", "Goiás", "Maranhão", "Mato Grosso", "Mato Grosso do Sul", "Minas Gerais", "Pará",
            "Paraíba", "Paraná", "Pernambuco", "Piauí", "Rio de Janeiro", "Rio Grande do Norte", "Rio Grande do Sul",
            "Rondônia", "Roraima", "Santa Catarina", "São Paulo", "Sergipe", "Tocantins"};

    private String[] cityListSpinner = new String []{"Selecione uma Cidade"};

    private String[] categoryListSpinner = new String []{"Selecione uma Categoria"};

    private String[] subCategoryListSpinner = new String []{"Selecione uma Subcategoria"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_serach);

        spState = (Spinner) findViewById(R.id.spState);
        spCity = (Spinner) findViewById(R.id.spCity);
        spCategory = (Spinner) findViewById(R.id.spCategory);
        spSubCategory = (Spinner) findViewById(R.id.spSubCategory);
        btnResearch = (BootstrapButton) findViewById(R.id.btnResearch);
        btnAddAds = (BootstrapButton) findViewById(R.id.btnAddAds);
        txtRegisterOrLogin = (TextView) findViewById(R.id.txtRegister);
        txtQuestion = (TextView) findViewById(R.id.txtQuestion);

        btnResearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnAddAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        txtRegisterOrLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                toEnter();
            }
        });

        fillStateSpinner();

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

    private void openProfileActivity() {
        Intent intent = new Intent(MainSerachActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    @SuppressLint("WrongConstant")
    private void fillStateSpinner(){
        //spState.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item, stateListSpinner));

        ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout_with_border, stateListSpinner);
        spState.setAdapter(stateAdapter);

        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout_with_border, cityListSpinner);
        spCity.setAdapter(cityAdapter);

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout_with_border, categoryListSpinner);
        spCategory.setAdapter(categoryAdapter);

        ArrayAdapter<String> subCategoryAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout_with_border, subCategoryListSpinner);
        spSubCategory.setAdapter(subCategoryAdapter);
    }

    private void toEnter(){
        Intent intent = new Intent(MainSerachActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}