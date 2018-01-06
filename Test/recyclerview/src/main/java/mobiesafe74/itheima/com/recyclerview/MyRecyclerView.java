package mobiesafe74.itheima.com.recyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;

import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2017/4/18.
 */

public class MyRecyclerView extends RecyclerView{

    private scrollListener s;
    private View mCurrentView;

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                View newView = getChildAt(0);//第一个显示的始终是它第一个child(具体实现不知)
                if (s != null)
                {
                    if (newView != null && newView != mCurrentView)
                    {
                        mCurrentView = newView ;
                        s.change(mCurrentView,
                                getChildPosition(mCurrentView));
                    }
                }
            }
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e("onLayout","onLayout");
        super.onLayout(changed, l, t, r, b);
        View newview=getChildAt(0);
        if(s!=null){
            mCurrentView=newview;
            s.change(mCurrentView,getChildPosition(mCurrentView));
        }
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        Log.e("onMeasure","onMeasure");
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);
        Log.e("onDraw","onDraw");
    }

    public interface scrollListener{
        void change(View currentView, int childPosition);
    }

    public void setScrollListener(scrollListener s){
        this.s=s;
    }
}
