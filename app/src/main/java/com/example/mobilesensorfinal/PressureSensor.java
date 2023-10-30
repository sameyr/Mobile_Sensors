package com.example.mobilesensorfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class PressureSensor extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor pressure_Sensor;
    private RadioButton milliBar;
    private RadioButton Pascal;
    private TextView txt_pressure;
    private TextView txt_listPressure;
    private Button btn_start;
    private Button btn_stop;
    private boolean isPressureSensorAvailable , isPrinting = false;
    private float sensorValue;
    private int time = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pressure_sensor);

        txt_pressure = this.findViewById(R.id.txt_pressure);
        milliBar = this.findViewById(R.id.rdb_millibar);
        Pascal = this.findViewById(R.id.rdb_pascal);
        txt_listPressure = this.findViewById(R.id.txt_listPressure);
        btn_start = this.findViewById(R.id.btn_Pressurestart);
        btn_stop = this.findViewById(R.id.btnPressurestop);

       sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
       pressure_Sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
       txt_listPressure.setMovementMethod(new ScrollingMovementMethod());

        if(pressure_Sensor != null){
            isPressureSensorAvailable = true;
        }
        else {
            txt_pressure.setText("Pressure Sensor not Available");
            isPressureSensorAvailable = false;
        }
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_listPressure.setText(null);
                isPrinting = true;
                txt_listPressure.setText("Pressure Value ");
                context();

            }
        });
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPrinting=false;
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        sensorValue=event.values[0];
        if (milliBar.isChecked()){
            txt_pressure.setText("Pressure is : " + sensorValue + "mbar. Press 'Start' to start recording.");
        }
        else if(Pascal.isChecked()){
            sensorValue = sensorValue/100;

            txt_pressure.setText("Pressure is : " + sensorValue + "Pa. Press 'Start' to start recording.");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,pressure_Sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    public void context(){
        time++;
        if(time > 0){
            txt_listPressure.setText(txt_listPressure.getText() + "\n" + sensorValue);
        }
        if(isPrinting == true){
            refresh(1000);
        }
    }

    public void refresh(int millisecond){
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                context();
            }
        };
        handler.postDelayed(runnable, millisecond);
    }
}