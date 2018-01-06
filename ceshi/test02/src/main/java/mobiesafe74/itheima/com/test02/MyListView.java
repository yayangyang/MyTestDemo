package mobiesafe74.itheima.com.test02;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by Administrator on 2017/4/30.
 */

public class MyListView extends ListView{

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.e("x",ev.getX()+"");
        Log.e("y",ev.getY()+"");
        return super.onTouchEvent(ev);
    }
}
