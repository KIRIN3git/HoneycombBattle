package kirin3.jp.honeycombbattle.util;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;

public class ViewUtils {

    static float mirrorX,mirrorY;

    /*
     * PX→DP変換
     */
    public static float pxToDp(float px, Resources resources){

        float dp = px * getDensity(resources) + 0.5f;
        return dp;
    }

    /*
     * density(密度取得)
     */
    public static float getDensity(Resources resources){
        return resources.getDisplayMetrics().density;
    }

    // テキストビューの反転表示
    // 後々canvasサイズはこちらで保持？
    public static void mirrorDrowText(Canvas canvas, Paint paint, float x, float y, String text){
        canvas.drawText(text, x - paint.measureText(text) / 2, y - ((paint.descent() + paint.ascent()) / 2), paint);

        // 反転表示
        mirrorX = canvas.getWidth() - x;
        mirrorY = canvas.getHeight() - y;

        canvas.rotate(180,mirrorX,mirrorY);
        canvas.drawText(text, mirrorX - paint.measureText(text) / 2, mirrorY - ((paint.descent() + paint.ascent()) / 2), paint);
        canvas.rotate(180,mirrorX,mirrorY);
    }
}
