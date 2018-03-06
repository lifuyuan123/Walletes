package com.wallet.bo.wallets.ui.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.wallet.bo.wallets.Utils.NetworkUtils;
import com.wallet.bo.wallets.Utils.RequestHelpr;
import com.wallet.bo.wallets.Utils.ScreenUtil;
import com.wallet.bo.wallets.http.ApiCallback;
import com.wallet.bo.wallets.http.ApiStores;
import com.wallet.bo.wallets.http.AppClient;
import com.wallet.bo.wallets.http.HttpLoader;
import com.wallet.bo.wallets.ui.weiget.MainDialog;
import com.wallet.bo.wallets.ui.weiget.load.DefaultLoadingLayout;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;


/**
 * 只有有左滑菜单的页面全部需要继承这个类
 */
public abstract class BaseFragment extends Fragment {


    //标题栏
    // @BindView(R.id.title)
    protected TextView tv_title;

//    @BindView(R.id.ease_titlebar)
//    protected EaseTitleBar titleBar;


    protected Context context;
    protected FragmentActivity activity;
    protected InputMethodManager inputMethodManager;
    protected ProgressDialog progressDialog;
    // protected ApiStores apiStores;
    protected LayoutInflater inflater;
    protected String Tag = "ggband";
    protected Unbinder unbinder;
    protected FragmentManager fragmentManager;
    protected Fragment currentFragment;
    protected View view;
    protected ApiStores apiStores;
    protected RequestHelpr requestHelpr;
    protected DefaultLoadingLayout loadingLayout;
    protected HttpLoader httpLoader = HttpLoader.getInstance();
    protected MainDialog mainDialog;



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        initView();
        setViewUp();
    }

    private void init() {
        inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        requestHelpr = RequestHelpr.getInstance();
        loadingLayout = DefaultLoadingLayout.createDefaultLayout(activity, ScreenUtil.getContentView(activity));
        loadingLayout.setErrorButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingLayout.onDone();
                NetworkUtils.openWirelessSettings();
            }
        });
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        activity = getActivity();
        mainDialog = MainDialog.showDialog(context);
        fragmentManager = activity.getSupportFragmentManager();
        inflater = LayoutInflater.from(context);
        apiStores = AppClient.retrofit().create(ApiStores.class);
        // apiStores = AppClient.retrofit(context).create(ApiStores.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        this.view = getView();
        currentFragment = this;
    }


    protected void hideSoftKeyboard() {
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    protected ProgressDialog showProgressDialog() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("执行中...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        return progressDialog;
    }

    protected ProgressDialog showProgressDialog(CharSequence message) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.show();
        return progressDialog;
    }

    protected void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    protected void toastShow(String mes) {
        Toast.makeText(context, mes, Toast.LENGTH_SHORT).show();
    }


    protected void sendRequest(String url, Map<String, String> map, ApiCallback<ResponseBody> callback) {
        if (!NetworkUtils.isConnected()) {
//            //网络无连接
//            mainDialog.dismiss();
//            loadingLayout.setErrorButtonText("无网络，去设置网络");
//            loadingLayout.onError();
//            return;
        }
        if (!NetworkUtils.isAvailableByPing()) {
//            //网络不可用
//            mainDialog.dismiss();
//            loadingLayout.setErrorButtonText("无网络，去设置网络");
//            loadingLayout.onError();
//            return;
        }
        apiStores
                .common(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);
    }


    public abstract void initView();

    public abstract void setViewUp();

    public abstract int getLayoutId();

    protected void bindView() {
        unbinder = ButterKnife.bind(this, getView());
    }

}
