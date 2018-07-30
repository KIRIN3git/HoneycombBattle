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

    // アイテムの開始位置
    public int startPositionX, startPositionY;

    // アイテムベースカラー
    static int ITEM_BASE_COLOR[][] = {
            {0,0,0},
            {0,0,0},
            {0,0,0},
            {0,0,0}};

    // アイテムテキストカラー
    static int ITEM_TEXT_COLOR[][] = {
            {255,0,0},
            {255,255,255},
            {255,255,255},
            {255,255,255}};

    // アイテムデータ
    public static ArrayList<ItemStatus> items = new ArrayList<ItemStatus>();


    public static void createItem(Context context,Canvas canvas){

        Random r = new Random();
        int random = r.nextInt(100);

        ItemStatus item;

        if( (int)random == 0 ){
            sItemNum++;
            int max_x = 0;
            int max_y = canvas.getHeight();

            int start_y = r.nextInt(max_y) + 1;

            int x_dire = r.nextInt(5) + 1;
            int y_dire = r.nextInt(10) - 5;

            Log.w( "DEBUG_DATA", "ITEMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM x " + x_dire + "y " + y_dire);
            item = new ItemStatus( sItemNum,0,start_y, ITEM_BASE_COLOR[0],ITEM_TEXT_COLOR[0],x_dire,y_dire );
            items.add(item);
        }

    }


    public static void drawItem(Context context, Paint paint, Canvas canvas) {

        float widht,height;
        ITEM_RADIUS_PX = dpToPx(ITEM_RADIUS_DP,context.getResources());
        ITEM_TEXT_PX = dpToPx(ITEM_TEXT_DP,context.getResources());

        Log.w( "DEBUG_DATAccc", "ITEM_RADIUS_PX " + ITEM_RADIUS_PX );
        Log.w( "DEBUG_DATAbbb", "ITEM_TEXT_PX " + ITEM_TEXT_PX );

        paint.reset();



        for(int i = 0; i < sItemNum; i++ ) {

            paint.setStrokeWidth(1);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setColor(Color.argb(255, ItemMng.items.get(i).baseColorR, ItemMng.items.get(i).baseColorG, ItemMng.items.get(i).baseColorB));
            canvas.drawCircle(ItemMng.items.get(i).nowPositionX, ItemMng.items.get(i).nowPositionY, ITEM_RADIUS_PX, paint);

            paint.setTextSize( ITEM_TEXT_PX );
            paint.setColor(Color.argb(255, ItemMng.items.get(i).textColorR, ItemMng.items.get(i).textColorG, ItemMng.items.get(i).textColorB));

            widht = paint.measureText("A");
            height = (paint.getFontMetrics().leading - paint.getFontMetrics().ascent) / 1.5f;
            canvas.drawText("A", ItemMng.items.get(i).nowPositionX - (widht / 2), ItemMng.items.get(i).nowPositionY + (height / 2), paint);

            ItemMng.items.get(i).nowPositionX = ItemMng.items.get(i).nowPositionX + ItemMng.items.get(i).x_direction;
            ItemMng.items.get(i).nowPositionY = ItemMng.items.get(i).nowPositionY + ItemMng.items.get(i).y_direction;

        }
    }
}
