package kirin3.jp.honeycombbattle.mng;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;

import static kirin3.jp.honeycombbattle.util.ViewUtils.dpToPx;

public class ItemMng {

    // プレイヤーの半径
    static float ITEM_RADIUS_DP = 15.0f;
    static float ITEM_RADIUS_PX;

    static float ITEM_TEXT_DP = 20.0f;
    static float ITEM_TEXT_PX;


    public static void drawItem(Context context, Paint paint, Canvas canvas) {

        float widht,height;
        ITEM_RADIUS_PX = dpToPx(ITEM_RADIUS_DP,context.getResources());
        ITEM_TEXT_PX = dpToPx(ITEM_TEXT_DP,context.getResources());
        Log.w( "DEBUG_DATAccc", "ITEM_RADIUS_PX " + ITEM_RADIUS_PX );
        Log.w( "DEBUG_DATAbbb", "ITEM_TEXT_PX " + ITEM_TEXT_PX );




        // Canvas 中心点
        float center_x = canvas.getWidth() / 2;
        float center_y = canvas.getHeight() / 2;

        Path path = new Path();

        paint.reset();
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
/*
        path.reset();
        // 右
        path.moveTo(center_x, center_y);
        // 右下
        path.lineTo(center_x + 100, center_y);
        // 右上
        path.lineTo(center_x + 50, center_y - 100);
        path.close();

        canvas.drawPath(path, paint);
*/
        canvas.drawCircle(center_x, center_y, ITEM_RADIUS_PX, paint);

        paint.setTextSize( ITEM_TEXT_PX );
        paint.setColor(Color.RED);

        widht = paint.measureText("A");
        height = paint.getFontMetrics().leading - paint.getFontMetrics().ascent - 20;
        Log.w( "DEBUG_DATAbbb", "widht " + widht );
        Log.w( "DEBUG_DATAbbb", "height " + height );

        Log.w( "DEBUG_DATA", "paint.measureText(\"A\") " + paint.measureText("A") );
        canvas.drawText("A", center_x - (widht / 2), center_y + (height / 2), paint);

    }
}
