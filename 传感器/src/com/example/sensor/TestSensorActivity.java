package com.example.sensor;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;


public class TestSensorActivity extends Activity implements SensorEventListener {
	private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    
    private TextView tvx,tvy,tvz;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second);
		 mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
         mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
         tvx = (TextView) findViewById(R.id.tvx);
         tvy = (TextView) findViewById(R.id.tvy);
         tvz = (TextView) findViewById(R.id.tvz);
 
	}

     @Override
	protected void onResume() {
         super.onResume();
         mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
     }

     @Override
	protected void onPause() {
         super.onPause();
         mSensorManager.unregisterListener(this);
     }

     @Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
    	 
     }
     
     @Override
	public void onSensorChanged(SensorEvent event) {
    	 float[] values = event.values;
    	 float x = values[0];
    	 float y = values[1];
    	 float z = values[2];
//    	 System.out.println("大小："+x+","+y+","+z);
//    	 System.out.println("光度大小："+x);
    	 
		tvx.setText("x:" + x);
		tvy.setText("y:" + y);
		tvz.setText("z:" + z);
//    	 Toast.makeText(this, "光度大小："+x, 0).show();
     }
 }
 