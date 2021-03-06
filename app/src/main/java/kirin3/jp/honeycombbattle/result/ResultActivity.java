package kirin3.jp.honeycombbattle.result;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import kirin3.jp.honeycombbattle.R;
import kirin3.jp.honeycombbattle.mng.PlayerMng;
import kirin3.jp.honeycombbattle.top.TopActivity;
import kirin3.jp.honeycombbattle.util.AdmobHelper;
import kirin3.jp.honeycombbattle.util.LogUtils;
import kirin3.jp.honeycombbattle.util.SettingsUtils;

public class ResultActivity extends AppCompatActivity {

    public static final String INTENT_WINNER_ID = "INTENT_WINNER_ID";

    public Context mContext;

    private static final String TAG = LogUtils.makeLogTag(ResultActivity.class);

    private InterstitialAd mInterstitialAd;

    static TextView sTextPlayerColor1, sTextPlayerColor2, sTextPlayerColor3, sTextPlayerColor4;
    static TextView sTextPlayerRank1, sTextPlayerRank2, sTextPlayerRank3, sTextPlayerRank4;
    static TextView sTextPlayerScore1, sTextPlayerScore2, sTextPlayerScore3, sTextPlayerScore4;
    static TextView sTextWinningNum1, sTextWinningNum2, sTextWinningNum3, sTextWinningNum4;

    static LinearLayout sLayoutPlayer1, sLayoutPlayer2, sLayoutPlayer3, sLayoutPlayer4;
    static ImageView sImgRank1, sImgRank2, sImgRank3, sImgRank4;
    static ImageView sImgBone1, sImgBone2, sImgBone3, sImgBone4;
    static Button sButtonReset, sButtonBack;
    static int sRankPlayer1 = -1, sRankPlayer2 = -1, sRankPlayer3 = -1, sRankPlayer4 = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        mContext = getApplicationContext();

        AdmobHelper.loadBanner((AdView) findViewById(R.id.adView));


        sLayoutPlayer1 = (LinearLayout) findViewById(R.id.layoutPlayer1);
        sLayoutPlayer2 = (LinearLayout) findViewById(R.id.layoutPlayer2);
        sLayoutPlayer3 = (LinearLayout) findViewById(R.id.layoutPlayer3);
        sLayoutPlayer4 = (LinearLayout) findViewById(R.id.layoutPlayer4);
/*
        sTextPlayerRank1 = (TextView)findViewById(R.id.textPlayerRank1);
        sTextPlayerRank2 = (TextView)findViewById(R.id.textPlayerRank2);
        sTextPlayerRank3 = (TextView)findViewById(R.id.textPlayerRank3);
        sTextPlayerRank4 = (TextView)findViewById(R.id.textPlayerRank4);
*/
        sTextPlayerScore1 = (TextView) findViewById(R.id.textPlayerScore1);
        sTextPlayerScore2 = (TextView) findViewById(R.id.textPlayerScore2);
        sTextPlayerScore3 = (TextView) findViewById(R.id.textPlayerScore3);
        sTextPlayerScore4 = (TextView) findViewById(R.id.textPlayerScore4);

        sTextWinningNum1 = (TextView) findViewById(R.id.textWinningNum1);
        sTextWinningNum2 = (TextView) findViewById(R.id.textWinningNum2);
        sTextWinningNum3 = (TextView) findViewById(R.id.textWinningNum3);
        sTextWinningNum4 = (TextView) findViewById(R.id.textWinningNum4);

        sImgRank1 = (ImageView) findViewById(R.id.imgRank1);
        sImgRank2 = (ImageView) findViewById(R.id.imgRank2);
        sImgRank3 = (ImageView) findViewById(R.id.imgRank3);
        sImgRank4 = (ImageView) findViewById(R.id.imgRank4);

        sButtonReset = (Button) findViewById(R.id.buttonReset);
        sButtonBack = (Button) findViewById(R.id.buttonBack);

        /*
         * リセットボタン
         */
        sButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsUtils.resetWinningNum(mContext);
                sTextWinningNum1.setText("0");
                sTextWinningNum2.setText("0");
                sTextWinningNum3.setText("0");
                sTextWinningNum4.setText("0");

