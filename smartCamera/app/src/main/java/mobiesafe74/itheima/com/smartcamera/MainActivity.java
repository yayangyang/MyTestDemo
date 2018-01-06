package mobiesafe74.itheima.com.smartcamera;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText mEtname;
    private EditText mEtuid;
    private EditText mEtpwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mEtname = (EditText) findViewById(R.id.et_name);
        mEtuid = (EditText) findViewById(R.id.et_uid);
        mEtpwd = (EditText) findViewById(R.id.et_pwd);
    }

    public void startConnect(View view){
        String name = mEtname.getText().toString().trim();
        String uid = mEtuid.getText().toString().trim();
        String pwd = mEtpwd.getText().toString().trim();
        if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"名称不能为空",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(uid)&&uid.length()!=20){
            Toast.makeText(this,"UID错误",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(pwd)){
            Toast.makeText(this,"密码不能为空",Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(this,CameraActivity.class);
            intent.putExtra("name",name);
            intent.putExtra("uid",uid);
            intent.putExtra("pwd",pwd);
            startActivity(intent);
        }
    }
}
