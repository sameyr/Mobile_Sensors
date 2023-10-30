package com.example.mobilesensorfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AccelerometerSensor extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private TextView txt_accelerometer;
    private RadioButton radio_ms;
    private RadioButton radio_kms;
    private boolean isAccelAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer_sensor);
        txt_accelerometer = findViewById(R.id.txt_accelerometer);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        radio_kms = this.findViewById(R.id.radio_kms);
        radio_ms = this.findViewById(R.id.radio_ms);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) !=null){
            isAccelAvailable = true;
        }
        else{
            isAccelAvailable = false;
            txt_accelerometer.setText("Sorry, Sensor not Available");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        float x = values[0];
        float y = values[1];
        float z = values [2];
        if (radio_ms.isChecked()) {
            txt_accelerometer.setText(String.format("X: %f m/s² \nY: %f m/s² \nZ: %f m/s²", x, y, z));
        }
        else if(radio_kms.isChecked()){
            x = x/1000;
            y = y/1000;
            z = z/1000;
            txt_accelerometer.setText(String.format("X: %f km/s² \nY: %f km/s² \nZ: %f km/s²", x, y, z));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(isAccelAvailable) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (isAccelAvailable) {
            sensorManager.unregisterListener(this);
        }
    }
}