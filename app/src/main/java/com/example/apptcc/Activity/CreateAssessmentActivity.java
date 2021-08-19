package com.example.apptcc.Activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.apptcc.Adapter.AssessmentAdapter;
import com.example.apptcc.Adapter.UserAdsAdapter;
import com.example.apptcc.Entities.Ads;
import com.example.apptcc.Entities.Assessment;
import com.example.apptcc.Entities.User;
import com.example.apptcc.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

public class CreateAssessmentActivity extends AppCompatActivity {

    private Assessment mAssessment;
    private User user;
    private User userImport;
    private BootstrapEditText edtComment;
    private BootstrapButton btnConfirmAssessment;
    private ImageView imgStarOff1, imgStarOff2, imgStarOff3, imgStarOff4, imgStarOff5;
    private ImageView imgStarOn1, imgStarOn2, imgStarOn3, imgStarOn4, imgStarOn5;
    private int valueAssessment;
    private SimpleDateFormat formataData;

    private FirebaseStorage storage;
    private FirebaseAuth mAuth;
    private FirebaseUser userAuth;
    private DatabaseReference myRef, databaseReference;
    private FirebaseDatabase database;

    private List<Assessment> assessmentList;
    private RecyclerView recyclerViewAssessment;
    private AssessmentAdapter mAssessmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_assessment);

        userImport = getIntent().getExtras().getParcelable("user");
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        formataData = new SimpleDateFormat("dd-MM-yyyy");

        edtComment = (BootstrapEditText) findViewById(R.id.edtComment);
        btnConfirmAssessment = (BootstrapButton) findViewById(R.id.btnConfirmAssessment);
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


        imgStarOff1.setVisibility(View.INVISIBLE);
        imgStarOff2.setVisibility(View.VISIBLE);
        imgStarOff3.setVisibility(View.VISIBLE);
        imgStarOff4.setVisibility(View.VISIBLE);
        imgStarOff5.setVisibility(View.VISIBLE);
        imgStarOn1.setVisibility(View.VISIBLE);
        imgStarOn2.setVisibility(View.INVISIBLE);
        imgStarOn3.setVisibility(View.INVISIBLE);
        imgStarOn4.setVisibility(View.INVISIBLE);
        imgStarOn5.setVisibility(View.INVISIBLE);
        valueAssessment = 1;

        imgStarOff2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgStarOff1.setVisibility(View.INVISIBLE);
                imgStarOff2.setVisibility(View.INVISIBLE);
                imgStarOff3.setVisibility(View.VISIBLE);
                imgStarOff4.setVisibility(View.VISIBLE);
                imgStarOff5.setVisibility(View.VISIBLE);
                imgStarOn1.setVisibility(View.VISIBLE);
                imgStarOn2.setVisibility(View.VISIBLE);
                imgStarOn3.setVisibility(View.INVISIBLE);
                imgStarOn4.setVisibility(View.INVISIBLE);
                imgStarOn5.setVisibility(View.INVISIBLE);

                valueAssessment = 2;

            }
        });

        imgStarOff3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgStarOff1.setVisibility(View.INVISIBLE);
                imgStarOff2.setVisibility(View.INVISIBLE);
                imgStarOff3.setVisibility(View.INVISIBLE);
                imgStarOff4.setVisibility(View.VISIBLE);
                imgStarOff5.setVisibility(View.VISIBLE);
                imgStarOn1.setVisibility(View.VISIBLE);
                imgStarOn2.setVisibility(View.VISIBLE);
                imgStarOn3.setVisibility(View.VISIBLE);
                imgStarOn4.setVisibility(View.INVISIBLE);
                imgStarOn5.setVisibility(View.INVISIBLE);

                valueAssessment = 3;

            }
        });

        imgStarOff4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgStarOff1.setVisibility(View.INVISIBLE);
                imgStarOff2.setVisibility(View.INVISIBLE);
                imgStarOff3.setVisibility(View.INVISIBLE);
                imgStarOff4.setVisibility(View.INVISIBLE);
                imgStarOff5.setVisibility(View.VISIBLE);
                imgStarOn1.setVisibility(View.VISIBLE);
                imgStarOn2.setVisibility(View.VISIBLE);
                imgStarOn3.setVisibility(View.VISIBLE);
                imgStarOn4.setVisibility(View.VISIBLE);
                imgStarOn5.setVisibility(View.INVISIBLE);

                valueAssessment = 4;

            }
        });

        imgStarOff5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgStarOff1.setVisibility(View.INVISIBLE);
                imgStarOff2.setVisibility(View.INVISIBLE);
                imgStarOff3.setVisibility(View.INVISIBLE);
                imgStarOff4.setVisibility(View.INVISIBLE);
                imgStarOff5.setVisibility(View.INVISIBLE);
                imgStarOn1.setVisibility(View.VISIBLE);
                imgStarOn2.setVisibility(View.VISIBLE);
                imgStarOn3.setVisibility(View.VISIBLE);
                imgStarOn4.setVisibility(View.VISIBLE);
                imgStarOn5.setVisibility(View.VISIBLE);

                valueAssessment = 5;

            }
        });

        imgStarOn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgStarOff1.setVisibility(View.INVISIBLE);
                imgStarOff2.setVisibility(View.VISIBLE);
                imgStarOff3.setVisibility(View.VISIBLE);
                imgStarOff4.setVisibility(View.VISIBLE);
                imgStarOff5.setVisibility(View.VISIBLE);
                imgStarOn1.setVisibility(View.VISIBLE);
                imgStarOn2.setVisibility(View.INVISIBLE);
                imgStarOn3.setVisibility(View.INVISIBLE);
                imgStarOn4.setVisibility(View.INVISIBLE);
                imgStarOn5.setVisibility(View.INVISIBLE);

                valueAssessment = 1;

            }
        });

        imgStarOn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgStarOff1.setVisibility(View.INVISIBLE);
                imgStarOff2.setVisibility(View.INVISIBLE);
                imgStarOff3.setVisibility(View.VISIBLE);
                imgStarOff4.setVisibility(View.VISIBLE);
                imgStarOff5.setVisibility(View.VISIBLE);
                imgStarOn1.setVisibility(View.VISIBLE);
                imgStarOn2.setVisibility(View.VISIBLE);
                imgStarOn3.setVisibility(View.INVISIBLE);
                imgStarOn4.setVisibility(View.INVISIBLE);
                imgStarOn5.setVisibility(View.INVISIBLE);

                valueAssessment = 2;

            }
        });

        imgStarOn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgStarOff1.setVisibility(View.INVISIBLE);
                imgStarOff2.setVisibility(View.INVISIBLE);
                imgStarOff3.setVisibility(View.INVISIBLE);
                imgStarOff4.setVisibility(View.VISIBLE);
                imgStarOff5.setVisibility(View.VISIBLE);
                imgStarOn1.setVisibility(View.VISIBLE);
                imgStarOn2.setVisibility(View.VISIBLE);
                imgStarOn3.setVisibility(View.VISIBLE);
                imgStarOn4.setVisibility(View.INVISIBLE);
                imgStarOn5.setVisibility(View.INVISIBLE);

                valueAssessment = 3;

            }
        });

        imgStarOn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgStarOff1.setVisibility(View.INVISIBLE);
                imgStarOff2.setVisibility(View.INVISIBLE);
                imgStarOff3.setVisibility(View.INVISIBLE);
                imgStarOff4.setVisibility(View.INVISIBLE);
                imgStarOff5.setVisibility(View.VISIBLE);
                imgStarOn1.setVisibility(View.VISIBLE);
                imgStarOn2.setVisibility(View.VISIBLE);
                imgStarOn3.setVisibility(View.VISIBLE);
                imgStarOn4.setVisibility(View.VISIBLE);
                imgStarOn5.setVisibility(View.INVISIBLE);

                valueAssessment = 4;

            }
        });

        imgStarOn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgStarOff1.setVisibility(View.INVISIBLE);
                imgStarOff2.setVisibility(View.INVISIBLE);
                imgStarOff3.setVisibility(View.INVISIBLE);
                imgStarOff4.setVisibility(View.INVISIBLE);
                imgStarOff5.setVisibility(View.INVISIBLE);
                imgStarOn1.setVisibility(View.VISIBLE);
                imgStarOn2.setVisibility(View.VISIBLE);
                imgStarOn3.setVisibility(View.VISIBLE);
                imgStarOn4.setVisibility(View.VISIBLE);
                imgStarOn5.setVisibility(View.VISIBLE);

                valueAssessment = 5;

            }
        });

        btnConfirmAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAssessment();
                //openShowDataUserActivity();
            }
        });

        recyclerViewAssessment = findViewById(R.id.recyclerViewAssessment);
        recyclerViewAssessment.setLayoutManager(new LinearLayoutManager(this));
        assessmentList = new ArrayList<>();
        String uid = userImport.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("assessments");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                //assessmentList.clear();
                for(DataSnapshot adsSnapshot : snapshot.getChildren()){
                    mAssessment = adsSnapshot.getValue(Assessment.class);
                    if (mAssessment.getUidAssessment().equals(uid)) {
                        assessmentList.add(mAssessment);
                    }
                }
                mAssessmentAdapter = new AssessmentAdapter(assessmentList, CreateAssessmentActivity.this);
                recyclerViewAssessment.setAdapter(mAssessmentAdapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    private void createAssessment(){
        Date data = new Date();
        String dataFormatada = formataData.format(data);
        String uid = userImport.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                //adsList.clear();
                for(DataSnapshot adsSnapshot : snapshot.getChildren()){
                    user = adsSnapshot.getValue((User.class));
                    if (user.getUid().equals(uid)) {
                        mAssessment = new Assessment();
                        mAssessment.setDate(dataFormatada);
                        if(!edtComment.getText().toString().equals("")){
                            mAssessment.setComment(edtComment.getText().toString());
                        }else{
                            mAssessment.setComment("Usúario não deixou comentário.");
                        }
                        String value = String.valueOf(valueAssessment);
                        mAssessment.setAssessmentValue(value);
                        insertAssessmentDatabase(mAssessment);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void insertAssessmentDatabase(Assessment mAssessment){

        String keyUser = userImport.getKeyUser();

        int assessmentCounter = userImport.getAssessmentCounter();
        assessmentCounter += 1;
        userImport.setAssessmentCounter(assessmentCounter);

        int assessmentValue = userImport.getAssessmentValue();
        assessmentValue += valueAssessment;
        userImport.setAssessmentValue(assessmentValue);

        double assessmentAvg = (assessmentValue / assessmentCounter);
        Log.i("teste", "double - " + assessmentAvg);
        String avg = String.valueOf(assessmentAvg);
        userImport.setAssessmentAvg(avg);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("users");
        Map<String, Object> userValues = userImport.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/users/" + keyUser, userValues);
        databaseReference.updateChildren(childUpdates);

        myRef = database.getReference("assessments");
        String key = myRef.child("assessments").push().getKey();
        mAssessment.setKeyAssessment(key);
        mAssessment.setUidAssessment(userImport.getUid());
        myRef.child(key).setValue(mAssessment);
        Toast.makeText(this, "Avaliação efetuada!", Toast.LENGTH_SHORT).show();
    }

    private void openShowDataUserActivity(){
        Intent intent = new Intent(CreateAssessmentActivity.this, ShowDataUserActivity.class);
        startActivity(intent);
    }

}