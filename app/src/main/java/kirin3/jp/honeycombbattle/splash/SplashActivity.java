package kirin3.jp.honeycombbattle.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;

import io.fabric.sdk.android.Fabric;
import kirin3.jp.honeycombbattle.R;
import kirin3.jp.honeycombbattle.game.GameActivity;
import kirin3.jp.honeycombbattle.game.GameSurfaceView;
import kirin3.jp.honeycombbattle.top.TopActivity;
import kirin3.jp.honeycombbattle.util.AdmobHelper;
import kirin3.jp.honeycombbattle.util.AnalyticsHelper;
import kirin3.jp.honeycombbattle.util.CrashlyticsHelper;
import kirin3.jp.honeycombbattle.util.SettingsUtils;
import kirin3.jp.honeycombbattle.util.ViewUtils;

public class SplashActivity extends AppCompatActivity {

    Context mContext;

    private static final int SPLASH_DISPLAY_LENGHT = 2 * 1000;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getApplicationContext();


        CrashlyticsHelper.initializeCrashlytics(mContext);
        AdmobHelper.initializeAdmob(mContext);
        AnalyticsHelper.initializeAnalytic(mContext);

        // 勝利数カウントの初期化
        SettingsUtils.resetWinningNum(mContext);

        setContentView(R.layout.activity_splash);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isFinishing()) {
                    Intent intent = new Intent(SplashActivity.this, TopActivity.class);
                    startActivity(intent);
                }
            }
        }, SPLASH_DISPLAY_LENGHT);

    }

}
