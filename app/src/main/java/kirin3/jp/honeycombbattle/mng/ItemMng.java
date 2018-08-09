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
import static kirin3.jp.honeycombbattle.mng.FieldMng.changeIntADigit;
import static kirin3.jp.honeycombbattle.mng.FieldMng.hex_color_num;
import static kirin3.jp.honeycombbattle.util.ViewUtils.dpToPx;

public class ItemMng {

    // アイテムの半径
    static float ITEM_RADIUS_DP = 14.0f;
    static float ITEM_RADIUS_PX;

    static float ITEM_TEXT_DP = 20.0f;
    static float ITEM_TEXT_PX;

    // アイテム数
    public static int sItemNum = 0;


    // アイテム種類
    static int type;

    // アイテムベースTEXT
    static String ITEM_BASE_TEXT[] ={"B","L","W","S","U"};

    // アイテムベースカラー
    static int ITEM_BASE_COLOR[][] = {
            {0,0,0},
            {0,0,0},
            {0,0,0},
            {0,0,0},
            {0,0,0}};

    // アイテムテキストカラー
    static int ITEM_TEXT_COLOR[][] = {
            {255,0,0},
            {0,255,0},
            {140,140,200},
            {255,0,255},
            {255,0,255}};

    // ステータス
    final static public int STATUS_NORMAL = 0;
    final static public int STATUS_USED = 1;

    // アイテム出現量
    static int sQuantityCandidate[] = {200,150,100,80,60};
    static int sItemQuantity;


    // アイテムデータ
    public static ArrayList<ItemStatus> items = new ArrayList<ItemStatus>();

    // スピードアップ時間（ミリ秒）
    final static int SPEEDUP_TIME = 5 * 1000;

    // 無敵時間（ミリ秒）
    final static int UNRIVALE_TIME = 5 * 1000;

    public static void itemInit(int quantityNo){
        sItemQuantity = sQuantityCandidate[quantityNo];
    }

    public static void createItem(Context context,Canvas canvas){

        int max_x,max_y;
        int throw_random,place_random,start_x_random,start_y_random,item_random,x_dire_random,y_dire_random;
        ItemStatus item;

        Random r = new Random();
        throw_random = r.nextInt(sItemQuantity);

        if( throw_random == 0 ){
            sItemNum++;
            max_x = canvas.getWidth();
            max_y = canvas.getHeight();

            place_random = r.nextInt(4); // 0:左、1:右、2:上、3:下

            item_random = r.nextInt(ITEM_BASE_TEXT.length);

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

            item = new ItemStatus( sItemNum,start_x_random,start_y_random,item_random,ITEM_BASE_TEXT[item_random],ITEM_BASE_COLOR[item_random],ITEM_TEXT_COLOR[item_random],x_dire_random,y_dire_random );
            items.add(item);
        }
    }

