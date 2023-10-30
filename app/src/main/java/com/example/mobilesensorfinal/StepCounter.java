package com.example.mobilesensorfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import org.w3c.dom.Text;

public class StepCounter extends AppCompatActivity implements SensorEventListener {
private SensorManager sensorManager;
private Sensor mStepCounter;
private boolean isCounterSensorPresent;
private TextView txt_stepCounter , txt_stepDetector;
private int stepCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_counter);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // keeps screen on

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mStepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        txt_stepCounter = this.findViewById(R.id.txt_stepCounter);
        txt_stepDetector = this.findViewById(R.id.txt_stepdetector);

        if (mStepCounter != null){
                isCounterSensorPresent = true;
        }
        else{
            txt_stepCounter.setText("Sorry Sensor not available.");
            isCounterSensorPresent = false;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mStepCounter != null) {
            sensorManager.registerListener(this,mStepCounter,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mStepCounter !=null){
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor == mStepCounter){
            stepCount = (int) event.values[0];
            txt_stepCounter.setText(String.valueOf(stepCount));
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}