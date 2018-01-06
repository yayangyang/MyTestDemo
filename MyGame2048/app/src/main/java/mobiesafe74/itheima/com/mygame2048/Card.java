package mobiesafe74.itheima.com.mygame2048;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class Card extends FrameLayout{

	private int num=0;
	private TextView label;
	
	public Card(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public Card(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public Card(Context context) {
		super(context);
		label=new TextView(getContext());
		label.setTextSize(25);
		label.setBackgroundResource(R.drawable.border_card0);
		label.setGravity(Gravity.CENTER);
		LayoutParams lp = new LayoutParams(-1,-1);
		lp.setMargins(10, 10, 0, 0);
		this.addView(label,lp);
	}
	
	public void setNum(int num){
		this.num=num;
		if(num<=0){
			label.setText("");
		}else{
			label.setText(num+"");
		}
	}

	public int getNum() {
		return num;
	}
	
	public boolean isEqual(Card c){
		return getNum()==c.getNum();
	}
	
	public void setCardColor(int num){
		if(num==0){
			label.setBackgroundResource(R.drawable.border_card0);
		}else if(num==2){
			label.setBackgroundResource(R.drawable.border_card0002);
		}else if(num==4){
			label.setBackgroundResource(R.drawable.border_card0004);
		}else if(num==8){
			label.setBackgroundResource(R.drawable.border_card0008);
		}else if(num==16){
			label.setBackgroundResource(R.drawable.border_card0016);
		}else if(num==32){
			label.setBackgroundResource(R.drawable.border_card0032);
		}else if(num==64){
			label.setBackgroundResource(R.drawable.border_card0064);
		}else if(num==128){
			label.setBackgroundResource(R.drawable.border_card0128);
		}else if(num==256){
			label.setBackgroundResource(R.drawable.border_card0256);
		}else if(num==512){
			label.setBackgroundResource(R.drawable.border_card0512);
		}else if(num==1024){
			label.setBackgroundResource(R.drawable.border_card1024);
		}else if(num==2048){
			label.setBackgroundResource(R.drawable.border_card2048);
		}else if(num==4096){
			label.setBackgroundResource(R.drawable.border_card4096);
		}else if(num==8192){
			label.setBackgroundResource(R.drawable.border_card8192);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		Log.e("Card","onMeasure");
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		Log.e("Card","onLayout");
		super.onLayout(changed, left, top, right, bottom);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Log.e("Card","onDraw");
		super.onDraw(canvas);
	}
}
