package com.example.omnisheba;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * To switch between fragments and activities
 * @author Nafisa Hossain Nujat
 */
public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "my_shared_preff";

    private static SharedPrefManager mInstance;
    private Context mCtx;

    private SharedPrefManager(Context mCtx) {
        this.mCtx = mCtx;
    }

    /**
     * Method to fetch activity/fragment
     * @param mCtx
     * @return instance containing the context
     */
    public static synchronized SharedPrefManager getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(mCtx);
        }
        return mInstance;
    }

    /**
     * Method to clear activity/fragment
     */
    public void clear() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public class Editor {
    }
}
