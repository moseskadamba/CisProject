package com.example.user.cisproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tv_admin, tv_user;
    String useridAdminStored = "", passwordAdminStored = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_admin = (TextView) findViewById(R.id.admin);
        tv_user = (TextView) findViewById(R.id.citizen);
        tv_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        SharedPreferences pref = getSharedPreferences("loginAdminData", MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        //editor.clear();  //for bebugging
                        //editor.commit(); //for bebugging
                        useridAdminStored = pref.getString("userid", null);
                        passwordAdminStored = pref.getString("password", null);

                        if(useridAdminStored == null){
                            Intent in = new Intent(getApplicationContext(), AdminLoginActivity.class);
                            startActivity(in);
                        }
                        else{
                            Intent in = new Intent(getApplicationContext(), AdminDashboardActivity.class);
                            String get_user=useridAdminStored.toString();
                            Bundle bundle=new Bundle();
                            bundle.putString("userid", get_user);
                            in.putExtras(bundle);
                            startActivity(in);
                        }
                        MainActivity.this.finish();
                    }
                }, 3000);
            }
        });
        tv_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        SharedPreferences pref = getSharedPreferences("loginCitizenData", MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        //editor.clear();  //for bebugging
                        //editor.commit(); //for bebugging
                        useridAdminStored = pref.getString("national_id", null);
                        passwordAdminStored = pref.getString("password", null);

                        if(useridAdminStored == null){
                            Intent in = new Intent(getApplicationContext(), CitizenLoginActivity.class);
                            startActivity(in);
                        }
                        else{
                            Intent in = new Intent(getApplicationContext(), CitizenDashboardActivity.class);
                            String get_user=useridAdminStored.toString();
                            Bundle bundle=new Bundle();
                            bundle.putString("national_id", get_user);
                            in.putExtras(bundle);
                            startActivity(in);
                        }
                        MainActivity.this.finish();
                    }
                }, 3000);
            }
        });
    }
}
