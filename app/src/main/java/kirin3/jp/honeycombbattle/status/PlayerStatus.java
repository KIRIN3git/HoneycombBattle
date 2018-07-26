package kirin3.jp.honeycombbattle.status;

/**
 * Created by shinji on 2017/06/07.
 */

public class PlayerStatus {

	// プレイヤーナンバー
	public int no;
	// プレイヤーの開始位置
	public int startPositionX, startPositionY;

	// プレイヤーの現在位置(x,y)
	public int nowPositionX, nowPositionY;

	// プレイヤーの現在位置(row,col)
	public int nowPositionNow, nowPositionCol;

	// プレイヤーの初回エリア表示判定
	public boolean erea_flg = false;

	// プレイヤーカラー
	public int colorR, colorG, colorB;

	// プレイヤーの状態
	// 0:通常,1:死,2:ゲームオーバー
	public int status = 0;

	// 死亡時間（ミリ秒）
	public long deadTime;

	// プレイヤーのライフ数
	public int lifeNum;

	// タッチの開始位置
	public int startTouchX, startTouchY;
	// タッチの現在位置
	public int nowTouchX, nowTouchY;
	// タッチ中かのフラグ
	public boolean touchFlg;
	// タッチ用ポイントID
	public int pointId;

	// 指示器のXY位置
	public int indicatorXY[] = {0,0};
	// 開始位置と指示器の差分
	public int indicatorDiff[] = {0,0};

	public int score;

	public PlayerStatus(int _no, int x,int y, int color[],int life_num ){
		no = _no;
		startPositionX = x;
		startPositionY = y;
		nowPositionX = x;
		nowPositionY = y;
		pointId = -1;
		score = 0;

		colorR = color[0];
		colorG = color[1];
		colorB = color[2];

		lifeNum = life_num;

	}
}
