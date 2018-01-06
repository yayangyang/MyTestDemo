package mobiesafe74.itheima.com.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static mobiesafe74.itheima.com.recyclerview.R.drawable.h;

/**
 * Created by Administrator on 2017/4/18.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

//    private Context context;
    private List<Integer> data;
    private LayoutInflater mInflater;
    private clickListener c;

    public MyAdapter(Context context, List<Integer> data) {
//        this.context=context;
        this.data=data;
        mInflater=LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = View.inflate(context, R.layout.item,null);
//        MyAdapter.ViewHolder holder=new ViewHolder(view);
//        return holder;
        View view = mInflater.inflate(R.layout.item,
                parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.image01 = (ImageView) view
                .findViewById(R.id.image01);
        viewHolder.tv01= (TextView) view
                .findViewById(R.id.tv01);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.image01.setImageResource(data.get(position));
        if(c!=null){
            holder.image01.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    c.click(holder.itemView,position);
                }
            });
        }
        holder.tv01.setText(position+"");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView image01;
        public TextView tv01;
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface clickListener{
        void click(View view,int position);
    }

    public void setClickListener(clickListener c){
        this.c=c;
    }
}
