package kirin3.jp.honeycombbattle.util;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

import static kirin3.jp.honeycombbattle.util.LogUtils.LOGD;

public class AnalyticsHelper {

    private static final String TAG = LogUtils.makeLogTag(AnalyticsHelper.class);

    private static FirebaseAnalytics mFirebaseAnalytics;

    public static synchronized void initializeAnalytic(Context context) {
        try {
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        } catch (Exception e) {
            setAnalyticsEnabled(false);
        }
    }


    public static void setAnalyticsConfig(String key,String value){
        setAnalytics("event001","CONFIG",key,value);
    }

    // P1,2,3,4の勝利数
    public static void setAnalyticsWinNum(String key,String value){
        setAnalytics("event002","WIN_NUM",key,value);
    }

    // インストールからのプレイ完了数を取得
    public static void setAnalyticsPlayNum(String key,String value){
        setAnalytics("event003","PLAY_NUM",key,value);
    }

    public static void setAnalytics(String id,String name,String key,String value){
        LOGD(TAG, "setAnalytics: id[" + id + "]" + "name[" + name + "]" + "key[" + key + "]" + "value[" + value + "]");
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        bundle.putString(key, value);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }


    private static void setAnalyticsEnabled(boolean enabled) {
        LOGD(TAG, "setAnalyticsEnabled: " + enabled);
        if (mFirebaseAnalytics != null) {
            mFirebaseAnalytics.setAnalyticsCollectionEnabled(enabled);
        }
    }
}
