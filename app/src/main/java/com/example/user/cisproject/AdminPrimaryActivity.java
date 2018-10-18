package com.example.user.cisproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class AdminPrimaryActivity extends AppCompatActivity {
    private EditText etName, etGrade, etLocation, etYear;
    private String PrimaryNationalId, PrimaryNameHOlder, PrimaryGradeHolder, PrimaryLocationHolder, PrimaryYearHolder;
    private Button btnSkip, btnRegister;
    private TextView etNational_id;

    Boolean CheckEditText ;
    ProgressDialog progressDialog;
    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String HttpURL = "http://mkadamba.hostingerapp.com/cis/reg_primary_school.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_primary);

        Bundle bundle=getIntent().getExtras();
        String national_id_reg=bundle.getString("national_id");

        etNational_id = (TextView) findViewById(R.id.PrimaryNationalId);
        etName = (EditText) findViewById(R.id.PrimaryName);
        etGrade = (EditText) findViewById(R.id.PrimaryGrade);
        etLocation = (EditText) findViewById(R.id.PrimaryLocation);
        etYear = (EditText) findViewById(R.id.PrimaryYearCompleted);

        etNational_id.setText(national_id_reg);

        btnRegister = (Button) findViewById(R.id.btnPrimaryAdd);
        btnSkip = (Button) findViewById(R.id.btnPrimarySkip);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckEditTextIsEmptyOrNot();
                if(CheckEditText) {

                    PrimaryRegistration(PrimaryNationalId, PrimaryNameHOlder, PrimaryGradeHolder, PrimaryLocationHolder, PrimaryYearHolder);
                } else {
                    // If EditText is empty then this block will execute .
                    Toast.makeText(AdminPrimaryActivity.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();
                }

            }
        });


    }
    public void PrimaryRegistration(final String S_National, final String S_Name, final String S_Grade, final String S_Location, final String S_Year ){

        class PrimaryRegistrationClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(AdminPrimaryActivity.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Toast.makeText(AdminPrimaryActivity.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(AdminPrimaryActivity.this, AdminHighActivity.class);
                String get_national = PrimaryNationalId.toString();
                Bundle bundle = new Bundle();
                bundle.putString("national_id", get_national);
                intent.putExtras(bundle);
                startActivity(intent);
                AdminPrimaryActivity.this.finish();

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("national_id",params[0]);

                hashMap.put("name",params[1]);

                hashMap.put("grade_attained",params[2]);

                hashMap.put("location",params[3]);

                hashMap.put("year_completed",params[4]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        PrimaryRegistrationClass primaryRegistrationClass = new PrimaryRegistrationClass();

        primaryRegistrationClass.execute(S_National, S_Name, S_Grade, S_Location, S_Year);
    }


    public void CheckEditTextIsEmptyOrNot(){

        PrimaryNationalId = etNational_id.getText().toString();
        PrimaryNameHOlder = etName.getText().toString();
        PrimaryGradeHolder = etGrade.getText().toString();
        PrimaryLocationHolder = etLocation.getText().toString();
        PrimaryYearHolder = etYear.getText().toString();

        if(TextUtils.isEmpty(PrimaryNationalId) || TextUtils.isEmpty(PrimaryNameHOlder) || TextUtils.isEmpty(PrimaryGradeHolder)
                || TextUtils.isEmpty(PrimaryLocationHolder)|| TextUtils.isEmpty(PrimaryYearHolder))
        {

            CheckEditText = false;

        }
        else {

            CheckEditText = true ;
        }

    }
}
