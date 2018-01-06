package mobiesafe74.itheima.com.test02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private ListView lv01;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        lv01.setAdapter(new MyAdapter(MainActivity.this));
    }

    private void initUI() {
        lv01= (ListView) findViewById(R.id.lv01);
    }
}
