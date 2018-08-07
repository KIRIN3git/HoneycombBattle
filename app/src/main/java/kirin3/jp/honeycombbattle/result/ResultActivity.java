package kirin3.jp.honeycombbattle.result;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.TextView;

import kirin3.jp.honeycombbattle.R;
import kirin3.jp.honeycombbattle.mng.PlayerMng;

public class ResultActivity extends AppCompatActivity {

    public static final String INTENT_WINNER_NO = "INTENT_WINNER_NO";
    public Context mContext;

    static TextView sTextWinUser1,sTextWinUser2,sTextWinUser3,sTextWinUser4;

    static int rank1 = -1,rank2 = -1,rank3 = -1,rank4 = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();

        setRank();


    }

    public void setRank(){

        Bundle extras = getIntent().getExtras();
        int winner_no = extras.getInt(INTENT_WINNER_NO);

        if(winner_no != -1){
            rank1 = winner_no;
        }

        for(int i = 0; i < PlayerMng.sPlayerNumber; i++){


        }
    }
}
