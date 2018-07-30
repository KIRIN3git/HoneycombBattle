package kirin3.jp.honeycombbattle.status;

/**
 * Created by shinji on 2017/06/07.
 */

public class ItemStatus {

	// プレイヤーナンバー
	public int no;

	// プレイヤーの開始位置 （注意）左上座標基準
	public int startPositionX, startPositionY;

	// プレイヤーの現在位置(x,y) （注意）左上座標基準
	public int nowPositionX, nowPositionY;

	// テキスト
	public String text;

	// ベースカラー
	public int baseColorR, baseColorG, baseColorB;

	// テキストカラー
	public int textColorR, textColorG, textColorB;

	// 移動方向
	public int x_direction,y_direction;

	// プレイヤーの状態
	// 0:通常,1:死,2:ゲームオーバー
	public int status = 0;

	public int score;

	public ItemStatus(int _no, int x, int y, String _text, int base_color[], int text_color[], int x_dire, int y_dire ){
		no = _no;
		startPositionX = x;
		startPositionY = y;
		nowPositionX = x;
		nowPositionY = y;

		baseColorR = base_color[0];
		baseColorG = base_color[1];
		baseColorB = base_color[2];

		textColorR = text_color[0];
		textColorG = text_color[1];
		textColorB = text_color[2];

		x_direction = x_dire;
		y_direction = y_dire;

		text = _text;
	}
}
