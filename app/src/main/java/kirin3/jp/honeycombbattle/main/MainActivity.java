package kirin3.jp.honeycombbattle.main;

import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.lang.reflect.Method;

import kirin3.jp.honeycombbattle.R;
import kirin3.jp.honeycombbattle.game.GameActivity;

public class MainActivity extends AppCompatActivity {

    static RadioGroup sRadioGroupBattleTime;
    static RadioGroup sRadioGroupPlayerNumber;
    static RadioGroup sRadioGroupPlayerSpeed;
    static RadioGroup sRadioGroupItemQuantity;

    static RadioButton sRadioIButtonBattleTime;
    static RadioButton sRadioIButtonPlayerNumber;
    static RadioButton sRadioIButtonPlayerSpeed;
    static RadioButton sRadioIButtonItemQuantity;

    public static final String INTENT_BATTLE_TIME = "INTENT_BATTLE_TIME";
    public static final String INTENT_PLAYER_SPEED = "INTENT_PLAYER_SPEED";
    public static final String INTENT_PLAYER_NUMBER = "INTENT_PLAYER_NUMBER";
    public static final String INTENT_ITEM_QUANTITY = "INTENT_ITEM_QUANTITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sRadioGroupBattleTime = (RadioGroup) findViewById(R.id.RadioGroupBattleTime);
        sRadioGroupPlayerNumber = (RadioGroup) findViewById(R.id.RadioGroupPlayerNumber);
        sRadioGroupPlayerSpeed = (RadioGroup) findViewById(R.id.RadioGroupPlayerSpeed);
        sRadioGroupItemQuantity = (RadioGroup) findViewById(R.id.RadioGroupItemQuantity);


        sRadioGroupItemQuantity.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            // ラジオグループのチェック状態が変更された時に呼び出されます
            // チェック状態が変更されたラジオボタンのIDが渡されます
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) findViewById(checkedId);
            }
        });

        Button btn = (Button)findViewById(R.id.game_start);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sRadioIButtonBattleTime = (RadioButton) findViewById(sRadioGroupPlayerNumber.getCheckedRadioButtonId());
                sRadioIButtonPlayerNumber = (RadioButton) findViewById(sRadioGroupPlayerNumber.getCheckedRadioButtonId());
                sRadioIButtonPlayerSpeed = (RadioButton) findViewById(sRadioGroupPlayerSpeed.getCheckedRadioButtonId());
                sRadioIButtonItemQuantity = (RadioButton) findViewById(sRadioGroupItemQuantity.getCheckedRadioButtonId());

                // インテントのインスタンス生成
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra(INTENT_BATTLE_TIME,sRadioIButtonBattleTime.getText());
                intent.putExtra(INTENT_PLAYER_NUMBER,sRadioIButtonPlayerNumber.getText());
                intent.putExtra(INTENT_PLAYER_SPEED,sRadioIButtonPlayerSpeed.getText());
                intent.putExtra(INTENT_ITEM_QUANTITY,sRadioIButtonItemQuantity.getText());
                // ゲーム画面の起動
                startActivity(intent);

            }
        });
    }

}
