package com.example.readPicture;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	protected static final int CHANGE_UI = 1;
	protected static final int ERROR = 0;
	private EditText et_path;
	private ImageView iView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		et_path = (EditText) findViewById(R.id.et_path);
		iView = (ImageView) findViewById(R.id.iv);
	}

	private final Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			if (msg.what == CHANGE_UI) {
				Bitmap bitmap = (Bitmap) msg.obj;
				iView.setImageBitmap(bitmap);
			} else if (msg.what == ERROR) {
				Toast.makeText(MainActivity.this, "显示失败！", 0).show();
			}
		};
	};

	public void click(View view) {
		final String pathString = et_path.getText().toString().trim();
		if (TextUtils.isEmpty(pathString)) {
			Toast.makeText(this, "not null", 0).show();

		} else {
			new Thread() {
				@Override
				public void run() {
					try {
						// 根据url发送http的请求
						URL url = new URL(pathString);
						HttpURLConnection connection = (HttpURLConnection) url
								.openConnection();
						connection.setRequestMethod("GET");
						connection.setConnectTimeout(5000);
						connection
								.setRequestProperty(
										"User-Agent",
										"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.101 Safari/537.36");
						// 得到服务器响应码
						int code = connection.getResponseCode();
						if (code == 200) {
							InputStream inputStream = connection
									.getInputStream();
							Bitmap bitmap = BitmapFactory
									.decodeStream(inputStream);
							// 子线程
							// iView.setImageBitmap(bitmap);
							// 告诉主线程一个消息，帮我更改，内容：bitmap
							Message msg = new Message();
							msg.what = CHANGE_UI;
							msg.obj = bitmap;

							handler.sendMessage(msg);

						} else {
							Message msg = new Message();
							msg.what = ERROR;
							handler.sendMessage(msg);
						}

					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						Message msg = new Message();
						msg.what = ERROR;
						handler.sendMessage(msg);
					}
				};
			}.start();
		}
	}
}
