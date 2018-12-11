package kirin3.jp.honeycombbattle.util;

import android.content.Context;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

import static kirin3.jp.honeycombbattle.util.LogUtils.LOGD;

public class CrashlyticsHelper {

    private static final String TAG = LogUtils.makeLogTag(CrashlyticsHelper.class);

    public static void initializeCrashlytics(Context context) {
        LOGD(TAG, "initializeCrashlytics");
        Fabric.with(context, new Crashlytics());
    }

    public static void forceCrashlytics() {
        LOGD(TAG, "forceCrashlytics");
        Crashlytics.getInstance().crash(); // Force a crash
    }
}
