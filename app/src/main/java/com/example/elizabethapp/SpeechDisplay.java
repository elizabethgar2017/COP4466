package com.example.elizabethapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class SpeechDisplay extends AppCompatActivity {

    TextView cityView;
    TextView tempView;
    TextView humidityView;
    TextView latView;
    TextView lonView;
    TextView feelsView;
    TextView minView;
    TextView maxView;
    TextView pressureView;
    TextView countryView;
    public static String name;
    public static Double lat;
    public static Double lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_display);

        cityView =  findViewById(R.id.cityView);
        tempView =  findViewById(R.id.tempView);
        humidityView = findViewById(R.id.humidityView);
        latView = findViewById(R.id.latView);
        lonView = findViewById(R.id.lonView);
        feelsView = findViewById(R.id.feelsView);
        minView = findViewById(R.id.minView);
        maxView = findViewById(R.id.maxView);
        pressureView = findViewById(R.id.pressureView);
        countryView = findViewById(R.id.countryView);

        JSONObject weatherdata = com.example.elizabethapp.SpeechActivity.savedResponse;


        JSONObject coord = null;
        JSONObject main = null;
        JSONArray weather = null;
        JSONObject sys = null;
        JSONObject innerweather = null;

        try {
            coord = weatherdata.getJSONObject("coord");
            main = weatherdata.getJSONObject("main");
            weather = weatherdata.getJSONArray("weather");
            innerweather = weather.getJSONObject(0);
            sys = weatherdata.getJSONObject("sys");
        }
        catch(JSONException e){
            System.out.println("Something went wrong while parsing");
        }

        if(coord != null && main != null && weather != null && sys != null){
            try {
                cityView.setText("Weather for " + weatherdata.getString("name"));
                name = weatherdata.getString("name");
                tempView.setText("Temperature: " + main.getString("temp") + "F");
                humidityView.setText("Humidity: " + main.getString("humidity"));
                feelsView.setText("Feels like: " + main.getString("feels_like") + "F");
                pressureView.setText("Pressure: " + main.getString("pressure"));
                minView.setText("Min temp: " + main.getString("temp_min") + "F");
                maxView.setText("Max temp: " + main.getString("temp_max") + "F");
                countryView.setText("Country: " + sys.getString("country"));
                lonView.setText("Longitude: " + coord.getString("lon"));
                latView.setText("Latitude: " + coord.getString("lat"));
                lat = coord.getDouble("lat");
                lon = coord.getDouble("lon");
            }
            catch(JSONException e){
                System.out.println("Something went wrong while parsing again");

            }
        }
    }

    public void onMapBtnClick(View v){
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }
}