package kirin3.jp.honeycombbattle.mng;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;


import kirin3.jp.honeycombbattle.game.GameSurfaceView;
import kirin3.jp.honeycombbattle.status.PlayerStatus;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.round;
import static java.lang.Math.sqrt;
import static kirin3.jp.honeycombbattle.util.TimeUtils.getCurrentTime;
import static kirin3.jp.honeycombbattle.util.ViewUtils.dpToPx;

/**
 * Created by shinji on 2017/06/07.
 * プレイヤー管理クラス
 */

public class PlayerMng {

	// 長さ倍率
	static float sSizeMagnification;

	// プレイヤー人数
    public static int sPlayerNumberCandidate[] = {2,3,4};
    public static int sPlayerNumber;

    // スピード
    static int sPlayerSpeedCandidate[] = {28,22,16,10,4};
    static int sPlayerSpeed;


    // プレイヤースタート位置
	static float sPlayerDpXY[][] = {{-92,116},{-92,-116},{92,116},{92,-116}};
//	static float sPlayerDpXY[][] = {{-72,106},{-82,-116},{65,150},{92,-116}};

	// プレイヤー残機位置
	static float sLifeDpXY[][] = {{165,-220},{165,240},{-165,-220},{-165,240}};

	//    static int sPlayerDpXY[][];
	// プレイヤーカラー
	public static int PLAYER_COLOR[][] = {
            {255,127,127},
			{127,127,255},
			{50,205,50},
			{255,227,80}};

	static int UNRIVAL_COLOR[] = {230,180,34};

	// プレイヤーの半径
	static float PLAYER_RADIUS_DP = 10.0f;
	static float PLAYER_RADIUS_PX;

	// 移動マーカーの半径
	static float DIRECTION_RADIUS_DP = 20.0f;
	static float DIRECTION_RADIUS_PX;


	// 移動マーカーカラー
	static int DIRECTION_ARAUND_COLOR[] = {255, 204, 102};
	static int DIRECTION_CENTER_COLOR[] = {0, 51, 204};

	// 移動マーカーの線の太さ
	static float DIRECTION_WIDHT_DP = 5.0f;
	static float DIRECTION_WIDHT_PX;

	// ライフ
	final static int LIFE_NUMBER = 3;

	// ステータス
	final static public int STATUS_NORMAL = 0;
	final static public int STATUS_DEAD = 1;
	final static public int STATUS_GAMEOVER = 2;

	// プレイヤー復活時間（ミリ秒）
	final static int REVIVAL_TIME = 2 * 1000;

	// スピードアップ時間（ミリ秒）
	final static int SPEEDUP_TIME = 5 * 1000;

	// 無敵時間（ミリ秒）
	final static int UNRIVALE_TIME = 5 * 1000;

	// プライヤーデータ
	public static ArrayList<PlayerStatus> players = new ArrayList<PlayerStatus>();

	public static void playerInit(Context context,int numberNo,int speedNo,float sizeMagnification){

		sSizeMagnification = sizeMagnification;

		PLAYER_RADIUS_PX = dpToPx(PLAYER_RADIUS_DP,context.getResources()) * sSizeMagnification;
		DIRECTION_RADIUS_PX = dpToPx(DIRECTION_RADIUS_DP,context.getResources()) * sSizeMagnification;
		DIRECTION_WIDHT_PX = dpToPx(DIRECTION_WIDHT_DP,context.getResources()) * sSizeMagnification;

		sPlayerSpeed = sPlayerSpeedCandidate[speedNo];
		sPlayerNumber = sPlayerNumberCandidate[numberNo];;

		PlayerStatus player;
		players.clear();
		for(int i = 0; i < sPlayerNumber; i++ ){
			player = new PlayerStatus( i+1, (int)(dpToPx(sPlayerDpXY[i][0],context.getResources()) * sSizeMagnification),(int)(dpToPx(sPlayerDpXY[i][1],context.getResources()) * sSizeMagnification), PLAYER_COLOR[i],LIFE_NUMBER );
			players.add(player);
		}
	}

