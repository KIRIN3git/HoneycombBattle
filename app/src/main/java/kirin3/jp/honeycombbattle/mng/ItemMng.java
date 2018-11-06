package kirin3.jp.honeycombbattle.mng;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import kirin3.jp.honeycombbattle.status.ItemStatus;
import kirin3.jp.honeycombbattle.util.TimeUtils;

import static kirin3.jp.honeycombbattle.mng.FieldMng.DALETE_NO;
import static kirin3.jp.honeycombbattle.mng.FieldMng.WALL_NO;
import static kirin3.jp.honeycombbattle.mng.FieldMng.hex_color_num;
import static kirin3.jp.honeycombbattle.mng.FieldMng.hex_effect_num;
import static kirin3.jp.honeycombbattle.mng.PlayerMng.PLAYER_RADIUS_BOOST_PX;
import static kirin3.jp.honeycombbattle.mng.PlayerMng.PLAYER_RADIUS_DEFO_PX;
import static kirin3.jp.honeycombbattle.mng.PlayerMng.PLAYER_RADIUS_PX;
import static kirin3.jp.honeycombbattle.util.ViewUtils.dpToPx;

public class ItemMng {

    // 長さ倍率
    static float sSizeMagnification;

    // アイテムの半径
    static float ITEM_RADIUS_DP = 14.0f;
    static float ITEM_RADIUS_PX;

    static float ITEM_TEXT_DP = 20.0f;
    static float ITEM_TEXT_PX;




    // アイテム種類
    static int type;

    // アイテムベースTEXT
    static String ITEM_BASE_TEXT[] ={"B","L","W","S","U"};

    // ブーストアイテムの確率
    static Integer ITEM_BOOST_PROPORTION = 3;

    // アイテムベースカラー
    static int ITEM_BASE_COLOR[][] = {
            {51,0,255},
            {0,0,0}};

    // アイテムテキストカラー
    static int ITEM_TEXT_COLOR[][] = {
            {255,0,0},
            {0,255,0},
            {140,140,200},
            {255,255,100},
            {255,0,255}};

    // ステータス
    final static public int STATUS_NORMAL = 0;
    final static public int STATUS_USED = 1;

    // アイテム出現量
    static int sQuantityCandidate[] = {200,150,100,80,20};
    static int sItemQuantity;

    // アイテム数
    public static int sItemNum = 0;

    // アイテムデータ
    public static ArrayList<ItemStatus> items;

    // スピードアップ時間（ミリ秒）
    final static int SPEEDUP_TIME = 5 * 1000;

    // 無敵時間（ミリ秒）
    final static int UNRIVALE_TIME = 5 * 1000;

    public static void itemInit(int quantityNo,float sizeMagnification){

        sItemNum = 0;
        items = new ArrayList<ItemStatus>();

        sSizeMagnification = sizeMagnification;

        sItemQuantity = sQuantityCandidate[quantityNo];
    }

    public static void createItem(Context context,Canvas canvas){

        boolean item_boost_flg;
        int max_x,max_y;
        int throw_random,place_random,start_x_random,start_y_random,item_type_random,x_dire_random,y_dire_random;
        ItemStatus item;

        Random r = new Random();
        throw_random = r.nextInt(sItemQuantity);

        if( throw_random == 0 ){
            sItemNum++;
            max_x = canvas.getWidth();
            max_y = canvas.getHeight();

            place_random = r.nextInt(4); // 0:左、1:右、2:上、3:下

            item_type_random = r.nextInt(ITEM_BASE_TEXT.length);
//            item_type_random = 0; //☆
            item_type_random = 4;

            if( r.nextInt(ITEM_BOOST_PROPORTION) == 0 ) item_boost_flg = true;
            else item_boost_flg = false;

            if( place_random == 0 ) {
                start_x_random = 0;
                start_y_random = r.nextInt(max_y) + 1;
                x_dire_random = r.nextInt(5) + 1;
                y_dire_random = r.nextInt(10) - 5;
            }
            else if( place_random == 1 ) {
                start_x_random = max_x;
                start_y_random = r.nextInt(max_y) + 1;
                x_dire_random = - ( r.nextInt(5) + 1 );
                y_dire_random = r.nextInt(10) - 5;
            }
            else if( place_random == 2 ) {
                start_x_random = r.nextInt(max_x) + 1;
                start_y_random = 0;
                x_dire_random = r.nextInt(10) - 5;
                y_dire_random = r.nextInt(5) + 1;
            }
            else{
                start_x_random = r.nextInt(max_x) + 1;
                start_y_random = max_y;
                x_dire_random = r.nextInt(10) - 5;
                y_dire_random = - ( r.nextInt(5) + 1 );
            }

            item = new ItemStatus( sItemNum,start_x_random,start_y_random,item_type_random,ITEM_BASE_TEXT[item_type_random],ITEM_BASE_COLOR[item_boost_flg?1:0],ITEM_TEXT_COLOR[item_type_random],x_dire_random,y_dire_random,item_boost_flg );
            items.add(item);
        }
    }

