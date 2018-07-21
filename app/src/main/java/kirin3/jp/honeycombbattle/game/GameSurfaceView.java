package kirin3.jp.honeycombbattle.game;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.sql.Time;

import kirin3.jp.honeycombbattle.AppApplication;
import kirin3.jp.honeycombbattle.main.MainActivity;
import kirin3.jp.honeycombbattle.mng.FieldMng;
import kirin3.jp.honeycombbattle.mng.PlayerMng;
import kirin3.jp.honeycombbattle.mng.TimeMng;
import kirin3.jp.honeycombbattle.util.ViewUtils;

import static kirin3.jp.honeycombbattle.mng.TimeMng.sleepGameOver;

/**
 * Created by shinji on 2017/04/06.
 */

public class GameSurfaceView extends SurfaceView implements  Runnable,SurfaceHolder.Callback{

	// スクリーンの大きさ(px)
	int screen_width, screen_height;

	Context mContext;

	// 背景ALPHA
	final static int BACK_ALPHA = 255 ;

	// 背景RGB
	final static int BACK_R = 200 ;
	final static int BACK_G = 200 ;
	final static int BACK_B = 200 ;

	boolean scoreFlg;

	SurfaceHolder surfaceHolder;
	Thread thread;


	public GameSurfaceView(Context context){
		super(context);

		mContext = context;

		// 時間情報の初期化
		TimeMng.timeInit(context);
		// フィールド情報の初期化
		FieldMng.fieldInit(context);
		// プレイヤー情報の初期化
		PlayerMng.playerInit(context,2);


		scoreFlg = false;

		surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);


//		hex_color_num = new int[SQUARE_NUM][SQUARE_NUM];

	}

	@Override
	public void run() {
		// 時間情報の初期化
		TimeMng.countDownStart(mContext);

		// キャンバスを設定
		Canvas canvas;

		// ペイントを設定
		Paint paint = new Paint();
		Paint bgPaint = new Paint();
		bgPaint.setColor(Color.argb(BACK_ALPHA, BACK_R, BACK_G, BACK_B));

		TimeMng.countDownStart(mContext);

		while(thread != null){
			try{
				TimeMng.fpsStart();

				canvas = surfaceHolder.lockCanvas();
				canvas.drawRect( 0, 0, screen_width, screen_height, bgPaint);

				// 基本六角形
				FieldMng.DrawHex(paint, canvas);
				// プレイヤーの表示
				PlayerMng.DrawPlayer(mContext,paint, canvas);

				// カウントダウン中
				if( TimeMng.getSituation() == TimeMng.SITUATION_COUNTDOWN ){
					// 開始カウントダウンの表示
					TimeMng.drawCountDown(mContext,paint, canvas);
				}
				// バトル中
				else if( TimeMng.getSituation() == TimeMng.SITUATION_BATTLE ){
					// タップ移動比率xyと指示マーカーのxyを取得
					PlayerMng.GetMoveXY();

					// 指示器の表示
					PlayerMng.DrawIndicator(paint, canvas);

					// リミット時間の表示
					TimeMng.drawLimitTime(mContext,paint, canvas);
				}
				else if( TimeMng.getSituation() == TimeMng.SITUATION_GAMEOVER ){
					TimeMng.sleepGameOver();

					// スコア画面の起動
					if(!scoreFlg){
						/* ☆
						Intent intent = new Intent(getContext(), ResultActivity.class);
						getContext().startActivity(intent);
						scoreFlg = true;
						*/
					}
				}

				// 描画
				surfaceHolder.unlockCanvasAndPost(canvas);

				// fps
				TimeMng.fpsEnd();

			} catch(Exception e){}
		}
	}

	// タッチイベントを処理するためOverrideする
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		// タッチしている数を取得
		int count = event.getPointerCount();
		// タッチアクションの情報を取得
		int action = event.getAction();
		int index_id = event.getActionIndex();
		int point_id = event.getPointerId(index_id);

		int user_i = -1;
		float x,y;
		x = event.getX(index_id);
		y = event.getY(index_id);

		switch(action & MotionEvent.ACTION_MASK) {
			// 最初の指を下げる
			case MotionEvent.ACTION_DOWN:
			// 最初じゃない指を下げる
			case MotionEvent.ACTION_POINTER_DOWN:

				//2人プレイなら
				if(PlayerMng.sPlayerNum == 2){
					//フィールド下半分が1P

					if( screen_height / 2 < y )
						user_i = 0;
					else user_i = 1;

				}
				else break;

				// 未タッチでなければ、処理せず
				if(PlayerMng.players.get(user_i).point_id != -1) break;

				PlayerMng.players.get(user_i).start_touch_x = (int)x;
				PlayerMng.players.get(user_i).start_touch_y = (int)y;
				PlayerMng.players.get(user_i).now_touch_x = (int)x;
				PlayerMng.players.get(user_i).now_touch_y = (int)y;
				PlayerMng.players.get(user_i).touch_flg = true;
				PlayerMng.players.get(user_i).point_id = point_id;

				break;
			// 最後の指一本を上げる
			case MotionEvent.ACTION_UP:
			// 最後じゃない指を上げる
			case MotionEvent.ACTION_POINTER_UP:

				for( user_i = 0; user_i < PlayerMng.sPlayerNum; user_i++ ){
					if( PlayerMng.players.get(user_i).point_id == point_id ) break;
				}
				// ユーザー一致せず
				if( user_i == PlayerMng.sPlayerNum ) break;

				PlayerMng.players.get(user_i).touch_flg = false;
				PlayerMng.players.get(user_i).point_id = -1;
				PlayerMng.players.get(user_i).indicatorXY[0] = 0;
				PlayerMng.players.get(user_i).indicatorXY[1] = 0;

				break;
		}

		for(int i = 0; i < count; i++) {
			// ポインタID
			point_id = event.getPointerId(i);
			// インデックスID
			//index_id = event.findPointerIndex(point_id);
			index_id = i; // 必ず同一

			x = event.getX(index_id);
			y = event.getY(index_id);

			if (point_id == -1) continue;

			for( user_i = 0; user_i < PlayerMng.sPlayerNum; user_i++ ){
				if(point_id == PlayerMng.players.get(user_i).point_id ){
					// タッチしている位置取得
					PlayerMng.players.get(user_i).now_touch_x = (int)x;
					PlayerMng.players.get(user_i).now_touch_y = (int)y;
				}
			}
		}
		// 再描画の指示
		invalidate();

		return true;

	}

	// 変更時に呼び出される
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		screen_width = width;
		screen_height = height;
	}
	// 作成時に読みだされる
	// この時点で描画準備はできていて、SurfaceHoderのインスタンスを返却する
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		thread = new Thread(this);
		thread.start();
	}
	// 破棄時に呼び出される
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		thread = null;
	}

}

