/*!
 @file AnalyticsHelper.java
 ITS AP Forum FUKUOKA

 @author
    Created by Nagakura Hideharu.
 @copyright
    Copyright (c) 2018 Jorudan Co.,Ltd. All rights reserved.
 */
package kirin3.jp.honeycombbattle.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
/*
import com.google.firebase.analytics.FirebaseAnalytics;

import static jp.co.jorudan.itsapfukuoka.util.LogUtils.LOGD;

public class AnalyticsHelper {

    private static final String TAG = LogUtils.makeLogTag(AnalyticsHelper.class);

    @SuppressLint("StaticFieldLeak")
    private static Context sAppContext = null;

    private static FirebaseAnalytics mFirebaseAnalytics;

    private static String FA_CONTENT_TYPE_UI_EVENT = "ui event";
    private static String FA_KEY_UI_ACTION = "ui_action";
    private static String FA_KEY_USER_ID = "user_id";

    public static void prepareAnalytics(Context context) {
        sAppContext = context.getApplicationContext();
        initializeAnalytics();
    }

    private static synchronized void initializeAnalytics() {
        try {
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(sAppContext);
        } catch (Exception e) {
            setAnalyticsEnabled(false);
        }
    }

    public static void sendEvent(String itemId, String action, String userId) {
        Bundle params = new Bundle();
        params.putString(FirebaseAnalytics.Param.ITEM_ID, itemId);
        params.putString(FirebaseAnalytics.Param.CONTENT_TYPE, FA_CONTENT_TYPE_UI_EVENT);
        params.putString(FA_KEY_UI_ACTION, action);
        params.putString(FA_KEY_USER_ID, userId);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, params);
        LOGD(TAG, "Event recorded for " + itemId + ", " + action);
    }

    private static void setAnalyticsEnabled(boolean enabled) {
        LOGD(TAG, "Setting Analytics enabled: " + enabled);
        if (mFirebaseAnalytics != null) {
            mFirebaseAnalytics.setAnalyticsCollectionEnabled(enabled);
        }
    }
}
*/