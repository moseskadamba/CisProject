package com.example.user.cisproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewStub;
import android.widget.Toast;

public class AdminDashboardActivity extends AppCompatActivity implements ViewStub.OnClickListener{
    private CardView regCitizen, viewCitizen, searchCitizen, logoutAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);


        regCitizen = (CardView) findViewById(R.id.regCitizen);
        viewCitizen = (CardView) findViewById(R.id.viewCitizen);
        searchCitizen = (CardView) findViewById(R.id.searchCitizen);
        logoutAdmin = (CardView) findViewById(R.id.logoutAdmin);

        regCitizen.setOnClickListener(this);
        viewCitizen.setOnClickListener(this);
        searchCitizen.setOnClickListener(this);
        logoutAdmin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.regCitizen:
                i = new Intent(AdminDashboardActivity.this, AdminRegisterActivity.class);
                startActivity(i);
                break;

            case R.id.viewCitizen:
                i = new Intent(AdminDashboardActivity.this, ShowAllCitizensActivity.class);
                startActivity(i);
                break;

            case R.id.logoutAdmin:
                SharedPreferences pref = getSharedPreferences("loginAdminData", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.remove("userid");
                editor.remove("password");
                editor.commit();
                i = new Intent(AdminDashboardActivity.this, MainActivity.class);
                startActivity(i);
                finish();
                Toast.makeText(getApplicationContext(), "You have logged out", Toast.LENGTH_SHORT).show();
                break;


        }

    }
}
