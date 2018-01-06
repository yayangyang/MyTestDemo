package mobiesafe74.itheima.com.newcalendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements NewCalendar.NewsCalendarListener{
    private NewCalendar newCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e("www","升水");
        newCalendar= (NewCalendar) findViewById(R.id.newCalendar);
        newCalendar.mListener=this;
    }

    @Override
    public void onItemLongPress(Date day) {
        DateFormat df= SimpleDateFormat.getInstance();
        Toast.makeText(this,df.format(day),Toast.LENGTH_LONG).show();
    }
}
