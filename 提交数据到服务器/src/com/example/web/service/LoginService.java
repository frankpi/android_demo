package com.example.web.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.example.web.utils.StreamTools;

public class LoginService {

	public static String loginByGet(String username, String password) {
		try {
			// 提交到服务器端
			// 拼接路径
			String path = "http://10.15.168.211:8080/webandroid/LoginServlet?username="
					+ URLEncoder.encode(username)
					+ "&password="
					+ URLEncoder.encode(password);
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			int code = conn.getResponseCode();

			if (code == 200) {
				InputStream is = conn.getInputStream();
				String text = StreamTools.readInputStream(is);
				return text;
			} else {
				return null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 */

	public static String loginByPost(String username, String password) {
		try {
			// 提交到服务器端
			String path = "http://10.15.168.211:8080/webandroid/LoginServlet";
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("POST");

			// 准备数据
			String data = "username=" + username + "&password=" + password;
			conn.setRequestProperty("Content-type",
					"application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", data.length() + "");

			// post 的方式，实际上是浏览器把数据写给了服务器
			conn.setDoOutput(true);
			OutputStream os = conn.getOutputStream();
			os.write(data.getBytes());

			int code = conn.getResponseCode();
			if (code == 200) {
				// 请求成功
				InputStream is = conn.getInputStream();
				String text = StreamTools.readInputStream(is);
				return text;
			} else {
				return null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 采用httpclient get请求
	 * 
	 * @param username
	 * @param password
	 * @return null,登录异常
	 * 
	 */

	public static String loginByClientGet(String username, String password) {
		try {
			// 1,打开浏览器
			HttpClient client = new DefaultHttpClient();
			// 2,输入地址
			String path = "http://10.15.168.211:8080/webandroid/LoginServlet?username="
					+ URLEncoder.encode(username)
					+ "&password="
					+ URLEncoder.encode(password);
			HttpGet httpGet = new HttpGet(path);
			// 3,敲回车,进入

			HttpResponse response = client.execute(httpGet);
			int code = response.getStatusLine().getStatusCode();
			if (code == 200) {
				InputStream is = response.getEntity().getContent();
				String text = StreamTools.readInputStream(is);
				return text;
			} else {
				return null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 采用httpclient post请求
	 * 
	 * @param username
	 * @param passwordz
	 * @return
	 */
	public static String loginByClientPost(String username, String password) {
		try {
			// 1,打开一个浏览器
			HttpClient client = new DefaultHttpClient();
			// 2,输入地址
			String path = "http://10.15.168.211:8080/webandroid/LoginServlet";
			// 3,指定要提交的数据实体
			HttpPost httpPost = new HttpPost(path);
			List<NameValuePair> paraList = new ArrayList<NameValuePair>();
			paraList.add(new BasicNameValuePair("username", username));
			paraList.add(new BasicNameValuePair("password", password));
			httpPost.setEntity(new UrlEncodedFormEntity(paraList, "utf-8"));

			// 3,客户端执行发送到服务器端
			HttpResponse response = client.execute(httpPost);
			// 4，得到返回码
			int code = response.getStatusLine().getStatusCode();
			if (code == 200) {
				// 请求成功，得到响应提示
				InputStream is = response.getEntity().getContent();
				// 把流转化为文本；
				String text = StreamTools.readInputStream(is);
				return text;
			} else {
				return null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
