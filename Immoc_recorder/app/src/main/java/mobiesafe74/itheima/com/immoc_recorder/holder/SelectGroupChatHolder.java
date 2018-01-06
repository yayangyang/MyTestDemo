package mobiesafe74.itheima.com.immoc_recorder.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import mobiesafe74.itheima.com.immoc_recorder.R;
import mobiesafe74.itheima.com.immoc_recorder.Utils.UIUtils;

/**
 * Created by Administrator on 2017/5/22.
 */

public class SelectGroupChatHolder extends BaseHolder<ArrayList<String>>{

    @Override
    public void initData() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("设置备注及标签");
        arrayList.add("标为星标朋友");
        arrayList.add("设置朋友圈权限");
        arrayList.add("发送该名片");
        arrayList.add("投诉");
        arrayList.add("加入黑名单");
        arrayList.add("删除");
        arrayList.add("添加到桌面");
        setData(arrayList);
    }

    @Override
    public View initView() {
        ListView listView = (ListView) View.inflate(UIUtils.getContext(),R.layout.selectgroupchat_listview,null);
        return listView;
    }

    @Override
    protected void refreshView(ArrayList<String> data) {
        ((ListView)getRootView()).setAdapter(new adapter());
    }

    public class adapter extends BaseAdapter{

        @Override
        public int getCount() {
            return getData().size();
        }

        @Override
        public String getItem(int position) {
            return getData().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
//            Log.e("getView","ww"+position);
//            Log.e("getCount","ww"+getCount());
            Viewholder holder=null;
            if(convertView==null){
                holder=new Viewholder();
                convertView=View.inflate(UIUtils.getContext(),R.layout.selectgroupchat_item,null);
                holder.headPortrait= (ImageView) convertView.findViewById(R.id.headPortrait);
                holder.name= (TextView) convertView.findViewById(R.id.name);
                holder.totalNumber= (TextView) convertView.findViewById(R.id.totalNumber);
                convertView.setTag(holder);
            }else{
                holder= (Viewholder) convertView.getTag();
            }
            holder.headPortrait.setBackgroundResource(R.drawable.ic_launcher);
            holder.name.setText(getItem(position));
            holder.totalNumber.setText(getItem(position));
            return convertView;
        }
    }

    public static class Viewholder{
        public ImageView headPortrait;
        public TextView name;
        public TextView totalNumber;
    }

}
