package com.wallet.bo.wallets.Utils;

import android.content.Context;
import android.util.Log;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * author:ggband
 * date:2017/7/18 16:58
 * email:ggband520@163.com
 * desc:请求数据封装
 */

public class RequestHelpr {
    public static final JSONObject job = new JSONObject();
    public static final String APPID = "10202820";
    public static final String APPIDKEY = "appid";
    public static final String KEYKEY = "key";
    public static final String SIGN = "sign";
    public static RequestHelpr instance;

    public String getKey() {
        return getInstance().keyFromJNI(Utils.getContext());
    }

    private RequestHelpr() {
    }

    static {
        System.loadLibrary("native-lib");
    }


    public static synchronized RequestHelpr getInstance() {
        if (instance == null) {
            instance = new RequestHelpr();
            try {
                job.put(KEYKEY, instance.getKey());
                Log.i("ggband", "KEY:" + instance.getKey());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }


    public static Map<String, String> getRequestParms(Map<String, String> parms) {
        return null;
    }


    public Map<String, String> getRequestParm(Map<String, String> parms) {
        byte[] result = null;
        try {
            if (parms != null)
                for (String key : parms.keySet())
                    job.put(key, parms.get(key));
            result = Base64.encodeBase64(job.toString().getBytes());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        parms.clear();
        parms.put(APPIDKEY, APPID);
        parms.put(SIGN, new String(result));
        return parms;

    }


    public Map<String, Object> getRequestParmo(Map<String, Object> parms) {
        byte[] result = null;
        try {
            if (parms != null)
                for (String key : parms.keySet())
                    job.put(key, parms.get(key));
            result = Base64.encodeBase64(job.toString().getBytes());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        parms.clear();
        parms.put(APPIDKEY, APPID);
        parms.put(SIGN, new String(result));
        return parms;

    }

    public native String keyFromJNI(Context context);


}
