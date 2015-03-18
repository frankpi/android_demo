package com.example.web.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StreamTools {

	public static String readInputStream(InputStream is) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int len = 0;
			byte[] buffer = new byte[1024];
			while ((len = is.read(buffer)) !=-1) {
				baos.write(buffer, 0, len);
			}
			is.close();
			baos.close();
			byte[] result = baos.toByteArray();
			
			//解析result里面的字符串
			return new String(result);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "获取失败！";
		}
	}
}
