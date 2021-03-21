package com.example.elizabethapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
public class WeatherMapActivity extends AppCompatActivity {
    TextView cityName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_map);
        cityName = findViewById(R.id.cityMapView);
        cityName.setText("Map of " + MapDisplay.name);
    }
}