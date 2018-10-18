package com.example.user.cisproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ShowSingleRecordActivity extends AppCompatActivity {

    HttpParse httpParse = new HttpParse();
    ProgressDialog pDialog;

    // Http Url For Filter Student Data from Id Sent from previous activity.
    String HttpURL = "http://mkadamba.hostingerapp.com/cis/FilterCitizenData.php";

    // Http URL for delete Already Open Student Record.
    String HttpUrlDeleteRecord = "http://mkadamba.hostingerapp.com/cis/DeleteCitizen.php";

    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    String ParseResult ;
    HashMap<String,String> ResultHash = new HashMap<>();
    String FinalJSonObject ;
    TextView NAME,PHONE_NUMBER,GENDER, NATIONAL_ID, MARITAL_STATUS, ADDRESS, DOB;
    String NameHolder, NumberHolder, GENDERHolder, National_idHolder, Marital_statusHolder, AddressHolder, DobHolder;
    Button UpdateButton, DeleteButton;
    String TempItem;
    ProgressDialog progressDialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_single_record);

        NAME = (TextView)findViewById(R.id.textName);
        PHONE_NUMBER = (TextView)findViewById(R.id.textPhone);
        GENDER = (TextView)findViewById(R.id.textClass);
        NATIONAL_ID = (TextView) findViewById(R.id.tvNatID);
        MARITAL_STATUS = (TextView) findViewById(R.id.tvStatus);
        ADDRESS = (TextView) findViewById(R.id.tvAddress);
        DOB = (TextView) findViewById(R.id.tvDob);

        UpdateButton = (Button)findViewById(R.id.buttonUpdate);
        DeleteButton = (Button)findViewById(R.id.buttonDelete);

        //Receiving the ListView Clicked item value send by previous activity.
        TempItem = getIntent().getStringExtra("ListViewValue");

        //Calling method to filter Student Record and open selected record.
        HttpWebCall(TempItem);


        UpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ShowSingleRecordActivity.this,AdminDashboardActivity.class);

                // Sending Student Id, Name, Number and Class to next UpdateActivity.
                intent.putExtra("Id", TempItem);
                intent.putExtra("Name", NameHolder);
                intent.putExtra("Number", NumberHolder);
                intent.putExtra("gender", GENDERHolder);
                intent.putExtra("national_id", National_idHolder);
                intent.putExtra("dob", DobHolder);
                intent.putExtra("marital_status", Marital_statusHolder);
                intent.putExtra("address", AddressHolder);

                startActivity(intent);

                // Finishing current activity after opening next activity.
                finish();

            }
        });

        // Add Click listener on Delete button.
        DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Calling Student delete method to delete current record using Student ID.
                CitizenDelete(TempItem);

            }
        });
    }
    // Method to Delete Student Record
    public void CitizenDelete(final String CitizenID) {

        class CitizenDeleteClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog2 = ProgressDialog.show(ShowSingleRecordActivity.this, "Loading Data", null, true, true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog2.dismiss();

                Toast.makeText(ShowSingleRecordActivity.this, httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

                finish();

            }

            @Override
            protected String doInBackground(String... params) {

                // Sending STUDENT id.
                hashMap.put("CitizenID", params[0]);

                finalResult = httpParse.postRequest(hashMap, HttpUrlDeleteRecord);

                return finalResult;
            }
        }

        CitizenDeleteClass citizenDeleteClass = new CitizenDeleteClass();

        citizenDeleteClass.execute(CitizenID);
    }
    //Method to show current record Current Selected Record
    public void HttpWebCall(final String PreviousListViewClickedItem){

        class HttpWebCallFunction extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                pDialog = ProgressDialog.show(ShowSingleRecordActivity.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                pDialog.dismiss();

                //Storing Complete JSon Object into String Variable.
                FinalJSonObject = httpResponseMsg ;

                //Parsing the Stored JSOn String to GetHttpResponse Method.
                new GetHttpResponse(ShowSingleRecordActivity.this).execute();

            }

            @Override
            protected String doInBackground(String... params) {

                ResultHash.put("CitizenID",params[0]);

                ParseResult = httpParse.postRequest(ResultHash, HttpURL);

                return ParseResult;
            }
        }

        HttpWebCallFunction httpWebCallFunction = new HttpWebCallFunction();

        httpWebCallFunction.execute(PreviousListViewClickedItem);
    }


    // Parsing Complete JSON Object.
    private class GetHttpResponse extends AsyncTask<Void, Void, Void>
    {
        public Context context;

        public GetHttpResponse(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
            try
            {
                if(FinalJSonObject != null)
                {
                    JSONArray jsonArray = null;

                    try {
                        jsonArray = new JSONArray(FinalJSonObject);

                        JSONObject jsonObject;

                        for(int i=0; i<jsonArray.length(); i++)
                        {
                            jsonObject = jsonArray.getJSONObject(i);

                            // Storing Student Name, Phone Number, Gender into Variables.
                            NameHolder = jsonObject.getString("name").toString() ;
                            NumberHolder = jsonObject.getString("phone_no").toString() ;
                            GENDERHolder = jsonObject.getString("gender").toString() ;
                            National_idHolder = jsonObject.getString("national_id").toString() ;
                            DobHolder = jsonObject.getString("dob").toString() ;
                            Marital_statusHolder = jsonObject.getString("marital_status").toString() ;
                            AddressHolder = jsonObject.getString("address").toString() ;

                        }
                    }
                    catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {

            // Setting Citizen Name, Phone Number, Gender into TextView after done all process .
            NAME.setText(NameHolder);
            PHONE_NUMBER.setText(NumberHolder);
            GENDER.setText(GENDERHolder);
            NATIONAL_ID.setText(National_idHolder);
            ADDRESS.setText(AddressHolder);
            DOB.setText(DobHolder);
            MARITAL_STATUS.setText(Marital_statusHolder);

        }
    }
}
