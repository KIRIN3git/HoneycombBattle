package kirin3.jp.honeycombbattle.mng;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import kirin3.jp.honeycombbattle.status.ItemStatus;
import kirin3.jp.honeycombbattle.status.PlayerStatus;

import static kirin3.jp.honeycombbattle.util.ViewUtils.dpToPx;

public class ItemMng {

    // アイテムの半径
    static float ITEM_RADIUS_DP = 10.0f;
    static float ITEM_RADIUS_PX;

    static float ITEM_TEXT_DP = 20.0f;
    static float ITEM_TEXT_PX;

    // アイテム人数
    public static int sItemNum = 0;

    // アイテムベースTEXT
    static String ITEM_BASE_TEXT[] ={"S","B","L","D"};

    // アイテムベースカラー
    static int ITEM_BASE_COLOR[][] = {
            {0,0,0},
            {0,0,0},
            {0,0,0},
            {0,0,0}};

    // アイテムテキストカラー
    static int ITEM_TEXT_COLOR[][] = {
            {255,0,0},
            {0,255,0},
            {0,0,255},
            {255,0,255}};

    // アイテムデータ
    public static ArrayList<ItemStatus> items = new ArrayList<ItemStatus>();


    public static void createItem(Context context,Canvas canvas){

        int max_x,max_y;
        int throw_random,place_random,start_x_random,start_y_random,item_random,x_dire_random,y_dire_random;
        ItemStatus item;

        Random r = new Random();
        throw_random = r.nextInt(100);

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

            item = new ItemStatus( sItemNum,start_x_random,start_y_random,ITEM_BASE_TEXT[item_random],ITEM_BASE_COLOR[item_random],ITEM_TEXT_COLOR[item_random],x_dire_random,y_dire_random );
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
if(i != 0 ) continue;
            // 描画範囲から出たものは非表示
            if( ItemMng.items.get(i).nowPositionX < 0 || ItemMng.items.get(i).nowPositionX > max_x  || ItemMng.items.get(i).nowPositionY < 0 || ItemMng.items.get(i).nowPositionY > max_y )
                continue;

            paint.setStrokeWidth(1);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setColor(Color.argb(255, ItemMng.items.get(i).baseColorR, ItemMng.items.get(i).baseColorG, ItemMng.items.get(i).baseColorB));
            canvas.drawCircle(ItemMng.items.get(i).nowPositionX, ItemMng.items.get(i).nowPositionY, ITEM_RADIUS_PX, paint);

            paint.setTextSize( ITEM_TEXT_PX );
            paint.setColor(Color.argb(255, ItemMng.items.get(i).textColorR, ItemMng.items.get(i).textColorG, ItemMng.items.get(i).textColorB));

            widht = paint.measureText(ItemMng.items.get(i).text);
            height = (paint.getFontMetrics().leading - paint.getFontMetrics().ascent) / 1.5f;
            canvas.drawText(ItemMng.items.get(i).text, ItemMng.items.get(i).nowPositionX - (widht / 2), ItemMng.items.get(i).nowPositionY + (height / 2), paint);

            // アイテムとプレイヤーの重なりをチェック
            // プレイヤー同士の円の重なりをチェック
            for (int j = 0; j < PlayerMng.sPlayerNum; j++) {
if(j != 0 ) continue;
                if( PlayerMng.players.get(j).status == PlayerMng.STATUS_DEAD ) continue;
                if( PlayerMng.players.get(j).status == PlayerMng.STATUS_GAMEOVER ) continue;

                Log.w( "DEBUG_DATA99", "ItemMng.items.get(i).nowPositionX " + ItemMng.items.get(i).nowPositionX );
                Log.w( "DEBUG_DATA99", "(max_x / 2) " + (max_x / 2) );
                Log.w( "DEBUG_DATA991", "PlayerMng.players.get(j).nowPositionX " + (PlayerMng.players.get(j).nowPositionX) );
                Log.w( "DEBUG_DATA99", "PlayerMng.players.get(j).nowPositionX + (max_x / 2) " + (PlayerMng.players.get(j).nowPositionX + (max_x / 2)) );
                Log.w( "DEBUG_DATA99", "ItemMng.items.get(i).nowPositionY " + ItemMng.items.get(i).nowPositionY );
                Log.w( "DEBUG_DATA99", "(max_y / 2) " + (max_y / 2) );
                Log.w( "DEBUG_DATA992", "PlayerMng.players.get(j).nowPositionY " + (PlayerMng.players.get(j).nowPositionY) );
                Log.w( "DEBUG_DATA99", "PlayerMng.players.get(j).nowPositionY + (max_y / 2) " + (PlayerMng.players.get(j).nowPositionY + (max_y / 2)) );
                Log.w( "DEBUG_DATA99", "ITEM_RADIUS_PX " + ITEM_RADIUS_PX );
                Log.w( "DEBUG_DATA99", "PlayerMng.PLAYER_RADIUS_PX " + PlayerMng.PLAYER_RADIUS_PX );

                // 三平方の定理で接しているか調べる（プレイヤー座標は中心座標を基準にしているので注意）
                if( Math.pow((ItemMng.items.get(i).nowPositionX - ( (max_x / 2) - PlayerMng.players.get(j).nowPositionX )),2) + Math.pow((ItemMng.items.get(i).nowPositionY - ( (max_y / 2) - PlayerMng.players.get(j).nowPositionY  )),2)
                        <= Math.pow(ITEM_RADIUS_PX + PlayerMng.PLAYER_RADIUS_PX, 2)  ){

                    Log.w( "DEBUG_DATA99", "HITTTTTTTTTTTTTTTT" );

                }
            }


ItemMng.items.get(i).nowPositionX = 700;
ItemMng.items.get(i).nowPositionY = 800;

//            ItemMng.items.get(i).nowPositionX = ItemMng.items.get(i).nowPositionX + ItemMng.items.get(i).x_direction;
//            ItemMng.items.get(i).nowPositionY = ItemMng.items.get(i).nowPositionY + ItemMng.items.get(i).y_direction;

        }
    }
}