    public static void drawItem(Context context, Paint paint, Canvas canvas) {

        int max_x,max_y;
        float widht,height;

        max_x = canvas.getWidth();
        max_y = canvas.getHeight();

        ITEM_RADIUS_PX = dpToPx(ITEM_RADIUS_DP,context.getResources()) * sSizeMagnification;
        ITEM_TEXT_PX = dpToPx(ITEM_TEXT_DP,context.getResources()) * sSizeMagnification;
        paint.reset();

        for(int i = 0; i < sItemNum; i++ ) {
            // 利用済みは非表示
            if( ItemMng.items.get(i).status != STATUS_NORMAL ){
                continue;
            }

            // 描画範囲から出たものは非表示
            if( ItemMng.items.get(i).nowPositionX < 0 || ItemMng.items.get(i).nowPositionX > max_x  || ItemMng.items.get(i).nowPositionY < 0 || ItemMng.items.get(i).nowPositionY > max_y ){
                ItemMng.items.get(i).status = STATUS_USED;
                continue;
            }


            paint.setStrokeWidth(1);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setColor(Color.argb(255, ItemMng.items.get(i).baseColorR, ItemMng.items.get(i).baseColorG, ItemMng.items.get(i).baseColorB));
            canvas.drawCircle(ItemMng.items.get(i).nowPositionX, ItemMng.items.get(i).nowPositionY, ITEM_RADIUS_PX, paint);

            paint.setTextSize( ITEM_TEXT_PX );
            paint.setColor(Color.argb(255, ItemMng.items.get(i).textColorR, ItemMng.items.get(i).textColorG, ItemMng.items.get(i).textColorB));

            widht = paint.measureText(ItemMng.items.get(i).text);
            height = (paint.getFontMetrics().leading - paint.getFontMetrics().ascent) / 1.5f;
            canvas.drawText(ItemMng.items.get(i).text, ItemMng.items.get(i).nowPositionX - (widht / 2), ItemMng.items.get(i).nowPositionY + (height / 2), paint);

            // プレイヤーのアイテム取得チェック
            for (int j = 0; j < PlayerMng.sPlayerNumber; j++) {
                if( PlayerMng.players.get(j).status == PlayerMng.STATUS_DEAD ) continue;
                if( PlayerMng.players.get(j).status == PlayerMng.STATUS_GAMEOVER ) continue;

                // アイテムに重なっているか判定（三平方の定理）（プレイヤー座標は中心座標を基準にしているので注意）
                if( Math.pow((ItemMng.items.get(i).nowPositionX - ( (max_x / 2) + PlayerMng.players.get(j).nowPositionX )),2) + Math.pow((ItemMng.items.get(i).nowPositionY - ( (max_y / 2) + PlayerMng.players.get(j).nowPositionY  )),2)
                        <= Math.pow(ITEM_RADIUS_PX + PLAYER_RADIUS_PX[j], 2)  ){

                    ItemMng.items.get(i).status = STATUS_USED;

//                    if(ItemMng.items.get(i).type == 0) setAtackColoer(j,PlayerMng.players.get(j).nowPositionCol,PlayerMng.players.get(j).nowPositionRow,0,4,true);
                    // ボム攻撃（ブースト時範囲4）
                    if(ItemMng.items.get(i).type == 0) setAtackColoer(j,PlayerMng.players.get(j).nowPositionCol,PlayerMng.players.get(j).nowPositionRow,0,ItemMng.items.get(i).boost_flg?4:2,true);
                    // 縦ライン攻撃
                    else if(ItemMng.items.get(i).type == 1) setAtackColoer(j,PlayerMng.players.get(j).nowPositionCol,PlayerMng.players.get(j).nowPositionRow,1,ItemMng.items.get(i).boost_flg?3:1,true);
                    // 横ライン攻撃
                    else if(ItemMng.items.get(i).type == 2) setAtackColoer(j,PlayerMng.players.get(j).nowPositionCol,PlayerMng.players.get(j).nowPositionRow,2,ItemMng.items.get(i).boost_flg?3:1,true);
                    else if(ItemMng.items.get(i).type == 3) setSpeedUp(j,ItemMng.items.get(i).boost_flg);
                    else setUnrivale(j,ItemMng.items.get(i).boost_flg);

                }
            }

            ItemMng.items.get(i).nowPositionX = ItemMng.items.get(i).nowPositionX + ItemMng.items.get(i).x_direction;
            ItemMng.items.get(i).nowPositionY = ItemMng.items.get(i).nowPositionY + ItemMng.items.get(i).y_direction;

        }
    }

