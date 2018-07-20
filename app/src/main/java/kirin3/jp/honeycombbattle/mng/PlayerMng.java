package kirin3.jp.honeycombbattle.mng;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;


import kirin3.jp.honeycombbattle.status.PlayerStatus;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.round;
import static java.lang.Math.sqrt;

/**
 * Created by shinji on 2017/06/07.
 * プレイヤー管理クラス
 */

public class PlayerMng {

	// プレイヤー人数
	static int sPlayerNum = 2;

	// プレイヤースタート位置
	static int playerXY2[][] = {{0,-150},{0,150}};
	static int playerXY3[][] = {{20,-150},{20,150},{-20,-150}};
	static int playerXY4[][] = {{20,-150},{20,150},{-20,-150},{-20,150}};

	// プレイヤーカラー
	static int playerColor[][] = {{255,127,127},{127,127,255},{50,205,50},{200,205,50}};

	// プレイヤーの半径
	static float PLAYER_RADIUS_DP = 10.0f;
	// 移動マーカーの半径
	static float DIRECTION_RADIUS_DP = 20.0f;
	// 移動マーカーの線の太さ
	static float DIRECTION_WIDHT_DP = 5.0f;

	// プレイヤーのスピード
	final static int playerSpeed = 10;

	// プライヤーデータ
	public static ArrayList<PlayerStatus> players = new ArrayList<PlayerStatus>();

	public static void playerInit(int num){

		PlayerStatus player;

		sPlayerNum = num;
		players.clear();
		for(int i = 0; i < sPlayerNum; i++ ){
			player = new PlayerStatus( getPlayerXY(sPlayerNum,i),playerColor[i] );
			players.add(player);
		}
	}

	public static int[] getPlayerXY(int num,int no){
		int xy[];

		if( num == 2 ){
			if(no == 1) return playerXY2[0];
			else return playerXY2[1];
		}


	}

	public static void DrawPlayer(Paint paint, Canvas canvas){
		// Canvas 中心点
		float center_x = canvas.getWidth() / 2;
		float center_y = canvas.getHeight() / 2;

		paint.reset();
//		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);

//		paint.setColor(Color.argb(255, PlayerMng.players.get(1).r, PlayerMng.players.get(1).g, PlayerMng.players.get(1).b));
//		canvas.drawCircle(0,0, 800, paint);

		for(int i = 0; i < sPlayerNum; i++ ) {
			paint.setColor(Color.argb(255, PlayerMng.players.get(i).r, PlayerMng.players.get(i).g, PlayerMng.players.get(i).b));
			// (x1,y1,r,paint) 中心x1座標, 中心y1座標, r半径
			canvas.drawCircle(center_x - PlayerMng.players.get(i).now_position_x, center_y - PlayerMng.players.get(i).now_position_y, PLAYER_RADIUS_PX, paint);
//			canvas.drawCircle(0,0, PLAYER_RADIUS_PX, paint);

/*
			for (int j = 0; j < sPlayerNum; j++) {

				if (i == j) continue;
				if( ((PlayerMng.players.get(i).now_position_x - PlayerMng.players.get(j).now_position_x) * (PlayerMng.players.get(i).now_position_x - PlayerMng.players.get(j).now_position_x)
						+ (PlayerMng.players.get(i).now_position_y - PlayerMng.players.get(j).now_position_y) * (PlayerMng.players.get(i).now_position_y - PlayerMng.players.get(j).now_position_y)) < Math.pow(PLAYER_RADIUS_PX * 2, 2) ){

					Log.w( "DEBUG_DATA", "円重なり");
					// もし侵入中だったら
					if( PlayerMng.players.get(i).status == 1 && PlayerMng.players.get(j).status == 0){
						Log.w( "DEBUG_DATA", "死亡" + TimeMng.gameOverFlg );
							// 死亡
						TimeMng.gameOverFlg = true;
						TimeMng.battleFlg = false;
						PlayerMng.players.get(i).status = 2;
						return;
					}
				}

			}
*/
		}
	}

	public static void DrawIndicator(Paint paint, Canvas canvas){

		int start_touch_x = 0,start_touch_y = 0;
		int indicatorXY[] = {0,0};

		for(int i = 0; i < sPlayerNum; i++ ){
			if(!players.get(i).touch_flg) continue;
			start_touch_x = players.get(i).start_touch_x;
			start_touch_y = players.get(i).start_touch_y;
			indicatorXY = players.get(i).indicatorXY;

			// セーブタップ位置に〇を表示
			paint.reset();
			paint.setColor(Color.argb(120, 188, 200, 219)); // 水浅葱
			paint.setStrokeWidth(DIRECTION_WIDHT_PX);
			paint.setStyle(Paint.Style.STROKE);
			paint.setAntiAlias(true);
			canvas.drawCircle(start_touch_x, start_touch_y, DIRECTION_RADIUS_PX, paint);

			// セーブタップ位置を中心にタップ〇移動範囲を表示
			paint.reset();
			paint.setColor(Color.argb(120, 188, 200, 219)); // 水浅葱
			paint.setStrokeWidth(DIRECTION_WIDHT_PX);
			paint.setStyle(Paint.Style.STROKE);
			paint.setAntiAlias(true);
			canvas.drawCircle(start_touch_x, start_touch_y, DIRECTION_RADIUS_PX * 3, paint);

			// 移動方向に〇を表示
			paint.reset();
			paint.setColor(Color.argb(120, 235, 121, 136)); // ピンク
			paint.setStrokeWidth(DIRECTION_WIDHT_PX);
			paint.setStyle(Paint.Style.STROKE);
			paint.setAntiAlias(true);
			// 計算が完了していたら表示可能
			if( indicatorXY[0] != 0 && indicatorXY[1] != 0){
				canvas.drawCircle(indicatorXY[0], indicatorXY[1], DIRECTION_RADIUS_PX, paint);
			}
		}


		//Log.w( "DEBUG_DATA", "CENTER players.get(i).start_touch_x " + players.get(i).start_touch_x );
		//Log.w( "DEBUG_DATA", "CENTER players.get(i).start_touch_y " + players.get(i).start_touch_y );
		//Log.w( "DEBUG_DATA", "CENTER direXY[0] " + players.get(i).indicatorXY[0] );
		//Log.w( "DEBUG_DATA", "CENTER direXY[1] " + players.get(i).indicatorXY[1] );

	}

