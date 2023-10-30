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
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class PositionSensor extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_sensor);

        listView = this.findViewById(R.id.listView_positionSensor);

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);

        List<String> environmentalSensor = new ArrayList<>();
        for(Sensor sensor : sensorList){
            if(sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD ||
                    sensor.getType() == Sensor.TYPE_ORIENTATION||
                    sensor.getType() == Sensor.TYPE_PROXIMITY) {
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
                if (selectedItem.contains("Magnetic")) {
                    startActivity(new Intent(PositionSensor.this, MagneticSensor.class));
                }
            }
        });

    }
}