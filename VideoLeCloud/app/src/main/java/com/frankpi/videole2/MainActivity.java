package com.frankpi.videole2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.VideoView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.frankpi.utils.Log2;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;


import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity implements OnClickListener,
        OnErrorListener {

    private WebView webView1;
    private static String TAG = "gameassist";
    private TextView tvPut;
    private TextView tvStart;
    private EditText tvLog;
    private OkHttpClient mOkHttpClient = new OkHttpClient();
    private String strMp41, strMp42, strMp43;
    private JSONObject data2;
    protected JSONArray dataArray;
    private int i = -1;
    private boolean isAuto = true;

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
//        Log2.sendMailMessage("异常退出");
        Log.i(TAG, "ondestroy");
        Intent intentActivity = new Intent(this, MainActivity.class);
        startActivity(intentActivity);
        super.onDestroy();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        webView1 = (WebView) findViewById(R.id.webView1);
        findViewById(R.id.tv_leshi).setOnClickListener(this);
        findViewById(R.id.tv_parse).setOnClickListener(this);
        findViewById(R.id.tv_parse2).setOnClickListener(this);
        tvLog = (EditText) findViewById(R.id.tv_log);
        tvLog.setMovementMethod(ScrollingMovementMethod.getInstance());

        findViewById(R.id.tv_clear_log).setOnClickListener(this);
        tvPut = (TextView) findViewById(R.id.tv_put);
        tvPut.setOnClickListener(this);
        VideoView mVideoViewLE = (VideoView) findViewById(R.id.vv_play_le);
        tvStart = (TextView) findViewById(R.id.tv_start);
        // 支持js
        webView1.getSettings().setJavaScriptEnabled(true);
        // 设置编码
        webView1.getSettings().setDefaultTextEncodingName("utf-8");
        webView1.getSettings()
                .setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        webView1.setWebViewClient(new HelloWebViewClient());

        Timer leTimer = new Timer();
        leTimer.schedule(new TimerTask() {

            @Override
            public void run() {//定时更新
                // TODO Auto-generated method stub
                playHandler.sendEmptyMessage(100);
            }
        }, 1000 * 3, 1000 * 3600);
    }

    public class HelloWebViewClient extends WebViewClient {

        @Override
        public void onLoadResource(final WebView view, final String url) {
            if (url.contains("http://api.letvcloud.com/gpc.php")) {
                Request request = new Request.Builder().url(url).build();
                mOkHttpClient.newCall(request).enqueue(new Callback() {

                    @Override
                    public void onResponse(Response arg0) throws IOException {
                        // TODO Auto-generated method stub
                        String res = arg0.body().string();
                        if (res.contains("u6210")) {//检测成功
                            Log.i(TAG, "乐视服务器请求成功");
                        } else {
                            errorLog("乐视服务器");
                            return;
                        }
                    }

                    @Override
                    public void onFailure(Request arg0, IOException arg1) {
                        // TODO Auto-generated method stub
                        arg1.printStackTrace();
                    }
                });
            }
            if (url.contains("mp4")) {
                // vtype=21 标清
                // vtype=13高清
                // vtype=22超清
                if (url.contains("vtype=21")) {
                    strMp41 = url;
                    Log.i(TAG, "41:" + url);
                    if (ci == 1) {
                        Log2.displayLog(tvLog, "标清已存储");
                        playHandler.sendEmptyMessage(42);
                    } else if (ci == 2) {
                        Log2.displayLog(tvLog, "---高清");
                        playHandler.sendEmptyMessage(43);
                    } else if (ci == 3) {
                        Log2.displayLog(tvLog, "---超清");
                        playHandler.sendEmptyMessage(41);
                    }
                } else if (url.contains("vtype=13")) {
                    strMp42 = url;
                    Log.i(TAG, "42:" + url);
                    Log2.displayLog(tvLog, "高清已存储");
                    playHandler.sendEmptyMessage(43);
                } else if (url.contains("vtype=22")) {
                    strMp43 = url;
                    Log.i(TAG, "43:" + url);
                    Log2.displayLog(tvLog, "超清已存储");
                    playHandler.sendEmptyMessage(41);
                }
            }
            super.onLoadResource(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView webview, String url) {
            // Log.i(TAG, "shouldOverrideUrlLoading url=" + url);
            webview.loadUrl(url);
            return true;
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(final WebView view,
                                                          final String url) {
            return super.shouldInterceptRequest(view, url);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            errorLog("加载url失败" + description);
            return;
        }
    }

    private int ci = 0;
    Handler playHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            // 此处可以更新UI
            switch (msg.what) {
                case 41://上传清晰度
                    if (isAuto) {
                        setPut();
                    }
                    break;
                case 42://高清
                    try {
                        ci = 2;
                        webView1.loadUrl(data2.getString("highUrl"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        playHandler.sendEmptyMessage(10);
                    }
                    break;
                case 43://超清
                    try {
                        ci = 3;
                        webView1.loadUrl(data2.getString("superUrl"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        playHandler.sendEmptyMessage(10);
                    }
                    break;
                case 100://下载链接们
                    getLinks();
                    isAuto = true;
                    playHandler.sendEmptyMessageDelayed(101, 1000);
                    if (!Log2.isNetworkAvailable(getApplicationContext())) {
                        errorLog("网络连接异常");
                    }
                    break;
                case 101://播放点击
                    if (isAuto) {
                        if (TextUtils.isEmpty(strMp41)
                                || TextUtils.isEmpty(strMp42)
                                || TextUtils.isEmpty(strMp43)) {
                            Log.i(TAG, "播放");
                            setSimulateClick(webView1, tvStart.getLeft() + 5,
                                    tvStart.getTop() + 5);
                        }
                        playHandler.sendEmptyMessageDelayed(101, 1000);
                    }
                    break;

                case 200://上传成功
                    Log2.displayLog(tvLog, setLog("成功上传:"));
                    data2 = null;
                    strMp41 = null;
                    strMp42 = null;
                    strMp43 = null;
//                    mVideoViewLE.stopPlayback();
                    playHandler.sendEmptyMessage(10);

                    break;
                case 10://切换下一个
                    if (i >= dataArray.size() - 1) {
                        Log2.displayLog(tvLog, "更新完成" + Log2.getTime());
                        Log2.saveLog("leLog");
                        webView1.loadUrl("http://www.yyhudong.com/");
                        isAuto = false;
                        i = -1;
                    } else {
                        i++;
                        isAuto = true;
                        loadHtml(i);
                    }
                    break;
                case 400:
                    Log2.displayLog(tvLog, setLog("服务器异常:"));
                    isAuto = false;
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.tv_leshi:
                getLinks();
                isAuto = true;
                break;
            case R.id.tv_parse:
                i++;
                loadHtml(i);
                break;
            case R.id.tv_parse2:
                i--;
                if (i < 0) {
                    i = 0;
                }
                loadHtml(i);
                break;
            case R.id.tv_put:
                if (isAuto) {
                    tvPut.setText("手动");
                    isAuto = false;
                } else {
                    tvPut.setText("自动");
                    isAuto = true;
                }
                break;
            case R.id.tv_clear_log:
                Log2.clearLog(tvLog);
                break;
            default:
                break;
        }
    }

    /**
     * 下载链接
     */
    private void getLinks() {
        Request request = new Request.Builder().url(
                "http://rd1.ggzs.me/api/get_dead_links").build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Response response) throws IOException {
                // TODO Auto-generated method stub
                try {
                    JSONObject result = (JSONObject) JSONObject.parse(response
                            .body().string());
                    if (result.getIntValue("rc") == 0) {
                        Log.i(TAG, result.getString("msg") + "下载");
                        // Log.i(TAG,
                        // result.getJSONArray("data").toJSONString());
                        dataArray = result.getJSONArray("data");
                        Log2.displayLog(tvLog, "总数量:" + dataArray.size());
                        playHandler.sendEmptyMessage(10);//下一个
                        return;
                    } else {
                        playHandler.sendEmptyMessage(400);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    playHandler.sendEmptyMessage(400);
                }
            }

            @Override
            public void onFailure(Request arg0, IOException arg1) {
                // TODO Auto-generated method stub
                arg1.printStackTrace();
                playHandler.sendEmptyMessage(400);
            }
        });
    }

    /**
     * 加载item
     *
     * @param i
     */
    private void loadHtml(int i) {
        try {
            data2 = (JSONObject) dataArray.get(i);
            setLog(i + "加载:");
            Log2.displayLog(tvLog, "加载" + i);
            strMp41 = null;
            strMp42 = null;
            strMp43 = null;
            webView1.loadUrl(data2.getString("url"));
            ci = 1;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            playHandler.sendEmptyMessage(10);
        }

    }

    /**
     * 播放出错
     *
     * @param mp
     * @param what
     * @param extra
     * @return
     */
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        // TODO Auto-generated method stub
        errorLog("播放出错");
        return true;
    }

    /**
     * 播放模拟点击
     *
     * @param view
     * @param x
     * @param y
     */
    private void setSimulateClick(View view, float x, float y) {

// long downTime = SystemClock.uptimeMillis();
// final MotionEvent downEvent = MotionEvent.obtain(downTime, downTime,
// MotionEvent.ACTION_DOWN, x, y, 0);
// downTime += 100;
// final MotionEvent upEvent = MotionEvent.obtain(downTime, downTime,
// MotionEvent.ACTION_UP, x, y, 0);
        final MotionEvent downEvent = MotionEvent.obtain(
                System.currentTimeMillis(), System.currentTimeMillis() + 100,
                MotionEvent.ACTION_DOWN, x, y, 0);
        final MotionEvent upEvent = MotionEvent.obtain(
                System.currentTimeMillis(), System.currentTimeMillis() + 100,
                MotionEvent.ACTION_UP, x, y, 0);
        // Log.i(TAG, "x:" + x + "Y:" + y);
        view.onTouchEvent(downEvent);
        view.onTouchEvent(upEvent);
        downEvent.recycle();
        upEvent.recycle();

    }

    /**
     * 上传裸链
     */
    void setPut() {
        String urlput = "http://rd1.ggzs.me/api/update_video_naked";
        FormEncodingBuilder builder = new FormEncodingBuilder();
        if (data2 != null) {
            builder.add("id", "" + data2.getIntValue("id"));
        } else {
            playHandler.sendEmptyMessage(10);
            return;
        }
        if (!TextUtils.isEmpty(strMp41)) {
            builder.add("standard", strMp41);
        } else {
            Log2.displayLog(tvLog, "---标清");
            playHandler.sendEmptyMessage(10);
            return;
        }
        if (!TextUtils.isEmpty(strMp42)) {
            builder.add("high", strMp42);
        }
        if (!TextUtils.isEmpty(strMp43)) {
            builder.add("super", strMp43);
        }
        Request request1 = new Request.Builder().url(urlput)
                .post(builder.build()).build();
        mOkHttpClient.newCall(request1).enqueue(new Callback() {

            @Override
            public void onFailure(Request arg0, IOException arg1) {
                // TODO Auto-generated method stub
                arg1.printStackTrace();
                playHandler.sendEmptyMessage(400);
            }

            @Override
            public void onResponse(Response arg0) {
                // TODO Auto-generated method stub
                if (arg0.code() == 200) {
                    try {
                        JSONObject result = (JSONObject) JSONObject.parse(arg0.body()
                                .string());
                        if (result.getInteger("rc") == 0) {
                            Log.i(TAG, result.getString("msg") + "上传");
                            playHandler.sendEmptyMessage(200);
                        } else {
                            playHandler.sendEmptyMessage(10);//下一个
                            return;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        playHandler.sendEmptyMessage(10);//下一个
                    }


                }
            }
        });
    }

    private String setLog(String msg) {
        if (data2 != null) {
            Log.i(TAG,
                    msg + i + "title:" + data2.getString("title") + "id:"
                            + data2.getIntValue("id") + "url:"
                            + data2.getString("url"));
            msg = msg + i + "--title:" + data2.getString("title")
                    + "id:" + data2.getIntValue("id") + "url:"
                    + data2.getString("url");
        }
        return msg;
    }

    /**
     * 错误显示
     *
     * @param exception
     */
    private void errorLog(String exception) {
        Log2.displayLog(tvLog, setLog(exception + "异常:"));
        playHandler.sendEmptyMessage(10);
    }
}