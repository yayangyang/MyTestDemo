package mobiesafe74.itheima.com.immoc_recorder.holder;

import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import mobiesafe74.itheima.com.immoc_recorder.R;
import mobiesafe74.itheima.com.immoc_recorder.Utils.UIUtils;

import static mobiesafe74.itheima.com.immoc_recorder.R.id.Scan;
import static mobiesafe74.itheima.com.immoc_recorder.R.id.Shake;
import static mobiesafe74.itheima.com.immoc_recorder.R.id.Shopping;
import static mobiesafe74.itheima.com.immoc_recorder.R.id.rl_right;

/**
 * Created by Administrator on 2017/5/7.
 */

public class DiscoveryHolder extends BaseHolder {

    private RelativeLayout friendplace,scan,shake,nearby,bottle,shopping,game,rl_right_friendplace;
    private LinearLayout ll_right_game;

    @Override
    public void initData() {}

    @Override
    public View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.discovery, null);
        friendplace = (RelativeLayout) view.findViewById(R.id.friendplace);
        rl_right_friendplace= (RelativeLayout) friendplace.findViewById(rl_right);
        rl_right_friendplace.setVisibility(View.VISIBLE);
        scan = (RelativeLayout) view.findViewById(Scan);
        ((TextView)scan.findViewById(R.id.tv_start)).setText("扫一扫");
        shake = (RelativeLayout) view.findViewById(Shake);
        ((TextView)shake.findViewById(R.id.tv_start)).setText("摇一摇");
        nearby = (RelativeLayout) view.findViewById(R.id.nearby);
        ((TextView)nearby.findViewById(R.id.tv_start)).setText("附近的人");
        bottle = (RelativeLayout) view.findViewById(R.id.bottle);
        ((TextView)bottle.findViewById(R.id.tv_start)).setText("漂流瓶");
        shopping = (RelativeLayout) view.findViewById(Shopping);
        ((TextView)shopping.findViewById(R.id.tv_start)).setText("购物");
        game = (RelativeLayout) view.findViewById(R.id.game);
        ll_right_game= (LinearLayout) game.findViewById(R.id.ll_right);
        ll_right_game.setVisibility(View.VISIBLE);
        ((TextView)game.findViewById(R.id.tv_start)).setText("游戏");
        return view;
    }

    @Override
    protected void refreshView(Object data) {

    }
}