    /*
     * 攻撃の表示
     * mode:0 円、mode:1 縦線、mode:2 横線
     * effect:効果の表示あり、なし
     */
    public static void setAtackColoer(int user_id, int col, int row, int mode, int distance,boolean effect ){
        List<List<Integer>> cr;

        if(mode == 0) cr = getAround(col,row,distance);
        else if(mode == 1) cr = getColLine(row,distance);
        else cr = getRowLine(col,distance);

        if( hex_color_num[col][row] != PlayerMng.players.get(user_id).no ) {
            if( hex_color_num[col][row] != 0 ) PlayerMng.players.get((hex_color_num[col][row] ) - 1).score--;
            hex_color_num[col][row] = PlayerMng.players.get(user_id).no;
            PlayerMng.players.get(user_id).score++;
        }

        for(int i = 0; i < cr.size(); i++){
            // 壁等は色を塗れない
            if( hex_color_num[cr.get(i).get(0)][cr.get(i).get(1)] == WALL_NO || hex_color_num[cr.get(i).get(0)][cr.get(i).get(1)] == DALETE_NO) continue;
            // 自分の色じゃなければ色塗り
            if( hex_color_num[cr.get(i).get(0)][cr.get(i).get(1)] != PlayerMng.players.get(user_id).no ) {
                if( hex_color_num[cr.get(i).get(0)][cr.get(i).get(1)] != 0 ) PlayerMng.players.get((hex_color_num[cr.get(i).get(0)][cr.get(i).get(1)] ) - 1).score--;
                hex_color_num[cr.get(i).get(0)][cr.get(i).get(1)] = PlayerMng.players.get(user_id).no;
                PlayerMng.players.get(user_id).score++;
            }

            if( effect ) hex_effect_num[cr.get(i).get(0)][cr.get(i).get(1)] = 1;

            // 爆破範囲内のキャラは死亡
            for(int j = 0; j < PlayerMng.sPlayerNumber; j++){
                // 自分は平気
                if( j == user_id ) continue;
                if( PlayerMng.players.get(j).nowPositionCol == cr.get(i).get(0) && PlayerMng.players.get(j).nowPositionRow == cr.get(i).get(1) ){
                    PlayerMng.deadPlayer(j);
                }
            }
        }

        PlayerMng.players.get(user_id).erea_flg = true;
    }

    public static void addList(List<List<Integer>> list,int col, int row, int col_add,int row_add){

        int c = col + col_add;
        int r = row + row_add;


        if( c < 0 || r < 0 || c > hex_color_num[0].length - 1 || r > hex_color_num.length - 2) return;

        list.add(Arrays.asList(c, r));
    }

