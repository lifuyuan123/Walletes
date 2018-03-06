package com.wallet.bo.wallets;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.LayoutParams;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.jaeger.library.StatusBarUtil;
import com.ppdai.loan.PPDLoanAgent;
import com.wallet.bo.wallets.Utils.MainLooper;
import com.wallet.bo.wallets.Utils.SharedPreferencesUtil;
import com.wallet.bo.wallets.databinding.ActivityMainBinding;
import com.wallet.bo.wallets.pojo.Config;
import com.wallet.bo.wallets.pojo.ContactsBean;
import com.wallet.bo.wallets.pojo.Navagation;
import com.wallet.bo.wallets.ui.activity.BaseNoStatusActivity;
import com.wallet.bo.wallets.ui.activity.CommonClientWebActivity;
import com.wallet.bo.wallets.ui.activity.CommonWebActivity;
import com.wallet.bo.wallets.ui.activity.JGuangMesActivity;
import com.wallet.bo.wallets.ui.activity.LogingActivity;
import com.wallet.bo.wallets.ui.activity.MyApplication;
import com.wallet.bo.wallets.ui.fragment.CreditFragment;
import com.wallet.bo.wallets.ui.fragment.LoanFragment;
import com.wallet.bo.wallets.ui.fragment.LoanProductFragment;
import com.wallet.bo.wallets.ui.fragment.SlideMineFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends BaseNoStatusActivity {
    private FragmentTransaction transaction;
    private Fragment[] fragments = new Fragment[4];
    private int lastFragmentIndex;
    private int nowcheckedId;
    private Fragment currentFragment;
    public static boolean isForeground = false;
    private long firstTime;//第一次点击
    private long secondTime;//第二次点击
    private long spaceTime;//两次时间差

    private boolean iscommon = false;
    private ActivityMainBinding mainBinding;


    private int[] a = {R.drawable.main_buttom_one, R.drawable.main_button_two, R.drawable.main_button_three, R.drawable.main_button_fore};



    //初始化  监听设置等
    @Override
    protected void initView() {
        mainBinding=DataBindingUtil.setContentView(this,R.layout.activity_main);
        EventBus.getDefault().register(this);

        PPDLoanAgent.getInstance().onLaunchCreate(this);
        registerMessageReceiver();  // used for receive msg
        //  CrashReport.testJavaCrash();
        //百度统计用户量
        StatService.start(this);

        mainBinding.buttomContent.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //   StatusBarUtil.setColorNoTranslucent(activity, Color.parseColor(fragmentColors[checkedId]));

                RadioButton radioButton = (RadioButton) findViewById(checkedId);
                //未登陆不能调信用页面
                if (radioButton.getText().toString().trim().equals("信用") && !iscommon) {
                    toastShow("请先登录");
                    nowcheckedId=checkedId;
                    Intent intent = new Intent(activity, LogingActivity.class);
                    startActivityForResult(intent,101);
                    return;
                }
                //根据下标展示fragment
                showFragment(checkedId);
            }
        });


        StatusBarUtil.setTranslucentForCoordinatorLayout(activity, 0);
        if (hasSoftKeys(getWindowManager())) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mainBinding.buttomContent.getLayoutParams();
            layoutParams.setMargins(0, 0, 0, getNavigationBarHeight(activity));
        }
        addButtom();

        //获取通讯录权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            //申请权限  第二个参数是一个 数组 说明可以同时申请多个权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
        } else { //已授权
            //获取通讯录
            getContacts();
        }

        //客服图片点击事件
        mainBinding.floatIamge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isQQAvailable(MainActivity.this)) {
                    String url = "mqqwpa://im/chat?chat_type=wpa&uin=2852271874";
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                } else {
                    String urlss = "https://webpage.qidian.qq.com/2/chat/pc/index.html?linkType=1&env=ol&kfuin=2852062105&fid=38&key=75f975adf80386fd48a2fa0fc28dc942&cate=1&type=16&ftype=1&_type=wpa&qidian=true&waitTime=7181&clickid=s12hc3.pe6osl.jahugl4s&callImType=1&delayTime=7&roleValue=0&roleData=2852271874";
                    Intent intent = new Intent(MainActivity.this, CommonClientWebActivity.class);
                    intent.putExtra(CommonWebActivity.URL, urlss);
                    intent.putExtra(CommonWebActivity.TITLE, "客服");
                    startActivity(intent);
                }
            }
        });

    }

    //判断是否有安装qq
    public static boolean isQQAvailable(Context context) {

        final PackageManager mPackageManager = context.getPackageManager();

        List<PackageInfo> pinfo = mPackageManager.getInstalledPackages(0);

        if (pinfo != null) {

            for (int i = 0; i < pinfo.size(); i++) {

                String pn = pinfo.get(i).packageName;

                if (pn.equals("com.tencent.mobileqq")) {

                    return true;
                }
            }
        }

        return false;

    }


    @Override
    protected void setUpView() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(Color.parseColor("#000000"));
        }

    }


    //添加fragment和radiobutton
    private void addButtom() {
        fragments[0] = new LoanFragment();
        fragments[1] = new LoanProductFragment();
        fragments[2] = new CreditFragment();
        fragments[3] = new SlideMineFragment();
        mainBinding.buttomContent.removeAllViews();
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
            button.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
            button.setGravity(Gravity.CENTER);
            button.setLayoutParams(params);
            button.setTextColor(getResources().getColorStateList(R.color.buttom_color_selector));
            mainBinding.buttomContent.addView(button);
        }
        mainBinding.buttomContent.getChildAt(0).performClick();


    }


    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
        PPDLoanAgent.getInstance().onLaunchResume(this);

        //没有登陆的状态下不能进入信用页面
        if (!SharedPreferencesUtil.getInstance().getBoolean(Config.ISLOGIN, false)) {
            iscommon = false;
        } else {
            iscommon = true;
        }

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
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
    public void onEventMainThread(final Navagation navagation) {
        mainBinding.buttomContent.getChildAt(navagation.getIndex()).performClick();
        Log.e("navagation", "navagation:" + navagation.getIndex());

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


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            firstTime = System.currentTimeMillis();
            spaceTime = firstTime - secondTime;
            secondTime = firstTime;
            if (spaceTime > 2000) {
                toastShow("再按一次退出程序");
            } else
                MainActivity.this.finish();
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //权限申请成功
                getContacts();
            } else {
                Toast.makeText(MainActivity.this, "获取联系人的权限申请失败", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //获取联系人
    public void getContacts() {
        List<ContactsBean> lists = new ArrayList<>();

        //获取手机通讯录联系人
        ContentResolver resolver = this.getContentResolver();

        // 获取手机联系人
        Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                null, null, null);
        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
                //得到手机号码
                String phoneNumber = phoneCursor.getString(phoneCursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                //当手机号码为空的或者为空字段 跳过当前循环
                if (TextUtils.isEmpty(phoneNumber))
                    continue;

                //得到联系人名称
                String contactName = phoneCursor.getString(phoneCursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                //把联系人用装起来装起来
                ContactsBean bean = new ContactsBean(contactName, phoneNumber);
                lists.add(bean);
            }
            phoneCursor.close();
        }

        Log.e("lists", lists.toString());
        MyApplication.setList(lists);
    }



    //用于控制radiobutton下标（未登录状态不能查看信用）
  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == 102) {
            //未登陆就返回到此页面则显示跳转登陆页面前的那个页面
            if (!SharedPreferencesUtil.getInstance().getBoolean(Config.ISLOGIN, false)) {
                mainBinding.buttomContent.getChildAt(lastFragmentIndex).performClick();
                //登陆后返回到此页面直接跳转信用页面
            }else {
               mainBinding.buttomContent.check(nowcheckedId);
               //根据下标展示fragment
               showFragment(nowcheckedId);
            }
        }

    }


    //根据下标展示fragment
   private void showFragment(int indext){
       transaction = getSupportFragmentManager().beginTransaction();
       currentFragment = getSupportFragmentManager().findFragmentByTag(String.valueOf(indext));
       Fragment last = getSupportFragmentManager().findFragmentByTag(String.valueOf(lastFragmentIndex));
       if (last != null) {
           transaction.hide(last);
       }

       if (currentFragment == null)
           currentFragment = fragments[indext];


       if (currentFragment != null)
           if (!currentFragment.isAdded())
               transaction.add(R.id.fragment_container, currentFragment, String.valueOf(indext));
       transaction.show(currentFragment).commitAllowingStateLoss();
       lastFragmentIndex = indext;
   }
}
