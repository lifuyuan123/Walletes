package com.wallet.bo.wallets.Utils;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * author:ggband
 * date:2017/7/20 13:42
 * email:ggband520@163.com
 * desc:
 */

public class JsonUtils {

    public static boolean isPrintException = true;

    private JsonUtils() {

        throw new AssertionError();

    }

    public static Long getLong(JSONObject jsonObject, String key, Long defaultValue) {

        if (jsonObject == null || key.isEmpty()) {

            return defaultValue;

        }

        try {

            return jsonObject.getLong(key);

        } catch (JSONException e) {

            if (isPrintException) {

                e.printStackTrace();

            }

            return defaultValue;

        }

    }

    public static Long getLong(String jsonData, String key, Long defaultValue) {

        if (jsonData.isEmpty()) {

            return defaultValue;

        }

        try {

            JSONObject jsonObject = new JSONObject(jsonData);

            return getLong(jsonObject, key, defaultValue);

        } catch (JSONException e) {

            if (isPrintException) {

                e.printStackTrace();

            }

            return defaultValue;

        }

    }

    public static long getLong(JSONObject jsonObject, String key, long defaultValue) {

        return getLong(jsonObject, key, (Long) defaultValue);

    }

    public static long getLong(String jsonData, String key, long defaultValue) {

        return getLong(jsonData, key, (Long) defaultValue);

    }

    public static Integer getInt(JSONObject jsonObject, String key, Integer defaultValue) {

        if (jsonObject == null || key.isEmpty()) {

            return defaultValue;

        }

        try {

            return jsonObject.getInt(key);

        } catch (JSONException e) {

            if (isPrintException) {

                e.printStackTrace();

            }

            return defaultValue;

        }

    }

    public static Integer getInt(String jsonData, String key, Integer defaultValue) {

        if (jsonData.isEmpty()) {

            return defaultValue;

        }

        try {

            JSONObject jsonObject = new JSONObject(jsonData);

            return getInt(jsonObject, key, defaultValue);

        } catch (JSONException e) {

            if (isPrintException) {

                e.printStackTrace();

            }

            return defaultValue;

        }

    }

    public static int getInt(JSONObject jsonObject, String key, int defaultValue) {

        return getInt(jsonObject, key, (Integer) defaultValue);

    }


    public static int getInt(String jsonData, String key, int defaultValue) {

        return getInt(jsonData, key, (Integer) defaultValue);

    }

    public static Double getDouble(JSONObject jsonObject, String key, Double defaultValue) {

        if (jsonObject == null || key.isEmpty()) {

            return defaultValue;

        }

        try {

            return jsonObject.getDouble(key);

        } catch (JSONException e) {

            if (isPrintException) {

                e.printStackTrace();

            }

            return defaultValue;

        }

    }

    public static Double getDouble(String jsonData, String key, Double defaultValue) {

        if (jsonData.isEmpty()) {

            return defaultValue;

        }

        try {

            JSONObject jsonObject = new JSONObject(jsonData);

            return getDouble(jsonObject, key, defaultValue);

        } catch (JSONException e) {

            if (isPrintException) {

                e.printStackTrace();

            }

            return defaultValue;

        }

    }

    public static double getDouble(JSONObject jsonObject, String key, double defaultValue) {

        return getDouble(jsonObject, key, (Double) defaultValue);

    }

    public static double getDouble(String jsonData, String key, double defaultValue) {

        return getDouble(jsonData, key, (Double) defaultValue);

    }

    public static String getString(JSONObject jsonObject, String key, String defaultValue) {

        if (jsonObject == null || key.isEmpty()) {

            return defaultValue;

        }

        try {

            return jsonObject.getString(key);

        } catch (JSONException e) {

            if (isPrintException) {

                e.printStackTrace();

            }

            return defaultValue;

        }

    }

    public static String getString(String jsonData, String key, String defaultValue) {

        if (jsonData.isEmpty()) {

            return defaultValue;

        }

        try {

            JSONObject jsonObject = new JSONObject(jsonData);

            return getString(jsonObject, key, defaultValue);

        } catch (JSONException e) {

            if (isPrintException) {

                e.printStackTrace();

            }

            return defaultValue;

        }

    }


    public static JSONObject getJSONObject(JSONObject jsonObject, String key, JSONObject defaultValue) {

        if (jsonObject == null || key.isEmpty()) {

            return defaultValue;

        }

        try {

            return jsonObject.getJSONObject(key);

        } catch (JSONException e) {

            if (isPrintException) {

                e.printStackTrace();

            }

            return defaultValue;

        }

    }

    public static JSONObject getJSONObject(String jsonData, String key, JSONObject defaultValue) {

        if (jsonData.isEmpty()) {

            return defaultValue;

        }

        try {

            JSONObject jsonObject = new JSONObject(jsonData);

            return getJSONObject(jsonObject, key, defaultValue);

        } catch (JSONException e) {

            if (isPrintException) {

                e.printStackTrace();

            }

            return defaultValue;

        }

    }


    public static boolean getBoolean(JSONObject jsonObject, String key, Boolean defaultValue) {

        if (jsonObject == null || key.isEmpty()) {

            return defaultValue;

        }

        try {

            return jsonObject.getBoolean(key);

        } catch (JSONException e) {

            if (isPrintException) {

                e.printStackTrace();

            }

            return defaultValue;

        }

    }

    public static boolean getBoolean(String jsonData, String key, Boolean defaultValue) {

        if (jsonData.isEmpty()) {

            return defaultValue;

        }

        try {

            JSONObject jsonObject = new JSONObject(jsonData);

            return getBoolean(jsonObject, key, defaultValue);

        } catch (JSONException e) {

            if (isPrintException) {

                e.printStackTrace();

            }

            return defaultValue;

        }

    }


    public static Object getObject(String jsonData, String key) {

        if (jsonData.isEmpty()) {

            return null;

        }
        try {

            JSONObject jsonObject = new JSONObject(jsonData);

            return jsonObject.get(key);

        } catch (JSONException e) {

            if (isPrintException) {

                e.printStackTrace();

            }

            return null;

        }


    }
}







