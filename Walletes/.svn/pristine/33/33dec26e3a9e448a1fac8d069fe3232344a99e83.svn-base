package com.wallet.bo.wallets.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;

import com.wallet.bo.wallets.R;
import com.wallet.bo.wallets.Utils.AccountValidatorUtil;
import com.wallet.bo.wallets.Utils.AddSpaceTextWatcher;
import com.wallet.bo.wallets.Utils.GsonUtils;
import com.wallet.bo.wallets.Utils.TextUtils;
import com.wallet.bo.wallets.Utils.dedclick.AntiShake;
import com.wallet.bo.wallets.http.ApiBaseResponseCallback;
import com.wallet.bo.wallets.pojo.Contacts;
import com.wallet.bo.wallets.pojo.Login;
import com.wallet.bo.wallets.ui.weiget.EaseTitleBar;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
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


    @Override
    protected int getContentView() {
        return R.layout.activity_addcontact;
    }

    @Override
    protected void initView() {

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


    @Override
    protected void setUpView() {

    }

    @OnClick({R.id.bt_goto, R.id.bt_contactOne, R.id.bt_contactTwo})
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

            case R.id.bt_contactOne:
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACTONE);
                break;

            case R.id.bt_contactTwo:
                startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), PICK_CONTACTTWO);

                break;
        }

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
            if (requestCode == PICK_CONTACTONE) {
                etConNameOne.setText(name);
                etConPhoneOne.setText(TextUtils.addTrim4(phoneNumber));
            } else {
                etConNameTwo.setText(name);
                etConPhoneTwo.setText(TextUtils.addTrim4(phoneNumber));
            }

        }
        //  关闭查询联系人信息的cursor
        cursor.close();
    }
}
