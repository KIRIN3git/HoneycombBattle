package kirin3.jp.honeycombbattle.mng;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.Locale;

import kirin3.jp.honeycombbattle.Config;
import kirin3.jp.honeycombbattle.util.LogUtils;

import static kirin3.jp.honeycombbattle.util.TimeUtils.getCurrentTime;
import static kirin3.jp.honeycombbattle.util.ViewUtils.dpToPx;
import static kirin3.jp.honeycombbattle.util.ViewUtils.mirrorDrowText;

/**
 * Created by shinji on 2017/06/08.
 *
 * 時間の管理、表示、状況の判断を行う
 */

public class TimeMng {

	// バトル時間ミリ秒
	public static int sBattleTimeCandidate[] = {2,60,120};
	static long sBattleTime;

	// カウントダウンテキストサイズ
	static float COUNTDONW_TEXT_SIZE_DP = 60.0f;
	static float COUNTDONW_TEXT_SIZE_PX;

	// リミットテキストサイズ
	static float LIMIT_TEXT_SIZE_DP = 40.0f;
	static float LIMIT_TEXT_SIZE_PX;

	// カウントダウンミリ秒
	static long COUNT_DONW_MS = 1 * 1000;
	// ゲームオーバー時間ミリ秒
	static long GAMEOVER_MS = 2 * 1000;
	// カウントダウン開始時間保存
	static long sStartCountDownMS;
	// 戦闘開始時間保存
	static long sStartBattleMS = 0;
	// 現在時間
	static long CurrentTimeMillis;
	// 前回時間
	static long BeforeTimeMillis = sStartCountDownMS;

	// 状況 0:何もなし,1:カウントダウン,2:バトル中,3,GAMEOVER
	public static final int SITUATION_NOTHING = 0;
	public static final int SITUATION_COUNTDOWN = 1;
	public static final int SITUATION_BATTLE = 2;
	public static final int SITUATION_GAMEOVER = 3;
	public static int sSituation = SITUATION_NOTHING;

	public static int coutnta = 0;
	public static int alla = 0;
	public static int sound_start1_count;
	public static boolean sound_start2_flg;


	// FPS
	static long sFps = Config.FPS;
	static long sRunStartTime = 0, sRunEndTime = 0;
	static long sNowFps = 0;

	static String sCname;

	public static void timeInit(Context context,int timeNo){
		sound_start1_count = -1;
		sound_start2_flg = false;

		sCname = LogUtils.makeLogTag(TimeMng.class);

		sBattleTime = sBattleTimeCandidate[timeNo] * 1000;

		COUNTDONW_TEXT_SIZE_PX = dpToPx(COUNTDONW_TEXT_SIZE_DP,context.getResources());
		LIMIT_TEXT_SIZE_PX = dpToPx(LIMIT_TEXT_SIZE_DP,context.getResources());
	}

	public static int getSituation(){
		return sSituation;
	}

	public static void setSituation(int situ){
		sSituation = situ;
	}

	public static void countDownStart(Context context){
		sSituation = SITUATION_COUNTDOWN;
		sStartCountDownMS = getCurrentTime();
	}

	public static void battleStart(Context context){
		sSituation = SITUATION_BATTLE;
		sStartBattleMS = getCurrentTime();
	}


	public static void drawCountDown(Context context, Paint paint, Canvas canvas){

		int count_num;
		String printText = "";
		float printX,printY;

		//カウントダウン開始からの経過ミリ秒
		long StartMillis = getCurrentTime() - sStartCountDownMS;

		paint.reset();
		paint.setTextSize(COUNTDONW_TEXT_SIZE_PX);
		paint.setColor(Color.RED);

		if( COUNT_DONW_MS - StartMillis > 0 ){
			count_num = (int)( (COUNT_DONW_MS - StartMillis) / 1000 ) + 1;
			// 値変更時に音を鳴らす
			if( count_num != sound_start1_count ){
				SoundMng.playSoundStart1();
			}
			sound_start1_count = count_num;
			printText = String.valueOf( count_num );
		}
		else if( COUNT_DONW_MS - StartMillis > -500 ){
			if( sound_start2_flg == false ){
				SoundMng.playSoundStart2();
				sound_start2_flg = true;
			}
			printText = "START";
		}
		else{
			// バトル開始
			battleStart(context);
		}

		if( sSituation == SITUATION_COUNTDOWN ){
			printX = canvas.getWidth() / 2;
			printY = canvas.getHeight() * 2 / 3;
			// 反転表示
			mirrorDrowText(canvas,paint,printX,printY,printText);
		}
	}

