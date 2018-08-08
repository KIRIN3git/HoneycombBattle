package kirin3.jp.honeycombbattle.result;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import kirin3.jp.honeycombbattle.R;
import kirin3.jp.honeycombbattle.mng.PlayerMng;

public class ResultActivity extends AppCompatActivity {

    public static final String INTENT_WINNER_NO = "INTENT_WINNER_NO";
    public Context mContext;

    static TextView sTextPlayerRank1,sTextPlayerRank2,sTextPlayerRank3,sTextPlayerRank4;
    static TextView sTextPlayerScore1,sTextPlayerScore2,sTextPlayerScore3,sTextPlayerScore4;
    static LinearLayout sLayoutPlayer3,sLayoutPlayer4;
    static int rankPlayer1 = -1,rankPlayer2 = -1,rankPlayer3 = -1,rankPlayer4 = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        mContext = getApplicationContext();

        setRank();

        outputData();
    }

    public void setRank(){

        Bundle extras = getIntent().getExtras();
        int winner_no = extras.getInt(INTENT_WINNER_NO);

        Log.w( "DEBUG_DATA", "INTENT_WINNER_NO " + INTENT_WINNER_NO);

        if( winner_no == 0 ) rankPlayer1 = 1;
        else if( winner_no == 1 ) rankPlayer2 = 1;
        else if( winner_no == 2 ) rankPlayer3 = 1;
        else if( winner_no == 3 ) rankPlayer4 = 1;

        rankPlayer1 = checkCostRank(0,winner_no);
        rankPlayer2 = checkCostRank(1,winner_no);
        if(PlayerMng.sPlayerNumber >= 3 ) rankPlayer3 = checkCostRank(2,winner_no);
        if(PlayerMng.sPlayerNumber >= 4 ) rankPlayer4 = checkCostRank(3,winner_no);
    }

    public int checkCostRank(int user_id,int winner_no){

        if(winner_no == user_id) return 1;

        int rank = 1;
        if( winner_no != -1) rank++;

        for(int i = 0; i < PlayerMng.sPlayerNumber; i++){
            if( i == winner_no ) continue;
            if( i == user_id ) continue;
            if( PlayerMng.players.get(i).score > PlayerMng.players.get(user_id).score ){
                rank++;
            }
        }
        return rank;
    }

    public void outputData(){
        sTextPlayerRank1 = (TextView)findViewById(R.id.textPlayerRank1);
        sTextPlayerRank2 = (TextView)findViewById(R.id.textPlayerRank2);
        if(PlayerMng.sPlayerNumber >= 3 ) sTextPlayerRank3 = (TextView)findViewById(R.id.textPlayerRank3);
        if(PlayerMng.sPlayerNumber >= 4 ) sTextPlayerRank4 = (TextView)findViewById(R.id.textPlayerRank4);

        sTextPlayerScore1 = (TextView)findViewById(R.id.textPlayerScore1);
        sTextPlayerScore2 = (TextView)findViewById(R.id.textPlayerScore2);
        if(PlayerMng.sPlayerNumber >= 3 ) sTextPlayerScore3 = (TextView)findViewById(R.id.textPlayerScore3);
        if(PlayerMng.sPlayerNumber >= 4 ) sTextPlayerScore4 = (TextView)findViewById(R.id.textPlayerScore4);

        sTextPlayerRank1.setText(rankPlayer1 + " 位");
        sTextPlayerRank2.setText(rankPlayer2 + " 位");
        if(PlayerMng.sPlayerNumber >= 3 ) sTextPlayerRank3.setText(rankPlayer3 + " 位");
        if(PlayerMng.sPlayerNumber >= 4 ) sTextPlayerRank4.setText(rankPlayer4 + " 位");

        sTextPlayerScore1.setText(PlayerMng.players.get(0).score + " 点");
        sTextPlayerScore2.setText(PlayerMng.players.get(1).score + " 点");
        if(PlayerMng.sPlayerNumber >= 3 ) sTextPlayerScore3.setText(PlayerMng.players.get(2).score + " 点");
        if(PlayerMng.sPlayerNumber >= 4 ) sTextPlayerScore4.setText(PlayerMng.players.get(3).score + " 点");

        sLayoutPlayer3 = (LinearLayout)findViewById(R.id.layoutPlayer3);
        sLayoutPlayer4 = (LinearLayout)findViewById(R.id.layoutPlayer4);
        if(PlayerMng.sPlayerNumber >= 3 ) sLayoutPlayer3.setVisibility(View.VISIBLE);
        if(PlayerMng.sPlayerNumber >= 4 ) sLayoutPlayer4.setVisibility(View.VISIBLE);
    }
}
