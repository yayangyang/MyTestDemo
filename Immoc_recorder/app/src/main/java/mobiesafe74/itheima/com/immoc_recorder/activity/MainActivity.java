package mobiesafe74.itheima.com.immoc_recorder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.AVIMTypedMessageHandler;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import mobiesafe74.itheima.com.immoc_recorder.R;
import mobiesafe74.itheima.com.immoc_recorder.Utils.GsonUtil;
import mobiesafe74.itheima.com.immoc_recorder.Utils.MyDatabaseUtil;
import mobiesafe74.itheima.com.immoc_recorder.Utils.sendUtil;
import mobiesafe74.itheima.com.immoc_recorder.bean.Recorder;
import mobiesafe74.itheima.com.immoc_recorder.bean.WetChat;
import mobiesafe74.itheima.com.immoc_recorder.bean.receiveMessage;
import mobiesafe74.itheima.com.immoc_recorder.holder.AddressBookHolder;
import mobiesafe74.itheima.com.immoc_recorder.holder.BaseHolder;
import mobiesafe74.itheima.com.immoc_recorder.holder.DiscoveryHolder;
import mobiesafe74.itheima.com.immoc_recorder.holder.MySelfHolder;
import mobiesafe74.itheima.com.immoc_recorder.holder.WetChatHolder;
import mobiesafe74.itheima.com.immoc_recorder.manager.MediaManager;

import static com.tencent.qc.stat.StatConfig.f;
import static mobiesafe74.itheima.com.immoc_recorder.activity.ChatActivity.LV_TO_LAST;
import static mobiesafe74.itheima.com.immoc_recorder.activity.ChatActivity.objectId;
import static mobiesafe74.itheima.com.immoc_recorder.activity.LoginActivity.db;
import static mobiesafe74.itheima.com.immoc_recorder.activity.LoginActivity.mAVIMTypedMessageHandler;
import static mobiesafe74.itheima.com.immoc_recorder.activity.LoginActivity.myName;
import static mobiesafe74.itheima.com.immoc_recorder.activity.LoginActivity.sMainActivity;

/**
 * Created by Administrator on 2017/5/7.
 */

public class MainActivity extends BaseActivity {

    private ViewPager viewpager;
    private RadioGroup mRadioGroup;

    private ArrayList<BaseHolder> page;

    private MyAdapter mMyAdapter;
    private Toolbar mToolbar;

    public static int rb_position=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Log.e("onCreate","onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weichat);

//        // 测试 SDK 是否正常工作的代码
//        AVObject testObject = new AVObject("TestObject");
//        testObject.put("words","Hello World!");
//        testObject.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(AVException e) {
//                if(e == null){
//                    Log.d("saved","success!");
//                }
//            }
//        });


        init();
        initUI();
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        page.get(0).initData();
//        sendUtil.getConversation(20);
    }

    private void init() {
        page=new ArrayList<BaseHolder>();
        WetChatHolder wetChatHolder = new WetChatHolder();
        wetChatHolder.setContext(MainActivity.this);
        page.add(wetChatHolder);

        AddressBookHolder addressBookHolder = new AddressBookHolder();
        addressBookHolder.setContext(MainActivity.this);
        page.add(addressBookHolder);

        DiscoveryHolder discoveryHolder = new DiscoveryHolder();
        discoveryHolder.setContext(MainActivity.this);
        page.add(discoveryHolder);

        MySelfHolder mySelfHolder = new MySelfHolder();
        mySelfHolder.setContext(MainActivity.this);
        page.add(mySelfHolder);
    }

