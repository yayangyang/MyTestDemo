package com.yayangyang.requestlayouttestdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

//requestLayout方法使View和其父容器都重新布局
//没有attach的View执行requestLayout方法没有父类所以没有重新布局
//TextView宽高包裹内容使用setText方法设置的内容不改变仍会调用requestLayout方法
//TextView宽100dp高包裹内容使用setText方法设置的内容不改变不会调用requestLayout方法
//TextView宽包裹内容高100dp使用setText方法设置的内容不改变不会调用requestLayout方法
//TextView使用setText方法设置的内容不调用requestLayout方法仍会调用invalidate方法
//去掉MyLinearLayout使用MyRelativeLayout作为父布局以上结果一样
//TextView的宽在包裹内容的时候无论其父布局是谁均会调用TextView的requestLayout方法,
//使用setText方法的时候检测当TextView控件足够显示时调用invalidate方法否则调用requestLayout方法
public class MainActivity extends AppCompatActivity {

    private int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ww","点击了");
//                findViewById(R.id.tv).requestLayout();

//                MyTextView myTextView = new MyTextView(getApplicationContext());
//                myTextView.setText("eeeeeeee");

                TextView tv = findViewById(R.id.tv);
                if(i==0){
                    i=1;
                    tv.setText("aaaaaa");
                }else{
                    i=0;
                    tv.setText("aaaaaa");
                }
            }
        });
    }
}
