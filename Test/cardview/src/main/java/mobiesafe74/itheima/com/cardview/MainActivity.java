package mobiesafe74.itheima.com.cardview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private CardView cardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardView = (CardView)findViewById(R.id.cardView);

        cardView.setRadius(80);//卡片的圆角大小

        cardView.setCardElevation(8);//设置阴影部分大小

        cardView.setContentPadding(200,200,200,200);//设置卡片内容于边距的间隔
    }
}
