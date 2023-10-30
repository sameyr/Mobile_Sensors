package com.example.mobilesensorfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class HumiditySensor extends AppCompatActivity implements SensorEventListener{
    private TextView txt_humidity;
    private SensorManager sensorManager;
    private Sensor humidity_sensor;
    private boolean isHumidityAvailable;
    private float sensorValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humidity_sensor);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        txt_humidity = findViewById(R.id.txt_humidity);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)!=null) {
            humidity_sensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
            isHumidityAvailable = true;
        }
        else{
            isHumidityAvailable = false;
            txt_humidity.setText("Sorry, Sensor not available");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        sensorValue = event.values[0];
        txt_humidity.setText("Humidity is : " + sensorValue + "%");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isHumidityAvailable){
        sensorManager.registerListener(this,humidity_sensor,SensorManager.SENSOR_DELAY_NORMAL);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isHumidityAvailable) {
            sensorManager.unregisterListener(this);
        }
    }
}