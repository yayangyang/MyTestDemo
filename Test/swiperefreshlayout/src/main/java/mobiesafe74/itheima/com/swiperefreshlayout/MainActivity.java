package mobiesafe74.itheima.com.swiperefreshlayout;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private SwipeRefreshLayout srl;
    private ListView listview;
    private List<String> mdata=new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    public Handler mhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==0x11){
                mdata.addAll(Arrays.asList("www","www","www"));
                adapter.notifyDataSetChanged();
                srl.setRefreshing(false);//刷新结束动画消失
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        initData();
    }

    private void initData() {
        for(int i=0;i<10;i++){
            mdata.add("s");
        }
    }

    private void initUI() {
        srl= (SwipeRefreshLayout) findViewById(R.id.srl);
        listview= (ListView) findViewById(R.id.listview);
        srl.setOnRefreshListener(this);
        srl.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light));
        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1,mdata);
        listview.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        mhandler.sendEmptyMessage(0x11);
    }
}
