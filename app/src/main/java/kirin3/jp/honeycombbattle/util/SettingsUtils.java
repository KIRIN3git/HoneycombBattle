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

    public static final String PREF_WINNING_NUM1 = "pref_winning_num1";
    public static final String PREF_WINNING_NUM2 = "pref_winning_num2";
    public static final String PREF_WINNING_NUM3 = "pref_winning_num3";
    public static final String PREF_WINNING_NUM4 = "pref_winning_num4";

    public static final String PREF_PLAY_NUM = "pref_play_num";

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


    // プレイヤー1の勝利数を保存
    public static void setWinningNum1(final Context context, final Integer num) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putInt(PREF_WINNING_NUM1, num).apply();

        if (num != 0) AnalyticsHelper.setAnalyticsWinNum("win_num1", num);
    }

    // プレイヤー1の勝利数を取得
    public static Integer getWiiningNum1(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getInt(PREF_WINNING_NUM1, 0);
    }

    // プレイヤー2の勝利数を保存
    public static void setWinningNum2(final Context context, final Integer num) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putInt(PREF_WINNING_NUM2, num).apply();

        if (num != 0) AnalyticsHelper.setAnalyticsWinNum("win_num2", num);
    }

    // プレイヤー2の勝利数を取得
    public static Integer getWiiningNum2(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getInt(PREF_WINNING_NUM2, 0);
    }

    // プレイヤー3の勝利数を保存
    public static void setWinningNum3(final Context context, final Integer num) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putInt(PREF_WINNING_NUM3, num).apply();

        if (num != 0) AnalyticsHelper.setAnalyticsWinNum("win_num3", num);
    }

    // プレイヤー3の勝利数を取得
    public static Integer getWiiningNum3(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getInt(PREF_WINNING_NUM3, 0);
    }

    // プレイヤー4の勝利数を保存
    public static void setWinningNum4(final Context context, final Integer num) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putInt(PREF_WINNING_NUM4, num).apply();

        if (num != 0) AnalyticsHelper.setAnalyticsWinNum("win_num4", num);
    }

    // プレイヤー4の勝利数を取得
    public static Integer getWiiningNum4(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getInt(PREF_WINNING_NUM4, 0);
    }

    // プレイヤーの勝利数をリセット
    public static void resetWinningNum(final Context context) {
        setWinningNum1(context, 0);
        setWinningNum2(context, 0);
        setWinningNum3(context, 0);
        setWinningNum4(context, 0);
    }


    // ゲームのクリア数を保存
    public static void setPlayNum(final Context context, final Integer num) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putInt(PREF_PLAY_NUM, num).apply();

        AnalyticsHelper.setAnalyticsPlayNum("play_num", num);
    }

    // プレイヤー4の勝利数を取得
    public static Integer getPlayNum(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getInt(PREF_PLAY_NUM, 0);
    }
}