                sTextWinningNum1.setTextColor(getResources().getColor(R.color.lightBlue));
                sTextWinningNum2.setTextColor(getResources().getColor(R.color.lightBlue));
                sTextWinningNum3.setTextColor(getResources().getColor(R.color.lightBlue));
                sTextWinningNum4.setTextColor(getResources().getColor(R.color.lightBlue));
            }
        });

        sButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AdmobHelper.loadInterstitialNextGame()) {

                    Intent intent = new Intent(v.getContext(), TopActivity.class);
                    startActivity(intent);
                }
            }
        });

        setRank();
        setWinnerNum();
        setBone();
        setScore();
        setPlayNum();


        AdmobHelper.setInterstitialNextGame();
        AdmobHelper.sInterstitialAdNextGame.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                Intent intent = new Intent(mContext, TopActivity.class);
                startActivity(intent);
            }
        });
    }

    public void setRank() {

        Bundle extras = getIntent().getExtras();
        int winner_no = extras.getInt(INTENT_WINNER_ID);

        sRankPlayer1 = checkRank(0, winner_no);
        sRankPlayer2 = checkRank(1, winner_no);
        if (PlayerMng.sPlayerNumber >= 3) sRankPlayer3 = checkRank(2, winner_no);
        if (PlayerMng.sPlayerNumber >= 4) sRankPlayer4 = checkRank(3, winner_no);


        setImgRank(sTextPlayerRank1, sImgRank1, sRankPlayer1);
        setImgRank(sTextPlayerRank2, sImgRank2, sRankPlayer2);
        setImgRank(sTextPlayerRank3, sImgRank3, sRankPlayer3);
        setImgRank(sTextPlayerRank4, sImgRank4, sRankPlayer4);
    }

    /*
     * 勝利数の表示、プリファランスへの登録
     * setRank後の必要あり
     */
    public void setWinnerNum() {
        int win_num1, win_num2, win_num3, win_num4;
        win_num1 = SettingsUtils.getWiiningNum1(mContext) + (sRankPlayer1 == 1 ? 1 : 0);
        win_num2 = SettingsUtils.getWiiningNum2(mContext) + (sRankPlayer2 == 1 ? 1 : 0);
        win_num3 = SettingsUtils.getWiiningNum3(mContext) + (sRankPlayer3 == 1 ? 1 : 0);
        win_num4 = SettingsUtils.getWiiningNum4(mContext) + (sRankPlayer4 == 1 ? 1 : 0);

        sTextWinningNum1.setText(String.valueOf(win_num1));
        sTextWinningNum2.setText(String.valueOf(win_num2));
        sTextWinningNum3.setText(String.valueOf(win_num3));
        sTextWinningNum4.setText(String.valueOf(win_num4));

        if (win_num1 > 0) sTextWinningNum1.setTextColor(getResources().getColor(R.color.lightRed));
        if (win_num2 > 0) sTextWinningNum2.setTextColor(getResources().getColor(R.color.lightRed));
        if (win_num3 > 0) sTextWinningNum3.setTextColor(getResources().getColor(R.color.lightRed));
        if (win_num4 > 0) sTextWinningNum4.setTextColor(getResources().getColor(R.color.lightRed));

        if (win_num1 > 0) SettingsUtils.setWinningNum1(mContext, win_num1);
        if (win_num2 > 0) SettingsUtils.setWinningNum2(mContext, win_num2);
        if (win_num3 > 0) SettingsUtils.setWinningNum3(mContext, win_num3);
        if (win_num4 > 0) SettingsUtils.setWinningNum4(mContext, win_num4);
    }


    public int checkRank(int user_id, int winner_id) {

        int rank = 1;

        // 自分が勝者
        if (winner_id == user_id) return rank;

        // 他人が勝者だったら
        if (winner_id != -1) rank++;

        for (int i = 0; i < PlayerMng.sPlayerNumber; i++) {
            if (i == winner_id) continue;
            if (i == user_id) continue;
            if (PlayerMng.players.get(i).score > PlayerMng.players.get(user_id).score) {
                rank++;
            }
        }
        return rank;
    }

    public void setBone() {

        sImgBone1 = (ImageView) findViewById(R.id.imgBone1);
        sImgBone2 = (ImageView) findViewById(R.id.imgBone2);
        sImgBone3 = (ImageView) findViewById(R.id.imgBone3);
        sImgBone4 = (ImageView) findViewById(R.id.imgBone4);

        if (PlayerMng.players.get(0).status == PlayerMng.STATUS_GAMEOVER)
            sImgBone1.setVisibility(View.VISIBLE);
        if (PlayerMng.players.get(1).status == PlayerMng.STATUS_GAMEOVER)
            sImgBone2.setVisibility(View.VISIBLE);
        if (PlayerMng.sPlayerNumber >= 3 && PlayerMng.players.get(2).status == PlayerMng.STATUS_GAMEOVER)
            sImgBone3.setVisibility(View.VISIBLE);
        if (PlayerMng.sPlayerNumber >= 4 && PlayerMng.players.get(3).status == PlayerMng.STATUS_GAMEOVER)
            sImgBone4.setVisibility(View.VISIBLE);
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

    public void setScore() {

        sTextPlayerScore1.setText(String.valueOf(PlayerMng.players.get(0).score));
        sTextPlayerScore2.setText(String.valueOf(PlayerMng.players.get(1).score));
        if (PlayerMng.sPlayerNumber >= 3)
            sTextPlayerScore3.setText(String.valueOf(PlayerMng.players.get(2).score));
        if (PlayerMng.sPlayerNumber >= 4)
            sTextPlayerScore4.setText(String.valueOf(PlayerMng.players.get(3).score));

        if (PlayerMng.sPlayerNumber >= 3) sLayoutPlayer3.setVisibility(View.VISIBLE);
        if (PlayerMng.sPlayerNumber >= 4) sLayoutPlayer4.setVisibility(View.VISIBLE);


    }


    public void setPlayNum() {
        int play_num;

        play_num = SettingsUtils.getPlayNum(mContext) + 1;
        SettingsUtils.setPlayNum(mContext, play_num);
    }

    /*
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
    */
    public void setImgRank(TextView textPlayerRank, ImageView imgRank, int rank) {

//        textPlayerRank.setText(rank + " 位");

        if (rank == 1) {
            imgRank.setImageResource(R.drawable.rank1);
        } else if (rank == 2) {
            imgRank.setImageResource(R.drawable.rank2);
        } else if (rank == 3) {
            imgRank.setImageResource(R.drawable.rank3);
        } else imgRank.setImageDrawable(null);
    }

    // 〇バックキーのハック
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != KeyEvent.KEYCODE_BACK) {
            return super.onKeyDown(keyCode, event);
        } else { // キーバック処理
            if (!AdmobHelper.loadInterstitialNextGame()) {
                Intent intent = new Intent(mContext, TopActivity.class);
                startActivity(intent);
            }
            return false;
        }
    }
}
