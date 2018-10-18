package com.example.user.cisproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CitizenDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen_dashboard);

        Bundle bundle=getIntent().getExtras();
        String national_idfromlogin=bundle.getString("national_id");
    }
}