	public static void drawPlayer(Context context, Paint paint, Canvas canvas){
		// Canvas 中心点
		float center_x = canvas.getWidth() / 2;
		float center_y = canvas.getHeight() / 2;


		paint.reset();
//		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);

		for(int i = 0; i < sPlayerNumber; i++ ) {
			if( PlayerMng.players.get(i).status == STATUS_GAMEOVER ) {
				//NOOP
			}
			else if( PlayerMng.players.get(i).status == STATUS_DEAD ) {
/*
				paint.setColor(Color.argb(255, PLAYER_DEAD_COLOR[0], PLAYER_DEAD_COLOR[1], PLAYER_DEAD_COLOR[2]));
				// (x1,y1,r,paint) 中心x1座標, 中心y1座標, r半径
				canvas.drawCircle(center_x - PlayerMng.players.get(i).nowPositionX, center_y - PlayerMng.players.get(i).nowPositionY, PLAYER_RADIUS_PX, paint);
*/
			}
			else{
				paint.setColor(Color.argb(255, PlayerMng.players.get(i).colorR, PlayerMng.players.get(i).colorG, PlayerMng.players.get(i).colorB));
				// (x1,y1,r,paint) 中心x1座標, 中心y1座標, r半径
				canvas.drawCircle(center_x + PlayerMng.players.get(i).nowPositionX, center_y + PlayerMng.players.get(i).nowPositionY, PLAYER_RADIUS_PX, paint);

				// 無敵の時の記載
				if(PlayerMng.players.get(i).unrivaledFlg){
					paint.setColor(Color.argb(255, UNRIVAL_COLOR[0], UNRIVAL_COLOR[1], UNRIVAL_COLOR[2]));
					canvas.drawCircle(center_x + PlayerMng.players.get(i).nowPositionX, center_y + PlayerMng.players.get(i).nowPositionY, PLAYER_RADIUS_PX / 2, paint);
				}


				// プレイヤー同士の円の重なりをチェック
				for (int j = 0; j < sPlayerNumber; j++) {
					if( PlayerMng.players.get(i).unrivaledFlg == false ) continue;
					if( PlayerMng.players.get(j).unrivaledFlg == true ) continue;
					if (i == j) continue;
					if( PlayerMng.players.get(i).status == STATUS_DEAD || PlayerMng.players.get(j).status == STATUS_DEAD ) continue;
					if( PlayerMng.players.get(i).status == STATUS_GAMEOVER || PlayerMng.players.get(j).status == STATUS_GAMEOVER ) continue;

					if( ((PlayerMng.players.get(i).nowPositionX - PlayerMng.players.get(j).nowPositionX) * (PlayerMng.players.get(i).nowPositionX - PlayerMng.players.get(j).nowPositionX)
							+ (PlayerMng.players.get(i).nowPositionY - PlayerMng.players.get(j).nowPositionY) * (PlayerMng.players.get(i).nowPositionY - PlayerMng.players.get(j).nowPositionY)) < Math.pow(PLAYER_RADIUS_PX * 2, 2) ){
							deadPlayer(j);
					}
				}
			}
		}
	}

	public static void drawIndicator(Paint paint, Canvas canvas){

		int start_touch_x = 0,start_touch_y = 0;
		int indicatorXY[] = {0,0};

		for(int i = 0; i < sPlayerNumber; i++ ){
			if(!players.get(i).touchFlg) continue;
			start_touch_x = players.get(i).startTouchX;
			start_touch_y = players.get(i).startTouchY;
			indicatorXY = players.get(i).indicatorXY;

			// セーブタップ位置に〇を表示
			paint.reset();
			paint.setColor(Color.argb(155, DIRECTION_ARAUND_COLOR[0], DIRECTION_ARAUND_COLOR[1], DIRECTION_ARAUND_COLOR[2])); // 水浅葱
			paint.setStrokeWidth(DIRECTION_WIDHT_PX);
			paint.setStyle(Paint.Style.STROKE);
			paint.setAntiAlias(true);
			canvas.drawCircle(start_touch_x, start_touch_y, DIRECTION_RADIUS_PX, paint);

			// セーブタップ位置を中心にタップ〇移動範囲を表示
			paint.reset();
			paint.setColor(Color.argb(155, DIRECTION_ARAUND_COLOR[0], DIRECTION_ARAUND_COLOR[1], DIRECTION_ARAUND_COLOR[2])); // 水浅葱
			paint.setStrokeWidth(DIRECTION_WIDHT_PX);
			paint.setStyle(Paint.Style.STROKE);
			paint.setAntiAlias(true);
			canvas.drawCircle(start_touch_x, start_touch_y, DIRECTION_RADIUS_PX * 3, paint);

			// 移動方向に〇を表示
			paint.reset();
			paint.setColor(Color.argb(155, DIRECTION_CENTER_COLOR[0], DIRECTION_CENTER_COLOR[1], DIRECTION_CENTER_COLOR[2])); // ピンク
			paint.setStrokeWidth(DIRECTION_WIDHT_PX);
			paint.setStyle(Paint.Style.STROKE);
			paint.setAntiAlias(true);
			// 計算が完了していたら表示可能
			if( indicatorXY[0] != 0 && indicatorXY[1] != 0){
				canvas.drawCircle(indicatorXY[0], indicatorXY[1], DIRECTION_RADIUS_PX, paint);
			}
		}


		//Log.w( "DEBUG_DATA", "CENTER players.get(i).startTouchX " + players.get(i).startTouchX );
		//Log.w( "DEBUG_DATA", "CENTER players.get(i).startTouchY " + players.get(i).startTouchY );
		//Log.w( "DEBUG_DATA", "CENTER direXY[0] " + players.get(i).indicatorXY[0] );
		//Log.w( "DEBUG_DATA", "CENTER direXY[1] " + players.get(i).indicatorXY[1] );

	}

