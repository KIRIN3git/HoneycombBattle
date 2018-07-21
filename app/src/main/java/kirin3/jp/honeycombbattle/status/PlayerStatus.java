package kirin3.jp.honeycombbattle.status;

import android.util.Log;

/**
 * Created by shinji on 2017/06/07.
 */

public class PlayerStatus {

	// プレイヤーナンバー
	public int no;
	// プレイヤーの開始位置
	public int start_position_x,start_position_y;
	// プレイヤーの現在位置(x,y)
	public int now_position_x,now_position_y;
	// プレイヤーの現在位置(row,col)
	public int now_position_row,now_position_col;
	// プレイヤーの初回エリア表示判定
	public boolean erea_flg = false;

	// プレイヤーカラー
	public int color_r,color_g,color_b;
	// プレイヤーの状態
	// 0:自分の領域,1:外部に侵入中,2:死亡
	public int status = 0;


	// タッチの開始位置
	public int start_touch_x,start_touch_y;
	// タッチの現在位置
	public int now_touch_x,now_touch_y;
	// タッチ中かのフラグ
	public boolean touch_flg;
	// タッチ用ポイントID
	public int point_id;

	// 指示器のXY位置
	public int indicatorXY[] = {0,0};
	// 開始位置と指示器の差分
	public int indicatorDiff[] = {0,0};

	public int score;

	public PlayerStatus(int _no, int xy[], int color[] ){
		no = _no;
		start_position_x = xy[0];
		start_position_y = xy[1];
		now_position_x = xy[0];
		now_position_y = xy[1];
		point_id = -1;
		score = 0;

		color_r = color[0];
		color_g = color[1];
		color_b = color[2];

	}
}
