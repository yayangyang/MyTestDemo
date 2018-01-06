package mobiesafe74.itheima.com.newcalendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.R.string.no;

/**
 * Created by Administrator on 2017/5/5.
 */

public class NewCalendar extends LinearLayout {

    private ImageView btnPrv;
    private ImageView btnNext;
    private TextView txtDate;
    private GridView mGridView;

    private Calendar mCalendar=Calendar.getInstance();
    private String displayFormat;

    public NewsCalendarListener mListener;

    public NewCalendar(Context context) {
        super(context);
    }

    public NewCalendar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initControl(context,attrs);
    }

    public NewCalendar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl(context,attrs);
    }

    private void initControl(Context context,AttributeSet attrs){
        Log.e("initControl","initControl");
        bindControl(context,attrs);
        bindControlEvent();

        TypedArray ta=getContext().obtainStyledAttributes(attrs,R.styleable.NewCalendar);
        try {
            String format=ta.getString(R.styleable.NewCalendar_dateFormat);
            displayFormat=format;
            if(displayFormat==null){
                displayFormat="MMM yyyy";
            }
        }finally {
            ta.recycle();
        }
        renderCalendar();
    }

    private void bindControl(Context context,AttributeSet attrs) {
        LayoutInflater inflater=LayoutInflater.from(context);
        inflater.inflate(R.layout.calendar_view,this);

        btnPrv= (ImageView) findViewById(R.id.btnPrev);
        btnNext= (ImageView) findViewById(R.id.btnNext);
        txtDate= (TextView) findViewById(R.id.txtDate);
        mGridView= (GridView) findViewById(R.id.calendar_grid);
    }

    private void bindControlEvent(){
        btnPrv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendar.add(Calendar.MONTH,-1);
                renderCalendar();
            }
        });

        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendar.add(Calendar.MONTH,1);
                renderCalendar();
            }
        });
    }

    private void renderCalendar() {
        Log.e("renderCalendar","renderCalendar");
        SimpleDateFormat sdf=new SimpleDateFormat(displayFormat);
        txtDate.setText(sdf.format(mCalendar.getTime()));

        ArrayList<Date> cells=new ArrayList<Date>();
        Calendar calendar = (Calendar) mCalendar.clone();

        calendar.set(Calendar.DAY_OF_MONTH,1);//设置当前日期为本月1号
//        Log.e("ww",calendar.get(Calendar.DAY_OF_WEEK)+"");
        int prevDays=calendar.get(Calendar.DAY_OF_WEEK)-1;//若为星期日则calendar.get(Calendar.DAY_OF_WEEK)返回1
        calendar.add(Calendar.DAY_OF_MONTH,-prevDays);

        int maxCellCount=6*7;
        while (cells.size()<maxCellCount){
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH,1);
        }
        mGridView.setAdapter(new CalendarAdapter(getContext(),cells));
        mGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(mListener==null){
                    return false;
                }else{
                    mListener.onItemLongPress((Date) parent.getItemAtPosition(position));
                    return true;
                }
            }
        });
    }

    private class CalendarAdapter extends ArrayAdapter<Date>{

        LayoutInflater mInflater;
        public CalendarAdapter(@NonNull Context context,ArrayList<Date> days) {
            super(context, 0,days);
            mInflater=LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Date date=getItem(position);
            if(convertView==null){
                convertView=mInflater.inflate(R.layout.calendar_text_day,parent,false);
            }
            int day=date.getDate();
            ((TextView)convertView).setText(String.valueOf(day));

            Date now=new Date();
            Boolean isTheSameMonth=false;
            if(date.getMonth()==now.getMonth()){
                isTheSameMonth=true;
            }
            if(isTheSameMonth){
                //代码设置颜色只能写8或6位颜色值
                ((TextView)convertView).setTextColor(Color.parseColor("#000000"));
            }

            if(now.getDate()==date.getDate()&&
                    now.getMonth()==date.getMonth()&&now.getYear()==date.getYear()){
                ((TextView)convertView).setTextColor(Color.parseColor("#ff0000"));
                ((Calendar_day_textView)convertView).isToday=true;
            }

            return convertView;
        }
    }

    public interface  NewsCalendarListener{
        void onItemLongPress(Date day);
    }

}
