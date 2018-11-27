package kirin3.jp.honeycombbattle.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class SettingsUtils {

    public static final String PREF_SETTING_TIME = "pref_setting_time";
    public static final String PREF_SETTING_NUMBER = "pref_setting_number";
    public static final String PREF_SETTING_SPEED = "pref_setting_speed";
    public static final String PREF_SETTING_ITEM = "pref_setting_item";
    public static final String PREF_SETTING_FIELD = "pref_setting_field";

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

    // 人数のラジオボタンのIDを保存
    public static void setSettingRadioIdNumber(final Context context, final Integer number_id) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putInt(PREF_SETTING_NUMBER, number_id).apply();
    }

    // 人数のラジオボタンのIDを取得
    public static Integer getSettingRadioIdNumber(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getInt(PREF_SETTING_NUMBER, 0);
    }

    // 速さのラジオボタンのIDを保存
    public static void setSettingRadioIdSpeed(final Context context, final Integer speed_id) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putInt(PREF_SETTING_SPEED, speed_id).apply();
    }

    // 速さのラジオボタンのIDを取得
    public static Integer getSettingRadioIdSpeed(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getInt(PREF_SETTING_SPEED, 0);
    }

    // アイテムのラジオボタンのIDを保存
    public static void setSettingRadioIdItem(final Context context, final Integer item_id) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putInt(PREF_SETTING_ITEM, item_id).apply();
    }

    // アイテムのラジオボタンのIDを取得
    public static Integer getSettingRadioIdItem(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getInt(PREF_SETTING_ITEM, 0);
    }

    // フィールドのラジオボタンのIDを保存
    public static void setSettingRadioIdField(final Context context, final Integer field_id) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putInt(PREF_SETTING_FIELD, field_id).apply();
    }

    // フィールドのラジオボタンのIDを取得
    public static Integer getSettingRadioIdField(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getInt(PREF_SETTING_FIELD, 0);
    }
}
