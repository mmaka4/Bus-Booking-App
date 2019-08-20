package com.example.root.onlinebusbooking;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by User on 7/24/2019.
 */

public class BusAdapter extends RecyclerView.Adapter<BusAdapter.ProductViewHolder> {
    ArrayList<Items> items = new ArrayList<>();
    Context context;
    int id;

    private static String TAG = BusAdapter.class.getSimpleName();

    private String wishlist_url = "http://192.168.43.233/onlineBusbooking/buses.php";

    public BusAdapter(ArrayList<Items> items, Context context) {
        super();
        this.items = items;
        this.context = context;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView busName;
        TextView busPrice;
        TextView seats;
        TextView busClass;
        ImageView image;
        RelativeLayout parentLayout;
        //private CardView card;


        public ProductViewHolder(View itemView) {
            super(itemView);
            busName = itemView.findViewById(R.id.name);
            busPrice = itemView.findViewById(R.id.busfare);
            seats = itemView.findViewById(R.id.numOfSeats);
            image = itemView.findViewById(R.id.pic);
            busClass = itemView.findViewById(R.id.bus_class);
            parentLayout = itemView.findViewById(R.id.f1);
        }
    }


    @Override
    public BusAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bus_menu,null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BusAdapter.ProductViewHolder holder, final int position) {
        holder.busName.setText( items.get(position).getBusName());
        holder.seats.setText(String.valueOf(items.get(position).getNumOfSeats()));
        holder.busPrice.setText( String.valueOf(items.get(position).getBPrice()));
        holder.busClass.setText( String.valueOf(items.get(position).getBClass()));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Departure.class);
                context.startActivity(intent);
            }
        });
        //Glide.with(context).load("http://192.168.43.233/android/img/"+items.get(position).getImage()).into(holder.image);
        //Log.d(TAG, "onBindViewHolder: "+ items.get(position).getBPrice());

        }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
