package com.wallet.bo.wallets.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.Utils.AccountValidatorUtil;
import com.wallet.bo.wallets.Utils.AddSpaceTextWatcher;
import com.wallet.bo.wallets.Utils.GsonUtils;
import com.wallet.bo.wallets.Utils.SharedPreferencesUtil;
import com.wallet.bo.wallets.Utils.TextUtils;
import com.wallet.bo.wallets.Utils.dedclick.AntiShake;
import com.wallet.bo.wallets.http.ApiBaseResponseCallback;
import com.wallet.bo.wallets.pojo.Contacts;
import com.wallet.bo.wallets.pojo.ContactsBean;
import com.wallet.bo.wallets.pojo.Login;
import com.wallet.bo.wallets.ui.weiget.CommonDialog;
import com.wallet.bo.wallets.ui.weiget.EaseTitleBar;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * author:ggband
 * date:2017/7/18 16:58
 * email:ggband520@163.com
 * desc:添加联系人
 */
public class AddContactActivity extends BaseSwipeActivity {

    @BindView(R.id.ease_titlebar)
    EaseTitleBar easeTitlebar;

    @BindView(R.id.et_conNameOne)
    EditText etConNameOne;
    @BindView(R.id.et_conPhoneOne)
    EditText etConPhoneOne;
    @BindView(R.id.et_conNameTwo)
    EditText etConNameTwo;
    @BindView(R.id.et_conPhoneTwo)
    EditText etConPhoneTwo;

    private final int PICK_CONTACTONE = 0;
    private final int PICK_CONTACTTWO = 1;

    private boolean isAgree = false;//是否同意访问通讯录


    @Override
    protected int getContentView() {
        return R.layout.activity_addcontact;
    }

    @Override
    protected void initView() {
        isAgree = SharedPreferencesUtil.getInstance().getBoolean("agree", false);
        if (!isAgree) {
            showPrompt();
        }
        easeTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        AddSpaceTextWatcher addSpaceTextWOne = new AddSpaceTextWatcher(etConPhoneOne, 13);
        addSpaceTextWOne.setSpaceType(AddSpaceTextWatcher.SpaceType.mobilePhoneNumberType);
        AddSpaceTextWatcher addSpaceTextWatcherTwo = new AddSpaceTextWatcher(etConPhoneTwo, 13);
        addSpaceTextWatcherTwo.setSpaceType(AddSpaceTextWatcher.SpaceType.mobilePhoneNumberType);

    }

