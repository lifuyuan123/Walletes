package com.wallet.bo.wallets.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.wallet.bo.wallets.pojo.Config;


public class SharedPreferencesUtil {
	static SharedPreferencesUtil mUtil;
	SharedPreferences mShared = null;
	
	public static void init(Context context){
		if(mUtil == null) {
			mUtil = new SharedPreferencesUtil(context.getApplicationContext());
		}
	}
	
	public static SharedPreferencesUtil getInstance() {
		if(mUtil == null) {
			try {
				throw new Throwable("please init first");
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		return mUtil;
	}

	private SharedPreferencesUtil(Context ctx) {
		mShared = ctx.getSharedPreferences(Config.SPKEY, Context.MODE_PRIVATE);
	}

	public boolean getBoolean(String key, boolean defValue) {
		return mShared.getBoolean(key, defValue);
	}

	public void putBoolean(String key, boolean value) {
		mShared.edit().putBoolean(key, value).commit();
	}

	public float getFloat(String key, float defValue) {
		return mShared.getFloat(key, defValue);
	}

	public void putFloat(String key, float value) {
		mShared.edit().putFloat(key, value).commit();
	}

	public long getLong(String key, long defValue) {
		return mShared.getLong(key, defValue);
	}

	public void putLong(String key, long value) {
		mShared.edit().putLong(key, value).commit();
	}

	public int getInt(String key, int defValue) {
		return mShared.getInt(key, defValue);
	}

	public void putInt(String key, int value) {
		mShared.edit().putInt(key, value).commit();
	}

	public String getString(String key, String defValue) {
		return mShared.getString(key, defValue);
	}

	public void putString(String key, String value) {
		mShared.edit().putString(key, value).commit();
	}

	public void clearAll(){
		mShared.edit().clear().commit();
	}
}