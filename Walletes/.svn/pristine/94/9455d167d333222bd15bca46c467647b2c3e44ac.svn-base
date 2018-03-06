package com.wallet.bo.wallets.ui.activity;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

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
 * desc:芝麻信用相关Activity
 */

public class ZmWebActivity extends BaseSwipeActivity {

    @BindView(R.id.ease_titlebar)
    EaseTitleBar easeTitlebar;
    @BindView(R.id.x5_view)
    WebViewBase webView;
    @BindView(R.id.ll_parent)
    LinearLayout llParent;
    private String titel;//titelbar
    private String url;//加载url
    public static final String URL = "url";
    public static final String TITLE = "title";

    private WebViewClient webViewClient = new WebViewClient() {


        //http://dai.moxtx.com/index.php/Wap/Credit/index.html
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.i("ggband", "加载URL:" + url + Thread.currentThread().getName());
            if (url.contains("index.php")) {
                view.stopLoading();
                setResult(RESULT_OK,getIntent());
                finish();
            } else
                view.loadUrl(url);
            return false;
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            LogUtils.i(Config.LOGTAG, "onPageStarted url:" + url);
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_common_web;
    }

    @Override
    protected void initView() {
        webView.requestFocus(View.FOCUS_DOWN);
        url = getIntent().getStringExtra(URL);
        titel = getIntent().getStringExtra(TITLE);
        if (titel != null)
            easeTitlebar.setTitle(titel);
        easeTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        webView.loadUrl(url);

        webView.setWebViewClient(webViewClient);

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
}
