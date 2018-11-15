package kirin3.jp.honeycombbattle.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class SettingsUtils {

    public static final String PREF_SETTING_TIME = "pref_setting_time";

    // TIMEのラジオボタンのIDを保存
    public static void setSettingRadioIdTime(final Context context, final Integer time_id) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putInt(PREF_SETTING_TIME, time_id).apply();
    }

    // TIMEのラジオボタンのIDを取得
    public static Integer getSettingRadioIdTime(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getInt(PREF_SETTING_TIME, 0);
    }
}
