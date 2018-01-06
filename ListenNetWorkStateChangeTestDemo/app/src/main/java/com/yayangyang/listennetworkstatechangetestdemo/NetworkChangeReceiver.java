package com.yayangyang.listennetworkstatechangetestdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Looper;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NetworkChangeReceiver extends BroadcastReceiver {

    public List<OnNetWorkChangeListener> onNetWorkChange = new ArrayList<>();
    private static NetworkChangeReceiver networkChange;

    public static NetworkChangeReceiver getInstance() {
        if (networkChange == null) {
            networkChange = new NetworkChangeReceiver();
        }
        return networkChange;
    }

    public interface OnNetWorkChangeListener {
        void onChange(String stateInfo);
    }

    /**
     * 增加网络变化监听回调对象
     * 如果设置多个回调，请务必不要设置相同名字的OnNetWorkChange对象，否则会无效
     *
     * @param onNetWorkChange 回调对象
     */
    public void setOnNetWorkChange(OnNetWorkChangeListener onNetWorkChange) {
        if (this.onNetWorkChange.contains(onNetWorkChange)) {
            return;
        }
        this.onNetWorkChange.add(onNetWorkChange);
        Log.i("网络状态", "添加一个回调。已设置：" + this.onNetWorkChange.size());
    }

    /**
     * 取消网络变化监听监听回调
     *
     * @param onNetWorkChange 回调对象
     */
    public void delOnNetWorkChange(OnNetWorkChangeListener onNetWorkChange) {
        if (this.onNetWorkChange.contains(onNetWorkChange)) {
            this.onNetWorkChange.remove(onNetWorkChange);
            Log.i("网络状态", "删除一个回调。还有：" + this.onNetWorkChange.size());
        }
    }

    /**
     * 触发网络状态监听回调
     *
     * @param stateInfo 当前网络状态
     */
    private void setChange(String stateInfo) {
        for (OnNetWorkChangeListener change : onNetWorkChange) {
            change.onChange(stateInfo);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // 这个监听wifi的打开与关闭，与wifi的连接无关
//        if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {
//            int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
//            Log.e(TAG1, "wifiState" + wifiState);
//            switch (wifiState) {
//                case WifiManager.WIFI_STATE_DISABLED:
//                    APP.getInstance().setEnablaWifi(false);
//                    break;
//                case WifiManager.WIFI_STATE_DISABLING:
//
//                    break;
//                case WifiManager.WIFI_STATE_ENABLING:
//                    break;
//                case WifiManager.WIFI_STATE_ENABLED:
//                    APP.getInstance().setEnablaWifi(true);
//                    break;
//                case WifiManager.WIFI_STATE_UNKNOWN:
//                    break;
//                default:
//                    break;
//
//
//            }
//        }
//        // 这个监听wifi的连接状态即是否连上了一个有效无线路由，当上边广播的状态是WifiManager
//        // .WIFI_STATE_DISABLING，和WIFI_STATE_DISABLED的时候，根本不会接到这个广播。
//        // 在上边广播接到广播是WifiManager.WIFI_STATE_ENABLED状态的同时也会接到这个广播，
//        // 当然刚打开wifi肯定还没有连接到有效的无线
//        if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
//            Parcelable parcelableExtra = intent
//                    .getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
//            if (null != parcelableExtra) {
//                NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
//                State state = networkInfo.getState();
//                boolean isConnected = state == State.CONNECTED;// 当然，这边可以更精确的确定状态
//                Log.e(TAG1, "isConnected" + isConnected);
//                if (isConnected) {
//                    APP.getInstance().setWifi(true);
//                } else {
//                    APP.getInstance().setWifi(false);
//                }
//            }
//        }

        String TAG="NetworkChangeReceiver";
//        boolean b = Looper.getMainLooper() == Looper.myLooper();
//        Toast.makeText(AppUtil.getContext(),b==true?"主线程":"子线程",Toast.LENGTH_LONG).show();

        // 这个监听网络连接的设置，包括wifi和移动数据的打开和关闭。.
        // 最好用的还是这个监听。wifi如果打开，关闭，以及连接上可用的连接都会接到监听。见log
        // 这个广播的最大弊端是比上边两个广播的反应要慢，如果只是要监听wifi，我觉得还是用上边两个配合比较合适
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            ConnectivityManager manager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
            if (activeNetwork != null) { // connected to the internet
                if (activeNetwork.isConnected()) {
                    if(activeNetwork.getType()==ConnectivityManager.TYPE_WIFI){
                        Log.e(TAG,activeNetwork.isAvailable()+"e");
                        setChange("WIFI");
                    }else if(activeNetwork.getType()==ConnectivityManager.TYPE_MOBILE){
                        setChange(MyNetWorkUtil.getMobileInfo(activeNetwork.getSubtype()));
                    }
                } else {
                    Log.e(TAG, "当前没有网络连接，请确保你已经打开网络 ");
                    setChange("无网络");
                }
                Log.e(TAG, "info.getTypeName()" + activeNetwork.getTypeName());
                Log.e(TAG, "getSubtypeName()" + activeNetwork.getSubtypeName());
                Log.e(TAG, "getState()" + activeNetwork.getState());
                Log.e(TAG, "getDetailedState()"
                        + activeNetwork.getDetailedState().name());
                Log.e(TAG, "getDetailedState()" + activeNetwork.getExtraInfo());
                Log.e(TAG, "getType()" + activeNetwork.getType());
            } else {   // not connected to the internet
                Log.e(TAG, "当前没有网络连接，请确保你已经打开网络 ");
                setChange("无网络");
            }


        }
    }
}
