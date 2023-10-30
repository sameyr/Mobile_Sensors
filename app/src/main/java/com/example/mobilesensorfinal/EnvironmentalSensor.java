package com.example.mobilesensorfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EnvironmentalSensor extends AppCompatActivity {
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_environmental_sensor);

        listView = this.findViewById(R.id.listView_environmental);

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);

        List<String> environmentalSensor = new ArrayList<>();
        for(Sensor sensor : sensorList){
            if(sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE ||
                    sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY ||
                    sensor.getType() == Sensor.TYPE_LIGHT||
                    sensor.getType() == Sensor.TYPE_PRESSURE ||
                    sensor.getType() ==Sensor.TYPE_TEMPERATURE){
                    environmentalSensor.add(sensor.getName());
            }
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,environmentalSensor);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = listView.getItemAtPosition(position).toString();
                if (selectedItem.contains("Humidity")) {
                    startActivity(new Intent(EnvironmentalSensor.this, HumiditySensor.class));
                }
                else if(selectedItem.contains("Temperature")){
                    startActivity(new Intent(EnvironmentalSensor.this,AmbientSensor.class));
                }
                else if(selectedItem.contains("Pressure")){
                    startActivity(new Intent(EnvironmentalSensor.this,PressureSensor.class));
                }
                else{
                    Toast.makeText(EnvironmentalSensor.this,"Not Available right now",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}