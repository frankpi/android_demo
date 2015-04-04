package com.example.readweb;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.readweb.utils.StreamTools;

public class MainActivity extends Activity {

	protected static final int ERROR = 1;
	protected static final int SHOW_TEXT = 2;
	private TextView tv_content;
	private EditText et_path;
	//1.主线程创建消息处理器Handler
	// 定义一个消息处理器
	private final Handler handler = new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case ERROR:
				Toast.makeText(MainActivity.this, "获取数据失败", 0).show();
				break;
			case SHOW_TEXT:
				String text = (String) msg.obj;
				tv_content.setText(text);
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv_content = (TextView) findViewById(R.id.tv_content);
		et_path = (EditText) findViewById(R.id.et_path);
	}

	public void click(View view) {
		final String path = et_path.getText().toString().trim();
		if (TextUtils.isEmpty(path)) {
			Toast.makeText(this, "not null", 0).show();
		} else {
			new Thread() {
				@Override
				public void run() {
					try {
						URL url = new URL(path);
						HttpURLConnection conn = (HttpURLConnection) url
								.openConnection();
						conn.setRequestMethod("GET");
						conn.setConnectTimeout(5000);
						conn.setRequestProperty("User-Agent",
								"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.101 Safari/537.36");
						conn.setRequestProperty("Accept-Language", "zh-cn");

						int code = conn.getResponseCode();

						if (code == 200) {
							InputStream is = conn.getInputStream();
							String result = StreamTools.readInputStream(is);

							// tv_content.setText(result);
							System.out.println("niaho");
							Message msg = new Message();
							msg.what = SHOW_TEXT;
							msg.obj = result;
							handler.sendMessage(msg);
						} else {
							System.out.println("code:" + code);
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
				}
			}.start();
		}
	}
}
