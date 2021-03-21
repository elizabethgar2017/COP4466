package com.example.elizabethapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.speech.tts.TextToSpeech;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class SpeechActivity extends AppCompatActivity {

    Button goButton;
    Button gpsButton;
    EditText query;
    RequestQueue queue = Volley.newRequestQueue(this);
    public static JSONObject savedResponse;
    Button btnText;
    TextToSpeech textToSpeech;
    SpeechActivity context;


    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    FusedLocationProviderClient fused;
    private final int REQ_CODE = 100;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.home:
                        setContentView(R.layout.activity_main);
                        break;

                    case R.id.speech:
                        setContentView(R.layout.activity_speech);
                        break;
                    case R.id.weathermap:
                        setContentView(R.layout.activity_map);
                        break;
                    case R.id.historical:
                        setContentView(R.layout.activity_historical);
                        break;
                }
                return true;
            }});


        context = this;
        goButton = findViewById(R.id.goButton);
        gpsButton = findViewById(R.id.locButton);
        query = findViewById(R.id.query);
        textView = findViewById(R.id.text);
        btnText = findViewById(R.id.btnText);

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                // if No error is found then only it will run
                if(i!=TextToSpeech.ERROR){
                    // To Choose language of speech
                    textToSpeech.setLanguage(Locale.US);
                }
            }
        });

        // Adding OnClickListener
        btnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textToSpeech.speak(query.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);
            }
        });


    }

    public void onGoClick(View v) {
        // Do something in response to button
        String q = query.getText().toString();
        sendRequest(q);
    }
    //  @RequiresApi(api = Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onGPSClick(View v) {
        if (ContextCompat.checkSelfPermission(this, mPermission)
                != PackageManager.PERMISSION_GRANTED){

            //  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);
            }

        }

        fused = LocationServices.getFusedLocationProviderClient(this);

        fused.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null){
                            sendRequestGPS(location.getLatitude(),location.getLongitude());
                        }
                        else{
                            System.out.println("No GPS");
                        }
                    }
                });
    }


    public void sendRequestGPS(Double lat,Double lon){
        if(queue == null){
            queue =   Volley.newRequestQueue(this);
        }
        String url = (String)getString(R.string.WEATHER_URL_GPS);
        String key = (String)getString(R.string.API_KEY);
        url = url.replace( "LAT_REPLACE",lat.toString());
        url = url.replace( "LON_REPLACE",lon.toString());
        url = url.replace( "API_KEY_REPLACE",key);

        System.out.println("Using url" + url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                        savedResponse = response;
                        Intent intent = new Intent(context, WeatherDisplay.class);
                        startActivity(intent);

                    }
                }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("That didn't work!");
                    }
                });
        queue.add(jsonObjectRequest);
    }

    public void sendRequest(String q){
        if(queue == null){
            queue =   Volley.newRequestQueue(this);
        }
        String url = (String)getString(R.string.WEATHER_URL);
        String key = (String)getString(R.string.API_KEY);
        url = url.replace( "QUERY_REPLACE",q);
        url = url.replace( "API_KEY_REPLACE",key);

        System.out.println("Using url" + url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                        savedResponse = response;
                        Intent intent = new Intent(context, WeatherDisplay.class);
                        startActivity(intent);

                    }
                }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("That didn't work!");
                    }
                });

        queue.add(jsonObjectRequest);
    }
}