    private void initUI() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        viewpager= (ViewPager) findViewById(R.id.viewpager);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.e("ww","是啥啥啥222222");
            }

            @Override
            public void onPageSelected(int position) {
                Log.e("ww","是啥啥啥");
                BaseHolder holder=page.get(position);
                holder.initData();
                rb_position=position;
                if(position==0){
                    mRadioGroup.check(R.id.wet_chat);
                }else if(position==1){
                    mRadioGroup.check(R.id.address_book);
                }else if(position==2){
                    mRadioGroup.check(R.id.discovery);
                }else if(position==3){
                    mRadioGroup.check(R.id.myself);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
        mMyAdapter=new MyAdapter();
        viewpager.setAdapter(mMyAdapter);
        mRadioGroup= (RadioGroup) findViewById(R.id.radiogroup);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                Log.e("选中改变","选中改变");
                if(checkedId==R.id.wet_chat){
                    viewpager.setCurrentItem(0);
                }else if(checkedId==R.id.address_book){
                    viewpager.setCurrentItem(1);
                }else if(checkedId==R.id.discovery){
                    viewpager.setCurrentItem(2);
                }else if(checkedId==R.id.myself){
                    viewpager.setCurrentItem(3);
                }
            }
        });
    }

    class MyAdapter extends PagerAdapter{

        @Override
        public int getCount() {
//            Log.e("page.size()",page.size()+"");
            return page.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
//            Log.e("position",position+"创建");
            BaseHolder holder=page.get(position);
            View view=holder.getRootView();
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            Log.e("position",position+"删除");
            container.removeView((View)object);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        Log.e("onCreateOptionsMenu","加载菜单布局");//运行在Resume方法之后
        getMenuInflater().inflate(R.menu.main, menu);//加载菜单布局
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.sousuo){
            Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
        }else if(item.getItemId()==R.id.groupchat){
            Toast.makeText(this, "发起群聊", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,InitiateGroupChatActivity.class);
            startActivity(intent);
        }else if(item.getItemId()==R.id.addfriend){
            Toast.makeText(this, "添加朋友", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,addNewFriendActivity.class);
            Bundle bundle = new Bundle();
            bundle.putBoolean("canVisible",false);
            intent.putExtras(bundle);
            startActivity(intent);
        }else if(item.getItemId()==R.id.scan){
            Toast.makeText(this, "扫一扫", Toast.LENGTH_SHORT).show();
        }else if(item.getItemId()==R.id.pay){
            Toast.makeText(this, "收付款", Toast.LENGTH_SHORT).show();
        }else if(item.getItemId()==R.id.help){
            Toast.makeText(this, "帮助与反馈", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        Log.e("MainActivity","onStart");
        super.onStart();
        LoginActivity.sMainActivity=this;
    }

    @Override
    protected void onResume() {
        Log.e("MainActivity","onResume");
        super.onResume();
        LoginActivity.isEnterMain=true;
        sendUtil.getConversation(20);//onCreate-onStart-onResume
        if(ChatActivity.isEnterChat&&!LoginActivity.isFirstEnterMain){
            viewpager.setCurrentItem(0);//进入聊天界面退出后默认选中主界面第一个view;
            ChatActivity.isEnterChat=false;
//          尽管多次注册不会收到多条消息(但是不知为什么有时会报空指针),但是还是保持只注册一个处理相同的逻辑较好
            //后来改成LoginActivity都是同一个handler(也就是LoginActivity没取消注册)
            AVIMMessageManager.registerMessageHandler(AVIMTypedMessage.class,LoginActivity.mAVIMTypedMessageHandler);
        }else if(!ChatActivity.isEnterChat&&!LoginActivity.isFirstEnterMain){
            Log.e("www","少时诵诗书");
            if(rb_position!=0){
                page.get(rb_position).initData();
            }
        }
        LoginActivity.isFirstEnterMain=false;
    }

    @Override
    protected void onPause() {
        Log.e("MainActivity","onPause");//执行onDestroy之前会执行onPause
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
//        Log.e("client",sendUtil.client+"ww");
        sendUtil.client.close(new AVIMClientCallback() {//断开连接回调有时没效果
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                if(e==null){
                    Log.e("退出登录成功","退出登录成功");
                }else{
                    Log.e("退出登录失败",e.getMessage());
                }
            }
        });
    }

}
