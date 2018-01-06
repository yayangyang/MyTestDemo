package mobiesafe74.itheima.com.slidingtablayout;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager pager= (ViewPager) findViewById(R.id.pager);
        SlidingTabLayout tab= (SlidingTabLayout) findViewById(R.id.tab);

        MyAdapte adapter= new MyAdapte();
        pager.setAdapter(adapter);
        tab.setViewPager(pager);
    }

    int[] colors={0xFF123456,0xFF654321,0xFF336699};

    class MyAdapte extends PagerAdapter{
        String[] titles={"AA","BB","CC"};

        ArrayList<LinearLayout> layouts=new ArrayList<LinearLayout>();
        MyAdapte() {

            for (int i = 0; i < 3; i++) {
                LinearLayout l=new LinearLayout(MainActivity.this);
                l.setBackgroundColor(colors[i]);
                l.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
                layouts.add(l);
            }

        }

        @Override
        public int getCount() {
            return layouts.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view==o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LinearLayout l=layouts.get(position);
            container.addView(l);
            return l;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(layouts.get(position));
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
