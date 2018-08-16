package kirin3.jp.honeycombbattle.game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import kirin3.jp.honeycombbattle.R;
import kirin3.jp.honeycombbattle.main.MainActivity;
import kirin3.jp.honeycombbattle.mng.FieldMng;
import kirin3.jp.honeycombbattle.mng.ItemMng;
import kirin3.jp.honeycombbattle.mng.PlayerMng;
import kirin3.jp.honeycombbattle.mng.TimeMng;
import kirin3.jp.honeycombbattle.result.ResultActivity;

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

	SurfaceHolder surfaceHolder;
	Thread thread;

	// intentで送られてくるゲーム設定
	public static int sBattleTimeNo;
	public static int sPlayerNumberNo;
	public static int sPlayerSpeedNo;
	public static int sItemQuantityNo;
	public static float sFieldSizeMagnification;


	public static int winnerNo = -1;

	public static final String INTENT_BATTLE_TIME = "INTENT_BATTLE_TIME";
	public static final String INTENT_PLAYER_SPEED = "INTENT_PLAYER_SPEED";
	public static final String INTENT_PLAYER_NUMBER = "INTENT_PLAYER_NUMBER";
	public static final String INTENT_ITEM_QUANTITY = "INTENT_ITEM_QUANTITY";
	public static final String INTENT_FIELD_SIZE = "INTENT_FIELD_SIZE";

	public GameSurfaceView(Context context){
		super(context);

		mContext = context;

		setIntentData();

		// 時間情報の初期化
		TimeMng.timeInit(context,sBattleTimeNo);
		// フィールド情報の初期化
		FieldMng.fieldInit(context,sFieldSizeMagnification);
		// プレイヤー情報の初期化
		PlayerMng.playerInit(context,sPlayerNumberNo,sPlayerSpeedNo,sFieldSizeMagnification);
		// アイテム情報の初期化
		ItemMng.itemInit(sItemQuantityNo,sFieldSizeMagnification);


		surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);