	public static long getBattleLimitTimeS(){
		return ( ( sBattleTime - (getCurrentTime() - sStartBattleMS) ) / 1000 ) + 1;
	}
	public static long getBattleLimitTImeMS(){
		return ( sBattleTime - (System.currentTimeMillis() - sStartBattleMS) ) - ( getBattleLimitTimeS() * 1000 ) + 1000;
	}


	public static void drawLimitTime(Context context,Paint paint, Canvas canvas){
		boolean timeOverFlg = false;
		String printText;
		float printX,printY;

		if( getBattleLimitTimeS() < 0 ) timeOverFlg = true;

		paint.reset();
		paint.setTextSize(LIMIT_TEXT_SIZE_PX);
		paint.setColor(Color.RED);
		if( !timeOverFlg ){
			// 反転表示
			printText = String.format(Locale.JAPAN, "%02d", getBattleLimitTimeS());
			printX = ( canvas.getWidth() / 2 );
			printY = canvas.getHeight()  + ((paint.descent() + paint.ascent()) / 2);
			mirrorDrowText(canvas,paint,printX,printY,printText);
		}
		else{
//			canvas.drawText("STOP", 0, canvas.getHeight(), paint);
			// 試合終了
			sSituation = SITUATION_GAMEOVER;
		}
	}

	public static void drawFps(Context context,Paint paint, Canvas canvas){
		String printText;
		float printX,printY;

		paint.reset();
		paint.setTextSize(LIMIT_TEXT_SIZE_PX);
		paint.setColor(Color.GREEN);
		// 反転表示
		printText = String.format(Locale.JAPAN, "%d", sNowFps);
		printX = paint.measureText(printText) / 2;
		printY = canvas.getHeight()  + ((paint.descent() + paint.ascent()) / 2);
		mirrorDrowText(canvas,paint,printX,printY,printText);
	}

	public static long getsFpsMsec(){
		return 1000 / sFps;
	}

	public static long getsMsecFps(long msec){
		return 1000 / msec;
	}

	public static void fpsStart(){
		// FPSのためにwhileの起動時間保存
		sRunStartTime = System.currentTimeMillis();
	}

	// 処理が速い場合は若干のスリープ
	public static void fpsEnd(){

		sRunEndTime = System.currentTimeMillis();

//		Log.w( "DEBUG_DATA", "time[" + (sRunEndTime - sRunStartTime)  + "]" );
		// 15で高速　40で低速
		sNowFps =  getsMsecFps(sRunEndTime - sRunStartTime);
		LogUtils.LOGW(sCname,"FPS[" +sNowFps + "]");
		LogUtils.LOGW(sCname,"time[" + (sRunEndTime - sRunStartTime)  + "]");
		LogUtils.LOGW(sCname,"getsFpsMsec()[" + getsFpsMsec()  + "]");

		// FPS制限
		// 基本引っ掛からない
		// 60fpsは1000 / 60 = 16.6666
		// 1フレームに16.7ミリ秒かけられる
		if(sRunEndTime - sRunStartTime < getsFpsMsec()){
			try {
				Thread.sleep(getsFpsMsec() - (sRunEndTime - sRunStartTime));
			} catch (InterruptedException e) {
			}
		}
	}

	// ゲームオーバー時のスリープ
	public static void sleepGameOver(){
		try {
			Thread.sleep(GAMEOVER_MS);
		} catch (InterruptedException e) {
		}
	}
}