	public static void GetMoveXY(){

		for(int i = 0; i < sPlayerNum; i++ ){
			if( players.get(i).touch_flg ){
				// タップ移動比率xyと指示マーカーのxyを取得
				getIndicatorXY(players.get(i).start_touch_x, players.get(i).start_touch_y, players.get(i).now_touch_x, players.get(i).now_touch_y, players.get(i).indicatorDiff, players.get(i).indicatorXY);
				players.get(i).now_position_x = players.get(i).now_position_x - (players.get(i).indicatorDiff[0] / playerSpeed[i]);
				players.get(i).now_position_y = players.get(i).now_position_y - (players.get(i).indicatorDiff[1] / playerSpeed[i]);
			}
		}
	}


	// 指示マーカーの位置を取得
	public static void getIndicatorXY(int start_touch_x,int start_touch_y,int now_touch_x,int now_touch_y,int[] indicatorDiff,int[] indicatorXY){
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

		//Log.w( "DEBUG_DATA", "PlayerMng.players.get(0).start_touch_x " + PlayerMng.players.get(0).start_touch_x  );
		//Log.w( "DEBUG_DATA", "PlayerMng.players.get(0).start_touch_y " + PlayerMng.players.get(0).start_touch_y  );
		//Log.w( "DEBUG_DATA", "PlayerMng.players.get(0).now_touch_x " + PlayerMng.players.get(0).now_touch_x  );
		//Log.w( "DEBUG_DATA", "PlayerMng.players.get(0).now_touch_y " + PlayerMng.players.get(0).now_touch_y  );

		//Log.w( "DEBUG_DATA", "sa_x " + sa_x  );
		//Log.w( "DEBUG_DATA", "sa_y " + sa_y  );

		// 三平方の定理で絶対値差分と表示位置の比率を取得
		ratio = sqrt( pow(DIRECTION_RADIUS_PX * 2,2) / ( pow(sa_x,2) + pow(sa_y,2) ) );

		//Log.w( "DEBUG_DATA", "pow(160,2) " + pow(DIRECTION_RADIUS_PX * 2,2)  );
		//Log.w( "DEBUG_DATA", "pow(sa_x,2) " + pow(sa_x,2) );
		//Log.w( "DEBUG_DATA", "pow(sa_y,2) " + pow(sa_y,2) );
		//Log.w( "DEBUG_DATA", "ratio " + ratio  );

		// 指示マーカーとセーブ位置の差分を取得（四捨五入のため誤差あり）
		if( positive_x ) indicatorDiff[0] = (int)round(sa_x * ratio);
		else indicatorDiff[0] = - (int)round(sa_x * ratio);
		if( positive_y ) indicatorDiff[1] = (int)round(sa_y * ratio);
		else indicatorDiff[1] = - (int)round(sa_y * ratio);

		// 四捨五入して指示マーカーの位置を取得
		indicatorXY[0] = start_touch_x + indicatorDiff[0];
		indicatorXY[1] = start_touch_y + indicatorDiff[1];

		//Log.w( "DEBUG_DATA", "(int)round(sa_x * ratio) " + (int)round(sa_x * ratio)  );
		//Log.w( "DEBUG_DATA", "(int)round(sa_y * ratio) " + (int)round(sa_y * ratio)  );

		//Log.w( "DEBUG_DATA", "PlayerMng.players.get(0).indicatorXY[0] " + PlayerMng.players.get(0).indicatorXY[0]  );
		//Log.w( "DEBUG_DATA", "PlayerMng.players.get(0).indicatorXY[1] " + PlayerMng.players.get(0).indicatorXY[1]  );

		//Log.w( "DEBUG_DATA", "結果 " + ( pow(PlayerMng.players.get(0).indicatorXY[0],2) + pow(PlayerMng.players.get(0).indicatorXY[1],2) )  );

	}

	public static int checkWinner(){
		int win_user_id = -1;
		int win_score = -1;
		for(int i = 0; i < sPlayerNum; i++ ){
			if( win_score == players.get(i).score ){
				win_user_id = 99; //ドロー
			}
			else if( players.get(i).score > win_score ){
				win_user_id = playerColorNo[i];
				win_score = players.get(i).score;
			}
		}

		return win_user_id;
	}
}
