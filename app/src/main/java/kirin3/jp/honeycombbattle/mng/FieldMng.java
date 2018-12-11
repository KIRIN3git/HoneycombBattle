package kirin3.jp.honeycombbattle.mng;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import static kirin3.jp.honeycombbattle.util.ViewUtils.dpToPx;

/**
 * Created by shinji on 2017/06/09.
 */

public class FieldMng {

	// 長さ倍率
	static float sSizeMagnification;

	// 六角形の半径の長さ
	static float HEX_LENGTH_DP = 12.0f;
	static float HEX_LENGTH_PX;

	// 六角形の線の太さ
	static float HEX_WIDHT_DP = 1.0f;
	static float HEX_WIDHT_PX;

	// 六角形の一辺の長さの比率
	static float HEX_RATIO = 0.86f;

	// 六角形の縦、横の数
//	final static int HEX_NUM_ROW = 15;
//	final static int HEX_NUM_COL = 17;
	final static int HEX_NUM_ROW = 19;
	final static int HEX_NUM_COL = 20;

	/*
	// タワーの線の太さ
	static float TOWER_WIDHT_DP = 2.0f;
	static float TOWER_WIDHT_PX;

	// タワーの半径
	static float TOWER_RADIUS_DP = 5.0f;
	static float TOWER_RADIUS_PX;

	// タワーの色
	static int TOWER_COLOR_RGB[] = {129,129,129};

	// タワーの所有者(カラー番号)
	static int towerCheck[] = {0,0,0,0,0};
	*/

	// １桁目
	final static int CLCOUNTEAN_NO = 0; // 白
	final static int COUNT_NO = 7;
	final static int WALL_NO = 8;
	final static int DALETE_NO = 9;
	// ２桁目
	final static int PLAYER_START_ONE = 1;
	final static int PLAYER_START_TWO = 2;
	final static int PLAYER_START_THREE = 3;
	final static int PLAYER_START_FOUR = 4;

	static boolean countHitFlg = false;

	// 六角形の塗りつぶし確認
	// ２桁の場合は1桁目が奪おうとするNO,１桁目が奪われようとしているNO
	static int hex_color_num[][];
	//static int hex_tower_num[][];
	static int hex_effect_num[][];

	static int HEX_COLOR_RGB[][] = {
			{255,255,255},
			{255,193,255}, //ピンク
			{127,255,255}, //水色
			{153,252,153}, //薄緑色
			{255,255,120}, //薄黄色
			{255,255,255},
			{129,129,129},
			{255,193,255},
			{129,129,129},
			{129,129,129}
	};

	static int HEX_COLOR_RGB2[][] = {
			{255,255,255},
			{255,100,255}, //ピンク
			{0,255,255}, //水色
			{50,252,50}, //薄緑色
			{255,255,0}, //薄黄色
			{255,255,255},
			{129,129,129},
			{255,193,255},
			{129,129,129},
			{129,129,129}
	};




	public static void fieldInit(Context context,float sizeMagnification){

		sSizeMagnification = sizeMagnification;

		// dp→px変換
		HEX_LENGTH_PX = dpToPx(HEX_LENGTH_DP,context.getResources()) * sSizeMagnification;
		HEX_WIDHT_PX = dpToPx(HEX_WIDHT_DP,context.getResources()) * sSizeMagnification;

//		TOWER_RADIUS_PX = dpToPx(TOWER_RADIUS_DP,context.getResources()) * sSizeMagnification;
//		TOWER_WIDHT_PX = dpToPx(TOWER_WIDHT_DP,context.getResources()) * sSizeMagnification;

//		//六角形1 19 20
		hex_color_num = new int[][]{
				{9,9,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,9,9},
				{8,8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8},
				{9,8,8,0,8,0,8,0,8,0,8,0,8,0,8,0,8,8,9},
				{9,9,9,8,9,8,9,8,9,8,9,8,9,8,9,8,9,9,9}
		};

		/*
		// タワー追加時はtowerCheckも変更
		hex_tower_num = new int[][]{
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
		};
		*/

		hex_effect_num = new int[][]{
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
		};
	}

	// 背景ALPHA
	final static int BACK_ALPHA = 255 ;

	// 背景RGB
	final static int BACKGROUND_USER_RGB[][] = {
			{200,200,210},
			{210,200,200}, //ピンク
			{200,210,210}, //水色
			{210,210,200}
	};


