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
import android.widget.Toast;

import java.util.HashMap;

public class AdminRegisterActivity extends AppCompatActivity {

    private EditText citizenName, citizenNational_id, citizenDob, citizenMarital_status, citizenGender, citizenPhone_no, citizenAddress, citizenPassword ;
    Button RegisterCitizen, ShowCitizen;
    String CitizenNameHolder, CitizenNationaidHolder, CitizendobHolder, CitizenMaritalHolder, CitizenGenderHolder, CitizenPhoneHolder, CitizenAdressHolder, CitizenPasswordHolder ;

    Boolean CheckEditText ;
    ProgressDialog progressDialog;
    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String HttpURL = "http://mkadamba.hostingerapp.com/cis/reg_citizen.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);

        citizenName = (EditText) findViewById(R.id.citizenNameAdmin);
        citizenNational_id = (EditText) findViewById(R.id.citizenNationalId);
        citizenDob = (EditText) findViewById(R.id.citizenDobAdmin);
        citizenMarital_status = (EditText) findViewById(R.id.citizenMaritalAdmin);
        citizenGender = (EditText) findViewById(R.id.citizenGenderAdmin);
        citizenPhone_no = (EditText) findViewById(R.id.citizenPhoneNoAdmin);
        citizenAddress = (EditText) findViewById(R.id.citizenAddressAdmin);
        citizenPassword = (EditText) findViewById(R.id.citizenPassAdmin);



        RegisterCitizen = (Button) findViewById(R.id.btnRegisterCitizen);
        ShowCitizen = (Button) findViewById(R.id.btnShow);

        RegisterCitizen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckEditTextIsEmptyOrNot();
                if(CheckEditText) {

                    CitizenRegistration(CitizenNameHolder, CitizenNationaidHolder, CitizendobHolder, CitizenMaritalHolder, CitizenGenderHolder, CitizenPhoneHolder, CitizenAdressHolder, CitizenPasswordHolder);
                } else {
                    // If EditText is empty then this block will execute .
                    Toast.makeText(AdminRegisterActivity.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();
                }

            }
        });
        ShowCitizen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminRegisterActivity.this, AdminDashboardActivity.class);
                startActivity(i);
                AdminRegisterActivity.this.finish();
            }
        });
    }

    public void CitizenRegistration(final String S_Name, final String S_National, final String S_Dob, final String S_Marital, final String S_Gender, final String S_Phone, final String S_Address, final String S_Password ){

        class CitizenRegistrationClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(AdminRegisterActivity.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Toast.makeText(AdminRegisterActivity.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(AdminRegisterActivity.this, AdminPrimaryActivity.class);
                String get_national = CitizenNationaidHolder.toString();
                Bundle bundle = new Bundle();
                bundle.putString("national_id", get_national);
                intent.putExtras(bundle);
                startActivity(intent);
                AdminRegisterActivity.this.finish();

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("name",params[0]);

                hashMap.put("national_id",params[1]);

                hashMap.put("dob",params[2]);

                hashMap.put("marital_status",params[3]);

                hashMap.put("gender",params[4]);

                hashMap.put("phone_no",params[5]);

                hashMap.put("address",params[6]);

                hashMap.put("password",params[7]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        CitizenRegistrationClass citizenRegistrationClass = new CitizenRegistrationClass();

        citizenRegistrationClass.execute(S_Name,S_National, S_Dob, S_Marital, S_Gender, S_Phone, S_Address, S_Password);
    }


    public void CheckEditTextIsEmptyOrNot(){

        CitizenNameHolder = citizenName.getText().toString();
        CitizenNationaidHolder = citizenNational_id.getText().toString();
        CitizendobHolder = citizenDob.getText().toString();
        CitizenMaritalHolder = citizenMarital_status.getText().toString();
        CitizenGenderHolder = citizenGender.getText().toString();
        CitizenPhoneHolder = citizenPhone_no.getText().toString();
        CitizenAdressHolder = citizenAddress.getText().toString();
        CitizenPasswordHolder = citizenPassword.getText().toString();

        if(TextUtils.isEmpty(CitizenNameHolder) || TextUtils.isEmpty(CitizenNationaidHolder) || TextUtils.isEmpty(CitizendobHolder)
                || TextUtils.isEmpty(CitizenMaritalHolder)|| TextUtils.isEmpty(CitizenGenderHolder)|| TextUtils.isEmpty(CitizenPhoneHolder)
                || TextUtils.isEmpty(CitizenAdressHolder)|| TextUtils.isEmpty(CitizenPasswordHolder))
        {

            CheckEditText = false;

        }
        else {

            CheckEditText = true ;
        }

    }
}
