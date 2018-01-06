package com.imooc.testfinal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

//将内部类里的变量指向局部变量的引用的空间
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test();
    }

    private void test() {
        TextView textView = (TextView) findViewById(R.id.tv);
        final bean bean = new bean();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bean.setName(bean.getName()+"456");
                Log.e("test1",bean.getName());
            }
        });
        textView.callOnClick();
        Log.e("test2",bean.getName());
    }
}
