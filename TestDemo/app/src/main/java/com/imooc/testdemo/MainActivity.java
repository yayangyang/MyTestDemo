package com.imooc.testdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

//每次出现一个item,对应item的布局就measure一次
public class MainActivity extends AppCompatActivity {
    private MyRecyclerView rc;
    private ArrayList<String> mArrayList=new ArrayList<>();;
    private MyAdapter myAdapter;
    public LinearLayout myll;
    private quickAdapter mQuickAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        init();
    }

    private void init() {
        rc = findViewById(R.id.rc);
        rc.setLayoutManager(new LinearLayoutManager(this));
        rc.setHasFixedSize(true);
        myll= findViewById(R.id.myll);
        myll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                myAdapter.notifyDataSetChanged();
//                mArrayList.clear();

//                initData();//单纯地增加数据不通知内部同步不会出错

                //删除数据不通知内部同步会出错,查看RecyclerView的Recycler类的bindViewToPosition方法,
                //通过mAdapterHelper检验对应position是否越界
                //(是否大于外部数据集的大小,匹配数据的调用应该是根据内部数据执行的,
                // 所以若删除外部数据没有及时通知而去执行绑定这时就会越界,抛出越界错误)
//                mArrayList.remove(mArrayList.size()-1);

//                mArrayList.remove(mArrayList.size()-6);
                //通知内部已删除对应的外部数据达到数据同步(内部可能缓存了一个相同的数据集,改变是需要通知他一起改变)
                //notifyItemRangeRemoved第一个参数是已删除数据在还没删除时的position
                //执行删除会执行动画(对应的item应该是删除掉了),删除的对应条目一般不会执行onBindViewHolder,
                //因删除而进入界面的当然会执行onBindViewHolder
//                myAdapter.notifyItemRangeRemoved(mArrayList.size()-5,1);
//                myAdapter.notifyItemRangeChanged(mArrayList.size()-5,
//                        5);
//                Log.e("123","456"+mArrayList.size());

//                mArrayList.remove(0);
//                myAdapter.notifyItemChanged(0);//notifyItemChanged不能替代notifyItemRemoved,这里会报错
//                Log.e("ww","eeeee"+0);

//                mArrayList.clear();
                //notify相关方法最终都运行到requestLayout方法,从下一行的log先输出可看出应该是使用handler发送到
                //主线程排队,在这里若当前方法执行完,notifyDataSetChanged(通知内部数据同步)发送到主线程排队这个消息
                //之前有一个消息需要取得已经删除的数据这时可能会报错
                //(不过一般是滑动到数据的最后才添加或删除数据,这时即使快速滑动到最底部也不会出现执行绑定数据的情况,不取出数据就不报错)
//                myAdapter.notifyDataSetChanged();
//                Log.e("clear","wwwwwwwwww"+myAdapter.getItemCount());

                //取出数据才报错,滑动不绑定item有执行getItemCount方法但不报错
//                mArrayList.remove(mArrayList.size()-1);

//                mArrayList = new ArrayList<>();
//                mArrayList.add("ww1");
//                mArrayList.add("ww2");
//                mArrayList.add("ww3");
//                mArrayList.add("ww4");
//                mArrayList.add("ww5");

//                TextView mytv = findViewById(R.id.mytv);
//                mytv.setText("eeeeeeeee");
                Log.e("onClick","onClick");
//                mQuickAdapter.setNewData(mArrayList);

                mArrayList.remove(0);
                myAdapter.notifyItemRangeRemoved(0,100);
            }
        });
        myAdapter = new MyAdapter();
//        mQuickAdapter = new quickAdapter(R.layout.item,mArrayList);
//        mQuickAdapter.addHeaderView(View.inflate(this,R.layout.activity_head,null));
        //预先建立(若没有可复用holder)绑定holder,
        // 如出现position==2进入屏幕的时候绘制position==2的item(之前position==1的时候已绑定),绑定position==3的item
        rc.setAdapter(myAdapter);
    }

    public void initData(){
        for(int i=0;i<300;i++){
            mArrayList.add("ww"+i);
//            Log.e("ww","ww");
        }
    }

    class MyAdapter extends RecyclerView.Adapter{

        @Override
        public int getItemViewType(int position) {
            return 1;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            Log.e("onCreateViewHolder","onCreateViewHolder");
            View view = View.inflate(getApplicationContext(), R.layout.item, null);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Log.e("onBindViewHolder","position:"+position);
            ((MyViewHolder)holder).titile.setText(mArrayList.get(position));
//            Log.e("onBindViewHolder","onBindViewHolder");
//            if(getItemViewType(position)==0){
//                ((MyViewHolder)holder).titile.setText(mArrayList.get(position));
//                ((MyViewHolder)holder).ll.setVisibility(View.GONE);
//            }else{
//                ((MyViewHolder)holder).titile.setVisibility(View.GONE);
//                ((MyViewHolder)holder).ll.setVisibility(View.VISIBLE);
//            }

        }

        @Override
        public int getItemCount() {
            Log.e("getItemCount","getItemCount");
            return mArrayList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder{
            public TextView titile;
            public LinearLayout ll;
            public MyViewHolder(View itemView) {
                super(itemView);
                titile= itemView.findViewById(R.id.title);
                ll= itemView.findViewById(R.id.ll);
            }
        }

    }

}