    public static void drawItem(Context context, Paint paint, Canvas canvas) {

        int max_x,max_y;
        float widht,height;

        max_x = canvas.getWidth();
        max_y = canvas.getHeight();

        ITEM_RADIUS_PX = dpToPx(ITEM_RADIUS_DP,context.getResources());
        ITEM_TEXT_PX = dpToPx(ITEM_TEXT_DP,context.getResources());
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
                        <= Math.pow(ITEM_RADIUS_PX + PlayerMng.PLAYER_RADIUS_PX, 2)  ){

                    ItemMng.items.get(i).status = STATUS_USED;

                    if(ItemMng.items.get(i).type == 0) setAtackColoer(j,PlayerMng.players.get(j).nowPositionCol,PlayerMng.players.get(j).nowPositionRow,0,2);
                    else if(ItemMng.items.get(i).type == 1) setAtackColoer(j,PlayerMng.players.get(j).nowPositionCol,PlayerMng.players.get(j).nowPositionRow,1,2);
                    else if(ItemMng.items.get(i).type == 2) setAtackColoer(j,PlayerMng.players.get(j).nowPositionCol,PlayerMng.players.get(j).nowPositionRow,2,2);
                    else if(ItemMng.items.get(i).type == 3) setSpeedUp(j);
                    else setUnrivale(j);

                }
            }

            ItemMng.items.get(i).nowPositionX = ItemMng.items.get(i).nowPositionX + ItemMng.items.get(i).x_direction;
            ItemMng.items.get(i).nowPositionY = ItemMng.items.get(i).nowPositionY + ItemMng.items.get(i).y_direction;

        }
    }

    public static void setAtackColoer(int user_id, int col, int row, int mode, int distance ){
        List<List<Integer>> cr;

        if(mode == 0) cr = GetAround(col,row,distance);
        else if(mode == 1) cr = GetColLine(row);
        else cr = GetRowLine(col);

        if( hex_color_num[col][row] % 10 != PlayerMng.players.get(user_id).no ) {
            if( hex_color_num[col][row] % 10 != 0 ) PlayerMng.players.get((hex_color_num[col][row] % 10) - 1).score--;
            hex_color_num[col][row] = changeIntADigit(hex_color_num[col][row], PlayerMng.players.get(user_id).no);
            PlayerMng.players.get(user_id).score++;
        }

        for(int i = 0; i < cr.size(); i++){
            // 壁等は色を塗れない
            if( hex_color_num[cr.get(i).get(0)][cr.get(i).get(1)] == WALL_NO || hex_color_num[cr.get(i).get(0)][cr.get(i).get(1)] == DALETE_NO) continue;
            if( hex_color_num[cr.get(i).get(0)][cr.get(i).get(1)] % 10 != PlayerMng.players.get(user_id).no ) {
                if( hex_color_num[cr.get(i).get(0)][cr.get(i).get(1)] % 10 != 0 ) PlayerMng.players.get((hex_color_num[cr.get(i).get(0)][cr.get(i).get(1)] % 10) - 1).score--;
                hex_color_num[cr.get(i).get(0)][cr.get(i).get(1)] = changeIntADigit(hex_color_num[cr.get(i).get(0)][cr.get(i).get(1)], PlayerMng.players.get(user_id).no);
                PlayerMng.players.get(user_id).score++;
            }
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

    public static List<List<Integer>> GetAround(int col, int row, int distance) {

        List<List<Integer>> list = new ArrayList<>();

        // 左端でなければ
        if (col >= 1) {
            list.add(Arrays.asList(col - 1, row));
        }
        // 右端でなければ
        if (col <= hex_color_num[0].length - 2) {
            list.add(Arrays.asList(col + 1, row));
        }
        // 上端でなければ
        if (row >= 1) {
            list.add(Arrays.asList(col, row - 1));
        }
        // 下端でなければ
        if (row <= hex_color_num.length - 2) {
            list.add(Arrays.asList(col, row + 1));
        }

        if (row % 2 == 0) {
            if (col <= hex_color_num[0].length - 2 && row >= 1) {
                list.add(Arrays.asList(col + 1, row - 1));
            }
            if (col <= hex_color_num[0].length - 2 && row <= hex_color_num.length - 2) {
                list.add(Arrays.asList(col + 1, row + 1));
            }
        } else{
            if (col >= 1 && row >= 1) {
                list.add(Arrays.asList(col - 1, row - 1));
            }
            if (col >= 1 && row <= hex_color_num.length - 2) {
                list.add(Arrays.asList(col - 1, row + 1));
            }
        }

        if( distance >= 2 ){
            // 左端でなければ
            if (col >= 2) {
                list.add(Arrays.asList(col - 2, row));
            }
            // 右端でなければ
            if (col <= hex_color_num[0].length - 3) {
                list.add(Arrays.asList(col + 2, row));
            }
            // 上端でなければ
            if (row >= 2) {
                list.add(Arrays.asList(col, row - 2));
            }
            // 下端でなければ
            if (row <= hex_color_num.length - 3) {
                list.add(Arrays.asList(col, row + 2));
            }

            if (row % 2 == 0) {
                if (col <= hex_color_num[0].length - 3 && row >= 1) {
                    list.add(Arrays.asList(col + 2, row - 1));
                }
                if (col <= hex_color_num[0].length - 3 && row <= hex_color_num.length - 2) {
                    list.add(Arrays.asList(col + 2, row + 1));
                }
                if (col <= hex_color_num[0].length - 2 && row >= 2) {
                    list.add(Arrays.asList(col + 1, row - 2));
                }
                if (col <= hex_color_num[0].length - 2 && row <= hex_color_num.length - 3) {
                    list.add(Arrays.asList(col + 1, row + 2));
                }
                if (col >= 1 && row >= 1) {
                    list.add(Arrays.asList(col - 1, row - 1));
                }
                if (col >= 1 && row <= hex_color_num.length - 2) {
                    list.add(Arrays.asList(col - 1, row + 1));
                }
                if (col >= 1 && row >= 2) {
                    list.add(Arrays.asList(col - 1, row - 2));
                }
                if (col >= 1 && row <= hex_color_num.length - 3) {
                    list.add(Arrays.asList(col - 1, row + 2));
                }
            } else{
                if (col >= 2 && row >= 1) {
                    list.add(Arrays.asList(col - 2, row - 1));
                }
                if (col >= 2 && row <= hex_color_num.length - 2) {
                    list.add(Arrays.asList(col - 2, row + 1));
                }
                if (col >= 1 && row >= 2) {
                    list.add(Arrays.asList(col - 1, row - 2));
                }
                if (col >= 1 && row <= hex_color_num.length - 3) {
                    list.add(Arrays.asList(col - 1, row + 2));
                }
                if (col <= hex_color_num[0].length - 2 && row >= 1) {
                    list.add(Arrays.asList(col + 1, row - 1));
                }
                if (col <= hex_color_num[0].length - 2 && row <= hex_color_num.length - 2) {
                    list.add(Arrays.asList(col + 1, row + 1));
                }
                if (col <= hex_color_num[0].length - 2 && row >= 2) {
                    list.add(Arrays.asList(col + 1, row - 2));
                }
                if (col <= hex_color_num[0].length - 2 && row <= hex_color_num.length - 3) {
                    list.add(Arrays.asList(col + 1, row + 2));
                }
            }
        }


        return list;
    }

    public static List<List<Integer>> GetColLine(int row) {

        List<List<Integer>> list = new ArrayList<>();

        for(int i = 0; i < hex_color_num.length; i++ ){
            list.add(Arrays.asList(i, row));
        }

        return list;
    }

    public static List<List<Integer>> GetRowLine(int col) {

        List<List<Integer>> list = new ArrayList<>();

        for(int i = 0; i < hex_color_num[0].length; i++ ){
            list.add(Arrays.asList(col, i));
        }

        return list;
    }

    public static void setSpeedUp(int user_id){
        PlayerMng.players.get(user_id).speedUpTime = TimeUtils.getCurrentTime();
        PlayerMng.players.get(user_id).speedUpFlg = true;
    }

    public static void setUnrivale(int user_id){
        PlayerMng.players.get(user_id).unrivaledTime = TimeUtils.getCurrentTime();
        PlayerMng.players.get(user_id).unrivaledFlg = true;
    }

    public static void checkItemEffect(){
        long currentTime = TimeUtils.getCurrentTime();

        for(int i = 0; i < PlayerMng.sPlayerNumber; i++){
            if( PlayerMng.players.get(i).speedUpFlg ){
                if( currentTime > PlayerMng.players.get(i).speedUpTime + SPEEDUP_TIME ){
                    PlayerMng.players.get(i).speedUpTime = 0;
                    PlayerMng.players.get(i).speedUpFlg = false;
                }
            }

            if( PlayerMng.players.get(i).unrivaledFlg ){
                if( currentTime > PlayerMng.players.get(i).unrivaledTime + UNRIVALE_TIME ){
                    PlayerMng.players.get(i).unrivaledTime = 0;
                    PlayerMng.players.get(i).unrivaledFlg = false;
                }
            }

        }
    }
}
