package mobiesafe74.itheima.com.immoc_recorder.holder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import mobiesafe74.itheima.com.immoc_recorder.R;
import mobiesafe74.itheima.com.immoc_recorder.Utils.MyDatabaseUtil;
import mobiesafe74.itheima.com.immoc_recorder.Utils.UIUtils;
import mobiesafe74.itheima.com.immoc_recorder.activity.GroupChatActivity;
import mobiesafe74.itheima.com.immoc_recorder.activity.LoginActivity;
import mobiesafe74.itheima.com.immoc_recorder.activity.PersonalDataActivity;
import mobiesafe74.itheima.com.immoc_recorder.activity.addNewFriendActivity;
import mobiesafe74.itheima.com.immoc_recorder.bean.addressbook;
import mobiesafe74.itheima.com.immoc_recorder.bean.user;
import mobiesafe74.itheima.com.immoc_recorder.view.QuickindexBar;

import static mobiesafe74.itheima.com.immoc_recorder.activity.LoginActivity.db;

/**
 * Created by Administrator on 2017/5/7.
 */

public class AddressBookHolder extends BaseHolder<ArrayList<addressbook>> {

    private QuickindexBar quickIndexBar;
    private RecyclerView mRecyclerView;
    private TextView currentWord;
    private View view;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean move=false;
    private int mIndex=-1;
    public int specialItem=4;

    @Override
    public View initView() {
        view=View.inflate(UIUtils.getContext(),R.layout.address_book,null);
        mRecyclerView= (RecyclerView) view.findViewById(R.id.recycleview);
        mRecyclerView.addOnScrollListener(new RecyclerViewListener());
        quickIndexBar= (QuickindexBar) view.findViewById(R.id.quickIndexBar);
        currentWord= (TextView) view.findViewById(R.id.currentWord);
        quickIndexBar.setOnTouchLetterListener(new QuickindexBar.OnTouchLetterListener() {
            @Override
            public void onTouchLetter(String letter) {
//				Log.e("letter", letter+"");
                for (int i = 0; i < getData().size(); i++) {
                    if(i>=specialItem){
                        String firstWord=getData().get(i).getPinyin().charAt(0)+"";
                        if(letter.equals(firstWord)){
                            //两个都不是setSelection的效果
//                        mRecyclerView.smoothScrollToPosition(i);
//                        mRecyclerView.scrollToPosition(i);
                            Log.e("ww",i+"");
                            moveToPosition(i);
                            break;
                        }
                    }
                }
                showCurrentWord(letter);
            }
        });
        ViewHelper.setScaleX(currentWord, 0);
        ViewHelper.setScaleY(currentWord, 0);
        return view;
    }