	/*
	 * プレイヤーの位置を取得
	 * インディケーターの位置を取得
	 * インディケーターと初回タップ位置の差分を取得
	 */
	public static void getMoveXY(){

		int speed;

		for(int i = 0; i < sPlayerNumber; i++ ){
//			if( players.get(i).touchFlg) {
				// タップ移動比率xyと指示マーカーのxyを取得
//				getIndicatorXY(i,players.get(i).startTouchX, players.get(i).startTouchY, players.get(i).nowTouchX, players.get(i).nowTouchY, players.get(i).indicatorDiff, players.get(i).indicatorXY);
				getIndicatorXY(i, players.get(i).startTouchX, players.get(i).startTouchY, players.get(i).nowTouchX, players.get(i).nowTouchY);
				// ユーザーの位置を登録
				if (players.get(i).status == STATUS_NORMAL) {
					if(players.get(i).speedUpFlg) speed = sPlayerSpeed - (sPlayerSpeed / 2);
					else speed = sPlayerSpeed;

					players.get(i).nowPositionX = players.get(i).nowPositionX + (players.get(i).indicatorDiff[0] / speed);
					players.get(i).nowPositionY = players.get(i).nowPositionY + (players.get(i).indicatorDiff[1] / speed);
				}
//			}
		}
	}


	// 指示マーカーの位置を取得
	public static void getIndicatorXY(int user_id,int start_touch_x,int start_touch_y,int now_touch_x,int now_touch_y){
//		public static void getIndicatorXY(int user_id,int startTouchX,int startTouchY,int nowTouchX,int nowTouchY,int[] indicatorDiff,int[] indicatorXY){
		// 移動方向の正、負
		boolean positive_x = true,positive_y = true;
		// セーブ位置と現在位置の絶対値差分
		double sa_x,sa_y;
		// 絶対値差分と表示位置の比率
		double ratio;

		// 移動方向の正、負を取得
		if( start_touch_x > now_touch_x ){
			positive_x = false;
		}
		if( start_touch_y > now_touch_y ){
			positive_y = false;
		}

		// セーブ位置と現在位置の絶対値差分を取得
		sa_x = abs(start_touch_x - now_touch_x);
		sa_y = abs(start_touch_y - now_touch_y);

		//Log.w( "DEBUG_DATA", "PlayerMng.players.get(0).startTouchX " + PlayerMng.players.get(0).startTouchX  );
		//Log.w( "DEBUG_DATA", "PlayerMng.players.get(0).startTouchY " + PlayerMng.players.get(0).startTouchY  );
		//Log.w( "DEBUG_DATA", "PlayerMng.players.get(0).nowTouchX " + PlayerMng.players.get(0).nowTouchX  );
		//Log.w( "DEBUG_DATA", "PlayerMng.players.get(0).nowTouchY " + PlayerMng.players.get(0).nowTouchY  );

		//Log.w( "DEBUG_DATA", "sa_x " + sa_x  );
		//Log.w( "DEBUG_DATA", "sa_y " + sa_y  );

		// 三平方の定理で絶対値差分と表示位置の比率を取得
		ratio = sqrt( pow(DIRECTION_RADIUS_PX * 2,2) / ( pow(sa_x,2) + pow(sa_y,2) ) );

		//Log.w( "DEBUG_DATA", "pow(160,2) " + pow(DIRECTION_RADIUS_PX * 2,2)  );
		//Log.w( "DEBUG_DATA", "pow(sa_x,2) " + pow(sa_x,2) );
		//Log.w( "DEBUG_DATA", "pow(sa_y,2) " + pow(sa_y,2) );
		//Log.w( "DEBUG_DATA", "ratio " + ratio  );

		// 指示マーカーとセーブ位置の差分を取得（四捨五入のため誤差あり）
		if( sa_x == 0 && sa_y == 0 ){
			// 差分に変更なしで移動し続ける
		}
		else {
			if (positive_x) players.get(user_id).indicatorDiff[0] = (int) round(sa_x * ratio);
			else players.get(user_id).indicatorDiff[0] = -(int) round(sa_x * ratio);
			if (positive_y) players.get(user_id).indicatorDiff[1] = (int) round(sa_y * ratio);
			else players.get(user_id).indicatorDiff[1] = -(int) round(sa_y * ratio);
		}


		// 四捨五入して指示マーカーの位置を取得
		players.get(user_id).indicatorXY[0] = start_touch_x + players.get(user_id).indicatorDiff[0];
		players.get(user_id).indicatorXY[1] = start_touch_y + players.get(user_id).indicatorDiff[1];

		//Log.w( "DEBUG_DATA", "(int)round(sa_x * ratio) " + (int)round(sa_x * ratio)  );
		//Log.w( "DEBUG_DATA", "(int)round(sa_y * ratio) " + (int)round(sa_y * ratio)  );

		//Log.w( "DEBUG_DATA", "PlayerMng.players.get(0).indicatorXY[0] " + PlayerMng.players.get(0).indicatorXY[0]  );
		//Log.w( "DEBUG_DATA", "PlayerMng.players.get(0).indicatorXY[1] " + PlayerMng.players.get(0).indicatorXY[1]  );

		//Log.w( "DEBUG_DATA", "結果 " + ( pow(PlayerMng.players.get(0).indicatorXY[0],2) + pow(PlayerMng.players.get(0).indicatorXY[1],2) )  );

	}

