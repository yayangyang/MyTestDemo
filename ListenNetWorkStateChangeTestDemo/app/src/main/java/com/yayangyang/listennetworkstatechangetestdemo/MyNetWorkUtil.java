package com.yayangyang.listennetworkstatechangetestdemo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * Created by Administrator on 2017/12/27.
 */

public class MyNetWorkUtil {

    /**
     * 判断是否有网络连接(连接上wifi但不能上网仍返回true)
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断当前网络是否是wifi网络
     *
     * @param context
     * @return boolean
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    /**
     * 判断当前网络是否是移动网络
     *
     * @param context
     * @return boolean
     */
    public static boolean isMoble(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }

    /**
     * 获取当前的网络状态 ：没有网络-0：WIFI网络1：4G网络-4：3G网络-3：2G网络-2
     * 自定义
     *
     * @param context
     * @return
     */
    public static int getAPNType(Context context) {
        //结果返回值
        int netType = 0;
        //获取手机所有连接管理对象
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context
                .CONNECTIVITY_SERVICE);
        //获取NetworkInfo对象
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        //NetworkInfo对象为空 则代表没有网络
        if (networkInfo == null) {
            return netType;
        }
        //否则 NetworkInfo对象不为空 则获取该networkInfo的类型
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_WIFI) {
            //WIFI
            netType = 1;
        } else if (nType == ConnectivityManager.TYPE_MOBILE) {
            int nSubType = networkInfo.getSubtype();
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService
                    (Context.TELEPHONY_SERVICE);
            //3G   联通的3G为UMTS或HSDPA 电信的3G为EVDO
            if (nSubType == TelephonyManager.NETWORK_TYPE_LTE
                    && !telephonyManager.isNetworkRoaming()) {
                netType = 4;
            } else if (nSubType == TelephonyManager.NETWORK_TYPE_UMTS
                    || nSubType == TelephonyManager.NETWORK_TYPE_HSDPA
                    || nSubType == TelephonyManager.NETWORK_TYPE_EVDO_0
                    && !telephonyManager.isNetworkRoaming()) {
                netType = 3;
                //2G 移动和联通的2G为GPRS或EGDE，电信的2G为CDMA
            } else if (nSubType == TelephonyManager.NETWORK_TYPE_GPRS
                    || nSubType == TelephonyManager.NETWORK_TYPE_EDGE
                    || nSubType == TelephonyManager.NETWORK_TYPE_CDMA
                    && !telephonyManager.isNetworkRoaming()) {
                netType = 2;
            } else {
                netType = 2;
            }
        }
        return netType;
    }

    /**
     * 获取网络连接的类型信息
     * @param context
     * @return
     */
    public static String getNetInfo(Context context) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        StringBuffer sb = new StringBuffer();
        if(activeNetworkInfo!=null){
            if (activeNetworkInfo.isConnected()){
                if(activeNetworkInfo.getType()==ConnectivityManager.TYPE_WIFI){
                    sb.append("WIFI");
                }else if(activeNetworkInfo.getType()==ConnectivityManager.TYPE_MOBILE){
                    sb.append(getMobileInfo(activeNetworkInfo.getSubtype()));
                }
            }
        }else{
            sb.append("无网络");
        }
        return sb.toString();
    }

    /**
     * 移动网络信息
     *
     * @param type
     * @return
     */
    public static String getMobileInfo(int type) {
        String netInfo = null;
        switch (type) {
            case 0:
                netInfo = "未知";
                break;
            case 1:
                netInfo = "联通2G";
                break;
            case 2:
                netInfo = "移动2G";
                break;
            case 3:
                netInfo = "联通3G";
                break;
            case 4:
                netInfo = "电信2G";
                break;
            case 5:
                netInfo = "电信3G";
                break;
            case 6:
                netInfo = "电信3G";
                break;
            case 7:
                netInfo = "1xRTT";
                break;
            case 8:
                netInfo = "联通3G";
                break;
            case 9:
                netInfo = "HSUPA";
                break;
            case 10:
                netInfo = "HSPA";
                break;
            case 11:
                netInfo = "IDEN";
                break;
            case 12:
                netInfo = "电信3G";
                break;
            case 13:
                netInfo = "4G";
                break;
            case 14:
                netInfo = "eHRPD";
                break;
            case 15:
                netInfo = "HSPAP";
                break;

            default:
                break;
        }

        return netInfo;
    }

}
