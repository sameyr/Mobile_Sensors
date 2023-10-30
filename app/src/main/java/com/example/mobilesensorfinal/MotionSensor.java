package com.example.mobilesensorfinal;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MotionSensor extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion_sensor);

        listView = (ListView) this.findViewById(R.id.listView);

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);

        List<String> sensorNames = new ArrayList<>();
        for (Sensor sensor : sensorList) {
            if (sensor.getType() == Sensor.TYPE_ACCELEROMETER ||
                    sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD||
                    sensor.getType() == Sensor.TYPE_GYROSCOPE ||
                    sensor.getType() == Sensor.TYPE_GRAVITY ||
                    sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
                sensorNames.add(sensor.getName());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, sensorNames);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = listView.getItemAtPosition(position).toString();
                if (selectedItem.contains("Magnetic")) {
                    startActivity(new Intent(MotionSensor.this, MagneticSensor.class));
                }
                else if(selectedItem.contains("Accelerometer")){
                    startActivity(new Intent(MotionSensor.this,AccelerometerSensor.class));
                }
            }
        });
    }
}