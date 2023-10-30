package com.example.mobilesensorfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class Compass extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accel_sensor;
    private Sensor magnet_sensor;
    private ImageView compass_image;
    private TextView txt_compass;

    private float[] lastAccelerometer = new float[3];
    private float[] lastMagentoMeter = new float[3];
    private float[] rotationMatrix = new float[9];
    private float[] orientation = new float[3];

    private boolean isLastAccelerometerCopied = false ,
            isLastMagnetoMeterCopied = false;

    long lastUpadtedTime = 0 ;
    float currentDegree = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

        compass_image = findViewById(R.id.compass_image);
        txt_compass = findViewById(R.id.txt_compass);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accel_sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnet_sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == accel_sensor){
            System.arraycopy(event.values,0,lastAccelerometer,0,event.values.length);
            isLastAccelerometerCopied = true;
        }
        else if (event.sensor == magnet_sensor){
            System.arraycopy(event.values,0,lastMagentoMeter,0,event.values.length);
            isLastMagnetoMeterCopied = true;
        }

        if(isLastAccelerometerCopied && isLastMagnetoMeterCopied && (System.currentTimeMillis()- lastUpadtedTime > 250 )){
            SensorManager.getRotationMatrix(rotationMatrix,null,lastAccelerometer,lastMagentoMeter);
            SensorManager.getOrientation(rotationMatrix,orientation);

            float azimuthInRad = orientation[0];
            float azimuthInDeg = (float) Math.toDegrees(azimuthInRad);

            RotateAnimation rotateAnimation =
                        new RotateAnimation(currentDegree, -azimuthInDeg , Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);

            rotateAnimation.setDuration(250);
            rotateAnimation.setFillAfter(true);
            compass_image.startAnimation(rotateAnimation);

            currentDegree = -azimuthInDeg;
            lastUpadtedTime = System.currentTimeMillis();


            int X = (int) azimuthInDeg + 180;
            txt_compass.setText(X + "°" );
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        sensorManager.registerListener(this,accel_sensor,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,magnet_sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this,accel_sensor);
        sensorManager.unregisterListener(this,magnet_sensor);
    }
}