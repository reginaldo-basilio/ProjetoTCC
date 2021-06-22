package com.example.apptcc.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.apptcc.R;

public class MainSerachActivity extends AppCompatActivity {

    private Spinner spState, spCity, spCategory, spSubCategory;

    private String[] stateListSpinner = new String []{"Selecione um estado", "Acre", "Alagoas", "Amapá", "Amazonas", "Bahia", "Ceará", "Distrito Federal",
            "Espírito Santo", "Goiás", "Maranhão", "Mato Grosso", "Mato Grosso do Sul", "Minas Gerais", "Pará",
            "Paraíba", "Paraná", "Pernambuco", "Piauí", "Rio de Janeiro", "Rio Grande do Norte", "Rio Grande do Sul",
            "Rondônia", "Roraima", "Santa Catarina", "São Paulo", "Sergipe", "Tocantins"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_serach);

        spState = (Spinner) findViewById(R.id.spState);
        spCity = (Spinner) findViewById(R.id.spState);
        spCategory = (Spinner) findViewById(R.id.spCategory);
        spSubCategory = (Spinner) findViewById(R.id.spSubCategory);

        fillStateSpinner();

    }

    private void fillStateSpinner(){
        //spState.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item, stateListSpinner));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_states, stateListSpinner);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spState.setAdapter(adapter);
    }

}