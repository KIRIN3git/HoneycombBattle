package kirin3.jp.honeycombbattle.util;

import android.util.Log;

import kirin3.jp.honeycombbattle.Config;


public class LogUtils {
    private static final String LOG_PREFIX = "util_";

    /*
     * private static final String TAG = LogUtils.makeLogTag(AnalyticsHelper.class);
     * LOGD(TAG, "setAnalyticsEnabled : " + enabled);
     */
    public static String makeLogTag(Class cls) {
        return makeLogTag(cls.getSimpleName());
    }

    private static String makeLogTag(String str) {
        return LOG_PREFIX + str;
    }

    public static void LOGD(String tag, String message) {
        if (Config.IS_DOGFOOD_BUILD) {
            Log.d(tag, message);
        }
    }

    public static void LOGV(String tag, String message) {
        if (Config.IS_DOGFOOD_BUILD) {
            Log.v(tag, message);
        }
    }

    public static void LOGI(String tag, String message) {
        if (Config.IS_DOGFOOD_BUILD) {
            Log.i(tag, message);
        }
    }

    public static void LOGW(String tag, String message) {
        if (Config.IS_DOGFOOD_BUILD) {
            Log.w(tag, message);
        }
    }

    public static void LOGE(String tag, String message) {
        if (Config.IS_DOGFOOD_BUILD) {
            Log.e(tag, message);
        }
    }



}
