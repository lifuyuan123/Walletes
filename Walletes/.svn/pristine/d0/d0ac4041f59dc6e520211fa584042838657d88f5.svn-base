package com.wallet.bo.wallets.ui.activity;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.pojo.Config;
import com.wallet.bo.wallets.ui.weiget.EaseTitleBar;
import com.wallet.bo.wallets.ui.weiget.load.DefaultLoadingLayout;

import java.net.URLEncoder;
import java.util.List;

import butterknife.BindView;

/**
 * author:ggband
 * date:2017/8/10 14:03
 * email:ggband520@163.com
 * desc:征信WebView方式(芝麻认证人脸识别)
 */

//                        if (credit.getApiUrl() == null || TextUtils.isEmpty(credit.getApiUrl())) {
//                            Intent rlIntents = new Intent(activity, CreditWebActivity.class);
//                            rlIntents.putExtra(ZmWebActivity.TITLE, credit.getName());
//                            rlIntents.putExtra(ZmWebActivity.URL, credit.getUrl());
//                            startActivity(rlIntents);
//                            //  requestZmCredit(credit.getUrl());
//                            break;
//                        }
//                        Intent rlIntent = new Intent(activity, ZmWebActivity.class);
//                        rlIntent.putExtra(ZmWebActivity.TITLE, credit.getName());
//                        rlIntent.putExtra(ZmWebActivity.URL, credit.getApiUrl());
//                        startActivityForResult(rlIntent, (int) position);

public class CreditWebActivity extends BaseActivity {
    @BindView(R.id.ease_titlebar)
    EaseTitleBar easeTitlebar;
    @BindView(R.id.x5_view)
    WebView webView;
    @BindView(R.id.ll_parent)
    LinearLayout llParent;
    private DefaultLoadingLayout loadingLayout;

    public static final String URL = "url";
    public static final String TITLE = "title";
    private String titel;

    //人脸识别
    private String url;

    @Override
    protected int getContentView() {
        return R.layout.activity_credit_web;
    }

    @Override
    protected void initView() {
        url = getIntent().getStringExtra(URL);
        titel = getIntent().getStringExtra(TITLE);
        if (titel != null)
            easeTitlebar.setTitle(titel);
        loadingLayout = DefaultLoadingLayout.createDefaultLayout(activity, llParent);
        easeTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        loadingLayout.setErrorButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingLayout.onDone();
                url = "http://dai.moxtx.com/index.php/Wap/Credit/index.html";
                webView.loadUrl(url);
            }
        });

    }

    @Override
    protected void setUpView() {
        Log.i("ggband", "主：Thread：" + Thread.currentThread().getName());

    }

    @Override
    protected void onResume() {
        super.onResume();
        startWeb();

    }

    private void startWeb() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i("ggband", "加载URL:" + url );
                if (url.contains("alipays://platformapi")) {
                    doVerify(url);
                } else {
                    view.loadUrl(url);
                }
                return false;
            }

            @TargetApi(Build.VERSION_CODES.M)
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Log.i("ggband", "webView加载错误：Utl:" + request.getUrl() +
                        "request.getMethod:" + request.getMethod()
                        + "getDescription:" + error.getDescription() + "errorCode:" + error.getErrorCode());
                toastShow("webView加载错误：");
                //   loadingLayout.onError();
            }
        });

        //启用支持javascript
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);


//设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

//缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

//其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
       webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

//判断页面加载过程
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                if (newProgress == 100) {
                    // 网页加载完成

                } else {
                    // 加载中

                }

            }
        });

//优先使用缓存
        // webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//不使用缓存：
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
    }


    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }


    /**
     * 启动支付宝进行认证
     *
     * @param url 开放平台返回的URL
     */
    private void doVerify(String url) {
        if (hasApplication()) {
            Log.i(Config.LOGTAG,"**********************************:"+url);
            Intent action = new Intent(Intent.ACTION_VIEW);
            StringBuilder builder = new StringBuilder();
            // 这里使用固定appid 20000067
            builder.append("alipays://platformapi/startapp?appId=20000067&url=");
            builder.append(URLEncoder.encode(url));
            action.setData(Uri.parse(builder.toString()));
            startActivity(action);
            finish();
        } else {
            // 处理没有安装支付宝的情况
            new AlertDialog.Builder(this)
                    .setMessage("是否下载并安装支付宝完成认证?")
                    .setPositiveButton("好的", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent action = new Intent(Intent.ACTION_VIEW);
                            action.setData(Uri.parse("https://m.alipay.com"));
                            startActivity(action);
                        }
                    }).setNegativeButton("算了", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
        }
    }

    /**
     * 判断是否安装了支付宝
     *
     * @return true 为已经安装
     */
    private boolean hasApplication() {
        PackageManager manager = getPackageManager();
        Intent action = new Intent(Intent.ACTION_VIEW);
        action.setData(Uri.parse("alipays://"));
        List list = manager.queryIntentActivities(action, PackageManager.GET_RESOLVED_FILTER);
        return list != null && list.size() > 0;
    }
}
