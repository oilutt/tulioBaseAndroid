package oilutt.baseproject.utils;

import android.content.Context;
import android.content.SharedPreferences;

import oilutt.baseproject.app.Constants;

/**
 * Created by oilut on 21/08/2017.
 */
public final class PreferencesManager {

    private static final String PREF_NAME = Constants.SharedPreferences.PATH_APP;

    private static PreferencesManager sInstance;
    private final SharedPreferences mPref;

    private PreferencesManager(Context context) {
        mPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized void initializeInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PreferencesManager(context);
        }
    }

    public static synchronized PreferencesManager getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException(PreferencesManager.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }
        return sInstance;
    }

    public void setValue(String key, long value) {
        mPref.edit()
                .putLong(key, value)
                .apply() ;
    }

    public void setValue(String key, int value) {
        mPref.edit()
                .putInt(key, value)
                .apply() ;
    }

    public void setValue(String key, float value) {
        mPref.edit()
                .putFloat(key, value)
                .apply() ;
    }

    public void setValue(String key, String value) {
        mPref.edit()
                .putString(key, value)
                .apply() ;
    }

    public void setValue(String key, boolean value) {
        mPref.edit()
                .putBoolean(key, value)
                .apply() ;
    }

    public long getLong(String key) {
        return mPref.getLong(key, 0);
    }


    public int getInt(String key) {
        return mPref.getInt(key, 0);
    }

    public int getInt(String key, int defaultValue) {
        return mPref.getInt(key, defaultValue);
    }

    public float getFloat(String key) {
        return mPref.getFloat(key, 0);
    }


    public String getString(String key) {
        return mPref.getString(key, "");
    }

    public boolean getBoolean(String key) {
        return mPref.getBoolean(key, false);
    }


    public boolean getBoolean(String key, boolean defaultValue) {
        return mPref.getBoolean(key, defaultValue);
    }

    public void remove(String key) {
        mPref.edit()
                .remove(key)
                .apply() ;
    }

    public void clear() {
        mPref.edit()
                .clear()
                .apply() ;
    }
}
