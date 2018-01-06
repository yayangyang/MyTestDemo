package mobiesafe74.itheima.com.demo_recyclerview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by Administrator on 2017/10/17.
 */

public class MyRecyclerView extends RecyclerView {
    public MyRecyclerView(Context context) {//只写这个构造函数会报错(自己理解是布局初始化到对象使用的是下面的构造方法)
        super(context);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        Log.e("onMeasure","onMeasure");
        super.onMeasure(widthSpec, heightSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e("onLayout","onLayout");
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    public void onDraw(Canvas c) {
        Log.e("onDraw","onDraw");
        super.onDraw(c);
    }
}
