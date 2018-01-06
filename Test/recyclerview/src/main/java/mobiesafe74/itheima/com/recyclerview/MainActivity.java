package mobiesafe74.itheima.com.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    private ImageView image01;
    private MyRecyclerView recycleview;
    private List<Integer> mData;
    private MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        initData();
    }

    private void initData() {
        mData= new ArrayList<Integer>(Arrays.asList(R.drawable.a,R.drawable.b,R.drawable.c,
                R.drawable.d,R.drawable.e,R.drawable.f,R.drawable.g,R.drawable.h));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycleview.setLayoutManager(linearLayoutManager);
        adapter=new MyAdapter(this,mData);
        recycleview.setScrollListener(new MyRecyclerView.scrollListener() {
            @Override
            public void change(View currentView, int childPosition) {
                image01.setImageResource(mData.get(childPosition));
            }
        });
        adapter.setClickListener(new MyAdapter.clickListener() {
            @Override
            public void click(View view,int position) {
                image01.setImageResource(mData.get(position));
            }
        });
        recycleview.setAdapter(adapter);
    }

    private void initUI() {
        image01= (ImageView) findViewById(R.id.image01);
        recycleview= (MyRecyclerView) findViewById(R.id.recyclerview);
    }


}
