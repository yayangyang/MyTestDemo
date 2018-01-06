package mobiesafe74.itheima.com.smartcamera;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tutk.IOTC.Monitor;

public class CameraActivity extends AppCompatActivity {

    private Monitor mMonitor;
    private TextView mTvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        initValue();
        initView();
    }

    private void initView() {
        mMonitor = (Monitor) findViewById(R.id.monitor);
        mTvStatus = (TextView) findViewById(R.id.tv_staus);
    }

    private void initValue() {
        Intent intent = new Intent();
        String name = intent.getStringExtra("name");
        String uid = intent.getStringExtra("uid");
        String pwd = intent.getStringExtra("pwd");
        conn(name,uid,pwd);
    }

    //连接摄像头
    private void conn(String name, String uid, String pwd) {

    }

    //点击按钮,发送不同指令
    public void sendCtrl(View v){
        if(v.getId()==R.id.ib_left){

        }else if(v.getId()==R.id.ib_right){

        }else if(v.getId()==R.id.ib_top){

        }else if(v.getId()==R.id.ib_bottom){

        }
    }
}
