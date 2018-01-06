package com.yayangyang.tagflowlayouttestdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> mStringList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        TagFlowLayout tagFlowLayout = findViewById(R.id.flowLayout);
        tagFlowLayout.setAdapter(new TagAdapter<String>(mStringList) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                View view = View.inflate(MainActivity.this, R.layout.item, null);
                TextView textView = view.findViewById(R.id.tv);
                textView.setText(s);
                return view;
            }
        });
    }

    private void initData() {
        for(int i=0;i<300;i++){
            mStringList.add("ww"+i);
        }
    }
}
