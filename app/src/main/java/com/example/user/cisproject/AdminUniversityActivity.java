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

public class AdminUniversityActivity extends AppCompatActivity {

    private EditText etName, etGrade, etCourse, etLocation, etYear;
    private String UniversityNationalId, UniversityNameHOlder, UniversityGradeHolder, UniversityCourseHolder, UniversityLocationHolder, UniversityYearHolder;
    private Button btnSkip, btnRegister;
    private TextView etNational_id;

    Boolean CheckEditText ;
    ProgressDialog progressDialog;
    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String HttpURL = "http://mkadamba.hostingerapp.com/cis/reg_university.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_university);

        Bundle bundle=getIntent().getExtras();
        String national_id_reg=bundle.getString("national_id");

        etNational_id = (TextView) findViewById(R.id.UniversityNationalId);
        etName = (EditText) findViewById(R.id.UniversityName);
        etGrade = (EditText) findViewById(R.id.UniversityGrade);
        etCourse = (EditText) findViewById(R.id.UniversityCourse);
        etLocation = (EditText) findViewById(R.id.UniversityLocation);
        etYear = (EditText) findViewById(R.id.UniversityYearCompleted);

        etNational_id.setText(national_id_reg);

        btnRegister = (Button) findViewById(R.id.btnUniversityAdd);
        btnSkip = (Button) findViewById(R.id.btnUniversitySkip);

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

                    UniversityRegistration(UniversityNationalId, UniversityNameHOlder, UniversityGradeHolder, UniversityCourseHolder, UniversityLocationHolder, UniversityYearHolder);
                } else {
                    // If EditText is empty then this block will execute .
                    Toast.makeText(AdminUniversityActivity.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    public void UniversityRegistration(final String S_National, final String S_Name, final String S_Grade, final String S_Course, final String S_Location, final String S_Year ){

        class UniversityRegistrationClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(AdminUniversityActivity.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Toast.makeText(AdminUniversityActivity.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AdminUniversityActivity.this, AdminHospitalActivity.class);
                String get_national = UniversityNationalId.toString();
                Bundle bundle = new Bundle();
                bundle.putString("national_id", get_national);
                intent.putExtras(bundle);
                startActivity(intent);
                AdminUniversityActivity.this.finish();

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("national_id",params[0]);

                hashMap.put("name",params[1]);

                hashMap.put("grade_attained",params[2]);

                hashMap.put("course",params[3]);

                hashMap.put("location",params[4]);

                hashMap.put("year_completed",params[5]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        UniversityRegistrationClass universityRegistrationClass = new UniversityRegistrationClass();

        universityRegistrationClass.execute(S_National, S_Name, S_Grade, S_Course, S_Location, S_Year);
    }


    public void CheckEditTextIsEmptyOrNot(){

        UniversityNationalId = etNational_id.getText().toString();
        UniversityNameHOlder = etName.getText().toString();
        UniversityGradeHolder = etGrade.getText().toString();
        UniversityCourseHolder = etCourse.getText().toString();
        UniversityLocationHolder = etLocation.getText().toString();
        UniversityYearHolder = etYear.getText().toString();

        if(TextUtils.isEmpty(UniversityNationalId) || TextUtils.isEmpty(UniversityNameHOlder) || TextUtils.isEmpty(UniversityGradeHolder) || TextUtils.isEmpty(UniversityCourseHolder)
                || TextUtils.isEmpty(UniversityLocationHolder)|| TextUtils.isEmpty(UniversityYearHolder))
        {

            CheckEditText = false;

        }
        else {

            CheckEditText = true ;
        }

    }
}
