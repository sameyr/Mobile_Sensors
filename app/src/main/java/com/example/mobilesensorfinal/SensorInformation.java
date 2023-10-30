package com.example.mobilesensorfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class SensorInformation extends AppCompatActivity {

    private Spinner sensorSP;
    private List sensorList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_information);

        sensorSP = findViewById(R.id.sensorSP);

        sensorList =new ArrayList<>();
        sensorList.add("Choose Sensor");

        sensorList.add("Motion Sensors");
        sensorList.add("Position Sensors");
        sensorList.add("Environmental Sensors");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,sensorList);
        sensorSP.setAdapter(arrayAdapter);
        sensorSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sensorSP.getSelectedItem().toString().equals("Motion Sensors")) {
                    startActivity(new Intent(SensorInformation.this, MotionSensor.class));
                }
                else if (sensorSP.getSelectedItem().toString().equals("Position Sensors")) {
                    startActivity(new Intent(SensorInformation.this, PositionSensor.class));
                }
                else if (sensorSP.getSelectedItem().toString().equals("Environmental Sensors")) {
                    startActivity(new Intent(SensorInformation.this, EnvironmentalSensor.class));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
}