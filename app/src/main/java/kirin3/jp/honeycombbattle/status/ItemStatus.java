package kirin3.jp.honeycombbattle.status;

import kirin3.jp.honeycombbattle.mng.ItemMng;

/**
 * Created by shinji on 2017/06/07.
 */

public class ItemStatus {

    // プレイヤーナンバー
    public int no;

    // プレイヤーの開始位置 （注意）左上座標基準、右下がプラス方向
    public int startPositionX, startPositionY;

    // プレイヤーの現在位置(x,y) （注意）左上座標基準、右下がプラス方向
    public int nowPositionX, nowPositionY;

    // タイプ
    public int type;

    // テキスト
    public String text;

    // ベースカラー
    public int baseColorR, baseColorG, baseColorB;

    // テキストカラー
    public int textColorR, textColorG, textColorB;

    // 移動方向
    public int x_direction, y_direction;

    public boolean boost_flg;

    // プレイヤーの状態
    // 0:通常,1:利用済み
    public int status;

    public int score;

    public ItemStatus(int _no, int x, int y, int _type, String _text, int base_color[], int text_color[], int x_dire, int y_dire, boolean _boost_flg) {
        no = _no;
        startPositionX = x;
        startPositionY = y;
        nowPositionX = x;
        nowPositionY = y;

        type = _type;

        baseColorR = base_color[0];
        baseColorG = base_color[1];
        baseColorB = base_color[2];

        textColorR = text_color[0];
        textColorG = text_color[1];
        textColorB = text_color[2];

        x_direction = x_dire;
        y_direction = y_dire;

        text = _text;

        status = ItemMng.STATUS_NORMAL;

        boost_flg = _boost_flg;
    }
}
