package mobiesafe74.itheima.com.test02;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/30.
 */

public class MyAdapter extends BaseAdapter{
    private Context mContext;
    private List<String> mStringList=new ArrayList<String>();
    MyAdapter(Context context){
        mContext=context;
        for(int i=0;i<100;i++){
                mStringList.add("ww");
        }
    }

    @Override
    public int getCount() {
        return mStringList.size();
    }

    @Override
    public String getItem(int position) {
        return mStringList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            holder=new ViewHolder();
            convertView=View.inflate(mContext,R.layout.item,null);
            convertView.setTag(holder);
            holder.tv01= (TextView) convertView.findViewById(R.id.tv01);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.tv01.setText(mStringList.get(position));
        return convertView;
    }

    private static class ViewHolder{
        public TextView tv01;
    }
}
