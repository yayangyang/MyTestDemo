package mobiesafe74.itheima.com.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private TextView tv01;
    private int a=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv01 = (TextView) findViewById(R.id.tv01);
    }
    public void dianji(View view){
        if(a==0){
            a++;
        }else{
            ViewGroup.LayoutParams layoutParams = tv01.getLayoutParams();
            layoutParams.width+=100;
            layoutParams.height+=100;
            tv01.setLayoutParams(layoutParams);
        }
        int measuredHeight = tv01.getMeasuredHeight();
        int bottom = tv01.getBottom();
        Log.e("measuredHeight",measuredHeight+"");
        Log.e("bottom",bottom+"");
    }
}