    public static List<List<Integer>> getAround(int col, int row, int distance) {

        List<List<Integer>> list = new ArrayList<>();

        list.add(Arrays.asList(col, row));

        addList(list,col,row,-1,0);
        addList(list,col,row,1,0);
        addList(list,col,row,0,-1);
        addList(list,col,row,0,1);
        if (row % 2 == 0) {
            addList(list, col, row, 1, -1);
            addList(list, col, row, 1, 1);
        } else {
            addList(list, col, row, -1, -1);
            addList(list, col, row, -1, 1);
        }

        if( distance >= 2 ){
            addList(list,col,row,-2,0);
            addList(list,col,row,2,0);
            addList(list,col,row,0,-2);
            addList(list,col,row,0,2);
            if (row % 2 == 0) {
                addList(list, col, row, 2, -1);
                addList(list, col, row, 2, 1);
                addList(list, col, row, 1, -2);
                addList(list, col, row, 1, 2);
                addList(list, col, row, -1, -1);
                addList(list, col, row, -1, 1);
                addList(list, col, row, -1, -2);
                addList(list, col, row, -1, 2);
            } else {
                addList(list, col, row, -2, -1);
                addList(list, col, row, -2, 1);
                addList(list, col, row, -1, -2);
                addList(list, col, row, -1, 2);
                addList(list, col, row, 1, -1);
                addList(list, col, row, 1, 1);
                addList(list, col, row, 1, -2);
                addList(list, col, row, 1, 2);
            }
        }

        if( distance >= 3 ) {
            addList(list,col,row,-3,0);
            addList(list,col,row,3,0);
            addList(list,col,row,0,-3);
            addList(list,col,row,0,3);
            if (row % 2 == 0) {
                addList(list, col, row, 3, -1);
                addList(list, col, row, 3, 1);
                addList(list, col, row, 2, -2);
                addList(list, col, row, 2, 2);
                addList(list, col, row, 2, -3);
                addList(list, col, row, 2, 3);
                addList(list, col, row, 1, -3);
                addList(list, col, row, 1, 3);
                addList(list, col, row, -1, -3);
                addList(list, col, row, -1, 3);
                addList(list, col, row, -2, -2);
                addList(list, col, row, -2, 2);
                addList(list, col, row, -2, -1);
                addList(list, col, row, -2, 1);
            } else{
                addList(list, col, row, -3, -1);
                addList(list, col, row, -3, 1);
                addList(list, col, row, -2, -2);
                addList(list, col, row, -2, 2);
                addList(list, col, row, -2, -3);
                addList(list, col, row, -2, 3);
                addList(list, col, row, -1, -3);
                addList(list, col, row, -1, 3);
                addList(list, col, row, 1, -3);
                addList(list, col, row, 1, 3);
                addList(list, col, row, 2, -2);
                addList(list, col, row, 2, 2);
                addList(list, col, row, 2, -1);
                addList(list, col, row, 2, 1);
            }
        }

        if( distance >= 4 ) {
            addList(list,col,row,-4,0);
            addList(list,col,row,4,0);
            addList(list,col,row,0,-4);
            addList(list,col,row,0,4);
            if (row % 2 == 0) {
                addList(list, col, row, 4, -1);
                addList(list, col, row, 4, 1);
                addList(list, col, row, 3, -2);
                addList(list, col, row, 3, 2);
                addList(list, col, row, 3, -3);
                addList(list, col, row, 3, 3);
                addList(list, col, row, 2, -4);
                addList(list, col, row, 2, 4);
                addList(list, col, row, 1, -4);
                addList(list, col, row, 1, 4);
                addList(list, col, row, -1, -4);
                addList(list, col, row, -1, 4);
                addList(list, col, row, -2, -4);
                addList(list, col, row, -2, 4);
                addList(list, col, row, -2, -3);
                addList(list, col, row, -2, 3);
                addList(list, col, row, -3, -2);
                addList(list, col, row, -3, 2);
                addList(list, col, row, -3, -1);
                addList(list, col, row, -3, 1);
            } else {
                addList(list, col, row, -4, -1);
                addList(list, col, row, -4, 1);
                addList(list, col, row, -3, -2);
                addList(list, col, row, -3, 2);
                addList(list, col, row, -3, -3);
                addList(list, col, row, -3, 3);
                addList(list, col, row, -2, -4);
                addList(list, col, row, -2, 4);
                addList(list, col, row, -1, -4);
                addList(list, col, row, -1, 4);
                addList(list, col, row, 1, -4);
                addList(list, col, row, 1, 4);
                addList(list, col, row, 2, -4);
                addList(list, col, row, 2, 4);
                addList(list, col, row, 2, -3);
                addList(list, col, row, 2, 3);
                addList(list, col, row, 3, -2);
                addList(list, col, row, 3, 2);
                addList(list, col, row, 3, -1);
                addList(list, col, row, 3, 1);
            }
        }
        return list;
    }

