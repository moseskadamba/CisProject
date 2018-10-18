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

public class AdminHighActivity extends AppCompatActivity {

    private EditText etName, etGrade, etLocation, etYear;
    private String HighNationalId, HighNameHOlder, HighGradeHolder, HighLocationHolder, HighYearHolder;
    private Button btnSkip, btnRegister;
    private TextView etNational_id;

    Boolean CheckEditText ;
    ProgressDialog progressDialog;
    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String HttpURL = "http://mkadamba.hostingerapp.com/cis/reg_high_school.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_high);

        Bundle bundle=getIntent().getExtras();
        String national_id_reg=bundle.getString("national_id");

        etNational_id = (TextView) findViewById(R.id.HighNationalId);
        etName = (EditText) findViewById(R.id.HighName);
        etGrade = (EditText) findViewById(R.id.HighGrade);
        etLocation = (EditText) findViewById(R.id.HighLocation);
        etYear = (EditText) findViewById(R.id.HighYearCompleted);

        etNational_id.setText(national_id_reg);

        btnRegister = (Button) findViewById(R.id.btnHighAdd);
        btnSkip = (Button) findViewById(R.id.btnHighSkip);

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

                    HighRegistration(HighNationalId, HighNameHOlder, HighGradeHolder, HighLocationHolder, HighYearHolder);
                } else {
                    // If EditText is empty then this block will execute .
                    Toast.makeText(AdminHighActivity.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
    public void HighRegistration(final String S_National, final String S_Name, final String S_Grade, final String S_Location, final String S_Year ){

        class HighRegistrationClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(AdminHighActivity.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Toast.makeText(AdminHighActivity.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AdminHighActivity.this, AdminUniversityActivity.class);
                String get_national = HighNationalId.toString();
                Bundle bundle = new Bundle();
                bundle.putString("national_id", get_national);
                intent.putExtras(bundle);
                startActivity(intent);
                AdminHighActivity.this.finish();

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

        HighRegistrationClass primaryRegistrationClass = new HighRegistrationClass();

        primaryRegistrationClass.execute(S_National, S_Name, S_Grade, S_Location, S_Year);
    }


    public void CheckEditTextIsEmptyOrNot(){

        HighNationalId = etNational_id.getText().toString();
        HighNameHOlder = etName.getText().toString();
        HighGradeHolder = etGrade.getText().toString();
        HighLocationHolder = etLocation.getText().toString();
        HighYearHolder = etYear.getText().toString();

        if(TextUtils.isEmpty(HighNationalId) || TextUtils.isEmpty(HighNameHOlder) || TextUtils.isEmpty(HighGradeHolder)
                || TextUtils.isEmpty(HighLocationHolder)|| TextUtils.isEmpty(HighYearHolder))
        {

            CheckEditText = false;

        }
        else {

            CheckEditText = true ;
        }

    }
}
