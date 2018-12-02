package kirin3.jp.honeycombbattle.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

//import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.AdView;

import kirin3.jp.honeycombbattle.R;
import kirin3.jp.honeycombbattle.game.GameActivity;
import kirin3.jp.honeycombbattle.game.GameSurfaceView;
import kirin3.jp.honeycombbattle.util.AdmobHelper;
import kirin3.jp.honeycombbattle.util.SettingsUtils;
import kirin3.jp.honeycombbattle.util.ViewUtils;

public class MainActivity extends AppCompatActivity {

    Context mContext;

    static RadioGroup sRadioGroupBattleTime;
    static RadioGroup sRadioGroupPlayerNumber;
    static RadioGroup sRadioGroupPlayerSpeed;
    static RadioGroup sRadioGroupItemQuantity;
    static RadioGroup sRadioGroupFieldSize;

    static RadioButton sRadioIButtonBattleTime;
    static RadioButton sRadioIButtonPlayerNumber;
    static RadioButton sRadioIButtonPlayerSpeed;
    static RadioButton sRadioIButtonItemQuantity;
    static RadioButton sRadioButtonFieldSize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Crashlytics.getInstance().crash(); // Force a crash

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        setTheme(R.style.AppTheme);


        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();

        // AdMob設定
        AdmobHelper.initializeAdmob(mContext);
        AdmobHelper.loadBanner((AdView)findViewById(R.id.adView));

        int id = 0;
        id = SettingsUtils.getSettingRadioIdTime(mContext);
        if( id != 0 ){
            sRadioIButtonBattleTime = (RadioButton) findViewById(id);
            sRadioIButtonBattleTime.setChecked(true);
        }
        id = SettingsUtils.getSettingRadioIdNumber(mContext);
        if( id != 0 ){
            sRadioIButtonPlayerNumber = (RadioButton) findViewById(id);
            sRadioIButtonPlayerNumber.setChecked(true);
        }
        id = SettingsUtils.getSettingRadioIdSpeed(mContext);
        if( id != 0 ){
            sRadioIButtonPlayerSpeed = (RadioButton) findViewById(id);
            sRadioIButtonPlayerSpeed.setChecked(true);
        }
        id = SettingsUtils.getSettingRadioIdItem(mContext);
        if( id != 0 ){
            sRadioIButtonItemQuantity = (RadioButton) findViewById(id);
            sRadioIButtonItemQuantity.setChecked(true);
        }
        id = SettingsUtils.getSettingRadioIdField(mContext);
        if( id != 0 ){
            sRadioButtonFieldSize = (RadioButton) findViewById(id);
            sRadioButtonFieldSize.setChecked(true);
        }
        // タブレットだった場合は大きいを最初から洗濯
        else{
            sRadioButtonFieldSize = (RadioButton) findViewById(R.id.RadioItemFieldSize5);
            sRadioButtonFieldSize.setChecked(true);
        }

        sRadioGroupBattleTime = (RadioGroup) findViewById(R.id.RadioGroupBattleTime);
        sRadioGroupPlayerNumber = (RadioGroup) findViewById(R.id.RadioGroupPlayerNumber);
        sRadioGroupPlayerSpeed = (RadioGroup) findViewById(R.id.RadioGroupPlayerSpeed);
        sRadioGroupItemQuantity = (RadioGroup) findViewById(R.id.RadioGroupItemQuantity);
        sRadioGroupFieldSize = (RadioGroup) findViewById(R.id.RadioGroupFieldSize);

        sRadioGroupItemQuantity.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            // ラジオグループのチェック状態が変更された時に呼び出されます
            // チェック状態が変更されたラジオボタンのIDが渡されます
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) findViewById(checkedId);
            }
        });

        Log.w( "DEBUG_DATA", "getXDpi " + ViewUtils.getXDpi(getApplicationContext().getResources()) );
        Log.w( "DEBUG_DATA", "getDisplayMagnification " + ViewUtils.getDisplayMagnification(getApplicationContext().getResources()) );


        Log.w( "DEBUG_DATA", "is_tablet " + ViewUtils.checkTablet(getApplicationContext().getResources()));

        DisplayMetrics metrics = new DisplayMetrics();
        Log.w( "DEBUG_DATA", "metrics.densityDpi " + metrics.densityDpi );


        Button btn = (Button)findViewById(R.id.game_start);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sRadioIButtonBattleTime = (RadioButton) findViewById(sRadioGroupBattleTime.getCheckedRadioButtonId());
                sRadioIButtonPlayerNumber = (RadioButton) findViewById(sRadioGroupPlayerNumber.getCheckedRadioButtonId());
                sRadioIButtonPlayerSpeed = (RadioButton) findViewById(sRadioGroupPlayerSpeed.getCheckedRadioButtonId());
                sRadioIButtonItemQuantity = (RadioButton) findViewById(sRadioGroupItemQuantity.getCheckedRadioButtonId());
                sRadioButtonFieldSize = (RadioButton) findViewById(sRadioGroupFieldSize.getCheckedRadioButtonId());

                SettingsUtils.setSettingRadioIdTime(mContext, sRadioGroupBattleTime.getCheckedRadioButtonId());
                SettingsUtils.setSettingRadioIdNumber(mContext, sRadioGroupPlayerNumber.getCheckedRadioButtonId());
                SettingsUtils.setSettingRadioIdSpeed(mContext, sRadioGroupPlayerSpeed.getCheckedRadioButtonId());
                SettingsUtils.setSettingRadioIdItem(mContext, sRadioGroupItemQuantity.getCheckedRadioButtonId());
                SettingsUtils.setSettingRadioIdField(mContext, sRadioGroupFieldSize.getCheckedRadioButtonId());


                // インテントのインスタンス生成
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra(GameSurfaceView.INTENT_BATTLE_TIME,sRadioIButtonBattleTime.getText());
                intent.putExtra(GameSurfaceView.INTENT_PLAYER_NUMBER,sRadioIButtonPlayerNumber.getText());
                intent.putExtra(GameSurfaceView.INTENT_PLAYER_SPEED,sRadioIButtonPlayerSpeed.getText());
                intent.putExtra(GameSurfaceView.INTENT_ITEM_QUANTITY,sRadioIButtonItemQuantity.getText());
                intent.putExtra(GameSurfaceView.INTENT_FIELD_SIZE,sRadioButtonFieldSize.getText());
                // ゲーム画面の起動
                startActivity(intent);

            }
        });
    }

}