    @Override
    protected void refreshView(ArrayList<addressbook> data) {
        Log.e("ww",data.size()+"王五");
        mLinearLayoutManager=new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(new HomeAdapter());
    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(View.inflate(getContext(),R.layout.address_book_item,null));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.name.setText(getData().get(position).getName());
            if(position>4){
                String currentWord=getData().get(position).getPinyin().charAt(0)+"";
                String lastWord=getData().get(position-1).getPinyin().charAt(0)+"";
                if(currentWord.equals(lastWord)){
                    holder.pinyin.setVisibility(View.GONE);
                }else{
                    holder.pinyin.setVisibility(View.VISIBLE);
                    holder.pinyin.setText(getData().get(position).getPinyin().charAt(0)+"");
                }
            }else if(position==4){
                holder.pinyin.setVisibility(View.VISIBLE);
                holder.pinyin.setText(getData().get(position).getPinyin().charAt(0)+"");
            }else{
                holder.pinyin.setVisibility(View.GONE);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   if(position>3){
                       Log.e("ww","对对对");
                       Intent intent = new Intent();
                       intent.setClass(getContext(), PersonalDataActivity.class);
                       Bundle bundle = new Bundle();
                       bundle.putString("username",getData().get(position).getName());
                       bundle.putBoolean("isFriend",true);
                       intent.putExtras(bundle);
                       getContext().startActivity(intent);//这里写成UIutils.getContext()在真机运行报错
                   }else{
                       Intent intent = new Intent();
                       if(position==0){//新的朋友
                           intent.setClass(getContext(),addNewFriendActivity.class);
                           Bundle bundle = new Bundle();
                           bundle.putBoolean("canVisible",true);
                           intent.putExtras(bundle);
                           getContext().startActivity(intent);
                       }else if(position==1){//群聊
                           intent.setClass(getContext(),GroupChatActivity.class);
                           getContext().startActivity(intent);
                       }else if(position==2){//标签

                       }else if(position==3){//公众号

                       }
                   }
                }
            });
        }

        @Override
        public int getItemCount() {
            return getData().size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            public ImageView image;
            public TextView name;
            public TextView pinyin;

            public MyViewHolder(View view) {
                super(view);
                image= (ImageView) view.findViewById(R.id.image);
                name= (TextView) view.findViewById(R.id.name);
                pinyin= (TextView) view.findViewById(R.id.pinyin);
            }
        }
    }

    public void initData(){
        ArrayList<addressbook> mData_addressbook=new ArrayList<addressbook>();
        List<user> user = MyDatabaseUtil.getUser(db);
        for(int i=0;i<user.size();i++){
            addressbook addressbook = new addressbook(user.get(i).getUsername());
            addressbook.setCreatedAt(user.get(i).getCreatedAt());
            addressbook.setUpdatedAt(user.get(i).getUpdatedAt());
            mData_addressbook.add(addressbook);
        }
//        mData_addressbook.add(new addressbook("李伟"));
//        mData_addressbook.add(new addressbook("张三"));
//        mData_addressbook.add(new addressbook("阿三"));
//        mData_addressbook.add(new addressbook("阿四"));
//        mData_addressbook.add(new addressbook("段誉"));
//        mData_addressbook.add(new addressbook("段正淳"));
//        mData_addressbook.add(new addressbook("张三丰"));
//        mData_addressbook.add(new addressbook("陈坤"));
//        mData_addressbook.add(new addressbook("林俊杰1"));
//        mData_addressbook.add(new addressbook("陈坤2"));
//        mData_addressbook.add(new addressbook("王二a"));
//        mData_addressbook.add(new addressbook("林俊杰a"));
//        mData_addressbook.add(new addressbook("张四"));
//        mData_addressbook.add(new addressbook("林俊杰"));
//        mData_addressbook.add(new addressbook("王二"));
//        mData_addressbook.add(new addressbook("王二b"));
//        mData_addressbook.add(new addressbook("赵四"));
//        mData_addressbook.add(new addressbook("杨坤"));
//        mData_addressbook.add(new addressbook("赵子龙"));
//        mData_addressbook.add(new addressbook("杨坤1"));
//        mData_addressbook.add(new addressbook("李伟1"));
//        mData_addressbook.add(new addressbook("宋江"));
//        mData_addressbook.add(new addressbook("宋江1"));
//        mData_addressbook.add(new addressbook("李伟3"));
        mData_addressbook.add(new addressbook(LoginActivity.myName));
        Collections.sort(mData_addressbook);
        List<addressbook> list = Arrays.asList(
                new addressbook("新的朋友"),
                new addressbook("群聊"),
                new addressbook("标签"),
                new addressbook("公众号"));
        mData_addressbook.addAll(0,list);
        setData(mData_addressbook);
    }

    private boolean isScale=false;
    private Handler handler=new Handler();
    private void showCurrentWord(String letter) {
        currentWord.setText(letter);
        if(!isScale){
            isScale=true;
            com.nineoldandroids.view.ViewPropertyAnimator.animate(currentWord).scaleX(1f)
                    .setInterpolator(new OvershootInterpolator())
                    .setDuration(450).start();
            com.nineoldandroids.view.ViewPropertyAnimator.animate(currentWord).scaleY(1f)
                    .setInterpolator(new OvershootInterpolator())
                    .setDuration(450).start();
        }

        //先移除之前的任务
        handler.removeCallbacksAndMessages(null);

        //延时隐藏currentWord
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                com.nineoldandroids.view.ViewPropertyAnimator.animate(currentWord).scaleX(0f).setDuration(450).start();
                com.nineoldandroids.view.ViewPropertyAnimator.animate(currentWord).scaleY(0f).setDuration(450).start();
                isScale=false;
            }
        }, 1500);
    }

    private void moveToPosition(int n) {
        mIndex=n;
        //先从RecyclerView的LayoutManager中获取第一项和最后一项的Position
        int firstItem = mLinearLayoutManager.findFirstVisibleItemPosition();
        int lastItem = mLinearLayoutManager.findLastVisibleItemPosition();
        //然后区分情况
        if (n <= firstItem ){
            //当要置顶的项在当前显示的第一个项的前面时
            mRecyclerView.scrollToPosition(n);
        }else if ( n <= lastItem ){
            //当要置顶的项已经在屏幕上显示时
            int top = mRecyclerView.getChildAt(n - firstItem).getTop();
            mRecyclerView.scrollBy(0, top);
        }else{
            //当要置顶的项在当前显示的最后一项的后面时
            mRecyclerView.scrollToPosition(n);
            //这里这个变量是用在RecyclerView滚动监听里面的
//            Log.e("搜索",Thread.activeCount()+"vvvv");
            move = true;
        }
    }

    class RecyclerViewListener extends RecyclerView.OnScrollListener{
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//            Log.e("onScrolled",Thread.activeCount()+"vvvv");
            super.onScrolled(recyclerView, dx, dy);
            //在这里进行第二次滚动（最后的100米！）
            if (move){
//                Log.e("move",move+"");
                move = false;
                //获取要置顶的项在当前屏幕的位置，mIndex是记录的要置顶项在RecyclerView中的位置
                int n = mIndex - mLinearLayoutManager.findFirstVisibleItemPosition();
//                Log.e("n",n+"");
                if ( 0 <= n && n < mRecyclerView.getChildCount()){
                    //获取要置顶的项顶部离RecyclerView顶部的距离
                    int top = mRecyclerView.getChildAt(n).getTop();
                    //最后的移动
                    mRecyclerView.scrollBy(0, top);
                }
            }
        }
    }

}
