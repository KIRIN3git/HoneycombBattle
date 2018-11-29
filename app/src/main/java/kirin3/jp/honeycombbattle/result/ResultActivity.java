package kirin3.jp.honeycombbattle.result;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import kirin3.jp.honeycombbattle.R;
import kirin3.jp.honeycombbattle.main.MainActivity;
import kirin3.jp.honeycombbattle.mng.PlayerMng;
import kirin3.jp.honeycombbattle.util.AdmobHelper;

public class ResultActivity extends AppCompatActivity {

    public static final String INTENT_WINNER_ID = "INTENT_WINNER_ID";
    public Context mContext;

    private InterstitialAd mInterstitialAd;

    static TextView sTextPlayerColor1,sTextPlayerColor2,sTextPlayerColor3,sTextPlayerColor4;
    static TextView sTextPlayerRank1,sTextPlayerRank2,sTextPlayerRank3,sTextPlayerRank4;
    static TextView sTextPlayerScore1,sTextPlayerScore2,sTextPlayerScore3,sTextPlayerScore4;
    static LinearLayout sLayoutPlayer1,sLayoutPlayer2,sLayoutPlayer3,sLayoutPlayer4;
    static ImageView sImgRank1,sImgRank2,sImgRank3,sImgRank4;
    static Button sButtonBack;
    static int sRankPlayer1 = -1,sRankPlayer2 = -1,sRankPlayer3 = -1,sRankPlayer4 = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        mContext = getApplicationContext();

        AdmobHelper.loadBanner((AdView)findViewById(R.id.adView));

        setRank();
        outputData();

        AdmobHelper.setInterstitialNextGame();
        AdmobHelper.sInterstitialAdNextGame.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void setRank(){

        Bundle extras = getIntent().getExtras();
        int winner_no = extras.getInt(INTENT_WINNER_ID);

        Log.w( "DEBUG_DATA", "INTENT_WINNER_ID " + INTENT_WINNER_ID);


        sRankPlayer1 = checkRank(0,winner_no);
        sRankPlayer2 = checkRank(1,winner_no);
        if(PlayerMng.sPlayerNumber >= 3 ) sRankPlayer3 = checkRank(2,winner_no);
        if(PlayerMng.sPlayerNumber >= 4 ) sRankPlayer4 = checkRank(3,winner_no);
    }





    public int checkRank(int user_id,int winner_id){

        int rank = 1;

        // 自分が勝者
        if(winner_id == user_id) return rank;

        // 他人が勝者だったら
        if( winner_id != -1 ) rank++;



         for (int i = 0; i < PlayerMng.sPlayerNumber; i++) {
             if (i == winner_id) continue;
             if (i == user_id) continue;
             if (PlayerMng.players.get(i).score > PlayerMng.players.get(user_id).score) {
                 rank++;
             }
         }
         return rank;
    }


    /*
    public int checkCostRank(int user_id,int winner_no){

        if(winner_no == user_id) return 1;

        int rank = 1;

        // 死んでいたら
        if(PlayerMng.players.get(user_id).lifeNum == 0){
            // 生存者の数をプラス
            rank += PlayerMng.getLifeUserNum();
            return rank;
        }
        //生存していたら
        else {
            if (winner_no != -1) rank++;

            for (int i = 0; i < PlayerMng.sPlayerNumber; i++) {
                if (i == winner_no) continue;
                if (i == user_id) continue;
                if (PlayerMng.players.get(i).lifeNum == 0) continue;
                if (PlayerMng.players.get(i).score > PlayerMng.players.get(user_id).score) {
                    rank++;
                }
            }
            return rank;
        }
    }
    */