//		hex_color_num = new int[SQUARE_NUM][SQUARE_NUM];
	}


	public void setIntentData(){

		Bundle extras = ((Activity)mContext).getIntent().getExtras();
		String battle_time = extras.getString(INTENT_BATTLE_TIME);
		String player_number = extras.getString(INTENT_PLAYER_NUMBER);
		String player_speed = extras.getString(INTENT_PLAYER_SPEED);
		String item_quantity = extras.getString(INTENT_ITEM_QUANTITY);
		String field_size = extras.getString(INTENT_FIELD_SIZE);

		if( battle_time.equals(getResources().getString(R.string.time_1)) ){
			sBattleTimeNo = 0;
		}
		else if( battle_time.equals(getResources().getString(R.string.time_2)) ){
			sBattleTimeNo = 1;
		}
		else if( battle_time.equals(getResources().getString(R.string.time_3)) ){
			sBattleTimeNo = 2;
		}
		else{
			sBattleTimeNo = 1;
		}

		if( player_number.equals(getResources().getString(R.string.number_1)) ){
			sPlayerNumberNo = 0;
		}
		else if( player_number.equals(getResources().getString(R.string.number_2)) ){
			sPlayerNumberNo = 1;
		}
		else if( player_number.equals(getResources().getString(R.string.number_3)) ){
			sPlayerNumberNo = 2;
		}
		else{
			sPlayerNumberNo = 0;
		}


		if( player_speed.equals(getResources().getString(R.string.speed_1)) ){
			sPlayerSpeedNo = 0;
		}
		else if( player_speed.equals(getResources().getString(R.string.speed_2)) ){
			sPlayerSpeedNo = 1;
		}
		else if( player_speed.equals(getResources().getString(R.string.speed_3)) ){
			sPlayerSpeedNo = 2;
		}
		else if( player_speed.equals(getResources().getString(R.string.speed_4)) ){
			sPlayerSpeedNo = 3;
		}
		else if( player_speed.equals(getResources().getString(R.string.speed_5)) ){
			sPlayerSpeedNo = 4;
		}
		else{
			sPlayerSpeedNo = 2;
		}


		if( item_quantity.equals(getResources().getString(R.string.quantity_1)) ){
			sItemQuantityNo = 0;
		}
		else if( item_quantity.equals(getResources().getString(R.string.quantity_2)) ){
			sItemQuantityNo = 1;
		}
		else if( item_quantity.equals(getResources().getString(R.string.quantity_3)) ){
			sItemQuantityNo = 2;
		}
		else if( item_quantity.equals(getResources().getString(R.string.quantity_4)) ){
			sItemQuantityNo = 3;
		}
		else if( item_quantity.equals(getResources().getString(R.string.quantity_5)) ){
			sItemQuantityNo = 4;
		}
		else{
			sItemQuantityNo = 2;
		}

		if( field_size.equals(getResources().getString(R.string.size_1)) ){
			sFieldSizeMagnification = 0.5f;
		}
		else if( field_size.equals(getResources().getString(R.string.size_2)) ){
			sFieldSizeMagnification = 0.8f;
		}
		else if( field_size.equals(getResources().getString(R.string.size_3)) ){
			sFieldSizeMagnification = 1.0f;
		}
		else if( field_size.equals(getResources().getString(R.string.size_4)) ){
			sFieldSizeMagnification = 1.2f;
		}
		else if( field_size.equals(getResources().getString(R.string.size_5)) ){
			sFieldSizeMagnification = 1.5f;
		}
		else{
			sFieldSizeMagnification = 1.0f;
		}
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
//				Log.w( "DEBUG_DATA", "lockCanvas" );

				canvas.drawRect( 0, 0, screen_width, screen_height, bgPaint);

				// 基本六角形
				FieldMng.drawHex(paint, canvas);

				// プレイヤーの復活処理
				PlayerMng.revivalPlayer(paint, canvas);

				// アイテムの効果チェック
				ItemMng.checkItemEffect();
				// プレイヤーの表示
				PlayerMng.drawPlayer(mContext, paint, canvas);
				// プレイヤーのライフ表示
				PlayerMng.drawLife(mContext, paint, canvas);
				// アイテムの作成
				ItemMng.createItem(mContext, canvas);
				// アイテムの表示
				ItemMng.drawItem(mContext, paint, canvas);

				// カウントダウン中
				if( TimeMng.getSituation() == TimeMng.SITUATION_COUNTDOWN ){
					// 開始カウントダウンの表示
					TimeMng.drawCountDown(mContext,paint, canvas);
				}
				// バトル中
				else if( TimeMng.getSituation() == TimeMng.SITUATION_BATTLE ){
					// タップ移動比率xyと指示マーカーのxyを取得
					PlayerMng.getMoveXY();
					// 指示器の表示
					PlayerMng.drawIndicator(paint, canvas);
					// リミット時間の表示
					TimeMng.drawLimitTime(mContext,paint, canvas);
				}
				else if( TimeMng.getSituation() == TimeMng.SITUATION_GAMEOVER ){
					processGameOver(canvas,winnerNo);
				}

				// 描画
				surfaceHolder.unlockCanvasAndPost(canvas);

//				Log.w( "DEBUG_DATA", "unlockCanvasAndPost" );
				// fps
				TimeMng.fpsEnd();


//				Log.w( "DEBUG_DATA check", "end");

				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
				}



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
				if(PlayerMng.sPlayerNumber == 2){
					//フィールド下半分が1P
					if( screen_height / 2 < y ) user_i = 0;
					else user_i = 1;
				}
				else if(PlayerMng.sPlayerNumber == 3){
					//フィールド下半分が1P
					if( screen_height / 2 < y ){
						if( screen_width / 2 > x) user_i = 0;
						else user_i = 2;
					}
					else{
						user_i = 1;
					}
				}
				else if(PlayerMng.sPlayerNumber == 4){
					//フィールド下半分が1P
					if( screen_height / 2 < y ){
						if( screen_width / 2 > x) user_i = 0;
						else user_i = 2;
					}
					else{
						if( screen_width / 2 > x) user_i = 1;
						else user_i = 3;
					}
				}
				else break;

				// 未タッチでなければ、処理せず
				if(PlayerMng.players.get(user_i).pointId != -1) break;

				PlayerMng.players.get(user_i).startTouchX = (int)x;
				PlayerMng.players.get(user_i).startTouchY = (int)y;
				PlayerMng.players.get(user_i).nowTouchX = (int)x;
				PlayerMng.players.get(user_i).nowTouchY = (int)y;
				PlayerMng.players.get(user_i).touchFlg = true;
				PlayerMng.players.get(user_i).pointId = point_id;

				break;
			// 最後の指一本を上げる
			case MotionEvent.ACTION_UP:
			// 最後じゃない指を上げる
			case MotionEvent.ACTION_POINTER_UP:

				for( user_i = 0; user_i < PlayerMng.sPlayerNumber; user_i++ ){
					if( PlayerMng.players.get(user_i).pointId == point_id ) break;
				}
				// ユーザー一致せず
				if( user_i == PlayerMng.sPlayerNumber ) break;

				PlayerMng.players.get(user_i).touchFlg = false;
				PlayerMng.players.get(user_i).pointId = -1;
				PlayerMng.players.get(user_i).indicatorXY[0] = 0;
				PlayerMng.players.get(user_i).indicatorXY[1] = 0;

				break;
		}

		for(int i = 0; i < count; i++) {
			// ポインタID
			point_id = event.getPointerId(i);
			// インデックスID
			//index_id = event.findPointerIndex(pointId);
			index_id = i; // 必ず同一

			x = event.getX(index_id);
			y = event.getY(index_id);

			if (point_id == -1) continue;

			for( user_i = 0; user_i < PlayerMng.sPlayerNumber; user_i++ ){
				if(point_id == PlayerMng.players.get(user_i).pointId){
					// タッチしている位置取得
					PlayerMng.players.get(user_i).nowTouchX = (int)x;
					PlayerMng.players.get(user_i).nowTouchY = (int)y;
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

		Log.w( "DEBUG_DATA", "surfaceDestroyed" );
		endThread();
	}

	public void endThread(){
		thread = null; // スレッド停止要請

		// スレッド停止まで0.1秒待つ
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
		}
	}

	public void processGameOver(Canvas canvas,int winner_no){

		TimeMng.sleepGameOver();

		// 描画
		surfaceHolder.unlockCanvasAndPost(canvas);
		thread = null; // スレッド停止要請

		Log.w( "DEBUG_DATA", "winner_no " + winner_no );

		Intent intent = new Intent(mContext, ResultActivity.class);
		intent.putExtra(ResultActivity.INTENT_WINNER_NO,winner_no);
		mContext.startActivity(intent);
	}


}