    //判断是否上传通讯录
    private void isAddMaillist() {
        if (MyApplication.getLogin().getIsMailList().equals("0")) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("提示")
//                    .setMessage("是否允许小陌钱包访问您的通信录")
//                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {


            List<ContactsBean> maps = MyApplication.getList();//获取通讯录集合
            Log.e("upMaillistmaps", maps.toString());

            Gson gson = new Gson();
            String str = gson.toJson(maps);
            Log.e("upMaillistgson", str.toString());

            final Map<String, String> map = new HashMap<>();
            map.put("uid", MyApplication.getLogin().getUserid());
            map.put("data", maps.toString());
            httpLoader.upMaillist(map, new ApiBaseResponseCallback<String>() {
                @Override
                public void onSuccessful(String s) {
                    Log.e("upMaillist", s.toString());
                    MyApplication.getLogin().setIsMailList(s);
                }

                @Override
                public void onFailure(String msg) {
                    if (msg != null)
                        toastShow(msg.toString());
                    Log.e("upMaillistonFailure", msg.toString());
                }

                @Override
                public void onFinish() {
                    Log.e("upMaillist", "完成");
                }
            });

//                        }
//                    })
//                    .setNegativeButton("取消", null)
//                    .show();
        }
    }


    @Override
    protected void setUpView() {

    }

    @OnClick({R.id.bt_goto, R.id.bt_contactOne, R.id.bt_contactTwo, R.id.lin_contacl_one, R.id.lin_contacl_two
    ,R.id.et_conNameOne, R.id.et_conPhoneOne, R.id.et_conNameTwo, R.id.et_conPhoneTwo})
    public void onClick(View view) {
        if (AntiShake.check(view.getId())) {    //判断是否多次点击
            return;
        }
        switch (view.getId()) {
            case R.id.bt_goto:

                String nameOne = TextUtils.repaceTrim(etConNameOne.getText().toString().trim());
                String nameTwo = TextUtils.repaceTrim(etConNameTwo.getText().toString().trim());
                String phoneOne = TextUtils.repaceTrim(etConPhoneOne.getText().toString().trim());
                String phoneTwo = TextUtils.repaceTrim(etConPhoneTwo.getText().toString().trim());

                if (android.text.TextUtils.isEmpty(nameOne) || android.text.TextUtils.isEmpty(nameTwo)) {
                    toastShow("姓名不能为空！");
                    return;
                }

                if (!AccountValidatorUtil.isMobile(phoneOne) || !AccountValidatorUtil.isMobile(phoneTwo)) {
                    toastShow("手机号格式有误！");
                    return;
                }

                if (phoneOne.equals(phoneTwo) || nameOne.equals(nameTwo)) {
                    toastShow("两个联系人或号码不能相同");
                    return;
                }

//                if (!isAgree) {
//                    toastShow("没有获取通讯录权限");
//                    return;
//                }

                mainDialog.show();
                Map<String, Object> stringMap = new HashMap<>();
                Contacts contactOne = new Contacts(phoneOne, nameOne);
                Contacts contactTwo = new Contacts(phoneTwo, nameTwo);
                List<Contacts> contactses = new ArrayList<>();
                contactses.add(contactOne);
                contactses.add(contactTwo);

                JSONArray ja = null;
                try {
                    ja = new JSONArray(GsonUtils.GsonString(contactses));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                stringMap.put("userid", MyApplication.getLogin().getUserid());
                stringMap.put("contacts", ja);
                httpLoader.updataUserInfoo(stringMap, new ApiBaseResponseCallback<Object>() {

                    @Override
                    public void onSuccessful(Object o) {
                        Login login = MyApplication.getLogin();
                        login.setContacts(1);
                        MyApplication.setLogin(login);
                        toastShow("添加成功");
                        setResult(RESULT_OK, getIntent());
                        finish();
                    }

                    @Override
                    public void onFailure(String msg) {
                        toastShow(msg);
                    }

                    @Override
                    public void onFinish() {
                        mainDialog.dismiss();
                    }
                });

                break;
            case R.id.lin_contacl_one:
            case R.id.et_conNameOne:
            case R.id.et_conPhoneOne:
            case R.id.bt_contactOne:
                if (!isAgree) {
                    showPrompt();
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    startActivityForResult(intent, PICK_CONTACTONE);
                }
                break;
            case R.id.et_conNameTwo:
            case R.id.et_conPhoneTwo:
            case R.id.lin_contacl_two:
            case R.id.bt_contactTwo:
                if (!isAgree) {
                    showPrompt();
                } else {
                    startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), PICK_CONTACTTWO);
                }
                break;
        }

    }


    //给读取通讯的提示
    private void showPrompt() {
        final CommonDialog dialog = new CommonDialog(AddContactActivity.this);
        dialog.setTitle("提示");
        dialog.setContent("小陌钱包需要访问您的通讯录");
        dialog.setCancelClickListener("不同意", new CommonDialog.CancelClickListener() {
            @Override
            public void clickCancel() {
                SharedPreferencesUtil.getInstance().putBoolean("agree", false);
                isAgree = false;
                dialog.dismiss();
            }
        });
        dialog.setConfirmClickListener("同意", new CommonDialog.ConfirmClickListener() {
            @Override
            public void clickConfirm() {
                //同意访问存储这个状态
                SharedPreferencesUtil.getInstance().putBoolean("agree", true);
                isAgree = true;
                isAddMaillist();//判断是否上传通讯录
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK)
            return;
        //  获取返回的联系人的Uri信息
        Uri contactDataUri = data.getData();
        Cursor cursor = getContentResolver().query(contactDataUri, null, null, null, null);
        assert cursor != null;
        if (cursor.moveToFirst()) {
            //   获得联系人记录的ID
            String contactId = cursor.getString(cursor.getColumnIndex(
                    ContactsContract.Contacts._ID));
            //  获得联系人的名字
            String name = cursor.getString(cursor.getColumnIndex(
                    ContactsContract.Contacts.DISPLAY_NAME));
            String phoneNumber = "未找到联系人号码";
            Cursor phoneCursor = getContentResolver().query(ContactsContract.CommonDataKinds.
                    Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.
                    Phone.CONTACT_ID + "=" + "?", new String[]{contactId}, null);
            if (phoneCursor.moveToFirst()) {
                phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER));
            }
            //  关闭查询手机号码的cursor
            phoneCursor.close();

            String s=phoneNumber.replaceAll(" ", "");
            if (s.contains("+86")){
                s = s.substring(3, s.length());
            }
            //TextUtils.addTrim4()  空格处理  加上会报手机格式错误
            if (requestCode == PICK_CONTACTONE) {
                etConNameOne.setText(name);
                etConPhoneOne.setText(s);
            } else {
                etConNameTwo.setText(name);
                etConPhoneTwo.setText(s);
            }

        }
        //  关闭查询联系人信息的cursor
        cursor.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

}
