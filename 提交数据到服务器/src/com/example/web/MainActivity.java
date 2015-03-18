package com.example.web;


import com.example.web.service.LoginService;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText et_username;
	private EditText et_password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		et_username = (EditText) findViewById(R.id.et_username);
		et_password = (EditText) findViewById(R.id.et_password);
	}
	
	//get
	public void click1(View view) {
		final String username = et_username.getText().toString().trim();
		final String password = et_password.getText().toString().trim();
		System.out.println(username+"===="+password);
		new Thread(){
			public void run() {
				final String result = LoginService.loginByGet(username, password);
				if (result != null) {
					runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(MainActivity.this, result, 0).show();
						}
					});
				}else {
					runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(MainActivity.this, "请求失败！", 0).show();
						}
					});
				}
			}
		}.start();
	}
	
	//post
	public void click2(View view) {
		final String username = et_username.getText().toString().trim();
		final String password = et_password.getText().toString().trim();
		
		new Thread(){
			public void run() {
				final String result = LoginService.loginByPost(username, password);
				if (result != null) {
					runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(MainActivity.this, result, 0).show();
						}
					});
				}else {
					runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(MainActivity.this, "请求失败！", 0).show();
						}
					});
				}
			}
		}.start();
	}
	
	//httpclientget
	public void click3(View view) {
		final String username = et_username.getText().toString().trim();
		final String password = et_password.getText().toString().trim();
		System.out.println(username+"===="+password);
		new Thread(){
			public void run() {
				final String result = LoginService.loginByClientGet(username, password);
				if (result != null) {
					runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(MainActivity.this, result, 0).show();
						}
					});
				}else {
					runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(MainActivity.this, "请求失败！", 0).show();
						}
					});
				}
			}
		}.start();
	}
	//httpclientPost
	public void click4(View view) {
		final String username = et_username.getText().toString().trim();
		final String password = et_password.getText().toString().trim();
		System.out.println(username+"===="+password);
		new Thread(){
			public void run() {
				final String result = LoginService.loginByClientPost(username, password);
				if (result != null) {
					runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(MainActivity.this, result, 0).show();
						}
					});
				}else {
					runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(MainActivity.this, "请求失败！", 0).show();
						}
					});
				}
			}
		}.start();
	}
}
