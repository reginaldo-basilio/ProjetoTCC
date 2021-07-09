package com.example.apptcc.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.apptcc.R;

public class CreateAdsActivity extends AppCompatActivity {

    private ImageView imgAds;
    private BootstrapEditText edtTitle, edtDescription;
    private BootstrapButton btnInsert, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ads);

        imgAds = (ImageView) findViewById(R.id.imgAds);
        edtTitle = (BootstrapEditText) findViewById(R.id.edtTitle);
        edtDescription = (BootstrapEditText) findViewById(R.id.edtDescription);
        btnInsert = (BootstrapButton) findViewById(R.id.btnInsert);
        btnCancel = (BootstrapButton) findViewById(R.id.btnCancel);

        imgAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}