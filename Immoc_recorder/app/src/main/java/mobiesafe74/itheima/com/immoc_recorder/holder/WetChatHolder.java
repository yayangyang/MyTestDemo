package mobiesafe74.itheima.com.immoc_recorder.holder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mobiesafe74.itheima.com.immoc_recorder.R;
import mobiesafe74.itheima.com.immoc_recorder.Utils.UIUtils;
import mobiesafe74.itheima.com.immoc_recorder.Utils.sendUtil;
import mobiesafe74.itheima.com.immoc_recorder.activity.ChatActivity;
import mobiesafe74.itheima.com.immoc_recorder.activity.LoginActivity;
import mobiesafe74.itheima.com.immoc_recorder.activity.MainActivity;
import mobiesafe74.itheima.com.immoc_recorder.bean.WetChat;
import mobiesafe74.itheima.com.immoc_recorder.bean.conversation;

import static android.R.id.list;
import static com.tencent.qc.stat.StatReportStrategy.f;
import static mobiesafe74.itheima.com.immoc_recorder.activity.ChatActivity.LV_TO_LAST;

/**
 * Created by Administrator on 2017/5/7.
 */

public class WetChatHolder extends BaseHolder<ArrayList<WetChat>> {

    private ArrayList<WetChat> mData_wetchat=new ArrayList<WetChat>();
    private RecyclerView mRecyclerView;
    private HomeAdapter homeAdapter;
//    private Handler mHandler=new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            if(msg.what==LV_TO_LAST){
//                //这里的mRecyclerView可能为空,具体原因可看myeclipse的test
//                mRecyclerView.scrollToPosition(mData_wetchat.size()-1);
//            }
//        }
//    };

    @Override
    public View initView() {
        sendUtil.setupdateListener(new sendUtil.updateListener() {
            @Override
            public void updateList(List<conversation> convs) {
                mData_wetchat.clear();
                Log.e("ww","搜索"+mData_wetchat.size());
//                Log.e("convs.size()",convs.size()+"");
                if(convs.size()>0){
                    Log.e("isGroupChat搜索",convs.get(0).isGroupChat()+"");
                }
                for(int i=0;i<convs.size();i++){
                    WetChat wetchat =new WetChat();
                    wetchat.setLeftImage(convs.get(i).getHeadPortrait());
                    wetchat.setCanVisivile(true);
                    wetchat.setName(convs.get(i).getName());
                    wetchat.setMessage("搜索");
                    wetchat.setTime("00:01");
                    wetchat.setConversationId(convs.get(i).getConversationId());
                    wetchat.setMembers(convs.get(i).getMembers());
                    wetchat.setGroupChat(convs.get(i).isGroupChat());
                    mData_wetchat.add(wetchat);
                }
                homeAdapter.notifyDataSetChanged();
            }
        });
        LoginActivity.setAddOneConversationListener(new LoginActivity.AddOneConversationListener() {
            @Override
            public void AddOneConversationListener(WetChat wetChat) {
                mData_wetchat.add(wetChat);
                homeAdapter.notifyDataSetChanged();
            }
        });
        mRecyclerView=new RecyclerView(UIUtils.getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        homeAdapter = new HomeAdapter();
        mRecyclerView.setAdapter(homeAdapter);
        return mRecyclerView;
    }

    @Override
    protected void refreshView(ArrayList<WetChat> data) {
        Log.e("ww",data.size()+"搜索");
        Log.e("homeAdapter",homeAdapter+"ssssss");
        //尝试从本地获取数据
    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(View.inflate(getContext(),R.layout.wet_chat_item,null));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(getContext(), ChatActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("friendName",getData().get(position).getName());
//                    bundle.putBoolean("",getData().get(position).is"");
//                    Log.e("conversationId",mData_wetchat.get(position).getConversationId()+"www");
                    bundle.putString("conversationId",mData_wetchat.get(position).getConversationId());
                    Log.e("啥啥啥",mData_wetchat.get(position).getMembers()+"");
                    bundle.putBoolean("isExistence",true);
                    ArrayList<String> arrayList = new ArrayList<String>();
                    String str=mData_wetchat.get(position).getMembers();
                    Log.e("str",str);

                    String s=str.substring(1,str.length());
                    String s1=s.substring(0,s.length()-1);
                    String s2[]=s1.split(",");
                    //之前这里出错而导致了多次创建对话,主要是数组转化为集合时忽略了空格
                    //str若为[yayang, tom, jerry]则执行后会得到{yayang, tom, jerry}
                    //其中s2[0]等于yayang,s2[1]等于 tom,s2[2]等于 jerry
                    //之后集合toString还会在逗号前加一个空格从而导致错误
                    for(int i=0;i<s2.length;i++){
//                        s2[i].trim();
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
                    bundle.putBoolean("isCreateGroupChat", mData_wetchat.get(position).isGroupChat());
                    Log.e("WetChatHolder-group",mData_wetchat.get(position).isGroupChat()+"");
                    bundle.putStringArrayList("Members", arrayList);
                    Log.e("Members",bundle.getStringArrayList("Members").toString());
                    intent.putExtras(bundle);
                    getContext().startActivity(intent);
                    AVIMMessageManager.unregisterMessageHandler(AVIMTypedMessage.class,LoginActivity.mAVIMTypedMessageHandler);
                }
            });
            holder.leftImage.setImageResource(getData().get(position).getLeftImage());
            if(getData().get(position).isCanVisivile()){
                holder.rightImage.setVisibility(View.VISIBLE);
            }else{
                holder.rightImage.setVisibility(View.GONE);
            }
            holder.name.setText(getData().get(position).getName());
            holder.message.setText(getData().get(position).getMessage());
            holder.time.setText(getData().get(position).getTime());
        }

        @Override
        public int getItemCount() {
            return getData().size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            public RelativeLayout root;
            public ImageView leftImage;
            public ImageView rightImage;
            public TextView name;
            public TextView message;
            public TextView time;

            public MyViewHolder(View view) {
                super(view);
                root= (RelativeLayout) view.findViewById(R.id.root);
                leftImage= (ImageView) view.findViewById(R.id.image_left);
                rightImage= (ImageView) view.findViewById(R.id.image_right);
                name= (TextView) view.findViewById(R.id.name);
                message= (TextView) view.findViewById(R.id.message);
                time= (TextView) view.findViewById(R.id.time);
            }
        }
    }

    @Override
    public void initData(){
        setData(mData_wetchat);
    }

}
