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

    static RadioGroup sRadioGroupItemQuantity;
    static RadioGroup sRadioGroupPlayerSpeed;

    static RadioButton sRadioIButtonItemQuantity;
    static RadioButton sRadioIButtonPlayerSpeed;

    public static final String INTENT_ITEM_QUANTITY = "INTENT_ITEM_QUANTITY";
    public static final String INTENT_PLAYER_SPEED = "INTENT_PLAYER_SPEED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sRadioGroupItemQuantity = (RadioGroup) findViewById(R.id.RadioGroupItemQuantity);
        sRadioGroupPlayerSpeed = (RadioGroup) findViewById(R.id.RadioGroupPlayerSpeed);


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

                sRadioIButtonItemQuantity = (RadioButton) findViewById(sRadioGroupItemQuantity.getCheckedRadioButtonId());
                sRadioIButtonPlayerSpeed = (RadioButton) findViewById(sRadioGroupPlayerSpeed.getCheckedRadioButtonId());

                Log.w( "DEBUG_DATA", "sRadioIButtonItemQuantity.getId()" + sRadioIButtonItemQuantity.getText());

                // インテントのインスタンス生成
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra(INTENT_ITEM_QUANTITY,sRadioIButtonItemQuantity.getText());
                intent.putExtra(INTENT_PLAYER_SPEED,sRadioIButtonPlayerSpeed.getText());
                // ゲーム画面の起動
                startActivity(intent);

            }
        });
    }

}
