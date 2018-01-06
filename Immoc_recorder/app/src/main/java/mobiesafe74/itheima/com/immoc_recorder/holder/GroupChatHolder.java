package mobiesafe74.itheima.com.immoc_recorder.holder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mobiesafe74.itheima.com.immoc_recorder.R;
import mobiesafe74.itheima.com.immoc_recorder.Utils.MyDatabaseUtil;
import mobiesafe74.itheima.com.immoc_recorder.Utils.UIUtils;
import mobiesafe74.itheima.com.immoc_recorder.activity.ChatActivity;
import mobiesafe74.itheima.com.immoc_recorder.activity.LoginActivity;
import mobiesafe74.itheima.com.immoc_recorder.bean.WetChat;
import mobiesafe74.itheima.com.immoc_recorder.bean.conversation;
import mobiesafe74.itheima.com.immoc_recorder.bean.groupchat;

/**
 * Created by Administrator on 2017/5/25.
 */

public class GroupChatHolder extends BaseHolder<ArrayList<conversation>> {
    @Override
    public void initData() {
        List<conversation> groupChatConversation = MyDatabaseUtil.getGroupChatConversation(LoginActivity.db);
        ArrayList<conversation> conversations = new ArrayList<>();
        conversations.addAll(groupChatConversation);
        setData(conversations);
    }

    @Override
    public View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.groupchat, null);
        RecyclerView mRecyclerView= (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return mRecyclerView;
    }

    @Override
    protected void refreshView(ArrayList<conversation> data) {
        RecyclerView rootView = (RecyclerView) getRootView();
        rootView.setAdapter(new HomeAdapter());
    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(UIUtils.getContext(), R.layout.groupchat_item, null);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(UIUtils.getContext(), ChatActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("friendName",getData().get(position).getName());//会话名称
                    bundle.putString("conversationId",getData().get(position).getConversationId());
                    bundle.putBoolean("isExistence",true);
                    ArrayList<String> arrayList = new ArrayList<String>();
                    String str=getData().get(position).getMembers();
                    Log.e("str",str);
                    String s=str.substring(1,str.length());
                    String s1=s.substring(0,s.length()-1);
                    String s2[]=s1.split(",");
                    for(int i=0;i<s2.length;i++){
                        if(i>=1){
                            s2[i]=s2[i].substring(1,s2[i].length());
                        }
                        Log.e("截取完成字符串"+i,s2[i]);
                    }
                    for (int i=0;i<s2.length;i++){
                        Log.e("s2[i]",s2[i]+"");
                        arrayList.add(s2[i]);
                    }
                    Log.e("arrayList",arrayList.toString());
                    bundle.putBoolean("isCreateGroupChat", true);
                    bundle.putStringArrayList("Members", arrayList);
                    Log.e("Members",bundle.getStringArrayList("Members").toString());
                    intent.putExtras(bundle);
                    UIUtils.getContext().startActivity(intent);
                }
            });
            if(position==0){
                holder.groupchat.setVisibility(View.VISIBLE);
            }else{
                holder.groupchat.setVisibility(View.GONE);
            }
            if(position==getItemCount()-1){
                holder.quantity.setVisibility(View.VISIBLE);
            }else{
                holder.quantity.setVisibility(View.GONE);
            }
            holder.image.setImageResource(R.drawable.ic_launcher);
            holder.name.setText(getData().get(position).getName());
            holder.quantity.setText(getItemCount()+"个群聊");
        }

        @Override
        public int getItemCount() {
            return getData().size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            public RelativeLayout root;
            public TextView groupchat;
            public ImageView image;
            public TextView name;
            public TextView quantity;

            public MyViewHolder(View view) {
                super(view);
                root= (RelativeLayout) view.findViewById(R.id.root);
                groupchat= (TextView) view.findViewById(R.id.groupchat);
                image= (ImageView) view.findViewById(R.id.image);
                name= (TextView) view.findViewById(R.id.name);
                quantity= (TextView) view.findViewById(R.id.quantity);
            }
        }
    }
}