    public void outputData(){

        sLayoutPlayer1 = (LinearLayout)findViewById(R.id.layoutPlayer1);
        sLayoutPlayer2 = (LinearLayout)findViewById(R.id.layoutPlayer2);
        sLayoutPlayer3 = (LinearLayout)findViewById(R.id.layoutPlayer3);
        sLayoutPlayer4 = (LinearLayout)findViewById(R.id.layoutPlayer4);
/*
        sTextPlayerRank1 = (TextView)findViewById(R.id.textPlayerRank1);
        sTextPlayerRank2 = (TextView)findViewById(R.id.textPlayerRank2);
        sTextPlayerRank3 = (TextView)findViewById(R.id.textPlayerRank3);
        sTextPlayerRank4 = (TextView)findViewById(R.id.textPlayerRank4);
*/
        sTextPlayerScore1 = (TextView)findViewById(R.id.textPlayerScore1);
        sTextPlayerScore2 = (TextView)findViewById(R.id.textPlayerScore2);
        sTextPlayerScore3 = (TextView)findViewById(R.id.textPlayerScore3);
        sTextPlayerScore4 = (TextView)findViewById(R.id.textPlayerScore4);

        sImgRank1 = (ImageView) findViewById(R.id.imgRank1);
        sImgRank2 = (ImageView) findViewById(R.id.imgRank2);
        sImgRank3 = (ImageView) findViewById(R.id.imgRank3);
        sImgRank4 = (ImageView) findViewById(R.id.imgRank4);

        sButtonBack = (Button) findViewById(R.id.buttonBack);

        setCircleColor();


        setImgRank(sTextPlayerRank1,sImgRank1,sRankPlayer1);
        setImgRank(sTextPlayerRank2,sImgRank2,sRankPlayer2);
        setImgRank(sTextPlayerRank3,sImgRank3,sRankPlayer3);
        setImgRank(sTextPlayerRank4,sImgRank4,sRankPlayer4);

        sTextPlayerScore1.setText(PlayerMng.players.get(0).score + "点");
        sTextPlayerScore2.setText(PlayerMng.players.get(1).score + "点");
        if(PlayerMng.sPlayerNumber >= 3 ) sTextPlayerScore3.setText(PlayerMng.players.get(2).score + "点");
        if(PlayerMng.sPlayerNumber >= 4 ) sTextPlayerScore4.setText(PlayerMng.players.get(3).score + "点");

        if(PlayerMng.sPlayerNumber >= 3 ) sLayoutPlayer3.setVisibility(View.VISIBLE);
        if(PlayerMng.sPlayerNumber >= 4 ) sLayoutPlayer4.setVisibility(View.VISIBLE);


        sButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( !AdmobHelper.loadInterstitialNextGame() ) {
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void setCircleColor(){

        sTextPlayerColor1 = (TextView)findViewById(R.id.textPlayerColor1);
        sTextPlayerColor2 = (TextView)findViewById(R.id.textPlayerColor2);
        sTextPlayerColor3 = (TextView)findViewById(R.id.textPlayerColor3);
        sTextPlayerColor4 = (TextView)findViewById(R.id.textPlayerColor4);

        sTextPlayerColor1.setTextColor(Color.rgb(PlayerMng.PLAYER_COLOR[0][0], PlayerMng.PLAYER_COLOR[0][1], PlayerMng.PLAYER_COLOR[0][2]));
        sTextPlayerColor2.setTextColor(Color.rgb(PlayerMng.PLAYER_COLOR[1][0], PlayerMng.PLAYER_COLOR[1][1], PlayerMng.PLAYER_COLOR[1][2]));
        sTextPlayerColor3.setTextColor(Color.rgb(PlayerMng.PLAYER_COLOR[2][0], PlayerMng.PLAYER_COLOR[2][1], PlayerMng.PLAYER_COLOR[2][2]));
        sTextPlayerColor4.setTextColor(Color.rgb(PlayerMng.PLAYER_COLOR[3][0], PlayerMng.PLAYER_COLOR[3][1], PlayerMng.PLAYER_COLOR[3][2]));
    }

    public void setImgRank(TextView textPlayerRank,ImageView imgRank,int rank){

//        textPlayerRank.setText(rank + " 位");

        if( rank == 1 ){
            imgRank.setImageResource(R.drawable.rank1);
        }
        else if( rank == 2 ){
            imgRank.setImageResource(R.drawable.rank2);
        }
        else if( rank == 3 ){
            imgRank.setImageResource(R.drawable.rank3);
        }
        else imgRank.setImageDrawable(null);
    }
}
