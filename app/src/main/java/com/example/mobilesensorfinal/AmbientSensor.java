package com.example.mobilesensorfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class AmbientSensor extends AppCompatActivity implements SensorEventListener {
    private TextView textView , show_text;
    private Button btn_Start;
    private Button btn_Stop;
    float eventValues;
    float output;
    private SensorManager sensorManager;
    private Sensor tempSensor;
    private boolean isTempSensorAvailable;
    private boolean isPrinting;
    int time = 0;
    RadioButton celsius;
    RadioButton fahrenheit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambient_sensor);
        btn_Start = findViewById(R.id.btn_start);
        btn_Stop = findViewById(R.id.btn_stop);
        textView = findViewById(R.id.txt_sensordata);
        celsius = this.findViewById(R.id.rbt_celsius);
        fahrenheit = this.findViewById(R.id.txt_fah);
        show_text = this.findViewById(R.id.show_text);


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            textView.setText("Press Start to record data");
            textView.setMovementMethod(new ScrollingMovementMethod());
            sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

            if (sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null) {
                tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
                isTempSensorAvailable = true;
            } else {
                isTempSensorAvailable = false;
            }

            btn_Start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textView.setText(null);
                    isPrinting = true;
                    textView.setText("Temperature");
                    content();
                }
            });
            btn_Stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isPrinting = false;
                }
            });
        }
        else{
            Toast.makeText(AmbientSensor.this,"Location Service needed",Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        eventValues = event.values[0];
        if (celsius.isChecked()){
            eventValues = eventValues;
            show_text.setText("Temperature is : " + eventValues + "°C" + "\n" + "Press Start to record" );
        }
        else if (fahrenheit.isChecked()){
            eventValues = ((eventValues)*9/5)+32;
            show_text.setText("Temperature is : " + eventValues + "°F" + "\n" + "Press Start to record" );
        }

    }


    public void content() {
            time++;
            if (time > 0) {
                textView.setText(textView.getText() + "\n" + eventValues );
            if (isPrinting) {
                refresh(1000);
            }
            }

    }
        public void refresh(int millisecond){
            final Handler handler = new Handler();
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    content();
                }
            };
            handler.postDelayed(runnable, millisecond);
        }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (isTempSensorAvailable == true) {
            sensorManager.registerListener(this, tempSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }




    @Override
    protected void onPause() {
        super.onPause();
        if(isTempSensorAvailable){
            sensorManager.unregisterListener(this);
        }
    }
}