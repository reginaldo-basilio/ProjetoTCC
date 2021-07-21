package com.example.apptcc.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.apptcc.R;

public class MyAdsActivity extends AppCompatActivity {

    private BootstrapButton btnInsertAds, btnInsertAssessment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ads);

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