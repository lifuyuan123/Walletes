package com.wallet.bo.wallets.ui.activity;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.Utils.LogUtils;
import com.wallet.bo.wallets.pojo.Config;
import com.wallet.bo.wallets.ui.weiget.EaseTitleBar;
import com.wallet.bo.wallets.ui.weiget.WebViewBase;

import butterknife.BindView;

/**
 * author:ggband
 * date:2017/8/17 14:50
 * email:ggband520@163.com
 * desc:公用webActivity
 */

public class CommonClientWebActivity extends BaseSwipeActivity {

    @BindView(R.id.ease_titlebar)
    EaseTitleBar easeTitlebar;
    @BindView(R.id.x5_view)
    WebViewBase webView;
    @BindView(R.id.ll_parent)
    LinearLayout llParent;
    @BindView(R.id.pg)
    ProgressBar pg;
    private String titel;//titelbar
    private String url;//加载url
    public static final String URL = "url";
    public static final String TITLE = "title";
    private boolean isClose;

    private String js = "javascript:function hideAd() {\n" +
            "    var adDiv0 = document.getElementsByClassName(\"download\");\n" +
            "    if(adDiv0 != null){\n" +
            "        var x;\n" +
            "        for (x= 0; x< adDiv0.length; x++) {\n" +
            "            adDiv0[x].style.display = \"none\";\n" +
            "        }\n" +
            "    }\n" +
            "\t}";//隐藏不必要的div
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (webView == null)
                return;
            webView.loadUrl(js); //加载js方法代码
            webView.loadUrl("javascript:hideAd();"); //调用js方法
        }
    };

    private WebViewClient webViewClient = new WebViewClient() {


        //http://dai.moxtx.com/index.php/Wap/Credit/index.html
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.i("ggband", "加载URL:" + url);
            Log.i("urlshould", url);
            if (url.contains("index.php")) {//若跳转官网主页面，就返回
//                view.stopLoading();
//                finish();
                view.loadUrl(url);
            } else
                view.loadUrl(url);
            return false;
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.i("urlonPageFinished",  url);
            isClose = true;
            if(webView!=null){
                webView.loadUrl(js); //加载js方法代码
                webView.loadUrl("javascript:hideAd();"); //调用js方法
            }

        }


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            LogUtils.i(Config.LOGTAG, "onPageStarted url:" + url);
            Log.i("urlonPageStarted",  url);
            if (webView!=null){
                webView.loadUrl(js); //加载js方法代码
                webView.loadUrl("javascript:hideAd();"); //调用js方法
            }


            if (isClose) { //如果线程正在运行就不用重新开启一个线程了
                return;
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    isClose = true;
                    while (isClose) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        handler.sendEmptyMessage(0x001);
                    }
                }
            }).start();
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_common_web;
    }

    @Override
    protected void initView() {
        webView.requestFocus(View.FOCUS_DOWN);
        url = getIntent().getStringExtra(URL) == null ? "" : getIntent().getStringExtra(URL);
        titel = getIntent().getStringExtra(TITLE) == null ? "" : getIntent().getStringExtra(TITLE);

        Log.e("url",url);
        if (titel != null)
            easeTitlebar.setTitle(titel);
        easeTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        webView.loadUrl(url);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        //设置缓存模式
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.setWebViewClient(webViewClient);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (pg == null)
                    return;
                if (newProgress == 100) {
                    pg.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    pg.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    pg.setProgress(newProgress);//设置进度值
                }
            }
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Log.e("title",title);
                if (easeTitlebar!=null)
                easeTitlebar.setTitle(title);
            }


//            @Override
//            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
//                if (message.contains("查询失败"))
//                    return true;
//                return super.onJsAlert(view, url, message, result);
//            }
        });


    }

    @Override
    protected void setUpView() {

    }


    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isClose = false;
    }
}
