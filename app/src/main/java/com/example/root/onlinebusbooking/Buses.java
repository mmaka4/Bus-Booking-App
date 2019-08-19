package com.example.root.onlinebusbooking;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

/**
 * Created by root on 2019/03/16.
 */

public class Buses extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    BusAdapter busAdapter;

    private static String TAG = Buses.class.getSimpleName();
    private static String url = "http://192.168.43.233/onlineBusbooking/buses.php";

    ArrayList<Items> items;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from new_activity.xml
        setContentView(R.layout.activity_buses);

        recyclerView = findViewById(R.id.Recycler);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));

        Log.d(TAG, "onCreate: ");
        recyclerView.setAdapter(busAdapter);

        new ItemsTask().execute();
        //return rootView;
    }

    private class ItemsTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {

            HttpHandler httpHandler = new HttpHandler();
            String resultJson = httpHandler.makeServiceCall(url);
            Log.d(TAG, "resultJson=" + resultJson);
            items = new ArrayList<>();

            try {
                // FIXME: 08/18/2019 NullPointerException
                JSONObject jsonObject = new JSONObject(resultJson);
                JSONArray jsonArray = jsonObject.getJSONArray("buses_list");
                Log.d(TAG, "doInBackground: "+jsonArray);

                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonData = jsonArray.getJSONObject(i);

                    String bus_id = jsonData.getString("BId");
                    String busName = jsonData.getString("BName");
                    String numSeats = jsonData.getString("NumOfSeats");
                    String busClass = jsonData.getString("BClass");
                    String busPrice = jsonData.getString("BPrice");

//                    Log.d("BusId : ", bus_id);
//                    Log.d("busName : ", busName);
//                    Log.d("numSeats : ", numSeats);
//                    Log.d("busPrice : ", busPrice);
//                    Log.d("busClass : ", busClass);


                    items.add(new Items(bus_id,busName, Integer.parseInt(numSeats),Integer.parseInt(busPrice), busClass));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            busAdapter = new BusAdapter(items,getApplicationContext());
            recyclerView.setAdapter(busAdapter);
        }
    }
}
