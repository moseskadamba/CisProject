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

public class AdminHospitalActivity extends AppCompatActivity {
    private EditText etName, etDate, etLocation, etReason;
    private String HospitalNationalId, HospitalNameHOlder, HospitalLocationHolder, HospitalDateHolder, HospitalReasonHolder;
    private Button btnSkip, btnRegister;
    private TextView etNational_id;

    Boolean CheckEditText ;
    ProgressDialog progressDialog;
    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String HttpURL = "http://mkadamba.hostingerapp.com/cis/reg_hospital.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_hospital);

        Bundle bundle=getIntent().getExtras();
        String national_id_reg=bundle.getString("national_id");

        etNational_id = (TextView) findViewById(R.id.HospitalNationalId);
        etName = (EditText) findViewById(R.id.HospitalName);
        etDate = (EditText) findViewById(R.id.HospitalDate);
        etLocation = (EditText) findViewById(R.id.HospitalLocation);
        etReason = (EditText) findViewById(R.id.HospitalReason);

        etNational_id.setText(national_id_reg);

        btnRegister = (Button) findViewById(R.id.btnHospitalAdd);
        btnSkip = (Button) findViewById(R.id.btnHospitalSkip);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHospitalActivity.this, AdminDashboardActivity.class);
                startActivity(intent);
                AdminHospitalActivity.this.finish();

            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckEditTextIsEmptyOrNot();
                if(CheckEditText) {

                    HospitalRegistration(HospitalNationalId, HospitalNameHOlder, HospitalLocationHolder, HospitalDateHolder, HospitalReasonHolder);
                } else {
                    // If EditText is empty then this block will execute .
                    Toast.makeText(AdminHospitalActivity.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    public void HospitalRegistration(final String S_National, final String S_Name, final String S_Location, final String S_Date, final String S_Reason ){

        class HospitalRegistrationClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(AdminHospitalActivity.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Toast.makeText(AdminHospitalActivity.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AdminHospitalActivity.this, AdminDashboardActivity.class);
                startActivity(intent);
                AdminHospitalActivity.this.finish();

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("national_id",params[0]);

                hashMap.put("name",params[1]);

                hashMap.put("location",params[2]);

                hashMap.put("date",params[3]);

                hashMap.put("reason",params[4]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        HospitalRegistrationClass hospitalRegistrationClass = new HospitalRegistrationClass();

        hospitalRegistrationClass.execute(S_National, S_Name, S_Location, S_Date, S_Reason);
    }


    public void CheckEditTextIsEmptyOrNot(){

        HospitalNationalId = etNational_id.getText().toString();
        HospitalNameHOlder = etName.getText().toString();
        HospitalLocationHolder = etLocation.getText().toString();
        HospitalDateHolder = etDate.getText().toString();
        HospitalReasonHolder = etReason.getText().toString();

        if(TextUtils.isEmpty(HospitalNationalId) || TextUtils.isEmpty(HospitalNameHOlder) || TextUtils.isEmpty(HospitalLocationHolder)
                || TextUtils.isEmpty(HospitalDateHolder)|| TextUtils.isEmpty(HospitalReasonHolder))
        {

            CheckEditText = false;

        }
        else {

            CheckEditText = true ;
        }

    }
}
