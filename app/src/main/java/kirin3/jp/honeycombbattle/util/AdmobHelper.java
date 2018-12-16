package kirin3.jp.honeycombbattle.util;

import android.content.Context;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import kirin3.jp.honeycombbattle.R;

import static kirin3.jp.honeycombbattle.util.LogUtils.LOGD;

public class AdmobHelper {

    private static final String TAG = LogUtils.makeLogTag(AdmobHelper.class);


    private static Context sAppContext = null;
    public static InterstitialAd sInterstitialAdNextGame;
    public static String test;

    // 初回のみ起動
    // バナーもインターステシャルも必要
    public static void initializeAdmob(Context context) {
        LOGD(TAG, "initializeAdmob");
        sAppContext = context.getApplicationContext();
        MobileAds.initialize(sAppContext, sAppContext.getResources().getString(R.string.admob_app_id));
    }

    /*
     * バナー呼び出し
     * AdmobHelper.setAdmob((AdView)findViewById(R.id.adView));
     */
    public static void loadBanner(AdView view) {
        LOGD(TAG, "loadBanner");
        AdRequest adRequest = new AdRequest.Builder().build();
        view.loadAd(adRequest);
    }

    /*
     * インターステシャル設定
     * onCreateで呼ぶと一度しかロードできないので注意
     */
    public static void setInterstitialNextGame() {
        LOGD(TAG, "setInterstitialNextGame");
        // イニシャライズ
        sInterstitialAdNextGame = new InterstitialAd(sAppContext);
        sInterstitialAdNextGame.setAdUnitId(sAppContext.getResources().getString(R.string.interstitial_ad_unit_id_test));
        // ロード
        sInterstitialAdNextGame.loadAd(new AdRequest.Builder().build());
    }

    /*
     * インターステシャル呼び出し
     * 成功時:true,失敗時:false
     */
    public static boolean loadInterstitialNextGame() {
        LOGD(TAG, "loadInterstitialNextGame");
        if (sInterstitialAdNextGame.isLoaded()) {
            sInterstitialAdNextGame.show();
            return true;
        }
        // まだロードできていない or ロードしていない
        else return false;
    }

    /*
     * インターステシャルの閉じた判定サンプル
     */
/*
        AdmobHelper.sInterstitialAdNextGame.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                Intent intent = new Intent(mContext, TopActivity.class);
                startActivity(intent);
            }
        });
 */
}
