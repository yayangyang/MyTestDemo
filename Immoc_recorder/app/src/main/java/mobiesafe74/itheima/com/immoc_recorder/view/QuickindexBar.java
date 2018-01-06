package mobiesafe74.itheima.com.immoc_recorder.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import mobiesafe74.itheima.com.immoc_recorder.R;

public class QuickindexBar extends View{

	private String[] indexArr = { "A", "B", "C", "D", "E", "F", "G", "H",
			"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
			"V", "W", "X", "Y", "Z" };
	private Paint paint;
	private int width;
	private float cellHeight;

	public QuickindexBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public QuickindexBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public QuickindexBar(Context context) {
		super(context);
		init();
	}

	private void init(){
		paint=new Paint(Paint.ANTI_ALIAS_FLAG);//设置抗锯齿
		paint.setColor(Color.BLACK);
		paint.setTextSize(50);
		paint.setTextAlign(Align.CENTER);//设置文本的起点是文字边框底边的中心
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//		Log.e("onSizeChanged","onSizeChanged");
		super.onSizeChanged(w, h, oldw, oldh);
		width=getMeasuredWidth();
		//得到一个格子的高度
		cellHeight=getMeasuredHeight()*1f/indexArr.length;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//这个类一般是运行6次onMeasure-onSizeChanged-onLayout-onDraw
		//即使将其布局文件的高度改为wrap_content,其高度也是填充父控件(不知原因)
//		Log.e("onMeasure","onMeasure");
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//		Log.e("onLayout","onLayout");
		super.onLayout(changed, left, top, right, bottom);
	}

	@Override
	protected void onDraw(Canvas canvas) {
//		Log.e("onDraw","onDraw");
		super.onDraw(canvas);

		for(int i=0;i<indexArr.length;i++){
			float x=width/2;
			float y=cellHeight/2+getTextHeight(indexArr[i])/2+i*cellHeight;

//			paint.setColor(lastIndex==i?Color.BLACK:Color.WHITE);

			canvas.drawText(indexArr[i], x, y, paint);
		}
	}
	private int lastIndex=-1;//记录上次触摸字母的索引
	@Override
	public boolean onTouchEvent(MotionEvent event) {
//		Log.e("event.getX", event.getX()+"");
//		Log.e("event.getY", event.getY()+"");
		if(event.getAction()==MotionEvent.ACTION_DOWN||
				event.getAction()==MotionEvent.ACTION_MOVE){
			setBackgroundColor(0x55000000);
			float y=event.getY();
			int index=(int) (y/cellHeight);//得到字母对应的索引
			if(lastIndex!=index){
				//说明当前触摸字母和上一个不是同一个字母
//				Log.e("tag", indexArr[index]);
				//对index安全性的检查
				if(index>=0&&index<indexArr.length){
					if(listener!=null){
						listener.onTouchLetter(indexArr[index]);
					}
				}
			}
			lastIndex=index;
		}else if(event.getAction()==MotionEvent.ACTION_UP){
			setBackgroundColor(0x00000000);
		}
		//引起重绘
		invalidate();
		return true;
	}

	/**
	 * 获取文本的高度
	 * @param text
	 * @return
	 */
	private int getTextHeight(String text) {
		//获取文本的高度
		Rect bounds=new Rect();
		paint.getTextBounds(text, 0, text.length(), bounds);
		return bounds.height();
	}

	private OnTouchLetterListener listener;
	public void setOnTouchLetterListener(OnTouchLetterListener listener){
		this.listener=listener;
	}
	/**
	 *	触摸字母的监听器
	 */
	public interface OnTouchLetterListener{
		void onTouchLetter(String letter);
	}
}
