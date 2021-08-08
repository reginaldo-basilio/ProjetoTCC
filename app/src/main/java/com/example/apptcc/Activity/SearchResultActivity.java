package com.example.apptcc.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.apptcc.Adapter.ResultSearchUsersAdapter;
import com.example.apptcc.Adapter.UserAdsAdapter;
import com.example.apptcc.Entities.User;
import com.example.apptcc.R;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {

    private BootstrapButton btnNewResearch;
    private RecyclerView mRecyclerViewUser;
    private ResultSearchUsersAdapter mUserAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        btnNewResearch = (BootstrapButton) findViewById(R.id.btnNewResearch);
        mRecyclerViewUser = findViewById(R.id.recyclerViewShowUser);

        mRecyclerViewUser.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<User> userList = getIntent().getParcelableArrayListExtra("users");

        mUserAdapter = new ResultSearchUsersAdapter(userList, SearchResultActivity.this);
        mRecyclerViewUser.setAdapter(mUserAdapter);

        btnNewResearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainSerachActivity();
            }
        });

    }

    private void openMainSerachActivity() {
        Intent intent = new Intent(SearchResultActivity.this, MainSerachActivity.class);
        startActivity(intent);
        finish();
    }
}