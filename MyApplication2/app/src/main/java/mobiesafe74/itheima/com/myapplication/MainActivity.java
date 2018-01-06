package mobiesafe74.itheima.com.myapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lujing();
    }

    private void lujing() {
        LogUtils.e(Environment.getDataDirectory().getPath() );
        LogUtils.e(Environment.getDownloadCacheDirectory().getPath() );
        LogUtils.e(Environment.getExternalStorageDirectory().getPath());
        LogUtils.e(Environment.getRootDirectory().getPath() );
        LogUtils.e(getCacheDir().getPath());
        LogUtils.e(getExternalCacheDir().getPath());
        LogUtils.e(getFilesDir().getPath());
        LogUtils.e(getObbDir().getPath());
        LogUtils.e(getPackageName());
        LogUtils.e(getPackageCodePath());
        LogUtils.e(getPackageResourcePath());
    }
}
