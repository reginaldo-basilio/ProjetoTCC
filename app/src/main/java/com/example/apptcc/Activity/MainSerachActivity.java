package com.example.apptcc.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.apptcc.R;

public class MainSerachActivity extends AppCompatActivity {

    private Spinner spState, spCity, spCategory, spSubCategory;

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

        fillStateSpinner();

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

}