	public static void drawBackground(Canvas canvas,int screen_width,int screen_height,Paint bgPaint,int sPlayerNumberNo) {



		// ２人プレイ
		if( sPlayerNumberNo == 0 ){
			bgPaint.setColor(Color.argb(BACK_ALPHA, BACKGROUND_USER_RGB[0][0],  BACKGROUND_USER_RGB[0][1],  BACKGROUND_USER_RGB[0][2]));
			canvas.drawRect( 0, 0, screen_width, screen_height/2, bgPaint);
			bgPaint.setColor(Color.argb(BACK_ALPHA, BACKGROUND_USER_RGB[1][0],  BACKGROUND_USER_RGB[1][1],  BACKGROUND_USER_RGB[1][2]));
			canvas.drawRect( 0, screen_height/2, screen_width, screen_height, bgPaint);
		}
		// ３人プレイ
		else if( sPlayerNumberNo == 1 ){
			bgPaint.setColor(Color.argb(BACK_ALPHA, BACKGROUND_USER_RGB[0][0],  BACKGROUND_USER_RGB[0][1],  BACKGROUND_USER_RGB[0][2]));
			canvas.drawRect( 0, 0, screen_width, screen_height/2, bgPaint);
			bgPaint.setColor(Color.argb(BACK_ALPHA, BACKGROUND_USER_RGB[1][0],  BACKGROUND_USER_RGB[1][1],  BACKGROUND_USER_RGB[1][2]));
			canvas.drawRect( 0, screen_height/2, screen_width/2, screen_height, bgPaint);
			bgPaint.setColor(Color.argb(BACK_ALPHA, BACKGROUND_USER_RGB[2][0],  BACKGROUND_USER_RGB[2][1],  BACKGROUND_USER_RGB[2][2]));
			canvas.drawRect( screen_width/2, screen_height/2, screen_width, screen_height, bgPaint);
		}
		// ４人プレイ
		else if( sPlayerNumberNo == 2 ){

			bgPaint.setColor(Color.argb(BACK_ALPHA, BACKGROUND_USER_RGB[0][0],  BACKGROUND_USER_RGB[0][1],  BACKGROUND_USER_RGB[0][2]));
			canvas.drawRect( 0, 0, screen_width/2, screen_height/2, bgPaint);
			bgPaint.setColor(Color.argb(BACK_ALPHA, BACKGROUND_USER_RGB[1][0],  BACKGROUND_USER_RGB[1][1],  BACKGROUND_USER_RGB[1][2]));
			canvas.drawRect( 0, screen_height/2, screen_width/2, screen_height, bgPaint);
			bgPaint.setColor(Color.argb(BACK_ALPHA, BACKGROUND_USER_RGB[2][0],  BACKGROUND_USER_RGB[2][1],  BACKGROUND_USER_RGB[2][2]));
			canvas.drawRect( screen_width/2, screen_height/2, screen_width, screen_height, bgPaint);
			bgPaint.setColor(Color.argb(BACK_ALPHA, BACKGROUND_USER_RGB[3][0],  BACKGROUND_USER_RGB[3][1],  BACKGROUND_USER_RGB[3][2]));
			canvas.drawRect( screen_width/2, 0, screen_width, screen_height/2, bgPaint);
		}

	}

