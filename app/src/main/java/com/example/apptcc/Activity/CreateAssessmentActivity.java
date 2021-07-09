package com.example.apptcc.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.apptcc.R;

public class CreateAssessmentActivity extends AppCompatActivity {

    private BootstrapEditText edtComment;
    private BootstrapButton btnAssessment;
    private ImageView imgStarOff1, imgStarOff2, imgStarOff3, imgStarOff4, imgStarOff5;
    private ImageView imgStarOn1, imgStarOn2, imgStarOn3, imgStarOn4, imgStarOn5;
    private int assessmentValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_assessment);

        edtComment = (BootstrapEditText) findViewById(R.id.edtComment);
        btnAssessment = (BootstrapButton) findViewById(R.id.btnAssessment);
        imgStarOff1 = (ImageView) findViewById(R.id.imgStarOff1);
        imgStarOff2 = (ImageView) findViewById(R.id.imgStarOff2);
        imgStarOff3 = (ImageView) findViewById(R.id.imgStarOff3);
        imgStarOff4 = (ImageView) findViewById(R.id.imgStarOff4);
        imgStarOff5 = (ImageView) findViewById(R.id.imgStarOff5);
        imgStarOn1 = (ImageView) findViewById(R.id.imgStarOn1);
        imgStarOn2 = (ImageView) findViewById(R.id.imgStarOn2);
        imgStarOn3 = (ImageView) findViewById(R.id.imgStarOn3);
        imgStarOn4 = (ImageView) findViewById(R.id.imgStarOn4);
        imgStarOn5 = (ImageView) findViewById(R.id.imgStarOn5);


        btnAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        imgStarOff1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        imgStarOff2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        imgStarOff3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        imgStarOff4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        imgStarOff5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}