package com.wallet.bo.wallets.Utils;

import android.util.Log;

import com.wallet.bo.wallets.pojo.ErrorMessage;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;

/**
 * author:ggband
 * date:2017/7/20 13:34
 * email:ggband520@163.com
 * desc:
 */

public class ResponseHelp<T> {
    private String responseBody;
    private T t;


    public ResponseHelp(ResponseBody responseBody) {
        try {
            this.responseBody = responseBody.string();
            Log.i("ggband", "responseBody：" + responseBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isSuccess() {
        boolean isSuccess = false;
        if (responseBody == null)
            return isSuccess;
        if (responseBody == null)
            return isSuccess;
        isSuccess = JsonUtils.getBoolean(responseBody, "code", false);


        return isSuccess;


    }

    public <T> T getData(Type type) {


        Object object = JsonUtils.getObject(responseBody, "data");
        String data = object.toString();
        T t = GsonUtils.GsonToBean(data, type);
        Log.i("ggband","data成功数据:"+t.toString());
        return t;
    }

    public String getReason() {
        int errorCode = JsonUtils.getInt(responseBody, "data", 0);
        return ErrorMessage.getErrorMeesage(errorCode);
    }
}
