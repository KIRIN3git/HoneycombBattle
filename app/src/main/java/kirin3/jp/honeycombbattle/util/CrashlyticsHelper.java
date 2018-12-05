package kirin3.jp.honeycombbattle.util;

import android.content.Context;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class CrashlyticsHelper {

    private static final String TAG = LogUtils.makeLogTag(CrashlyticsHelper.class);

    public static void initializeCrashlytics(Context context) {
        Fabric.with(context, new Crashlytics());
    }

    public static void forceCrashlytics() {
        Crashlytics.getInstance().crash(); // Force a crash
    }
}
