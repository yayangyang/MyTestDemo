package mobiesafe74.itheima.com.mygame2048;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import mobiesafe74.itheima.com.mygame2048.utils.UIUtils;


public class MainActivity extends Activity {
	private TextView currentScore;
	private GameView gameView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		gameView= (GameView) findViewById(R.id.gameView);
		currentScore= (TextView) findViewById(R.id.currentScore);
		gameView.setListener(new GameView.listener() {
			@Override
			public void fenshu(int fenshu) {
				currentScore.setText("分数\n"+fenshu+"");
			}

			@Override
			public void success(int fenshu) {
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(),SuccessActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("fenshu",fenshu);
				intent.putExtras(bundle);
				startActivity(intent);
				finish();
			}

			@Override
			public void fail() {
				Log.e("ww","游戏结束-失败");
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(),FailActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