	public static int checkWinner(){
		int win_user_id = -1;
		int win_score = -1;
		for(int i = 0; i < sPlayerNumber; i++ ){
			if( win_score == players.get(i).score ){
				win_user_id = 99; //ドロー
			}
			else if( players.get(i).score > win_score ){
				win_user_id = i;
				win_score = players.get(i).score;
			}
		}

		return win_user_id;
	}

	public static void drawLife(Context context, Paint paint, Canvas canvas) {
		int sa_x;
		// Canvas 中心点
		float center_x = canvas.getWidth() / 2;
		float center_y = canvas.getHeight() / 2;

		paint.reset();
//		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);

		for (int i = 0; i < sPlayerNumber ; i++) {
			paint.setColor(Color.argb(255, PlayerMng.players.get(i).colorR, PlayerMng.players.get(i).colorG, PlayerMng.players.get(i).colorB));
			sa_x = 0;
			for (int j = 0; j < PlayerMng.players.get(i).lifeNum - 1; j++) {
				// (x1,y1,r,paint) 中心x1座標, 中心y1座標, r半径
				canvas.drawCircle(center_x - dpToPx(PlayerMng.sLifeDpXY[i][0], context.getResources()) * sSizeMagnification  - sa_x, center_y - dpToPx(PlayerMng.sLifeDpXY[i][1], context.getResources()) * sSizeMagnification, PLAYER_RADIUS_PX, paint);
				if( i == 0 || i== 1 ) sa_x -= ( ( PLAYER_RADIUS_PX * 2 ) + dpToPx(3, context.getResources()) * sSizeMagnification );
				else sa_x += ( ( PLAYER_RADIUS_PX * 2 ) + dpToPx(3, context.getResources()) * sSizeMagnification );
			}
		}
	}


	public static void deadPlayer(int user_id){

		if( PlayerMng.players.get(user_id).status != PlayerMng.STATUS_NORMAL ) return;


		PlayerMng.players.get(user_id).status = PlayerMng.STATUS_DEAD;
		PlayerMng.players.get(user_id).deadTime = getCurrentTime();
	}

