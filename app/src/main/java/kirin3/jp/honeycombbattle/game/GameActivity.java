package kirin3.jp.honeycombbattle.game;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
	GameSurfaceView surfaceView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		surfaceView = new GameSurfaceView(this);
		setContentView(surfaceView);
	}
}
