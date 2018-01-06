package mobiesafe74.itheima.com.immoc_recorder.holder;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import mobiesafe74.itheima.com.immoc_recorder.R;
import mobiesafe74.itheima.com.immoc_recorder.Utils.UIUtils;

/**
 * Created by Administrator on 2017/5/7.
 */

public class DialogHolder extends BaseHolder {
    @Override
    public void initData() {}

    @Override
    public View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.dialog_item, null);
        view.setTag(this);
        return view;
    }

    @Override
    protected void refreshView(Object data) {
        ((TextView)getRootView()).setText((String)data);
    }
}
