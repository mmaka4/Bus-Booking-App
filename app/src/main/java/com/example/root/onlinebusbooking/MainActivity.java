package com.example.root.onlinebusbooking;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.isapanah.awesomespinner.AwesomeSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,DatePickerDialog.OnDateSetListener {

    ArrayList<String> buses;
    TextView dTime, rTime, ona;
    ImageView dView, rView;
    AwesomeSpinner fromSpinner, toSpinner;
    RequestQueue requestQueue;
    String date;
    private String jsonResponse;
    int dStatus=0,rStatus=0;
    Button contBtn;
    ActionBar toolbar;

    private static String url = "http://192.168.43.233/onlineBusbooking/regions.php";
    private static String TAG = "MainActivity";

    ArrayList<Booking> arrayList = new ArrayList<>();
    static ArrayList<Items> items;
    ArrayList<Button> seats = new ArrayList<>();

    DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("On create");
        Log.d("On CLick: ","Imefika");

        buses = new ArrayList<>();

        toolbar = getSupportActionBar();

        BottomNavigationView navigation = findViewById(R.id.navigationView);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        toolbar.setTitle("Trip");

        //actionBar = findViewById(R.id.navigationView);



        loadSpinnerData();
        dTime = findViewById(R.id.departuretime);
        rTime = findViewById(R.id.returntime);
        //ona = findViewById(R.id.ona);
        dView = findViewById(R.id.img1);
        dView.setOnClickListener(this);
        rView = findViewById(R.id.img2);
        rView.setOnClickListener(this);
        fromSpinner = findViewById(R.id.fromspin);
        toSpinner = findViewById(R.id.tospin);
        //fromSpinner.setOnClickListener(this);
        contBtn = findViewById(R.id.contBtn);
        contBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, Buses.class);
                startActivity(myIntent);
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int month, int day) {
                Toast.makeText(getApplicationContext(), "selected date is " + view.getYear() +
                        " / " + (view.getMonth()+1) +
                        " / " + view.getDayOfMonth(), Toast.LENGTH_SHORT).show();
                int add=1;
                month+=add;
                //dTime.setText(day+"/"+month+"/"+year);
                date = day+"/"+month+"/"+year;
                if (dStatus == 1){
                    dTime.setText(date);
                    dStatus=0;
                }else if(rStatus== 1){
                    rTime.setText(date);
                    rStatus=0;
                }

//                Date date = new Date(year,month,day,hh,mm);

//                Date argDate = new Date(); //set your date.
//                String argTime1 = "04:00"; //9 AM - 24 hour format :- Set your time.
//                String argTime2 = "00:30";
//                String argTime3 = "02:00";
//                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
//                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
//                dateTime = sdf.format(argDate) + " " + argTime1;
//                dateTime2 = sdf.format(argDate) + " " + argTime2;
//                dateTime3 = sdf.format(argDate) + " " + argTime3;
//
//                date = sdf.format(argDate);
//                Date requiredDate1;
//                Date requiredDate2;
//                Date requiredDate3;
//                try {
//                    requiredDate1 = dateFormat.parse(dateTime);
//                    requiredDate2 = dateFormat.parse(dateTime2);
//                    requiredDate3 = dateFormat.parse(dateTime3);
//                    Log.d("CurrDate",""+dateTime);
//                    Log.d("reqDate",""+requiredDate1);
//
//                    if(argDate.after(requiredDate1)){
//                        time1.setClickable(false);}
//                    if(argDate.after(requiredDate2)){
//                        time2.setClickable(false);}
//                    if(argDate.after(requiredDate3)){
//                        time3.setClickable(false);}
//
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }

//                Date newDate = new Date(currTime.getTime() + 1 * HOUR1);
//                Date dateTime3 = new Date(year,month,day,20,0);
//                Log.d(TAG, "onDateSet: "+date.getTime());
            }
        };
        Log.d(TAG, "onCreate: ");
        //new ItemsTask().execute();

        fromSpinner.setOnSpinnerItemClickListener(new AwesomeSpinner.onSpinnerItemClickListener<String>() {
            @Override
            public void onItemSelected(int position, String itemAtPosition) {
                //TODO YOUR ACTIONS
            }
        });

        toSpinner.setOnSpinnerItemClickListener(new AwesomeSpinner.onSpinnerItemClickListener<String>() {
            @Override
            public void onItemSelected(int position, String itemAtPosition) {
                //TODO YOUR ACTIONS
            }
        });
    }

    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            return false;
        }
    };

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.img1:
                dStatus=1;
                showDatePicker();
                //dTime.setText(date);
                Log.d("On CLick: ","Imefika");
//                for (Button btn : seats) {
//                    btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.btnunclicked));
//                }
                break;

            case R.id.img2:
                rStatus=1;
                showDatePicker();
                //rTime.setText(date);
                Log.d("On CLick2: ","Imefika");
//                for (Button btn : seats) {
//                    btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.btnunclicked));
//                }
                break;

            case R.id.contBtn:

                //AppControllerVolley.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);
                //requestQueue.add(jsonObjReq);
                break;

            default:
                break;
        }
    }

    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar cal = new GregorianCalendar(year, month, day);
        setDate(cal);
    }

    private void setDate(final Calendar calendar) {
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        //((TextView) findViewById(R.id.showdate)).setText(dateFormat.format(calendar.getTime()));

    }

    public void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 0);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 0);

//Set yesterday time milliseconds as date pickers minimum date
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        datePickerDialog.show();

        //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
    }

    private void loadSpinnerData (){
        System.out.println("Spinner");
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {

//                            JSONObject phone = response.getJSONObject("regions_list");
//                            String home = phone.getString("regName");
                    //String mobile = phone.getString("mobile");

                    JSONArray jsonArray=response.getJSONArray("regions_list");

                    for(int i=0;i<jsonArray.length();i++) {

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        String country = jsonObject1.getString("regName");
                        buses.add(country);
                        //System.out.println("XXXXXXXXX"+country);
                    }

                    //ona.append(jsonResponse);
                    fromSpinner.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, buses));
                    toSpinner.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, buses));

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error1: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error2: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

   /* static class ItemsTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {

            HttpHandler httpHandler = new HttpHandler();
            String resultJson = httpHandler.makeServiceCall(url);
            Log.d(TAG, "resultJson=" + resultJson);
            Log.d(TAG, "XXXXXXXX=");
            items = new ArrayList<>();

//            try {
//                // FIXME: 11/29/2017 NullPointerException
//                JSONObject jsonObject = new JSONObject(resultJson);
//                JSONArray jsonArray = jsonObject.getJSONArray("regions_list");
//
//                Log.d(TAG, "doInBackground: "+jsonArray);
//
//                for(int i=0;i<jsonArray.length();i++){
//                    JSONObject jsonData = jsonArray.getJSONObject(i);
//
//                    String reg_id = jsonData.getString("reg_id");
//                    String regName = jsonData.getString("regName");
//                    String busStop = jsonData.getString("busStop");
//                    //String image = jsonData.getString("image");
//
//                    Log.d(TAG, "Name "+regName);
//                    items.add(new Items(Integer.parseInt(reg_id),regName,busStop));
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
            return null;
        }


    }  */
}

