package com.wallet.bo.wallets.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;

import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.Utils.StatusBarUtils;
import com.wallet.bo.wallets.http.ApiCallback;
import com.wallet.bo.wallets.http.ApiStores;
import com.wallet.bo.wallets.http.AppClient;
import com.wallet.bo.wallets.http.HttpLoader;
import com.wallet.bo.wallets.ui.ba.SwipeBackActivity;
import com.wallet.bo.wallets.ui.ba.SwipeBackLayout;
import com.wallet.bo.wallets.ui.weiget.MainDialog;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import okhttp3.ResponseBody;





public abstract class BaseSwipeActivity extends SwipeBackActivity {
    protected Context context;
    protected InputMethodManager inputMethodManager;
    protected ProgressDialog progressDialog;
    private Unbinder unbinder;
    protected ApiStores apiStores;
    protected Activity activity;
       MainDialog mainDialog ;
    private SwipeBackLayout mSwipeBackLayout;
    protected HttpLoader httpLoader = HttpLoader.getInstance();




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColorNoTranslucent(this,getResources().getColor(R.color.app_theme));
        mSwipeBackLayout = getSwipeBackLayout();
//设置滑动方向，可设置EDGE_LEFT, EDGE_RIGHT, EDGE_ALL, EDGE_BOTTOM
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        setContentView(getContentView());
        unbinder = ButterKnife.bind(this);
        context = this;
        activity = this;
        apiStores = AppClient.retrofit().create(ApiStores.class);
        mainDialog = MainDialog.showDialog(this);
        initView();
        setUpView();
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

    }




    protected void hideSoftKeyboard() {
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.dispatchTouchEvent(ev);
    }


    // 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }


    protected ProgressDialog showProgressDialog() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("加载中");
        progressDialog.show();
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // 设置进度条是否为不明确
        progressDialog.setIndeterminate(true);
        // 设置进度条是否可以按退回键取消
        progressDialog.setCancelable(false);
        // 设置点击进度对话框外的区域对话框不消失
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    protected ProgressDialog showProgressDialog(CharSequence message) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // 设置进度条是否为不明确
        progressDialog.setIndeterminate(true);
        // 设置进度条是否可以按退回键取消
        progressDialog.setCancelable(false);
        // 设置点击进度对话框外的区域对话框不消失
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        return progressDialog;
    }

    protected void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    protected void toastShow(String mes) {
        Toast.makeText(context, mes, Toast.LENGTH_SHORT).show();
    }

    protected void sendRequest(String url, Map<String, String> map, ApiCallback<ResponseBody> callback) {
        apiStores
                .common(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

//        unbinder.unbind();
    }

    protected abstract int getContentView();

    protected abstract void initView();

    protected abstract void setUpView();


}
