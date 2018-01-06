package mobiesafe74.itheima.com.navigationview;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar= (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout= (DrawerLayout) findViewById(R.id.mDrawerLayout);

        toolbar.setLogo(R.drawable.button1);
        toolbar.setTitle("My Title");
        toolbar.setSubtitle("Sub title");

        setSupportActionBar(toolbar);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setItemIconTintList(null);
        //获取头布局文件
        View headerView = navigationView.getHeaderView(0);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("head","head");
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                //在这里处理item的点击事件
                if(item.getItemId()==R.id.favorite){
                    Log.e("favorite","favorite");
                }else if(item.getItemId()==R.id.wallet){
                    Log.e("wallet","wallet");
                }else if(item.getItemId()==R.id.photo){
                    Log.e("photo","photo");
                }else if(item.getItemId()==R.id.file){
                    Log.e("file","file");
                }
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if(item.getItemId()==android.R.id.home){
//            mDrawerLayout.openDrawer(Gravity.LEFT);
//        }
        return super.onOptionsItemSelected(item);
    }
}