    public static List<List<Integer>> getColLine(int row, int distance) {

        List<List<Integer>> list = new ArrayList<>();

        for(int i = 0; i < hex_color_num.length; i++ ){
            list.add(Arrays.asList(i, row));
        }

        if( distance == 3 ){
            for(int i = 0; i < hex_color_num.length; i++ ){
                list.add(Arrays.asList(i, row - 1));
            }
            for(int i = 0; i < hex_color_num.length; i++ ){
                list.add(Arrays.asList(i, row + 1));
            }
        }

        return list;
    }

    public static List<List<Integer>> getRowLine(int col, int distance) {

        List<List<Integer>> list = new ArrayList<>();

        for(int i = 0; i < hex_color_num[0].length; i++ ){
            list.add(Arrays.asList(col, i));
        }
        if( distance == 3 ){
            for(int i = 0; i < hex_color_num[0].length; i++ ){
                list.add(Arrays.asList(col - 1, i));
            }
            for(int i = 0; i < hex_color_num[0].length; i++ ){
                list.add(Arrays.asList(col + 1, i));
            }
        }
        return list;
    }

    public static void setSpeedUp(int user_id,boolean boost_flg){
        PlayerMng.players.get(user_id).speedUpTime = TimeUtils.getCurrentTime();
        PlayerMng.players.get(user_id).speedUpFlg = true;
        if( boost_flg ) PlayerMng.players.get(user_id).speedUpBoostFlg = true;
    }

    public static void setUnrivale(int user_id,boolean boost_flg){
        PlayerMng.players.get(user_id).unrivaledTime = TimeUtils.getCurrentTime();
        PlayerMng.players.get(user_id).unrivaledFlg = true;
        if( boost_flg ){
            PLAYER_RADIUS_PX[user_id] = PLAYER_RADIUS_BOOST_PX;
            PlayerMng.players.get(user_id).unrivaledBoostFlg = true;
        }
    }

    public static void checkItemEffect(){
        long currentTime = TimeUtils.getCurrentTime();

        for(int i = 0; i < PlayerMng.sPlayerNumber; i++){
            if( PlayerMng.players.get(i).speedUpFlg ){
                if( currentTime > PlayerMng.players.get(i).speedUpTime + SPEEDUP_TIME ){
                    PlayerMng.players.get(i).speedUpTime = 0;
                    PlayerMng.players.get(i).speedUpFlg = false;
                    PlayerMng.players.get(i).speedUpBoostFlg = false;
                }
            }

            if( PlayerMng.players.get(i).unrivaledFlg ){
                if( currentTime > PlayerMng.players.get(i).unrivaledTime + UNRIVALE_TIME ) {
                    PlayerMng.players.get(i).unrivaledTime = 0;
                    PlayerMng.players.get(i).unrivaledFlg = false;
                    if (PlayerMng.players.get(i).unrivaledBoostFlg) {
                        PlayerMng.players.get(i).unrivaledBoostFlg = false;
                        PLAYER_RADIUS_PX[i] = PLAYER_RADIUS_DEFO_PX;
                    }
                }
            }

        }
    }
}
