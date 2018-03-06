package com.wallet.bo.wallets.Utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.HashMap;
import java.util.Map;

/**
 * author:ggband
 * date:2017/7/29 17:34
 * email:ggband520@163.com
 * desc:读取联系人
 */

public class ContactsUtils {


    public static Map<String, String> read(Context context) {

        Map<String, String> map = new HashMap<>();


        Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
        //moveToNext方法返回的是一个boolean类型的数据
        while (cursor.moveToNext()) {
            //读取通讯录的姓名
            String name = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            //读取通讯录的号码
            String number = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            map.put(name, number);


        }
        return map;
    }
}


/*


获取联系人信息的Activity代码为：

public class ReadContacts extends Activity{
    private Button queryContacts;
    private TextView contactName;
    private TextView contactNumber;
    //  定义Activity的请求码
    private final int PICK_CONTACT = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queryContacts = (Button) findViewById(R.id.query_contacts);
        contactName = (TextView) findViewById(R.id.tv_contact_name);
        contactNumber = (TextView) findViewById(R.id.tv_contact_number);
        queryContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  通过Intent调到手机联系人页面，Action属性为Intent.ACTION_PICK使用户选择联系人，并返回所选数据
                Intent intent = new Intent(Intent.ACTION_PICK,ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent,PICK_CONTACT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case PICK_CONTACT:
                if(resultCode == Activity.RESULT_OK){
                    //  获取返回的联系人的Uri信息
                    Uri contactDataUri = data.getData();
                    Cursor cursor = getContentResolver().query(contactDataUri,null,null,null,null);
                    if(cursor.moveToFirst()){
                        //   获得联系人记录的ID
                        String contactId = cursor.getString(cursor.getColumnIndex(
                                ContactsContract.Contacts._ID));
                        //  获得联系人的名字
                        String name = cursor.getString(cursor.getColumnIndex(
                                ContactsContract.Contacts.DISPLAY_NAME));
                        String phoneNumber = "未找到联系人号码";
                        Cursor phoneCursor = getContentResolver().query(ContactsContract.CommonDataKinds.
                                Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.
                                Phone.CONTACT_ID+"="+"?",new String[]{contactId},null);
                        if(phoneCursor.moveToFirst()){
                            phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));
                        }
                        //  关闭查询手机号码的cursor
                        phoneCursor.close();
                        contactName.setText(name);
                        contactNumber.setText(phoneNumber);
                    }
                    //  关闭查询联系人信息的cursor
                    cursor.close();
                }
                break;
        }
    }
}

 */
