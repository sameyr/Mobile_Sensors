package com.example.mobilesensorfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class AvailableSensor extends AppCompatActivity {
    TextView textView;
    List<Sensor> deviceSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_sensor);
        textView = this.findViewById(R.id.sensorContainer);
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        deviceSensor = sensorManager.getSensorList(Sensor.TYPE_ALL);

        for (Sensor sensor : deviceSensor) {
            textView.setText(textView.getText() + "\n" + sensor.getName());
        }
    }
}