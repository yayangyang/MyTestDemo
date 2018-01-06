package mobiesafe74.itheima.com.ceshi;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

//循环中点击按钮会中断循环
public class MainActivity extends AppCompatActivity {

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            for(int i=0;i<50000;i++){
                Log.e("ww",i+"");
            }
            for(int i=0;i<100;i++){
                Log.e("伴随动画几乎都是就会不动事件会被速度滑冰","伴随动画几乎都是就会不动事件会被速度滑冰");
            }
//            Toast.makeText(getApplicationContext(),"结束",Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHandler.sendEmptyMessageDelayed(0,2000);
    }

public void dianji(View view){
        boolean t=Looper.getMainLooper() == Looper.myLooper();
    Toast.makeText(getApplicationContext(),"啊啊啊啊啊啊啊啊啊啊啊啊啊",Toast.LENGTH_SHORT).show();
        Log.e("ww","执行了");
        for(int i=0;i<100;i++){
            Log.e("ww",i+"");
        }
    }
}
