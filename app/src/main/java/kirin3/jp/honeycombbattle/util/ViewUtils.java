package kirin3.jp.honeycombbattle.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Method;

public class ViewUtils {

    static float mirrorX,mirrorY;

    /*
     * DP→DX変換
     * dpToPx(COUNTDONW_TEXT_SIZE_DP,context.getResources())
     */
    public static float dpToPx(float dp, Resources resources){

        float px = dp * getDensity(resources) + 0.5f;
        return px;
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

    // 端末のサイズを取得(Pointクラス px)
    public static  Point getDisplaySize(Context context) {

        WindowManager winMan = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display = winMan.getDefaultDisplay();
        Point real = new Point(0, 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            // Android 4.2以上
            display.getRealSize(real);
            return real;

        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            // Android 3.2以上
            try {
                Method getRawWidth = Display.class.getMethod("getRawWidth");
                Method getRawHeight = Display.class.getMethod("getRawHeight");
                int width = (Integer) getRawWidth.invoke(display);
                int height = (Integer) getRawHeight.invoke(display);
                real.set(width, height);
                return real;

            } catch (Exception e) {
                // TODO 自動生成された catch ブロック
                e.printStackTrace();
            }
        }

        return real;
    }
}
