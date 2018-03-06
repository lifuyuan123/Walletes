package com.wallet.bo.wallets.ui.activity;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.mob.MobSDK;
import com.ppdai.loan.PPDLoanAgent;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;
import com.wallet.bo.wallets.Utils.ActivityManager;
import com.wallet.bo.wallets.Utils.SharedPreferencesUtil;
import com.wallet.bo.wallets.Utils.Utils;
import com.wallet.bo.wallets.greendao.gen.GreenDaoManager;
import com.wallet.bo.wallets.greendao.gen.LoginDao;
import com.wallet.bo.wallets.pojo.Config;
import com.wallet.bo.wallets.pojo.ContactsBean;
import com.wallet.bo.wallets.pojo.Login;

import org.greenrobot.greendao.query.QueryBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.fraudmetrix.octopus.aspirit.main.OctopusManager;
import cn.jpush.android.api.JPushInterface;

/**
 * author:ggband
 * date:2017/7/28 10:11
 * email:ggband520@163.com
 * desc:
 */

public class MyApplication extends Application {

    private static LoginDao loginDao;
    private static List<ContactsBean> list=new ArrayList<>();
    private ActivityManager activityManager = null;

    public ActivityManager getActivityManager() {
        return activityManager;
    }

    public void setActivityManager(ActivityManager activityManager) {
        this.activityManager = activityManager;
    }

    public static List<ContactsBean> getList() {
        return list;
    }

    public static void setList(List<ContactsBean> list) {
        MyApplication.list = list;
    }


    public static Login getLogin() {
        if (login == null) {
            loginDao = GreenDaoManager.getSingleTon().getmDaoSession().getLoginDao();
            QueryBuilder<Login> loginQueryBuilder = loginDao.queryBuilder();
            loginQueryBuilder.where(LoginDao.Properties.UID.ge(Config.USERID));
            if (loginQueryBuilder.list() != null && loginQueryBuilder.list().size() > 0) {
                login = loginQueryBuilder.list().get(0);
            }
        }
        return login;
    }

    public static void setLogin(Login login) {
        loginDao = GreenDaoManager.getSingleTon().getmDaoSession().getLoginDao();
        login.setUID(Config.USERID);
        loginDao.insertOrReplace(login);
        MyApplication.login = login;
        SharedPreferencesUtil.getInstance().putBoolean(Config.ISLOGIN, true);
    }


    public static boolean isOpen() {
        Login login = getLogin();
        if (login == null || login.getCard() == null || TextUtils.isEmpty(login.getCard()))
            return false;
        else
            return true;
    }

    private static Login login;

    public MyApplication() {
    }

    protected String a() {
        return null;
    }

    protected String b() {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        Utils.init(getApplicationContext());
        activityManager = ActivityManager.getScreenManager();
        SharedPreferencesUtil.init(getApplicationContext());
        OctopusManager.getInstance().init("xiaomoqb_mohe", "b878b6ab02a742e789b9303695299b78");//魔盒
        PPDLoanAgent.getInstance().initApplication(this);//拍拍贷
        SharedPreferencesUtil.getInstance().putBoolean(Config.ISPUSH, SharedPreferencesUtil.getInstance().getBoolean(Config.ISPUSH, true));
        // appId 在管理后台申请的 appId
        // serverPublicKey 在管理后台申请的 服务端公钥
        // clientPrivateKey 在管理后台申请的 客户端私钥（字符串长度比 serverPublicKey 长）
        // NOTE: 请慎重修改 管理后台的 公私钥，改变了公私钥将导致接口调用失败
        // 最佳方案是通过你们的后台保存公私钥，通过你们自己的接口获取。
        PPDLoanAgent.getInstance().initConfig(this, "73bb5557df184855b3b130b15b535913", "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDMYuatjy1zXNyUryWiOXtB+lFhccpwbQdk5YhiIBAI2NZH7Nt/WlzdFn2gM/QnHULxuQFrlYAYU8Td/UR1neou64/OLAsf1x9QFmt8++GRUJKAWnfH2RlSn6/+IXy4RV+EETvdlu8bT8FXqnmApSCJR2Q5bk3t1nQb7j7Yy7RYJQIDAQAB", "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJCgha9KBfWwolJJbXXF8xtJrR3tmFRHm4ugj0rCuiZADhY1wQxjYmTMHgdE3gpC1eMaco7yR1DWM1ehOY8GSTmjFFhX6uncE7em6iabPOMuwytknt35AHv6ZpHfF673L5bKi45B71nHZjTvm/s4wphnNhoShghWXWTz3DaZfyCJAgMBAAECgYAkEWRFithhpWNV0ioSLQOr0nvx8scCfbk04R3bLyTtVRwuPcmfJUnsiVsTxQD63iApOvdG3vUaG9FCEVshaBfqf7/WaYI0Ir9QPLye9DMMbaF15CPDHJNe/1kwyIis59Q0fWOJtaJqd6HuuugA6JhoIAwzIbu5RtctLvu6eJRlYQJBAMp73uCeKJ53m2l6x220oAm9MA0H4LjVvpq4yjYDs5KckPMRCMJlm9hvynXfp+M6X+e31XbSmvPanoDptbV2oPsCQQC22gnMH05B8QVe8OFbnQ2g29Ln6pR9VwrFLHZPVg7uxPPFz14iPu7FSsW/bPJ2l5894/dzvwiZQy6baCSZHjVLAkBAGm85FVkushcVkbVJWgHAk5B+z5upaKHrQc1ixFE5fS+2hBIN2TU5lEq4fpIcMgLizSRbddlwkinUDilkxaW5AkEAqK2hRh1HCwu1gazCp8nM7ax79hdvXeTy+YuXpH5mDhvAKaWvV/YoO/ZLT9jIH2CltOlct/jtjLzt/MzonhvA2wJAR54ShIbppcFjkTwCPovW8U+m95H4XWJnQmmf0v6rcfB1xGRRGa7WAvFbihSu9eZNmoykSSG0njyGVrVTv1Fbxg==");

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
//        //建议添加tag标签，发送消息的之后就可以指定tag标签来发送了
//        Set<String> set = new HashSet<>();
//        set.add("andfixdemo");//名字任意，可多添加几个
//        JPushInterface.setTags(this, set, null);//设置标签


        Context context = getApplicationContext();
// 获取当前包名
        String packageName = context.getPackageName();
// 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
// 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
// 初始化Bugly
        //   CrashReport.initCrashReport(context, "d3efc99093", true, strategy);
// 如果通过“AndroidManifest.xml”来配置APP信息，初始化方法如下
// CrashReport.initCrashReport(context, strategy);

//        bugly应用升级 d3efc99093
        Bugly.init(getApplicationContext(), "09405e45a9", false);

/*       参数解析：

        参数1：上下文对象

        参数2：注册时申请的APPID

        参数3：是否开启debug模式，true表示打开debug模式，false表示关闭调试模式

        提示：已经接入Bugly用户改用上面的初始化方法,不影响原有的crash上报功能; init方法会自动检测更新，不需要再手动调用Beta.checkUpgrade(), 如需增加自动检查时机可以使用Beta.checkUpgrade(false,false);

        参数1：isManual 用户手动点击检查，非用户点击操作请传false

        参数2：isSilence 是否显示弹窗等交互，[true:没有弹窗和toast] [false:有弹窗或toast]
        */


        MobSDK.init(this, this.a(), this.b());
        initX5WebView();


    }

///**
// * butterknife绑定控件报空问题
// *
// * */
//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base); MultiDex.install(this);
//    }


    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    private void initX5WebView() {

    }


}
