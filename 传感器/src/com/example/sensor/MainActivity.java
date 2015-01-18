package com.example.sensor;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity implements OnClickListener {

	private MyListener listener;
	private ImageView iv;
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		iv = (ImageView) findViewById(R.id.iv);
		button = (Button) findViewById(R.id.btn);
		button.setOnClickListener(this);

		SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

		Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

		listener = new MyListener();
		sensorManager.registerListener(listener, sensor,
				SensorManager.SENSOR_DELAY_GAME);

	}


	private class MyListener implements SensorEventListener {

		float startangle = 0;
		@Override
		public void onSensorChanged(SensorEvent event) {
			// TODO Auto-generated method stub
			float[] values = event.values;
			float angle = values[0];
			System.out.println("方向:" + angle);
			
			RotateAnimation ra = new RotateAnimation(startangle, angle,
					Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 0.5f);
			ra.setDuration(100);
			iv.startAnimation(ra);
			startangle = -angle;
			// Toast.makeText(MainActivity.this, "光亮:" + angle, 1).show();

		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub

		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, TestSensorActivity.class);
		startActivity(intent);
	}
}