	public static void revivalPlayer(Paint paint, Canvas canvas){
		// Canvas 中心点
		float center_x = canvas.getWidth() / 2;
		float center_y = canvas.getHeight() / 2;

		paint.reset();
		paint.setStyle(Paint.Style.FILL_AND_STROKE);

		for (int i = 0; i < sPlayerNumber; i++) {

			if( PlayerMng.players.get(i).status == PlayerMng.STATUS_DEAD ){
				if( PlayerMng.players.get(i).deadTime + REVIVAL_TIME <= getCurrentTime() ){
					PlayerMng.players.get(i).status = PlayerMng.STATUS_NORMAL;
					PlayerMng.players.get(i).deadTime = 0;
					PlayerMng.players.get(i).nowPositionX = PlayerMng.players.get(i).startPositionX;
					PlayerMng.players.get(i).nowPositionY = PlayerMng.players.get(i).startPositionY;
					PlayerMng.players.get(i).lifeNum--;
					if( PlayerMng.players.get(i).lifeNum <= 0 ){
						PlayerMng.players.get(i).status = PlayerMng.STATUS_GAMEOVER;
						// 生存者が１人か確認して、１人ならゲーム終了
						checkOnlyOneUser();
					}
					else{
						PlayerMng.players.get(i).status = PlayerMng.STATUS_NORMAL;

						PlayerMng.players.get(i).indicatorDiff[0] = 0;
						PlayerMng.players.get(i).indicatorDiff[1] = 0;
						PlayerMng.players.get(i).indicatorXY[0] = 0;
						PlayerMng.players.get(i).indicatorXY[1] = 0;
//		PlayerMng.players.get(i).startTouchX = 0;
//		PlayerMng.players.get(i).startTouchY = 0;
						PlayerMng.players.get(i).nowTouchX = PlayerMng.players.get(i).startTouchX;
						PlayerMng.players.get(i).nowTouchY = PlayerMng.players.get(i).startTouchY;
					}
				}
				else {
					long sabun1 = ( getCurrentTime() - PlayerMng.players.get(i).deadTime ) / 7;
					long sabun2 = ( getCurrentTime() - PlayerMng.players.get(i).deadTime ) / 10;
					
					paint.setColor(Color.argb(255, PlayerMng.players.get(i).colorR, PlayerMng.players.get(i).colorG, PlayerMng.players.get(i).colorB));

					canvas.drawCircle(center_x + PlayerMng.players.get(i).nowPositionX + sabun1, center_y + PlayerMng.players.get(i).nowPositionY, PLAYER_RADIUS_PX / 2, paint);
					canvas.drawCircle(center_x + PlayerMng.players.get(i).nowPositionX - sabun1, center_y + PlayerMng.players.get(i).nowPositionY, PLAYER_RADIUS_PX / 2, paint);
					canvas.drawCircle(center_x + PlayerMng.players.get(i).nowPositionX, center_y + PlayerMng.players.get(i).nowPositionY + sabun1, PLAYER_RADIUS_PX / 2, paint);
					canvas.drawCircle(center_x + PlayerMng.players.get(i).nowPositionX, center_y + PlayerMng.players.get(i).nowPositionY - sabun1, PLAYER_RADIUS_PX / 2, paint);

					canvas.drawCircle(center_x + PlayerMng.players.get(i).nowPositionX + sabun2, center_y + PlayerMng.players.get(i).nowPositionY + sabun2, PLAYER_RADIUS_PX / 2, paint);
					canvas.drawCircle(center_x + PlayerMng.players.get(i).nowPositionX + sabun2, center_y + PlayerMng.players.get(i).nowPositionY - sabun2, PLAYER_RADIUS_PX / 2, paint);
					canvas.drawCircle(center_x + PlayerMng.players.get(i).nowPositionX - sabun2, center_y + PlayerMng.players.get(i).nowPositionY + sabun2, PLAYER_RADIUS_PX / 2, paint);
					canvas.drawCircle(center_x + PlayerMng.players.get(i).nowPositionX - sabun2, center_y + PlayerMng.players.get(i).nowPositionY - sabun2, PLAYER_RADIUS_PX / 2, paint);
				}
			}
		}
	}

	/*
	 * 生存者が１人か確認
	 */
	public static void checkOnlyOneUser(){
		// 生存者数
		int lifeUserNum = 0;
		// 生存者番号
		int lifeUserNo = 0;

		for (int i = 0; i < sPlayerNumber; i++) {
			if( PlayerMng.players.get(i).lifeNum != 0 ){
				lifeUserNum++;
				lifeUserNo = i;
			}
		}
		if( lifeUserNum == 1 ){
			for (int i = 0; i < sPlayerNumber; i++) {
				if( PlayerMng.players.get(i).lifeNum != 0 ){
					TimeMng.setSituation(TimeMng.SITUATION_GAMEOVER); // ゲーム終了
					GameSurfaceView.winnerNo = lifeUserNo;
				}
			}
		}
	}

	// 生存者数を取得
	public static int getLifeUserNum(){
		int num = 0;

		for (int i = 0; i < sPlayerNumber; i++) {
			if( PlayerMng.players.get(i).lifeNum != 0 ){
				num++;
			}
		}
		return num;
	}
}