	public static void drawHex(Paint paint, Canvas canvas){
		float add_x,add_y;

		// Canvas 中心点
		float center_x = canvas.getWidth() / 2;
		float center_y = canvas.getHeight() / 2;

		int tower_num = 0;

		// パスを設定
		Path path = new Path();

		paint.reset();

		for( int col = 0; col < HEX_NUM_COL; col++ ){
			for( int row = 0; row < HEX_NUM_ROW; row++ ){
				// 移動分
				// row - ( HEX_NUM / 2 ),col - ( HEX_NUM / 2 ) は左右対称にするため
				add_x = HEX_LENGTH_PX * (3.0f/2.0f) * (float)(row - ( HEX_NUM_ROW / 2 ));
				if( (row - ( HEX_NUM_ROW / 2 )) % 2  == 0 ) add_y = (HEX_LENGTH_PX * HEX_RATIO) * 2 * (col - ( HEX_NUM_COL / 2 ));
				else  add_y = HEX_LENGTH_PX * HEX_RATIO + ( (HEX_LENGTH_PX * HEX_RATIO) * 2 * (col - ( HEX_NUM_COL / 2 )));

				// すでにペイント済み、枠内に中心点が入ったら
				// 一旦、円で計算
				for(int i = 0; i < PlayerMng.sPlayerNumber; i++ ){
					if( ((add_x - PlayerMng.players.get(i).nowPositionX) * (add_x - PlayerMng.players.get(i).nowPositionX) + (add_y - PlayerMng.players.get(i).nowPositionY) * (add_y - PlayerMng.players.get(i).nowPositionY)) < Math.pow(HEX_LENGTH_PX, 2) ){

						//現在位置(col,row)を記録
						PlayerMng.players.get(i).nowPositionCol = col;
						PlayerMng.players.get(i).nowPositionRow = row;

						// 初期エリア色塗り
						if( PlayerMng.players.get(i).erea_flg == false ){
							ItemMng.setAtackEffect(i,col,row,0,0,false);
						}

						// 壁にぶつかったら
						if( hex_color_num[col][row]  == WALL_NO ){
							PlayerMng.deadPlayer(i);
						}

						// ステータスノーマルで、自分の領域でなかったら
						else if( PlayerMng.players.get(i).status == PlayerMng.STATUS_NORMAL && hex_color_num[col][row] != PlayerMng.players.get(i).no ){
/*
						    // スコアの増減
                            if( ((hex_color_num[col][row] ) - 1) >= 1 && ((hex_color_num[col][row] ) - 1) <= 4 ){
                            	PlayerMng.players.get((hex_color_num[col][row] ) - 1).score--;
	                        }
                            PlayerMng.players.get(i).score++;
*/
							// 色を記録
							// ２桁目は永続で保存
							hex_color_num[col][row] = PlayerMng.players.get(i).no;
						}
					}
				}

				// 表示なし
				if( hex_color_num[col][row] == DALETE_NO ) continue;

				// ○六角形の描画
				// 色

				// エフェクトを表示
				if( hex_effect_num[col][row] >= 1 ) {
					paint.setStrokeWidth(HEX_WIDHT_PX / 5);
					paint.setColor(Color.argb(255, HEX_COLOR_RGB2[hex_color_num[col][row]][0], HEX_COLOR_RGB2[hex_color_num[col][row]][1], HEX_COLOR_RGB2[hex_color_num[col][row]][2]));
					hex_effect_num[col][row]++;
					if( hex_effect_num[col][row] == 30 ) hex_effect_num[col][row] = 0;
				}
				else{
					paint.setStrokeWidth(HEX_WIDHT_PX);
					paint.setColor(Color.argb(255, HEX_COLOR_RGB[hex_color_num[col][row]][0], HEX_COLOR_RGB[hex_color_num[col][row]][1], HEX_COLOR_RGB[hex_color_num[col][row]][2]));

				}

				paint.setStyle(Paint.Style.FILL_AND_STROKE);

				path.reset();
				// 右
				path.moveTo(center_x + HEX_LENGTH_PX - HEX_WIDHT_PX + add_x, center_y + add_y);
				// 右下
				path.lineTo(center_x + (HEX_LENGTH_PX / 2) - (HEX_WIDHT_PX / 2) + add_x, center_y + (HEX_LENGTH_PX * HEX_RATIO) - (HEX_WIDHT_PX * HEX_RATIO) + add_y);
				// 左下
				path.lineTo(center_x - (HEX_LENGTH_PX / 2) + (HEX_WIDHT_PX / 2) + add_x, center_y + (HEX_LENGTH_PX * HEX_RATIO) - (HEX_WIDHT_PX * HEX_RATIO) + add_y);
				// 左
				path.lineTo(center_x - HEX_LENGTH_PX + HEX_WIDHT_PX + add_x, center_y + add_y);
				// 左上
				path.lineTo(center_x - (HEX_LENGTH_PX / 2) + (HEX_WIDHT_PX / 2) + add_x, center_y - (HEX_LENGTH_PX * HEX_RATIO) + (HEX_WIDHT_PX * HEX_RATIO) + add_y);
				// 右上
				path.lineTo(center_x + (HEX_LENGTH_PX / 2) - (HEX_WIDHT_PX / 2) + add_x, center_y - (HEX_LENGTH_PX * HEX_RATIO) + (HEX_WIDHT_PX * HEX_RATIO) + add_y);
				path.close();
				canvas.drawPath(path, paint);

/*
				// タワーを表示
				if( hex_tower_num[col][row] == 1 ){

					paint.setStyle(Paint.Style.STROKE);
					paint.setColor(Color.argb(255, TOWER_COLOR_RGB[0], TOWER_COLOR_RGB[1], TOWER_COLOR_RGB[2]));
					paint.setStrokeWidth(TOWER_WIDHT_PX);
					canvas.drawCircle(center_x + add_x, center_y + add_y, TOWER_RADIUS_PX, paint);

					// タワーの所有者を保存
					towerCheck[tower_num] = hex_color_num[col][row] ;
					tower_num++;

					if(towerCheck.length == tower_num){
						for(int i = 0; i < tower_num; i++){
							if( towerCheck[i] == 0 ) break;
							if(i != 0){
								if(towerCheck[i] != towerCheck[i-1]) break;
							}
							// 全てのタワーを一人が所有したら
							if( i == tower_num - 1 ){
								TimeMng.setSituation(TimeMng.SITUATION_GAMEOVER); // ゲーム終了
								GameSurfaceView.winnerId = towerCheck[i] - 1;
							}
						}
					}
				}
*/
			}
		}
	}

