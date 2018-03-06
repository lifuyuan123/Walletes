package com.wallet.bo.wallets;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.LayoutParams;
import android.widget.RelativeLayout;

import com.jaeger.library.StatusBarUtil;
import com.ppdai.loan.PPDLoanAgent;
import com.wallet.bo.wallets.pojo.Config;
import com.wallet.bo.wallets.pojo.Navagation;
import com.wallet.bo.wallets.ui.activity.BaseNoStatusActivity;
import com.wallet.bo.wallets.ui.activity.JGuangMesActivity;
import com.wallet.bo.wallets.ui.fragment.CreditFragment;
import com.wallet.bo.wallets.ui.fragment.LoanFragment;
import com.wallet.bo.wallets.ui.fragment.LoanProductFragment;
import com.wallet.bo.wallets.ui.fragment.SlideMineFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import cn.fraudmetrix.octopus.aspirit.utils.Constants;

public class MainActivity extends BaseNoStatusActivity {

    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    @BindView(R.id.buttom_content)
    RadioGroup buttomContent;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;
    private FragmentTransaction transaction;
    private Fragment[] fragments = new Fragment[4];
    private int lastFragmentIndex;
    private Fragment currentFragment;
    public static boolean isForeground = false;
    private String fragmentColors[] = {"#696ce5", "#E2564A", "#E2564A", "#bdbdbd"};


    private int[] a = {R.drawable.main_buttom_one, R.drawable.main_button_two, R.drawable.main_button_three, R.drawable.main_button_fore};


    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        PPDLoanAgent.getInstance().onLaunchCreate(this);
        registerMessageReceiver();  // used for receive msg
        //  CrashReport.testJavaCrash();


        buttomContent.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //   StatusBarUtil.setColorNoTranslucent(activity, Color.parseColor(fragmentColors[checkedId]));
                transaction = getSupportFragmentManager().beginTransaction();
                currentFragment = getSupportFragmentManager().findFragmentByTag(String.valueOf(checkedId));
                Fragment last = getSupportFragmentManager().findFragmentByTag(String.valueOf(lastFragmentIndex));
                if (last != null) {
                    transaction.hide(last);
                }
                if (currentFragment == null)
                    currentFragment = fragments[checkedId];
                if (currentFragment != null)
                    if (!currentFragment.isAdded())
                        transaction.add(R.id.fragment_container, currentFragment, String.valueOf(checkedId));
                transaction.show(currentFragment).commitAllowingStateLoss();
                lastFragmentIndex = checkedId;

            }
        });
        StatusBarUtil.setTranslucentForCoordinatorLayout(activity, 0);
        if (hasSoftKeys(getWindowManager())) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) buttomContent.getLayoutParams();
            layoutParams.setMargins(0, 0, 0, getNavigationBarHeight(activity));
        }
        addButtom();

    }

    @Override
    protected void setUpView() {


    }


    private void addButtom() {
        fragments[0] = new LoanFragment();
        fragments[1] = new LoanProductFragment();
        fragments[2] = new CreditFragment();
        fragments[3] = new SlideMineFragment();
        buttomContent.removeAllViews();
        String[] array = getResources().getStringArray(R.array.buttom_array);
        LayoutParams params = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        for (int i = 0; i < array.length; i++) {
            RadioButton button = new RadioButton(this);
            button.setId(i);
            button.setCompoundDrawablesWithIntrinsicBounds(0, a[i], 0, 0);
            button.setButtonDrawable(new BitmapDrawable());
            button.setBackgroundDrawable(null);
            button.setText(array[i]);
            button.setTextSize(12);
            button.setGravity(Gravity.CENTER);
            button.setLayoutParams(params);
            button.setTextColor(getResources().getColorStateList(R.color.buttom_color_selector));
            buttomContent.addView(button);
        }
        buttomContent.getChildAt(0).performClick();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
            if (requestCode == Constants.OCTOPUS_ACTIVITY_REQUEST_CODE) {
                ((CreditFragment) fragments[2]).taoBaoCredit(data);
            }

    }


    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
        PPDLoanAgent.getInstance().onLaunchResume(this);
    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!TextUtils.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
                    setCostomMsg(showMsg.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setCostomMsg(String msg) {
        Intent intent = new Intent(this, JGuangMesActivity.class);
        intent.putExtra(JGuangMesActivity.MESKEY, msg);
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(Navagation navagation) {
        buttomContent.getChildAt(navagation.getIndex()).performClick();
        Log.i(Config.LOGTAG, "navagation:" + navagation.getIndex());
    }

    public int getNavigationBarHeight(Activity activity) {
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        //获取NavigationBar的高度
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private boolean hasSoftKeys(WindowManager windowManager) {
        Display d = windowManager.getDefaultDisplay();


        DisplayMetrics realDisplayMetrics = new DisplayMetrics();
        d.getRealMetrics(realDisplayMetrics);


        int realHeight = realDisplayMetrics.heightPixels;
        int realWidth = realDisplayMetrics.widthPixels;


        DisplayMetrics displayMetrics = new DisplayMetrics();
        d.getMetrics(displayMetrics);


        int displayHeight = displayMetrics.heightPixels;
        int displayWidth = displayMetrics.widthPixels;


        return (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;
    }
}
