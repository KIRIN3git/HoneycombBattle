package kirin3.jp.honeycombbattle.game;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import kirin3.jp.honeycombbattle.util.LogUtils;

public class GameActivity extends AppCompatActivity {


	private static final String TAG = LogUtils.makeLogTag(GameActivity.class);

	GameSurfaceView surfaceView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/*
		Bundle extras = getIntent().getExtras();
		String data = extras.getString("INTENT_ITEM_QUANTITY");
		Log.w(TopActivity.INTENT_ITEM_QUANTITY, "data " + data);
		*/

		surfaceView = new GameSurfaceView(this);
		setContentView(surfaceView);

		View decor = this.getWindow().getDecorView();
		decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

	}

	@Override
	public void onPause() {
		super.onPause();

	}
	@Override
	public void onStop(){
		super.onStop();
	}
}