	/*
	 * １つの目引数の２桁目をキープして、２つ目の引数の値を１桁目に入れ替える
	 */
	public static int changeIntADigit( int a,int b){
		return ( a / 10 ) * 10 + b;

	}

	public static void countHex(Paint paint, Canvas canvas, int col_check, int row_check){
		float add_x,add_y;

		// Canvas 中心点
		float center_x = canvas.getWidth() / 2;
		float center_y = canvas.getHeight() / 2;

		// パスを設定
		Path path = new Path();

		paint.reset();
		paint.setStrokeWidth(HEX_WIDHT_PX);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		for( int col = 0; col < HEX_NUM_COL; col++ ){
			for( int row = 0; row < HEX_NUM_ROW; row++ ){

				if( col_check == col && row_check == row ){
					for(int user_i = 0; user_i < PlayerMng.sPlayerNumber; user_i++ ){
						if( hex_color_num[col][row] == PlayerMng.players.get(user_i).no ){
							hex_color_num[col][row] = COUNT_NO;
							countHitFlg = true;
//							PlayerMng.players.get(user_i).score++;
//☆							TimeMng.getsFpsMsec(ScoreMng.SCORE_FPS);
						}
					}
				}

				// 移動分
				// row - ( HEX_NUM / 2 ),col - ( HEX_NUM / 2 ) は左右対称にするため
				add_x = HEX_LENGTH_PX * (3.0f/2.0f) * (float)(row - ( HEX_NUM_ROW / 2 ));
				if( (row - ( HEX_NUM_ROW / 2 )) % 2  == 0 ) add_y = (HEX_LENGTH_PX * HEX_RATIO) * 2 * (col - ( HEX_NUM_COL / 2 ));
				else  add_y = HEX_LENGTH_PX * HEX_RATIO + ( (HEX_LENGTH_PX * HEX_RATIO) * 2 * (col - ( HEX_NUM_COL / 2 )));
//				Log.w( "LOG1", "col[" + col + "] add_x[" + add_x + "]");
//				Log.w( "LOG1", "row[" + row + "] add_y[" + add_y + "]");

				// 表示なし
				if( hex_color_num[col][row] == DALETE_NO ) continue;

				// 六角形の描画
				paint.setColor(Color.argb(255, HEX_COLOR_RGB[hex_color_num[col][row]][0], HEX_COLOR_RGB[hex_color_num[col][row]][1], HEX_COLOR_RGB[hex_color_num[col][row]][2]));
				path.reset();

				// 右
				path.moveTo(center_x + HEX_LENGTH_PX - HEX_WIDHT_PX + add_x, center_y + add_y);
				// 右下
				path.lineTo(center_x + (HEX_LENGTH_PX / 2) - (HEX_WIDHT_PX / 2) + add_x, center_y + (HEX_LENGTH_PX * HEX_RATIO) - (HEX_WIDHT_PX * HEX_RATIO) + add_y);
				// 左下
				path.lineTo(center_x - (HEX_LENGTH_PX / 2) + (HEX_WIDHT_PX / 2) + add_x, center_y + (HEX_LENGTH_PX * HEX_RATIO) - (HEX_WIDHT_PX * HEX_RATIO) + add_y);
				// 左
				path.lineTo(center_x - HEX_LENGTH_PX + HEX_WIDHT_PX + add_x, center_y + add_y);
				// 左上
				path.lineTo(center_x - (HEX_LENGTH_PX / 2) + (HEX_WIDHT_PX / 2) + add_x, center_y - (HEX_LENGTH_PX * HEX_RATIO) + (HEX_WIDHT_PX * HEX_RATIO) + add_y);
				// 右上
				path.lineTo(center_x + (HEX_LENGTH_PX / 2) - (HEX_WIDHT_PX / 2) + add_x, center_y - (HEX_LENGTH_PX * HEX_RATIO) + (HEX_WIDHT_PX * HEX_RATIO) + add_y);
				path.close();
				canvas.drawPath(path, paint);
			}
		}
	}


	public static void countScore(){

		for (int i = 0; i < PlayerMng.sPlayerNumber; i++) {
			PlayerMng.players.get(i).score = 0;

			for (int col = 0; col < HEX_NUM_COL; col++) {
				for (int row = 0; row < HEX_NUM_ROW; row++) {
					if( hex_color_num[col][row] == PlayerMng.players.get(i).no ) PlayerMng.players.get(i).score++;
				}
			}
		}
	}

}
