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

public class MagneticSensor extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager; // class that allow user to use sensor available
    private Sensor magneticSensor;         // senor variable to store sensor information
    private TextView txt_magnetic;
    private RadioButton teslaButton,gaussButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magnetic_sensor);
        txt_magnetic = findViewById(R.id.txt_magnetic);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE); // get an instance of the class using this function with this argument
        magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        teslaButton = this.findViewById(R.id.radio_tesla);
        gaussButton = this.findViewById(R.id.radio_gauss);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        float x = values[0];
        float y = values[1];
        float z = values[2];
        if (teslaButton.isChecked()) {
            txt_magnetic.setText(String.format("X: %f ∏T\nY:%f ∏T\nZ: %f ∏T", x, y, z));
        }
        else if (gaussButton.isChecked()){
            x = x/100;
            y = y/100;
            z = z/100;
            txt_magnetic.setText(String.format("X: %f G\nY:%f G\nZ: %f G", x, y, z));
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        sensorManager.registerListener(this,magneticSensor,SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);

    }


}